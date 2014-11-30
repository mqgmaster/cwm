package com.ufrgs.gerencia.agent;

public class CondominiumUtils {

	public static final String ID 	 	   = "id";
	public static final String SECTOR      = "sector_id" ;
	public static final String AP_NUMBER   = "number";
	public static final String AP_OWNER    = "owner_name";
	public static final String AP_ROOMS    = "num_rooms";
	public static final String AP_PEOPLE   = "num_people";
	public static final String TOTAL_W     = "total_water_usage";
	public static final String INSTANT_W   = "instant_water_usage";
	public static final String TOTAL_E	   = "total_electric_usage";
	public static final String INSTANT_E   = "instant_electric_usage";
	public static final String NAME		   = "name";
	public static final String ROLE		   = "role";
	public static final String WAGE		   = "month_wage";
	public static final String WORKLOAD    = "week_workload";
	public static final String WORKING     = "is_working";
	public static final String CONDOMINIUM = "condominium_id";
	public static final String GG_AP_NUM   = "apartment_id";
	public static final String GG_STATUS   = "is_occupied";
	public static final String LP_STATUS   = "is_on";
	public static final String ADDRESS	   = "address";
	public static final String MANAGER	   = "manager_name";
	public static final String UNKNOWN_P   = "num_unknown_people";
	public static final String LIMIT_W     = "instant_water_limit";
	public static final String LIMIT_E	   = "instant_electric_limit";
	public static final String LIMIT_AP_W  = "apt_instant_water_limit";
	public static final String LIMIT_AP_E  = "apt_instant_electric_limit";
	
	
	public static final String AP_QUERY  = "SELECT * FROM apartment";
	public static final String EP_QUERY  = "SELECT * FROM employee";
	public static final String GG_QUERY  = "SELECT * FROM garage";
	public static final String LP_QUERY  = "SELECT * FROM lamp";
	public static final String CM_QUERY  = "SELECT * FROM condominium";
}
