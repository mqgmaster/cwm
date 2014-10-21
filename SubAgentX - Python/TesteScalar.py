import sys, os, signal
import optparse
import pprint

# Make sure we use the local copy, not a system-wide one
sys.path.insert(0, os.path.dirname(os.getcwd()))
import netsnmpagent

prgname = sys.argv[0]

# Process command line arguments
parser = optparse.OptionParser()
parser.add_option(
    "-m",
    "--mastersocket",
    dest="mastersocket",
    help="Sets the transport specification for the master agent's AgentX socket",
    default="/var/agentx/master"
)
parser.add_option(
    "-p",
    "--persistencedir",
    dest="persistencedir",
    help="Sets the path to the persistence directory",
    default="/var/lib/net-snmp"
)
(options, args) = parser.parse_args()

# Get terminal width for usage with pprint
rows,columns = os.popen("stty size", "r").read().split()

# First, create an instance of the netsnmpAgent class. We specify the
# fully-qulified path to MIBS ourselves ourselves, so that you don't 
# have to copy the MIB to /usr/share/snmp/mibs

try:
    agent = netsnmpagent.netsnmpAgent (
        AgentName      = "Simple Agent",
        MasterSocket   = options.mastersocket,
        PersistenceDir = options.persistencedir,
        MIBFiles       = [os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/BASE-MIB.txt",os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/MINHAMIB-MIB.txt"]
        )
except netsnmpagent.netsnmpAgentException as e:
    print "{0}: {1}".format(prgname, e)
    sys.exit(1)

# Then we create all SNMP scalar variables we're willing to serve
infoAlunos = agent.OctetString (
    oidstr   = "MINHAMIB-MIB::infoAlunos",
    initval  = "Daniel & Mauricio"
    )

# Finally, we tell the agent to "start". This actually connects the agent
# to the master agent
try:
    agent.start()
except netsnmpagent.netsnmpAgentException as e:
    print "{0}: {1}".format(prgname, e)
    sys.exit(1)

print "{0}: AgentX connection to snmpd establhised.".format(prgname)

# Helper function that dumps the state of all registered SNMP variables
def DumpRegistered():
    for context in agent.getContexts():
        print "{0}: Registered SNMP objects in Context \"{1}\": ".format(prgname, context)
        vars = agent.getRegistered(context)
        pprint.pprint(vars, width=columns)
        print
DumpRegistered()

# Install a signal handler that terminates our simple agent when
# CTRL-C is pressed or a KILL signal is received
def TermHandler(signum, frame):
    global loop
    loop = False
signal.signal(signal.SIGINT, TermHandler)
signal.signal(signal.SIGTERM, TermHandler)

# Install a signal handler that dumps the state of all registered values
# when SIGHUP is received
def HupHandler(signum, frame):
    DumpRegistered()
signal.signal(signal.SIGHUP, HupHandler)

# The simple agent's main loop. We loop endlessly until our signal
# handler above changes the "loop" variable.
print "{0}: Serving SNMP requests, send SIGHUP to dump SNMP object state, press ^C to terminate...".format(prgname)

loop = True
while (loop):
    # Block and process SNMP requests, if available
    agent.check_and_process()

print "{0}: Terminating.".format(prgname)
agent.shutdown()