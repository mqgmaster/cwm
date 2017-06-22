package br.ufrgs.inf.gar.cwm.dash.data;

import org.snmp4j.smi.OID;

public class MIB {
	
	public static final String PREFIX = ".1.3.6.1.4.1.12619.1.";
	
	private static OID create(String sufix) {
		return new OID(PREFIX + sufix);
	}
	
	static class CondoOID {
		public static final OID NAME = create("2.1.0");
		public static final OID ADDRESS = create("2.2.0");
		public static final OID MANAGER_NAME = create("2.3.0");
		public static final OID NUM_UNKNOWN_PEOPLE = create("2.4.0");
		
		public static final OID WATER_TOTAL_USAGE = create("5.5.0");
		public static final OID WATER_INSTANT_USAGE = create("5.3.0");
		public static final OID WATER_INSTANT_LIMIT = create("5.6.0");
		public static final OID APT_WATER_INSTANT_LIMIT = create("5.7.0");
		
		public static final OID ELECTRIC_TOTAL_USAGE = create("6.5.0");
		public static final OID ELECTRIC_INSTANT_USAGE = create("6.7.0");
		public static final OID ELECTRIC_INSTANT_LIMIT = create("6.6.0");
		public static final OID APT_ELECTRIC_INSTANT_LIMIT = create("6.8.0");
	}
	
	static class ApartmentOID {
		static class Table {
			public static final OID COUNT = create("1.1.0");
			
			public static final OID ID = create("1.2.1.1");
			public static final OID NUMBER = create("1.2.1.2");
			public static final OID OWNER_NAME = create("1.2.1.3");
			public static final OID NUM_ROOMS = create("1.2.1.4");
			public static final OID NUM_PEOPLE = create("1.2.1.5");
			public static final OID SECTOR_ID = create("1.2.1.6");
		}
		
		static class WaterTable {
			public static final OID COUNT = create("5.1.0");
			
			public static final OID ID = create("5.2.1.1");
			public static final OID APT_NUMBER = create("5.2.1.2");
			public static final OID WATER_TOTAL_USAGE = create("5.2.1.3");
			public static final OID WATER_INSTANT_USAGE = create("5.2.1.5");
		}
		
		static class ElectricTable {
			public static final OID COUNT = create("6.1.0");
			
			public static final OID ID = create("6.2.1.1");
			public static final OID APT_NUMBER = create("6.2.1.2");
			public static final OID ELECTRIC_TOTAL_USAGE = create("6.2.1.3");
			public static final OID ELECTRIC_INSTANT_USAGE = create("6.2.1.5");
		}
	}
	
	static class GarageTableOID {
		public static final OID COUNT = create("4.1.0");
		
		public static final OID ID = create("4.2.1.1");
		public static final OID NUMBER = create("4.2.1.2");
		public static final OID APT_ID = create("4.2.1.3");		
		public static final OID IS_OCCUPIED = create("4.2.1.4");
		public static final OID SECTOR_ID = create("4.2.1.5");
	}
	
	static class EmployeeTableOID {
		public static final OID COUNT = create("3.1.0");
		
		public static final OID ID = create("3.2.1.1");
		public static final OID NAME = create("3.2.1.2");
		public static final OID ROLE = create("3.2.1.3");
		public static final OID MONTH_WAGE = create("3.2.1.4");
		public static final OID WEEK_WORKLOAD = create("3.2.1.5");
		public static final OID IS_WORKING = create("3.2.1.6");
	}
	
	static class LampTableOID {
		public static final OID COUNT = create("6.3.0");
		
		public static final OID ID = create("6.4.1.1");
		public static final OID IS_ON = create("6.4.1.4");
		public static final OID SECTOR_ID = create("6.4.1.2");
	}
}
