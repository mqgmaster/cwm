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


// Package Name (Dont Delete this comment)
package com.ufrgs.gerencia.agent;

// SNMP API Imports
import com.adventnet.snmp.snmp2.agent.BaseTableEntry;
import com.adventnet.utils.agent.RowStatusHandlerInterface;

// Agent Utility API Imports
import com.adventnet.utilities.common.AgentException;
import com.adventnet.utilities.common.CommonUtils;


import java.sql.Connection;
import java.sql.ResultSet;
// Java Imports
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;


/**
 * Contains the data handling under
 * tbEpEntry group
 */

public class TbEpEntry extends BaseTableEntry{

	protected Integer tbEpEntryIndex = new Integer(1);
	protected String tbEpEntryName = "tbEpEntryName not initialized";
	protected String tbEpEntryRole = "tbEpEntryRole not initialized";
	protected Integer tbEpEntryWage = new Integer(1);
	protected Integer tbEpEntryWorkLoad = new Integer(1);
	protected Integer tbEpEntryWorking = new Integer(1);
	protected Integer tbEpEntryCondominium = new Integer(1);

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBEPENTRYINDEX = 1;
	final static int TBEPENTRYNAME = 2;
	final static int TBEPENTRYROLE = 3;
	final static int TBEPENTRYWAGE = 4;
	final static int TBEPENTRYWORKLOAD = 5;
	final static int TBEPENTRYWORKING = 6;
	final static int TBEPENTRYCONDOMINIUM = 7;

	static Integer failedSubId = new Integer(-1);

	/**
	*
	* @return the index of the varbind for which the failure occured
	*/
	public Integer getFailedSubId(){
		return failedSubId;
	}

	/**
	*
	* @param subId failed subId
	*/
	public void setFailedSubId(Integer subId){
		failedSubId = subId;
	}

	/**
	*
	* @return Hashtable having values of all the Objects belonging to this Group
	* @throws Exception
	*/
	public Hashtable getAttributes() throws Exception { 
		Hashtable toReturn = new Hashtable();

		toReturn.put(new Integer(TBEPENTRYINDEX),getTbEpEntryIndex());
		toReturn.put(new Integer(TBEPENTRYNAME),getTbEpEntryName());
		toReturn.put(new Integer(TBEPENTRYROLE),getTbEpEntryRole());
		toReturn.put(new Integer(TBEPENTRYWAGE),getTbEpEntryWage());
		toReturn.put(new Integer(TBEPENTRYWORKLOAD),getTbEpEntryWorkLoad());
		toReturn.put(new Integer(TBEPENTRYWORKING),getTbEpEntryWorking());
		toReturn.put(new Integer(TBEPENTRYCONDOMINIUM),getTbEpEntryCondominium());

		return toReturn;
	}

	/**
	*
	* @param enumer Enumeration having the Objects for which value is sought
	* @return Hashtable having values of the Objects present in the Enumeration enumer
	* @throws Exception
	*/
	public Hashtable getAttributes(Enumeration enumer) throws Exception { 
		Hashtable toReturn = new Hashtable();

		while(enumer.hasMoreElements()){
			Integer key =(Integer) enumer.nextElement();
			switch(key.intValue()){
				case TBEPENTRYINDEX:
					toReturn.put(new Integer(TBEPENTRYINDEX),getTbEpEntryIndex());
					break;
				case TBEPENTRYNAME:
					toReturn.put(new Integer(TBEPENTRYNAME),getTbEpEntryName());
					break;
				case TBEPENTRYROLE:
					toReturn.put(new Integer(TBEPENTRYROLE),getTbEpEntryRole());
					break;
				case TBEPENTRYWAGE:
					toReturn.put(new Integer(TBEPENTRYWAGE),getTbEpEntryWage());
					break;
				case TBEPENTRYWORKLOAD:
					toReturn.put(new Integer(TBEPENTRYWORKLOAD),getTbEpEntryWorkLoad());
					break;
				case TBEPENTRYWORKING:
					toReturn.put(new Integer(TBEPENTRYWORKING),getTbEpEntryWorking());
					break;
				case TBEPENTRYCONDOMINIUM:
					toReturn.put(new Integer(TBEPENTRYCONDOMINIUM),getTbEpEntryCondominium());
					break;
				default:
			}
		}

		return toReturn;
	}

	/**
	*
	* @param values Hashtable having values of Objects,for which SET is to be performed
	* @param subIdList Vector having list of Objects for which SET is to be performed
	* @throws AgentException
	*/
	public void setAttributes(Hashtable values,Vector subIdList) throws AgentException { 
		Object var = null;
		int size = subIdList.size();
		for(int i = 0;i < size;i++) {
			failedSubId = (Integer)subIdList.elementAt(i);
			var = values.get(failedSubId);

			switch(failedSubId.intValue()){
				case TBEPENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBEPENTRYNAME:
					// This attribute is not Writable !!! 
					break;
				case TBEPENTRYROLE:
					setTbEpEntryRole((String)var);
					break;
				case TBEPENTRYWAGE:
					setTbEpEntryWage((Integer)var);
					break;
				case TBEPENTRYWORKLOAD:
					setTbEpEntryWorkLoad((Integer)var);
					break;
				case TBEPENTRYWORKING:
					// This attribute is not Writable !!! 
					break;
				case TBEPENTRYCONDOMINIUM:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbEpEntryIndex
	 */
	public Integer getTbEpEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbEpEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbEpEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryName
	 */
	public String getTbEpEntryName()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryName;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryName
	 * @param value - The String value to be set
	 */
	public synchronized void setTbEpEntryName(String value){
		// Fill up with necessary processing

		tbEpEntryName = value;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryRole
	 */
	public String getTbEpEntryRole()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryRole;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryRole
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbEpEntryRole(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbEpEntryRole = value;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryWage
	 */
	public Integer getTbEpEntryWage()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryWage;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryWage
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbEpEntryWage(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbEpEntryWage = value;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryWorkLoad
	 */
	public Integer getTbEpEntryWorkLoad()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryWorkLoad;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryWorkLoad
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbEpEntryWorkLoad(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbEpEntryWorkLoad = value;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryWorking
	 */
	public Integer getTbEpEntryWorking()
				throws AgentException{
		// Fill up with necessary processing
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.WORKING + " FROM employee WHERE id = " + tbEpEntryIndex);
			
			if(rs.next()) {
				tbEpEntryWorking = rs.getInt(CondominiumUtils.WORKING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbEpEntryWorking;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryWorking
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbEpEntryWorking(Integer value){
		// Fill up with necessary processing

		tbEpEntryWorking = tbEpEntryWorking;
	}


	/**
	 * Handles the SNMP Get Request for tbEpEntryCondominium
	 */
	public Integer getTbEpEntryCondominium()
				throws AgentException{
		// Fill up with necessary processing

		return tbEpEntryCondominium;
	}


	/**
	 * Handles the SNMP Set Request for tbEpEntryCondominium
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbEpEntryCondominium(Integer value){
		// Fill up with necessary processing

		tbEpEntryCondominium = value;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
