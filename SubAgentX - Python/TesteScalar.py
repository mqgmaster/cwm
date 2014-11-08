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
        MIBFiles       = [os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/BASE-MIB.txt",os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/CONDOMINIO-MIB.txt"]
        )
except netsnmpagent.netsnmpAgentException as e:
    print "{0}: {1}".format(prgname, e)
    sys.exit(1)

# Then we create all SNMP scalar variables we're willing to serve

#################
### Apartment
#################
tbApartments = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbApartments",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.Integer32(0)),
            (3, agent.DisplayString("No Owner")),
            (4, agent.Integer32(0)),
            (5, agent.Integer32(0))
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::apCount"
        )    
    )


#################
### Condominium
#################
cmName = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::cmName",
    initVal = "Condominio"
    )

cmAddress = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::cmAddress",
    initVal = "Rua Inventada, 00 - Bairro Imaginario"
    )

cmManager = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::cmManager",
    initVal  = "Sindico Contratado",
    writable = True
    )

cmUPeople = agent.Integer32 (
    oidstr  = "CONDOMINIO-MIB::cmUPeople",
    initVal = 0
    )


#################
### Employee
#################
tbEmployee = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbEmployee",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.DisplayString("No name")),
            (3, agent.DisplayString("No role")),
            (4, agent.Integer32(0)),
            (5, agent.Integer32(0)),
            (6, agent.DisplayString("False"))
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::epCount"
        )    
    )


#################
### Garage
#################
tbGarage = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbGarage",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.Integer32(0)),
            (3, agent.Integer32(0)),
            (4, agent.DisplayString("False")),
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::ggCount"
        )    
    )


#################
### Water
#################
tbApWater = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbApWater",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.Integer32(0)),
            (3, agent.Integer32(0)),
            (4, agent.Integer32(0)),
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::wtApCount"
        )    
    )

wtIConsumption = agent.Integer32 (
    oidstr  = "CONDOMINIO-MIB::wtIConsumption",
    initVal = 0
    )

wtIConsuptionLimit = agent.Integer32 (
    oidstr   = "CONDOMINIO-MIB::wtIConsuptionLimit",
    initVal  = 0,
    writable = True
    )

wtAConsumption = agent.Counter32 (
    oidstr   = "CONDOMINIO-MIB::wtAConsumption",
    initVal  = 0,
    )

wtAConsumptionLimit = agent.Integer32 (
    oidstr   = "CONDOMINIO-MIB::wtAConsumptionLimit",
    initVal  = 0,
    writable = True
    )


#################
### Electricity
#################
tbApElectricity = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbApElectricity",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.Integer32(0)),
            (3, agent.Integer32(0)),
            (4, agent.Integer32(0)),
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::elApCount"
        )    
    )

tbBulbElectricity = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbBulbElectricity",
    indexes = [agent.Integer32()],
    columns =
        [
            (2, agent.Integer32(0)),
            (3, agent.DisplayString("None")),
            (4, agent.Integer32(0)),
        ]
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::elBulbCounter"
        )    
    )

elConsumption = agent.Integer32 (
    oidstr  = "CONDOMINIO-MIB::elConsumption",
    initVal = 0
    )

elConsumptionLimit = agent.Integer32 (
    oidstr   = "CONDOMINIO-MIB::elConsumptionLimit",
    initVal  = 0,
    writable = True
    )


#################
### Table's rows
#################
tbTesteLinha1 = tbTeste.addRow([agent.Integer32(1)])                  # Adiciona o valor do indice (coluna 1)
tbTesteLinha1.setRowCell(2, agent.OctetString("A"))                   # Adiciona o valor para a segunda coluna
tbTesteLinha1.setRowCell(3, agent.OctetString("Primeira vogal"))      # Adiciona o valor para a terceira coluna
tbTesteLinha1.setRowCell(4, agent.Integer32(1))                       # Adiciona o valor para a quarta coluna

tbTesteLinha2 = tbTeste.addRow([agent.Integer32(2)])
tbTesteLinha2.setRowCell(2, agent.OctetString("E"))
tbTesteLinha2.setRowCell(3, agent.OctetString("Segunda vogal"))
tbTesteLinha2.setRowCell(4, agent.Integer32(5))

tbTesteLinha3 = tbTeste.addRow([agent.Integer32(3)])
tbTesteLinha3.setRowCell(2, agent.OctetString("I"))
tbTesteLinha3.setRowCell(3, agent.OctetString("Terceira vogal"))
tbTesteLinha3.setRowCell(4, agent.Integer32(9))

tbTesteLinha4 = tbTeste.addRow([agent.Integer32(4)])
tbTesteLinha4.setRowCell(2, agent.OctetString("O"))
tbTesteLinha4.setRowCell(3, agent.OctetString("Quarta vogal"))
tbTesteLinha4.setRowCell(4, agent.Integer32(15))

tbTesteLinha5 = tbTeste.addRow([agent.Integer32(5)])
tbTesteLinha5.setRowCell(2, agent.OctetString("U"))
tbTesteLinha5.setRowCell(3, agent.OctetString("Quinta vogal"))
tbTesteLinha5.setRowCell(4, agent.Integer32(21)) 


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