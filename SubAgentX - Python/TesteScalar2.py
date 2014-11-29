import sys, os, signal, threading, time
import optparse
import pprint

# Make sure we use the local copy, not a system-wide one
sys.path.insert(0, os.path.dirname(os.getcwd()))
import netsnmpagent
import MySQLdb
import random

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
DB_HOST = '127.0.0.1' #'143.54.12.155'
DB_USER = 'root'      #'gar'
DB_PASS = ''          #'gar'
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

# Employee Columns
EP_ID      = 'id'
EP_NAME    = 'name'
EP_ROLE    = 'role'
EP_WAGE    = 'month_wage'
EP_LOAD    = 'week_workload'
EP_STATUS  = 'is_working'
EP_COND    = 'condominium_id'

# Garage Columns
GG_ID        = 'id'
GG_NUMBER    = 'number'
GG_APARTMENT = 'apartment_id'
GG_OCCUPIED  = 'is_occupied'
GG_SECTOR    = 'sector_id'

# Lamp Columns
LP_ID     = 'id'
LP_SECTOR = 'sector_id'
LP_STATUS = 'is_on'
LP_NUMBER = 'number'

# Condominium Columns
CM_ID      = 'id'
CM_NAME    = 'name'
CM_ADDRESS = 'address'
CM_MANAGER = 'manager_name'
CM_WT_USAGE_TOTAL   = 'total_water_usage'
CM_WT_LIMIT         = 'instant_water_limit'
CM_WT_USAGE_INSTANT = 'instant_water_usage'
CM_EL_USAGE_TOTAL   = 'total_electric_usage'
CM_EL_LIMIT         = 'instant_electric_limit'
CM_EL_USAGE_INSTANT = 'instant_electric_usage'
CM_UN_PEOPLE        = 'num_unknown_people'
CM_AP_WATER_LIMIT   = 'apt_instant_water_limit'
CM_AP_EL_LIMIT      = 'apt_instant_electric_limit'


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
        (2, agent.Integer32(0),  True),
        (3, agent.DisplayString(), True),
        (4, agent.Integer32(0),  True),
        (5, agent.Integer32(0),  True),
        (6, agent.Integer32(0),  True)
    ],
    counterobj = agent.Integer32 (
        oidstr = "CONDOMINIO-MIB::apCount"
    ),
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
            (6, agent.Integer32(0)),
            (7, agent.Integer32(0))
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
            (5, agent.Integer32(0))
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
            (5, agent.DisplayString("None")),
            (6, agent.DisplayString("None")),
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

wtApConsumptionLimit = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::wtApConsumptionLimit",
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
            (3, agent.DisplayString("None"), True),
            (4, agent.DisplayString("None")),
            (5, agent.DisplayString("None")),
            (6, agent.DisplayString("None")),

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

elInstantConsumption = agent.DisplayString (
    oidstr  = "CONDOMINIO-MIB::elInstantConsumption",
    initval =  "0"
    )

elApConsumptionLimit = agent.DisplayString (
    oidstr   = "CONDOMINIO-MIB::elApConsumptionLimit",
    initval  = "0",
    writable = True
    )

# Tables array
apartments  = {}
water       = {}
electricity = {}
lamps       = {}
garages     = {}
employees   = {}


# Add all necessary tables
# Apartment table
try:
    dbConnection = MySQLdb.connect(DB_HOST, DB_USER, DB_PASS, DB_BASE, DB_PORT)

    with dbConnection:
        cursor = dbConnection.cursor(MySQLdb.cursors.DictCursor)
        
        cursor.execute("SELECT * FROM condominium")
        rows = cursor.fetchall()
        for row in rows:
            # Condominium
            cmName.update(row[CM_NAME])
            cmAddress.update(row[CM_ADDRESS])
            cmManager.update(row[CM_MANAGER])
            cmUPeople.update(row[CM_UN_PEOPLE])

            # Condominium water
            wtAConsumption.update(row[CM_WT_USAGE_TOTAL])
            wtAConsumptionLimit.update(row[CM_WT_LIMIT])
            wtIConsumption.update(row[CM_WT_USAGE_INSTANT])

            # Condominium water
            elConsumption.update(row[CM_EL_USAGE_TOTAL])
            elConsumptionLimit.update(row[CM_EL_LIMIT])
            elInstantConsumption.update(row[CM_EL_USAGE_INSTANT])

            # Global apartments limits
            elApConsumptionLimit.update(row[CM_AP_EL_LIMIT])
            wtApConsumptionLimit.update(row[CM_AP_WATER_LIMIT])



        cursor.execute("SELECT * FROM apartment")
        rows = cursor.fetchall();
        for row in rows:
           # Apartments
           apartments[row[AP_ID]] = tbApartments.addRow([agent.Integer32(row[AP_ID])])
           apartments[row[AP_ID]].setRowCell(2, agent.Integer32(row[AP_NUMBER]))
           apartments[row[AP_ID]].setRowCell(3, agent.DisplayString(row[AP_OWNER]))
           apartments[row[AP_ID]].setRowCell(4, agent.Integer32(row[AP_ROOMS]))
           apartments[row[AP_ID]].setRowCell(5, agent.Integer32(row[AP_PEOPLE]))
           apartments[row[AP_ID]].setRowCell(6, agent.Integer32(row[AP_SECTOR]))
           
           # Water
           water[row[AP_ID]] = tbApWater.addRow([agent.Integer32(row[AP_ID])])
           water[row[AP_ID]].setRowCell(2, agent.Integer32(row[WT_NUMBER]))
           water[row[AP_ID]].setRowCell(3, agent.DisplayString(row[WT_USAGE_TOTAL]))
           # water[row[AP_ID]].setRowCell(4, agent.DisplayString(row[WT_LIMIT_TOTAL]))
           water[row[AP_ID]].setRowCell(5, agent.DisplayString(row[WT_USAGE_INSTANT]))
           # WaterInstantLimit is not define in the database

           # Electricity
           electricity[row[AP_ID]] = tbApElectricity.addRow([agent.Integer32(row[AP_ID])])
           electricity[row[AP_ID]].setRowCell(2, agent.Integer32(row[EL_NUMBER]))
           electricity[row[AP_ID]].setRowCell(3, agent.DisplayString(row[EL_USAGE_TOTAL]))
           # electricity[row[AP_ID]].setRowCell(4, agent.DisplayString(row[EL_LIMIT_TOTAL]))
           electricity[row[AP_ID]].setRowCell(5, agent.DisplayString(row[EL_USAGE_INSTANT]))
           # ElectricityInstantLimit is not define in the database

        cursor.execute("SELECT * FROM employee")
        rows = cursor.fetchall()
        for row in rows:
            # Employees
            employees[row[EP_ID]] = tbEmployee.addRow([agent.Integer32(row[EP_ID])])
            employees[row[EP_ID]].setRowCell(2, agent.DisplayString(row[EP_NAME]))
            employees[row[EP_ID]].setRowCell(3, agent.DisplayString(row[EP_ROLE]))
            employees[row[EP_ID]].setRowCell(4, agent.Integer32(row[EP_WAGE]))
            employees[row[EP_ID]].setRowCell(5, agent.Integer32(row[EP_LOAD]))
            employees[row[EP_ID]].setRowCell(6, agent.Integer32(row[EP_STATUS]))
            employees[row[EP_ID]].setRowCell(7, agent.Integer32(row[EP_COND]))

        cursor.execute("SELECT * FROM garage")
        rows = cursor.fetchall()
        for row in rows:
            # Garages
            garages[row[GG_ID]] = tbGarage.addRow([agent.Integer32(row[GG_ID])])
            garages[row[GG_ID]].setRowCell(2, agent.Integer32(row[GG_NUMBER]))
            garages[row[GG_ID]].setRowCell(3, agent.Integer32(row[GG_APARTMENT]))
            garages[row[GG_ID]].setRowCell(4, agent.Integer32(row[GG_OCCUPIED]))
            garages[row[GG_ID]].setRowCell(5, agent.Integer32(row[GG_SECTOR]))

        cursor.execute("SELECT * FROM lamp")
        rows = cursor.fetchall()
        for row in rows:
            # Lamps
            lamps[row[LP_ID]] = tbBulbElectricity.addRow([agent.Integer32(row[GG_ID])])
            lamps[row[LP_ID]].setRowCell(2, agent.Integer32(row[LP_SECTOR]))
            # lamps[row[LP_ID]].setRowCell(3, agent.Integer32(row[LP_NUMBER])) # Not defined on the database
            lamps[row[LP_ID]].setRowCell(4, agent.Integer32(row[LP_STATUS]))

except MySQLdb.Error, e:
    print "Error %d: %s" %(e.args[0], e.args[1])

finally:
    if dbConnection:
        dbConnection.close()

def updateObjs():
    
    loop = True
    while loop:
        try:
            dbConnection = MySQLdb.connect(DB_HOST, DB_USER, DB_PASS, DB_BASE, DB_PORT)

            with dbConnection:
                cursor = dbConnection.cursor(MySQLdb.cursors.DictCursor)
                cursor.execute("SELECT * FROM apartment")

                rows = cursor.fetchall();
                for row in rows:
                    # Update apartment objects
                    apartments[row[AP_ID]].setRowCell(5, agent.Integer32(row[AP_PEOPLE]))               # Update number of people related to this apartment
                    water[row[AP_ID]].setRowCell(3, agent.DisplayString(row[WT_USAGE_TOTAL]))           # Update total water consumption
                    water[row[AP_ID]].setRowCell(5, agent.DisplayString(row[WT_USAGE_INSTANT]))         # Update instant water consumption
                    electricity[row[AP_ID]].setRowCell(3, agent.DisplayString(row[EL_USAGE_TOTAL]))     # Update total electricity consumption
                    electricity[row[AP_ID]].setRowCell(5, agent.DisplayString(row[EL_USAGE_INSTANT]))   # Update instatn electricity consumption

                cursor.execute("SELECT * FROM employee")
                rows = cursor.fetchall()
                for row in rows:
                    # Update employee objects
                    employees[row[EP_ID]].setRowCell(6, agent.Integer32(row[EP_STATUS]))

                cursor.execute("SELECT * FROM garage")
                rows = cursor.fetchall()
                for row in rows:
                    # Update garage objects
                    garages[row[GG_ID]].setRowCell(4, agent.Integer32(row[GG_OCCUPIED]))

                cursor.execute("SELECT * FROM lamp")
                rows = cursor.fetchall()
                for row in rows:
                    # Update lamp objects
                    lamps[row[LP_ID]].setRowCell(4, agent.Integer32(row[LP_STATUS]))

                cursor.execute("SELECT * FROM condominium")
                rows = cursor.fetchall()
                for row in rows:
                    # Update condominium objects
                    cmUPeople.update(row[CM_UN_PEOPLE])
                    wtAConsumption.update(row[CM_WT_USAGE_TOTAL])
                    wtIConsumption.update(row[CM_WT_USAGE_INSTANT])
                    elConsumption.update(row[CM_EL_USAGE_TOTAL])
                    elInstantConsumption.update(row[CM_EL_USAGE_INSTANT])

        except MySQLdb.Error, e:
            print "Error %d: %s" %(e.args[0], e.args[1])

        finally:
            if dbConnection:
                dbConnection.close()
        
        # Interval between objects value update
        time.sleep(3)


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


aps = tbApartments.value()
print "# Entradas: %d" %(len(aps)) 


for apartment in apartments:
    print "Ap. Number: %d" %(aps[apartment][2])
    print "Owner name: %s" %(aps[apartment][3])
    print "Ap. Rooms : %d" %(aps[apartment][4])
    print "Ap. People: %d" %(aps[apartment][5])
    print "Ap. Sector: %d" %(aps[apartment][6])
    print ""

# The simple agent's main loop. We loop endlessly until our signal
# handler above changes the "loop" variable.
print "{0}: Serving SNMP requests, send SIGHUP to dump SNMP object state, press ^C to terminate...".format(prgname)
random.seed()
loop = True
updateObjsAsync()
while (loop):
    agent.check_and_process()

print "{0}: Terminating.".format(prgname)
agent.shutdown()
