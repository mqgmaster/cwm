import sys, os, signal, threading, time
import optparse
import pprint

# Make sure we use the local copy, not a system-wide one
sys.path.insert(0, os.path.dirname(os.getcwd()))
import netsnmpagent
import MySQLdb as mdb

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
        MIBFiles       = [os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/BASE-MIB.txt", os.path.abspath(os.path.dirname(sys.argv[0])) + "/mibs/CONDOMINIO-MIB.txt"]
        )
except netsnmpagent.netsnmpAgentException as e:
    print "{0}: {1}".format(prgname, e)
    sys.exit(1)


# DB INFORMATION
# Connection
DB_HOST = '127.0.0.1'
DB_USER = 'root'
DB_PASS = ''
DB_BASE = 'condominium'
DB_PORT = 3306

# Apartment Columns
AP_ID     = 'id'
AP_NUMBER ='number'
AP_OWNER  = 'owner_name'
AP_ROOMS  = 'num_rooms'
AP_PEOPLE = 'num_people'
AP_SECTOR = 'sector_id'

# Water Columns
WT_NUMBER        = 'number'
WT_LIMIT_TOTAL   = 'total_water_limit'
WT_USAGE_TOTAL   = 'total_water_usage'
WT_USAGE_INSTANT = 'instant_water_usage'

# Electricity Columns
EL_NUMBER        = 'number'
EL_LIMIT_TOTAL   = 'total_electric_limit'
EL_USAGE_TOTAL   = 'total_electric_usage'
EL_USAGE_INSTANT = 'instant_electric_usage'







# Then we create all SNMP scalar variables we're willing to serve

#################
### Apartment
#################
tbApartments = agent.Table (
    oidstr  = "CONDOMINIO-MIB::tbApartments",
    indexes = [
        agent.Integer32()
    ],
    columns = [
        (2, agent.Integer32(0)),
        (3, agent.DisplayString("No Owner")),
        (4, agent.Integer32(0)),
        (5, agent.Integer32(0)),
        (6, agnet.Integer32(0))
    ],
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::apCount"
    )    
)


#################
### Condominium
#################
cmName = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::cmName",
    initval = "Condominio"
    )

cmAddress = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::cmAddress",
    initval = "Rua Inventada, 00 - Bairro Imaginario"
    )

cmManager = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::cmManager",
    initval  = "Sindico Contratado",
    writable = True
    )

cmUPeople = agent.Integer32 (
    oidstr  = "CONDOMINIO-MIB::cmUPeople",
    initval = 0
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
        ],
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
            (4, agent.Integer32(0)),
        ],
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
            (3, agent.DisplayString("None")),
            (4, agent.DisplayString("None")),
        ],
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::wtApCount"
        )    
    )

wtIConsumption = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::wtIConsumption",
    initval = "0"
    )

wtIConsuptionLimit = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::wtIConsuptionLimit",
    initval  = "0",
    writable = True
    )

wtAConsumption = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::wtAConsumption",
    initval  = "0",
    )

wtAConsumptionLimit = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::wtAConsumptionLimit",
    initval  = "0",
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
            (3, agent.DisplayString("None")),
            (4, agent.DisplayString("None")),
        ],
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
            (3, agent.Integer32(0)),
            (4, agent.Integer32(0)),
        ],
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::elBulbCounter"
        )    
    )

elConsumption = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::elConsumption",
    initval = "0"
    )

elConsumptionLimit = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::elConsumptionLimit",
    initval  = "0",
    writable = True
    )


# Add all necessary tables
# Apartment table
ap162 = tbApartments.addRow([agent.Integer32(162)]) # Index
ap162.setRowCell(2, agent.Integer32(101))           # Number
ap162.setRowCell(3, agent.DisplayString("None"))    # Owner
ap162.setRowCell(4, agent.Integer32(0))             # Rooms
ap162.setRowCell(5, agent.Integer32(0))             # People

ap163 = tbApartments.addRow([agent.Integer32(163)]) # Index
ap163.setRowCell(2, agent.Integer32(102))           # Number
ap163.setRowCell(3, agent.DisplayString("None"))    # Owner
ap163.setRowCell(4, agent.Integer32(0))             # Rooms
ap163.setRowCell(5, agent.Integer32(0))             # People

ap164 = tbApartments.addRow([agent.Integer32(164)])
ap164.setRowCell(2, agent.Integer32(103))
ap164.setRowCell(3, agent.DisplayString("None"))    # Owner
ap164.setRowCell(4, agent.Integer32(0))             # Rooms
ap164.setRowCell(5, agent.Integer32(0))             # People


ap165 = tbApartments.addRow([agent.Integer32(165)])
ap165.setRowCell(2, agent.Integer32(104))
ap165.setRowCell(3, agent.DisplayString("None"))    # Owner
ap165.setRowCell(4, agent.Integer32(0))             # Rooms
ap165.setRowCell(5, agent.Integer32(0))             # People

ap166 = tbApartments.addRow([agent.Integer32(166)])
ap166.setRowCell(2, agent.Integer32(201))
ap166.setRowCell(3, agent.DisplayString("None"))    # Owner
ap166.setRowCell(4, agent.Integer32(0))             # Rooms
ap166.setRowCell(5, agent.Integer32(0))             # People

ap167 = tbApartments.addRow([agent.Integer32(167)])
ap167.setRowCell(2, agent.Integer32(202))
ap167.setRowCell(3, agent.DisplayString("None"))    # Owner
ap167.setRowCell(4, agent.Integer32(0))             # Rooms
ap167.setRowCell(5, agent.Integer32(0))             # People

ap168 = tbApartments.addRow([agent.Integer32(168)])
ap168.setRowCell(2, agent.Integer32(203))
ap168.setRowCell(3, agent.DisplayString("None"))    # Owner
ap168.setRowCell(4, agent.Integer32(0))             # Rooms
ap168.setRowCell(5, agent.Integer32(0))             # People

ap169 = tbApartments.addRow([agent.Integer32(169)])
ap169.setRowCell(2, agent.Integer32(204))
ap169.setRowCell(3, agent.DisplayString("None"))    # Owner
ap169.setRowCell(4, agent.Integer32(0))             # Rooms
ap169.setRowCell(5, agent.Integer32(0))             # People

# Employee table
emp46 = tbEmployee.addRow([agent.Integer32(46)])    # Index
emp46.setRowCell(2, agent.DisplayString("None"))       # Name
emp46.setRowCell(3, agent.DisplayString("None"))       # Role
emp46.setRowCell(4, agent.Integer32(0))                # Wage
emp46.setRowCell(5, agent.Integer32(0))                # Workload
emp46.setRowCell(6, agent.DisplayString("False"))      # Working

emp47 = tbEmployee.addRow([agent.Integer32(47)])    # Index
emp47.setRowCell(2, agent.DisplayString("None"))       # Name
emp47.setRowCell(3, agent.DisplayString("None"))       # Role
emp47.setRowCell(4, agent.Integer32(0))                # Wage
emp47.setRowCell(5, agent.Integer32(0))                # Workload
emp47.setRowCell(6, agent.DisplayString("False"))      # Working

emp48 = tbEmployee.addRow([agent.Integer32(48)])    # Index
emp48.setRowCell(2, agent.DisplayString("None"))        # Name
emp48.setRowCell(3, agent.DisplayString("None"))        # Role
emp48.setRowCell(4, agent.Integer32(0))                 # Wage
emp48.setRowCell(5, agent.Integer32(0))                 # Workload
emp48.setRowCell(6, agent.DisplayString("False"))       # Working

# Garage table
gar31 = tbGarage.addRow([agent.Integer32(31)])      # Index
gar31.setRowCell(2, agent.Integer32(0))             # Number
gar31.setRowCell(3, agent.Integer32(0))             # Ap Number
gar31.setRowCell(4, agent.Integer32(0))              # Occupied

gar32 = tbGarage.addRow([agent.Integer32(32)])      # Index
gar32.setRowCell(2, agent.Integer32(0))             # Number
gar32.setRowCell(3, agent.Integer32(0))             # Ap Number
gar32.setRowCell(4, agent.Integer32(0))              # Occupied


# Water consumption table
wc162 = tbApartments.addRow([agent.Integer32(162)]) # Index
wc162.setRowCell(2, agent.Integer32(101))           # Ap Number
wc162.setRowCell(3, agent.DisplayString("None"))             # Consumption
wc162.setRowCell(4, agent.DisplayString("None"))             # Limit

wc163 = tbApartments.addRow([agent.Integer32(163)]) # 
wc163.setRowCell(2, agent.Integer32(102))           # 
wc163.setRowCell(3, agent.DisplayString("None"))             # 
wc163.setRowCell(4, agent.DisplayString("None"))             # 

wc164 = tbApartments.addRow([agent.Integer32(164)])
wc164.setRowCell(2, agent.Integer32(103))
wc164.setRowCell(3, agent.DisplayString("None"))             # 
wc164.setRowCell(4, agent.DisplayString("None"))             # 

wc165 = tbApartments.addRow([agent.Integer32(165)])
wc165.setRowCell(2, agent.Integer32(104))
wc165.setRowCell(3, agent.DisplayString("None"))             # 
wc165.setRowCell(4, agent.DisplayString("None"))             # 

wc166 = tbApartments.addRow([agent.Integer32(166)])
wc166.setRowCell(2, agent.Integer32(201))
wc166.setRowCell(3, agent.DisplayString("None"))             # 
wc166.setRowCell(4, agent.DisplayString("None"))             # 

wc167 = tbApartments.addRow([agent.Integer32(167)])
wc167.setRowCell(2, agent.Integer32(202))
wc167.setRowCell(3, agent.DisplayString("None"))             # 
wc167.setRowCell(4, agent.DisplayString("None"))             # 

wc168 = tbApartments.addRow([agent.Integer32(168)])
wc168.setRowCell(2, agent.Integer32(203))
wc168.setRowCell(3, agent.DisplayString("None"))             # 
wc168.setRowCell(4, agent.DisplayString("None"))             # 

wc169 = tbApartments.addRow([agent.Integer32(169)])
wc169.setRowCell(2, agent.Integer32(204))
wc169.setRowCell(3, agent.DisplayString("None"))             # 
wc169.setRowCell(4, agent.DisplayString("None"))             # 

# Electricity consumption table

# Water consumption table
el162 = tbApartments.addRow([agent.Integer32(162)])          # Index
el162.setRowCell(2, agent.Integer32(101))                    # Ap Number
el162.setRowCell(3, agent.DisplayString("None"))             # Consumption
el162.setRowCell(4, agent.DisplayString("None"))             # Limit

el163 = tbApartments.addRow([agent.Integer32(163)]) # 
el163.setRowCell(2, agent.Integer32(102))           # 
el163.setRowCell(3, agent.DisplayString("None"))             # 
el163.setRowCell(4, agent.DisplayString("None"))             # 

el164 = tbApartments.addRow([agent.Integer32(164)])
el164.setRowCell(2, agent.Integer32(103))
el164.setRowCell(3, agent.DisplayString("None"))             # 
el164.setRowCell(4, agent.DisplayString("None"))             # 

el165 = tbApartments.addRow([agent.Integer32(165)])
el165.setRowCell(2, agent.Integer32(104))
el165.setRowCell(3, agent.DisplayString("None"))             # 
el165.setRowCell(4, agent.DisplayString("None"))             # 

el166 = tbApartments.addRow([agent.Integer32(166)])
el166.setRowCell(2, agent.Integer32(201))
el166.setRowCell(3, agent.DisplayString("None"))             # 
el166.setRowCell(4, agent.DisplayString("None"))             # 

el167 = tbApartments.addRow([agent.Integer32(167)])
el167.setRowCell(2, agent.Integer32(202))
el167.setRowCell(3, agent.DisplayString("None"))             # 
el167.setRowCell(4, agent.DisplayString("None"))             # 

el168 = tbApartments.addRow([agent.Integer32(168)])
el168.setRowCell(2, agent.Integer32(203))
el168.setRowCell(3, agent.DisplayString("None"))             # 
el168.setRowCell(4, agent.DisplayString("None"))             # 

el169 = tbApartments.addRow([agent.Integer32(169)])
el169.setRowCell(2, agent.Integer32(204))
el169.setRowCell(3, agent.DisplayString("None"))             # 
el169.setRowCell(4, agent.DisplayString("None"))             # 


# Lamp electricity consumption table
lp30 = tbBulbElectricity.addRow([agent.Integer32(30)])  # Index
lp30.setRowCell(2, agent.Integer32(0))         # Sector
lp30.setRowCell(3, agent.Integer32(0))                  # Number
lp30.setRowCell(4, agent.Integer32(0))                  # Status

lp31 = tbBulbElectricity.addRow([agent.Integer32(31)])  # Index
lp31.setRowCell(2, agent.Integer32(0))         # Sector
lp31.setRowCell(3, agent.Integer32(0))                  # Number
lp31.setRowCell(4, agent.Integer32(0))                  # Status

lp32 = tbBulbElectricity.addRow([agent.Integer32(32)])  # Index
lp32.setRowCell(2, agent.Integer32(0))         # Sector
lp32.setRowCell(3, agent.Integer32(0))                  # Number
lp32.setRowCell(4, agent.Integer32(0))                  # Status

lp33 = tbBulbElectricity.addRow([agent.Integer32(33)])   # Index
lp33.setRowCell(2, agent.Integer32(0)) # Sector
lp33.setRowCell(3, agent.Integer32(0))                   # Number
lp33.setRowCell(4, agent.Integer32(0))                   # Status

# Register in a new Condominium
def RegisterBasicCondominium():
    try:
        dbConnection = mdb.connect(
            host   ='127.0.0.1', 
            user   ='root', 
            passwd ='', 
            db     = 'condominium',
            port   = 3306)

        with dbConnection:

            cursor = dbConnection.cursor(mdb.cursors.DictCursor)
            cursor.execute("SELECT * FROM condominium")

            row = cursor.fetchone()

            # Condominium name
            cmName.update(row['name'])
            # Manager name
            cmManager.update(row['manager_name'])
            # Condominium address
            cmAddress.update(row['address'])
            # Uknown people
            cmUPeople.update(row['num_unknown_people'])

            # Condominium instant water consumption
            wtIConsumption.update(row['water_consumption'])
            # Condominium instant water consuption limit
            wtIConsuptionLimit.update(row['water_cons_limit'])

            # Condominium instant electricity consuption
            elConsumption.update(row['light_consumption'])
            # Condominium instant electricity consuption limit
            elConsumptionLimit.update(row['light_cons_limit'])


    except mdb.Error, e:
        print "Error %d: %s" %(e.args[0], e.args[1])
        sys.exit(1)

    finally:
        if dbConnection:
            dbConnection.close()




def updateObjs():
    
    loop = True
    while loop:
       RegisterBasicCondominium()
       updateEmployeeObjs()
       updateGarageObjs()
       updateApartmentsObjs()
       updateLampObjs()

       # Interval between objects value update
       time.sleep(10)


def updateObjsAsync():

    # Allows only one update thread to run at a time
    if threading.active_count() == 1:
        t = threading.Thread(target=updateObjs, name="UpdateObjsThread")
        t.daemon = True
        t.start()
    else:
        print "One instance of updateObs is still active"


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

# Starting thread responsible for value updates
updateObjsAsync()

while (loop):
    # Block and process SNMP requests, if available
    agent.check_and_process()

print "{0}: Terminating.".format(prgname)
agent.shutdown()


def updateEmployeeObjs():
    try:
        dbConnection = mdb.connect(
            host   ='127.0.0.1', 
            user   ='root', 
            passwd ='', 
            db     = 'condominium',
            port   = 3306)

        with dbConnection:

            cursor = dbConnection.cursor(mdb.cursors.DictCursor)
            cursor.execute("SELECT * FROM employee ORDER BY id ASC")
            rows = cursor.fetchall()

            for row in rows:
                if row['id'] == 46:
                    emp46.setRowCell(2, agent.DisplayString(row['name']))       # Name
                    emp46.setRowCell(3, agent.DisplayString(row['role']))       # Role
                    emp46.setRowCell(4, agent.Integer32(row['month_wage']))     # Wage
                    emp46.setRowCell(5, agent.Integer32(row['week_workload']))  # Workload
                    emp46.setRowCell(6, agent.DisplayString(row['is_working'])) # Working

                if row['id'] == 47:
                    emp47.setRowCell(2, agent.DisplayString(row['name']))       # Name
                    emp47.setRowCell(3, agent.DisplayString(row['role']))       # Role
                    emp47.setRowCell(4, agent.Integer32(row['month_wage']))     # Wage
                    emp47.setRowCell(5, agent.Integer32(row['week_workload']))  # Workload
                    emp47.setRowCell(6, agent.DisplayString(row['is_working'])) # Working

                if row['id'] == 48:
                    emp48.setRowCell(2, agent.DisplayString(row['name']))      # Name
                    emp48.setRowCell(3, agent.DisplayString(row['role']))      # Role
                    emp48.setRowCell(4, agent.Integer32(row['month_wage']))    # Wage
                    emp48.setRowCell(5, agent.Integer32(row['week_workload'])) # Workload
                    emp48.setRowCell(6, agent.DisplayString(row['is_working']))# Working

    except mdb.Error, e:
        print "Error %d: %s" %(e.args[0], e.args[1])
        sys.exit(1)

    finally:
        if dbConnection:
            dbConnection.close()

def updateGarageObjs():
    try:
        dbConnection = mdb.connect(
            host   ='127.0.0.1', 
            user   ='root', 
            passwd ='', 
            db     = 'condominium',
            port   = 3306)

        with dbConnection:

            cursor = dbConnection.cursor(mdb.cursors.DictCursor)
            cursor.execute("SELECT * FROM garage ORDER BY id ASC")
            rows = cursor.fetchall()

            for row in rows:
                if row['id'] == 31:
                    lp31.setRowCell(2, agent.Integer32(row['number']))
                    lp31.setRowCell(3, agent.Integer32(row['apartment_id']))
                    lp31.setRowCell(4, agent.Integer32(row['is_occupied']))

                elif row['id'] == 32:
                    lp32.setRowCell(2, agent.Integer32(row['number']))
                    lp32.setRowCell(3, agent.Integer32(row['apartment_id']))
                    lp32.setRowCell(4, agent.Integew32(row['is_occupied']))

    except mdb.Error, e:
        print "Error %d: %s" %(e.args[0], e.args[1])
        sys.exit(1)

    finally:
        if dbConnection:
            dbConnection.close()

def updateLampObjs():
    try:
        dbConnection = mdb.connect(
            host   ='127.0.0.1', 
            user   ='root', 
            passwd ='', 
            db     = 'condominium',
            port   = 3306)

        with dbConnection:

            cursor = dbConnection.cursor(mdb.cursors.DictCursor)
            cursor.execute("SELECT * FROM lamp ORDER BY id ASC")
            rows = cursor.fetchall()

            for row in rows:
                if row['id'] == 30:
                    lp30.setRowCell(3, agent.Integer32(row['sector_id']))
                    lp30.setRowCell(4, agent.Integer32(row['is_on']))

                elif row['id'] == 31:
                    lp31.setRowCell(3, agent.Integer32(row['sector_id']))
                    lp31.setRowCell(4, agent.Integer32(row['is_on']))

                elif row['id'] == 32:
                    lp32.setRowCell(3, agent.Integer32(row['sector_id']))
                    lp32.setRowCell(4, agent.Integer32(row['is_on']))

                elif row['id'] == 33:
                    lp33.setRowCell(3, agent.Integer32(row['sector_id']))
                    lp33.setRowCell(4, agent.Integer32(row['is_on']))

    except mdb.Error, e:
        print "Error %d: %s" %(e.args[0], e.args[1])
        sys.exit(1)

    finally:
        if dbConnection:
            dbConnection.close()

def updateApartmentsObjs():
    try:
        dbConnection = mdb.connect(
            host   ='127.0.0.1', 
            user   ='root', 
            passwd ='', 
            db     = 'condominium',
            port   = 3306)

        with dbConnection:

            cursor = dbConnection.cursor(mdb.cursors.DictCursor)
            cursor.execute("SELECT * FROM apartment ORDER BY id ASC")
            rows = cursor.fetchall()

            for row in rows:
                if row['id'] == 162:
                    # Apartment information
                    ap162.setRowCell(2, agent.Integer32(row['number']))
                    ap162.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap162.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap162.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc162.setRowCell(2, agent.Integer32(row['number']))
                    wc162.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc162.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el162.setRowCell(2, agent.Integer32(row['number']))
                    el162.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el162.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 163:
                    # Apartment information
                    ap163.setRowCell(2, agent.Integer32(row['number']))
                    ap163.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap163.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap163.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc163.setRowCell(2, agent.Integer32(row['number']))
                    wc163.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc163.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el162.setRowCell(2, agent.Integer32(row['number']))
                    el162.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el162.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 164:
                    # Apartment information
                    ap164.setRowCell(2, agent.Integer32(row['number']))
                    ap164.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap164.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap164.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc164.setRowCell(2, agent.Integer32(row['number']))
                    wc164.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc164.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el164.setRowCell(2, agent.Integer32(row['number']))
                    el164.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el164.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 165:
                    # Apartment information
                    ap165.setRowCell(2, agent.Integer32(row['number']))
                    ap165.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap165.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap165.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc165.setRowCell(2, agent.Integer32(row['number']))
                    wc165.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc165.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el165.setRowCell(2, agent.Integer32(row['number']))
                    el165.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el165.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 166:
                    # Apartment information
                    ap166.setRowCell(2, agent.Integer32(row['number']))
                    ap166.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap166.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap166.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc166.setRowCell(2, agent.Integer32(row['number']))
                    wc166.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc166.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el166.setRowCell(2, agent.Integer32(row['number']))
                    el166.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el166.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 167:
                    # Apartment information
                    ap167.setRowCell(2, agent.Integer32(row['number']))
                    ap167.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap167.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap167.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc167.setRowCell(2, agent.Integer32(row['number']))
                    wc167.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc167.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el167.setRowCell(2, agent.Integer32(row['number']))
                    el167.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el167.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 168:
                    # Apartment information
                    ap168.setRowCell(2, agent.Integer32(row['number']))
                    ap168.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap168.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap168.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc168.setRowCell(2, agent.Integer32(row['number']))
                    wc168.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc168.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el168.setRowCell(2, agent.Integer32(row['number']))
                    el168.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el168.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

                elif row['id'] == 169:
                    # Apartment information
                    ap169.setRowCell(2, agent.Integer32(row['number']))
                    ap169.setRowCell(3, agent.DisplayString(row['owner_name']))
                    ap169.setRowCell(4, agent.Integer32(row['num_rooms']))
                    ap169.setRowCell(5, agent.Integer32(row['num_people']))

                    # Water consumption information
                    wc169.setRowCell(2, agent.Integer32(row['number']))
                    wc169.setRowCell(3, agent.DisplayString(row['water_consumption']))
                    wc169.setRowCell(4, agent.DisplayString(row['water_cons_limit']))

                    # Electricity information
                    el169.setRowCell(2, agent.Integer32(row['number']))
                    el169.setRowCell(3, agent.DisplayString(row['light_consumption']))
                    el169.setRowCell(4, agent.DisplayString(row['light_cons_limit']))

    except mdb.Error, e:
        print "Error %d: %s" %(e.args[0], e.args[1])
        sys.exit(1)

    finally:
        if dbConnection:
            dbConnection.close()