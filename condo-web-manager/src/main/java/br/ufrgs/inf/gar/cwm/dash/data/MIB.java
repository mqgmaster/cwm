package br.ufrgs.inf.gar.cwm.dash.data;

import org.snmp4j.smi.OID;

public class MIB {
	
	static class CondoOID {
		//public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID NAME = new OID(".1.3.6.1.2.1.4.20.1.2");
		public static final OID ADDRESS = new OID(".1.3.6.1.2.1.4.20.1.3");
		public static final OID MANAGER_NAME = new OID(".1.3.6.1.2.1.4.20.1.4");
		public static final OID WATER_CONS = new OID(".1.3.6.1.2.1.4.20.1.5");
		public static final OID WATER_CONS_LIMIT = new OID(".1.3.6.1.2.1.4.20.1.5");
		public static final OID LIGHT_CONS = new OID(".1.3.6.1.2.1.4.20.1.5");
		public static final OID LIGHT_CONS_LIMIT = new OID(".1.3.6.1.2.1.4.20.1.5");
		public static final OID NUM_UNKNOWN_PEOPLE = new OID(".1.3.6.1.2.1.4.20.1.5");
	}
	
	static class ApartmentOID {
		static class Table {
			public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID NUMBER = new OID(".1.3.6.1.2.1.4.20.1.2");
			public static final OID OWNER_NAME = new OID(".1.3.6.1.2.1.4.20.1.3");
			public static final OID NUM_ROOMS = new OID(".1.3.6.1.2.1.4.20.1.4");
			public static final OID NUM_PEOPLE = new OID(".1.3.6.1.2.1.4.20.1.5");
			//public static final OID CONDO_ID = new OID(".1.3.6.1.2.1.4.20.1.5");
		}
		
		static class WaterTable {
			public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID WATER_CONS = new OID(".1.3.6.1.2.1.4.20.1.4");
			public static final OID WATER_CONS_LIMIT = new OID(".1.3.6.1.2.1.4.20.1.5");
		}
		
		static class LightTable {
			public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
			public static final OID LIGHT_CONS = new OID(".1.3.6.1.2.1.4.20.1.4");
			public static final OID LIGHT_CONS_LIMIT = new OID(".1.3.6.1.2.1.4.20.1.5");
		}
	}
	
	static class GarageTableOID {
		public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID NUMBER = new OID(".1.3.6.1.2.1.4.20.1.2");
		public static final OID APT_ID = new OID(".1.3.6.1.2.1.4.20.1.3");
		public static final OID IS_OCCUPIED = new OID(".1.3.6.1.2.1.4.20.1.4");
		//public static final OID SECTOR_ID = new OID(".1.3.6.1.2.1.4.20.1.5");  
	}
	
	static class EmployeeTableOID {
		public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID NAME = new OID(".1.3.6.1.2.1.4.20.1.2");
		public static final OID ROLE = new OID(".1.3.6.1.2.1.4.20.1.3");
		public static final OID MONTH_WAGE = new OID(".1.3.6.1.2.1.4.20.1.4");
		public static final OID WEEK_WORKLOAD = new OID(".1.3.6.1.2.1.4.20.1.5");
		public static final OID IS_WORKING = new OID(".1.3.6.1.2.1.4.20.1.5");
		//public static final OID CONDO_ID = new OID(".1.3.6.1.2.1.4.20.1.5");
	}
	
	static class LampTableOID {
		public static final OID COUNT = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
		public static final OID IS_ON = new OID(".1.3.6.1.2.1.4.20.1.2");
		public static final OID SECTOR_ID = new OID(".1.3.6.1.2.1.4.20.1.2");
	}
	
	//static class SectorOID {
	//	public static final OID ID = new OID(".1.3.6.1.2.1.4.20.1.1");
	//	public static final OID NAME = new OID(".1.3.6.1.2.1.4.20.1.2");
	//	public static final OID CONDO_ID = new OID(".1.3.6.1.2.1.4.20.1.2");
	//}
}
