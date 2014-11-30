/* Copyright (c)  2009  ZOHO Corp. All Rights Reserved.
 * PLEASE READ THE ASSOCIATED COPYRIGHTS FILE FOR MORE DETAILS.
 * ZOHO CORPORATION MAKES NO REPRESENTATIONS OR WARRANTIES  ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT.
 * ZOHO CORPORATION SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE 
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE
 * OR ITS DERIVATIVES.
 */

/**
 * @Version : 6.0.3 Sat Nov 29 15:51:17 BRST 2014
 * @Author  : WebNMS Agent Toolkit Java Edition
 */

// Any changes made to this file will be lost, if regenerated. 
// User code should be added within user tags for code merging support, if regenerated.

// Package Name (Don't Delete this comment)
package com.ufrgs.gerencia.agent;

// SNMP Agent API Imports
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import com.adventnet.agent.snmp.configuration.AgentConfigInitializer;
import com.adventnet.snmp.snmp2.agent.AgentRuntimeException;
import com.adventnet.snmp.snmp2.agent.AgentUtil;
import com.adventnet.snmp.snmp2.agent.PduRequestHandler;
import com.adventnet.snmp.snmp2.agent.SnmpAgent;
import com.adventnet.snmp.snmp2.agent.SnmpAgentInitializer;
import com.adventnet.snmp.snmp2.agent.SnmpTrapService;
import com.adventnet.snmp.snmp2.agent.TrapRequestListener;
import com.adventnet.utilities.common.CommonUtils;
// Agent Utility API Imports
import com.adventnet.utils.agent.AgentParamOptions;
// Java Imports
import com.adventnet.utils.agent.RegistrationListenerException;
// SnmpV3 Imports


/** 
 * CondominioAgent Main Class
 * <p>
 * This is the main file which extends the <code> SnmpAgent</code>
 * and instantiates <code>PduRequestHandler</code> which handles
 * the registration of the Agent SubTrees.
 * This includes methods for checking external Index availability.
 * <p> 
 */

public class CondominioAgent extends SnmpAgent implements Runnable{

	private static final long serialVersionUID = 1L;
	public static  String agentDir = ".";
	private static String ipAddress="127.0.0.1";

	static {
		AgentUtil.setAgentDir(agentDir);
	}

	/**
	 * Primary constructor of the Agent
	 * @see #CondominioAgent(AgentParamOptions agentOptions)
	 * @see #CondominioAgent(String[] args)
	 */
	public CondominioAgent(){
		this(new AgentParamOptions());
	}

	/**
	 * Constructor with AgentParamOptions
	 * @see #CondominioAgent()
	 * @see #CondominioAgent(String[] args)
	 * @param agentOptions The Agent configuration options while starting.
	 */
	public CondominioAgent(AgentParamOptions agentOptions){
		this.agentOptions = agentOptions;
		if(!agentOptions.getAgentDir().equals(".")){
			AgentUtil.setAgentDir(agentOptions.getAgentDir());
		}
		Thread th = new Thread(this);
		th.start();
	}

	/**
	 * Constructor with commandline argument options
	 * @see #CondominioAgent()
	 * @see #CondominioAgent(AgentParamOptions agentOptions)
	 * @param args The Agent configuration options while starting.
	 */
	public CondominioAgent(String[] args){
		// This takes care of the options
		this(new AgentParamOptions( args));
	}

	/**
	 * This Starts the Agent
	 * @see #init
	 * @see #initCondominioAgent
	 */
	public void run(){
		try{
			init();
		}
		catch(AgentRuntimeException e){
			System.out.println("Bind Exception :: Port " + super.getPort() + " is in use");
		}
		catch(RegistrationListenerException te){
			System.out.println("RegistrationListenerException :: SnmpAgent is a Unicast Bean");
		}
	}

	/**
	 * This method is called by the Agent run method.
	 * @see #initCondominioAgent
	 * @exception AgentRuntimeException on inavailability of port to bind.
	 * @exception RegistrationListenerException if the registration tree is already registered.
	 */
	public void init() throws AgentRuntimeException, RegistrationListenerException{
		try{
			initCondominioAgent();
		}
		catch(AgentRuntimeException e){
			throw e;
		}
		catch(RegistrationListenerException te){
			throw te;
		}
	}

	/**
	 * Sets the Agent with the configuration options and restarts the SNMP Agent.
	 * @see #initSnmpExtensionNodes 
	 * @exception AgentRuntimeException on inavailability of port to bind.
	 * @exception RegistrationListenerException if the registration tree is already registered.
	 */
	public void initCondominioAgent() throws AgentRuntimeException, RegistrationListenerException{

		// Setting Agent parameters from Startup XML file
		Hashtable agentConf = null;
		try{
			agentConf = AgentConfigInitializer.parseInitializerProperties(agentDir+File.separator+"conf"+File.separator+"AgentAgentStartup.xml");
		}catch(Exception e)
		{
		System.out.println("Exception while initializing agent details from XML file: AgentAgentStartup.xml.");
		}
		SnmpAgentInitializer.setAgentReference((SnmpAgent) this);
		SnmpAgentInitializer.initializeAgentDetails(agentConf, agentOptions);

		String version = super.getSnmpVersion();
		trapListener = SnmpAgentInitializer.getTrapListener();

		 // Initializing V3 Compliance Tables
		SnmpAgentInitializer.initializeV3ComplianceTables();
		if(version.equalsIgnoreCase("V3")){
			initializeV3Settings();
		}

		// Initializing the Trap Forwarding Table
		SnmpAgentInitializer.initializeForwardingTables(hdlr);
		Hashtable configTableInstances = SnmpAgentInitializer.getConfigurationTables();
		forwardingTable = (com.adventnet.snmp.snmp2.agent.ForwardingTable)configTableInstances.get("V1V2TfTable");
		v3ForwardingTable = (com.adventnet.snmp.snmp2.agent.V3ForwardingTable)configTableInstances.get("V3TfTable");
		if(version.equalsIgnoreCase("V3")){
			v3ForwardingTable.addV3ForwardingEntry(com.adventnet.snmp.snmp2.agent.V3SimpleTrapForwardingTable.createV3ForwardingEntry("127.0.0.1", new Integer(8003), new Integer(3), "public", "noAuthUser", new Long(3), new Integer(1), "noAuth", new Long(5000), new Long(0)));
		}

		try{
			super.addSnmpPduRequestListener(hdlr);
			super.addTrapRequestListener((TrapRequestListener)trapListener);
		}
		catch(RegistrationListenerException e){
			throw e;
		}

		super.setDefaultTrap(false);

		try{
			super.restartSnmpAgent();
		}
 		catch(AgentRuntimeException ee){
			// Here add appropriate code to handle session.
			throw ee;
		}

		// Setting the agent reference in CommonUtils, so that
		// this can be used in xxxInstrument and xxxEntries 
		CommonUtils.setAgentReference(this);

		// Initializing tables for Group Counters and V2 Compliance 
		SnmpAgentInitializer.initializeV1V2ComplianceTables(hdlr);

		// Initializing authentication tables
		SnmpAgentInitializer.initializeAccessControlTables(hdlr);
		aclTable = (com.adventnet.snmp.snmp2.agent.AclTable)configTableInstances.get("AclTable");

		// Setting the Transport Protocol
		SnmpAgentInitializer.initializeTransportProvider();
		initSnmpExtensionNodes ();
	}

	/**
	 * Adds VarBindRequestListeners to SnmpAgent.
	 * Registers the Agent SubTrees to the <code>PduRequestHandler</code>
	 */
	public void initSnmpExtensionNodes(){
		
		apartmentListener =  new ApartmentRequestHandler(this);
		apartmentListener.setApartmentInstrument(getApartmentInstance());
		apartmentListener.addRegistrationListener(hdlr);

		condominiumListener =  new CondominiumRequestHandler(this);
		condominiumListener.setCondominiumInstrument(getCondominiumInstance());
		condominiumListener.addRegistrationListener(hdlr);
	
		employeeListener =  new EmployeeRequestHandler(this);
		employeeListener.setEmployeeInstrument(getEmployeeInstance());
		employeeListener.addRegistrationListener(hdlr);

		garageListener =  new GarageRequestHandler(this);
		garageListener.setGarageInstrument(getGarageInstance());
		garageListener.addRegistrationListener(hdlr);

		waterListener =  new WaterRequestHandler(this);
		waterListener.setWaterInstrument(getWaterInstance());
		waterListener.addRegistrationListener(hdlr);

		electricityListener =  new ElectricityRequestHandler(this);
		electricityListener.setElectricityInstrument(getElectricityInstance());
		electricityListener.addRegistrationListener(hdlr);

		tbApartmentsListener =  new TbApartmentsRequestHandler(this);
		tbApartmentsListener.addRegistrationListener(hdlr);
		
		tbEmployeeListener =  new TbEmployeeRequestHandler(this);
		tbEmployeeListener.addRegistrationListener(hdlr);

		tbGarageListener =  new TbGarageRequestHandler(this);
		tbGarageListener.addRegistrationListener(hdlr);

		tbApWaterListener =  new TbApWaterRequestHandler(this);
		tbApWaterListener.addRegistrationListener(hdlr);

		tbApElectricityListener =  new TbApElectricityRequestHandler(this);
		tbApElectricityListener.addRegistrationListener(hdlr);

		tbBulbElectricityListener =  new TbBulbElectricityRequestHandler(this);
		tbBulbElectricityListener.addRegistrationListener(hdlr);

		initValues();
		
	}
	
	/**
	 * Retrieves from database every initial values
	 * @return true if the operation was successfully performed; false otherwise
	 */
	public void initValues() {
		try {
			Connection con = DBConnection.getMySQLConnection();
			Statement  st  = con.createStatement();
			
			
			ResultSet  rs  = st.executeQuery(CondominiumUtils.AP_QUERY);
			
			// Set value to the number of entries in the table
			rs.last();
			apartmentListener.instrument.apCount     = rs.getRow();
			waterListener.instrument.wtApCount       = rs.getRow();
			electricityListener.instrument.elApCount = rs.getRow();
			rs.beforeFirst();
			
			while(rs.next()) {
				
				// Generates every row for apartment table
				tbApartmentsListener.createAndAddNewTbApEntry(rs.getInt(CondominiumUtils.ID), rs.getInt(CondominiumUtils.AP_NUMBER), 
						rs.getString(CondominiumUtils.AP_OWNER), rs.getInt(CondominiumUtils.AP_ROOMS), rs.getInt(CondominiumUtils.AP_PEOPLE), rs.getInt(CondominiumUtils.SECTOR));
				
				// Generates every row for tbApWater
				tbApWaterListener.createAndAddNewTbApWtEntry(rs.getInt(CondominiumUtils.ID), rs.getInt(CondominiumUtils.AP_NUMBER), 
						rs.getString(CondominiumUtils.TOTAL_W), "Undefined", rs.getString(CondominiumUtils.INSTANT_W), "Undefined");
				
				// Generates every row for tbApElectricity
				tbApElectricityListener.createAndAddNewTbApElEntry(rs.getInt(CondominiumUtils.ID), rs.getInt(CondominiumUtils.AP_NUMBER), 
						rs.getString(CondominiumUtils.TOTAL_E), "Undefined", rs.getString(CondominiumUtils.INSTANT_E), "Undefined");
			}
			
			// Set value to the number of entries in the table
			rs = st.executeQuery(CondominiumUtils.EP_QUERY);
			rs.last();
			employeeListener.instrument.epCount = rs.getRow();
			rs.beforeFirst();
			
			while(rs.next()) {
				
				// Generate every row for employee table
				tbEmployeeListener.createAndAddNewTbEpEntry(rs.getInt(CondominiumUtils.ID), rs.getString(CondominiumUtils.NAME), rs.getString(CondominiumUtils.ROLE), 
						rs.getInt(CondominiumUtils.WAGE), rs.getInt(CondominiumUtils.WORKLOAD), rs.getInt(CondominiumUtils.WORKING), rs.getInt(CondominiumUtils.CONDOMINIUM));
			}
			
			rs = st.executeQuery(CondominiumUtils.GG_QUERY);
			// Set value to the number of entries in the table
			rs.last();
			garageListener.instrument.ggCount = rs.getRow();
			rs.beforeFirst();
			while(rs.next()) {
				
				// Generate every row for garage table
				tbGarageListener.createAndAddNewTbGgEntry(rs.getInt(CondominiumUtils.ID), rs.getInt(CondominiumUtils.AP_NUMBER), rs.getInt(CondominiumUtils.GG_AP_NUM), 
						rs.getInt(CondominiumUtils.GG_STATUS), rs.getInt(CondominiumUtils.SECTOR));
			}
			
			rs = st.executeQuery(CondominiumUtils.LP_QUERY);
			// Set value to the number of entries in the table
			rs.last();
			electricityListener.instrument.elBulbCounter = rs.getRow();
			rs.beforeFirst();
			while(rs.next()) {
				
				// Generate every row for lamp table
				tbBulbElectricityListener.createAndAddNewTbBulbEntry(rs.getInt(CondominiumUtils.ID), rs.getInt(CondominiumUtils.SECTOR), -1, rs.getInt(CondominiumUtils.LP_STATUS));
			}
			
			rs = st.executeQuery(CondominiumUtils.CM_QUERY);
			while(rs.next()) {
				// Condominium related scalars
				condominiumListener.instrument.cmAddress = rs.getString(CondominiumUtils.ADDRESS);
				condominiumListener.instrument.cmManager = rs.getString(CondominiumUtils.MANAGER);
				condominiumListener.instrument.cmName    = rs.getString(CondominiumUtils.NAME);
				condominiumListener.instrument.cmUPeople = rs.getInt(CondominiumUtils.UNKNOWN_P);
				
				// Water related scalars
				waterListener.instrument.wtAConsumption       = rs.getString(CondominiumUtils.TOTAL_W);
				waterListener.instrument.wtAConsumptionLimit  = rs.getString(CondominiumUtils.LIMIT_W);	// As we only have one limit in the DB
				waterListener.instrument.wtIConsuptionLimit   = rs.getString(CondominiumUtils.LIMIT_W);	// both, total a instant limits are the same
				waterListener.instrument.wtIConsumption		  = rs.getString(CondominiumUtils.INSTANT_W );
				waterListener.instrument.wtApConsumptionLimit = rs.getString(CondominiumUtils.LIMIT_AP_W);
				
				// Electricity related scalars
				electricityListener.instrument.elConsumption  	    = rs.getString(CondominiumUtils.TOTAL_E);
				electricityListener.instrument.elConsumptionLimit   = rs.getString(CondominiumUtils.LIMIT_E);
				electricityListener.instrument.elInstantConsumption = rs.getString(CondominiumUtils.INSTANT_E);
				electricityListener.instrument.elApConsumptionLimit = rs.getString(CondominiumUtils.LIMIT_AP_E);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
	}


	/**
	 * Getter for ApartmentRequestHandler 
	 * @return Object of ApartmentRequestHandler
	 */
	public ApartmentRequestHandler getApartment(){
		return apartmentListener;
	}

	/**
	 * Getter for CondominiumRequestHandler 
	 * @return Object of CondominiumRequestHandler
	 */
	public CondominiumRequestHandler getCondominium(){
		return condominiumListener;
	}

	/**
	 * Getter for EmployeeRequestHandler 
	 * @return Object of EmployeeRequestHandler
	 */
	public EmployeeRequestHandler getEmployee(){
		return employeeListener;
	}

	/**
	 * Getter for GarageRequestHandler 
	 * @return Object of GarageRequestHandler
	 */
	public GarageRequestHandler getGarage(){
		return garageListener;
	}

	/**
	 * Getter for WaterRequestHandler 
	 * @return Object of WaterRequestHandler
	 */
	public WaterRequestHandler getWater(){
		return waterListener;
	}

	/**
	 * Getter for ElectricityRequestHandler 
	 * @return Object of ElectricityRequestHandler
	 */
	public ElectricityRequestHandler getElectricity(){
		return electricityListener;
	}

	/**
	 * Getter for TbApartmentsRequestHandler 
	 * @return Object of TbApartmentsRequestHandler
	 */
	public TbApartmentsRequestHandler getTbApartments(){
		return tbApartmentsListener;
	}

	/**
	 * Getter for TbEmployeeRequestHandler 
	 * @return Object of TbEmployeeRequestHandler
	 */
	public TbEmployeeRequestHandler getTbEmployee(){
		return tbEmployeeListener;
	}

	/**
	 * Getter for TbGarageRequestHandler 
	 * @return Object of TbGarageRequestHandler
	 */
	public TbGarageRequestHandler getTbGarage(){
		return tbGarageListener;
	}

	/**
	 * Getter for TbApWaterRequestHandler 
	 * @return Object of TbApWaterRequestHandler
	 */
	public TbApWaterRequestHandler getTbApWater(){
		return tbApWaterListener;
	}

	/**
	 * Getter for TbApElectricityRequestHandler 
	 * @return Object of TbApElectricityRequestHandler
	 */
	public TbApElectricityRequestHandler getTbApElectricity(){
		return tbApElectricityListener;
	}

	/**
	 * Getter for TbBulbElectricityRequestHandler 
	 * @return Object of TbBulbElectricityRequestHandler
	 */
	public TbBulbElectricityRequestHandler getTbBulbElectricity(){
		return tbBulbElectricityListener;
	}

	/**
	 * Getter for ApartmentInstrument 
	 * @return Object of ApartmentInstrument 
	 */
	public ApartmentInstrument getApartmentInstance(){
		return new ApartmentInstrument();
	}

	/**
	 * Getter for CondominiumInstrument 
	 * @return Object of CondominiumInstrument 
	 */
	public CondominiumInstrument getCondominiumInstance(){
		return new CondominiumInstrument();
	}

	/**
	 * Getter for EmployeeInstrument 
	 * @return Object of EmployeeInstrument 
	 */
	public EmployeeInstrument getEmployeeInstance(){
		return new EmployeeInstrument();
	}

	/**
	 * Getter for GarageInstrument 
	 * @return Object of GarageInstrument 
	 */
	public GarageInstrument getGarageInstance(){
		return new GarageInstrument();
	}

	/**
	 * Getter for WaterInstrument 
	 * @return Object of WaterInstrument 
	 */
	public WaterInstrument getWaterInstance(){
		return new WaterInstrument();
	}

	/**
	 * Getter for ElectricityInstrument 
	 * @return Object of ElectricityInstrument 
	 */
	public ElectricityInstrument getElectricityInstance(){
		return new ElectricityInstrument();
	}

	/**
	 * Getter for TbApEntry 
	 * @return Object of TbApEntry 
	 */
	public TbApEntry getTbApEntryInstance(){
		return  new TbApEntry();
	}

	/**
	 * Getter for TbEpEntry File
	 * @return Object of TbEpEntry 
	 */
	public TbEpEntry getTbEpEntryInstance(){
		return  new TbEpEntry();
	}

	/**
	 * Getter for TbGgEntry 
	 * @return Object of TbGgEntry 
	 */
	public TbGgEntry getTbGgEntryInstance(){
		return  new TbGgEntry();
	}

	/**
	 * Getter for TbApWtEntry 
	 * @return Object of TbApWtEntry 
	 */
	public TbApWtEntry getTbApWtEntryInstance(){
		return  new TbApWtEntry();
	}

	/**
	 * Getter for TbApElEntry 
	 * @return Object of TbApElEntry 
	 */
	public TbApElEntry getTbApElEntryInstance(){
		return  new TbApElEntry();
	}

	/**
	 * Getter for TbBulbEntry 
	 * @return Object of TbBulbEntry 
	 */
	public TbBulbEntry getTbBulbEntryInstance(){
		return  new TbBulbEntry();
	}


	// Variable Declarations
	protected AgentParamOptions agentOptions = null;

	// The Registration Listener
	protected PduRequestHandler hdlr = new PduRequestHandler ();

	//  The Snmp Trap Service to send traps
	protected  SnmpTrapService trapListener = null;

	// Acl Table Support
	private com.adventnet.snmp.snmp2.agent.AclTable aclTable = null;

	// V3Trap Forwarding Table Support.
	private com.adventnet.snmp.snmp2.agent.V3ForwardingTable v3ForwardingTable = null;

	// V1V2Trap Forwarding Table Support.
	private com.adventnet.snmp.snmp2.agent.ForwardingTable forwardingTable = null;


	// V3 Support.
	private com.adventnet.snmp.snmp2.agent.usm.UsmUserRequestHandler usmUserListener = null;

	private com.adventnet.snmp.snmp2.agent.usm.UsmUserTableRequestHandler usmUserTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmMIBViewsRequestHandler vacmMIBViewsListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmContextTableRequestHandler vacmContextTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmSecurityToGroupTableRequestHandler vacmSecurityToGroupTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmAccessTableRequestHandler vacmAccessTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmViewTreeFamilyTableRequestHandler vacmviewTreeFamilyTableListener = null;

	private com.adventnet.snmp.snmp2.agent.community.SnmpCommunityTableRequestHandler snmpCommunityTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetObjectsRequestHandler snmpTargetObjectsListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetParamsTableRequestHandler snmpTargetParamsTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetAddrTableRequestHandler snmpTargetAddrTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetAddrExtTableRequestHandler snmpTargetAddrExtTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyTableRequestHandler snmpNotifyTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterTableRequestHandler snmpNotifyFilterTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterProfileTableRequestHandler snmpNotifyFilterProfileTableListener = null;

	private ApartmentRequestHandler apartmentListener = null;

	private CondominiumRequestHandler condominiumListener = null;

	private EmployeeRequestHandler employeeListener = null;

	private GarageRequestHandler garageListener = null;

	private WaterRequestHandler waterListener = null;

	private ElectricityRequestHandler electricityListener = null;

	private TbApartmentsRequestHandler tbApartmentsListener = null;

	private TbEmployeeRequestHandler tbEmployeeListener = null;

	private TbGarageRequestHandler tbGarageListener = null;

	private TbApWaterRequestHandler tbApWaterListener = null;

	private TbApElectricityRequestHandler tbApElectricityListener = null;

	private TbBulbElectricityRequestHandler tbBulbElectricityListener = null;

	/**
	 * Used to run Agent as a stand-alone application.
	 * @param args The Agent configuration parameters passed from commandline.
	 */
	public static void main(String[] args){
		AgentUtil.parseLoggingParameters(agentDir+File.separator+"conf"+File.separator+"AgentAgentStartup.xml",args);
		CondominioAgent agent_sim = new CondominioAgent (args);
		System.out.println("Initializing Condominium Agent");
	}

	/**
	 * This method stops this Snmp Agent
	 */
	public void shutDownSNMPAgent(){
		killSnmpAgent();
	}

	/**
	 * This method initializes all the V3Tables.
	 */

	public void initializeV3Settings(){
		
	}
}

