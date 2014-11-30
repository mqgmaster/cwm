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
 * tbApElEntry group
 */

public class TbApElEntry extends BaseTableEntry{

	protected Integer tbApElEntryIndex = new Integer(1);
	protected Integer tbApElEntryNumber = new Integer(1);
	protected String tbApElTotalEntryConsumption = "tbApElTotalEntryConsumption not initialized";
	protected String tbApElTotalEntryLimit = "tbApElTotalEntryLimit not initialized";
	protected String tbApElInstantEntryConsumption = "tbApElInstantEntryConsumption not initialized";
	protected String tbApElInstantEntryLimit = "tbApElInstantEntryLimit not initialized";

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBAPELENTRYINDEX = 1;
	final static int TBAPELENTRYNUMBER = 2;
	final static int TBAPELTOTALENTRYCONSUMPTION = 3;
	final static int TBAPELTOTALENTRYLIMIT = 4;
	final static int TBAPELINSTANTENTRYCONSUMPTION = 5;
	final static int TBAPELINSTANTENTRYLIMIT = 6;

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

		toReturn.put(new Integer(TBAPELENTRYINDEX),getTbApElEntryIndex());
		toReturn.put(new Integer(TBAPELENTRYNUMBER),getTbApElEntryNumber());
		toReturn.put(new Integer(TBAPELTOTALENTRYCONSUMPTION),getTbApElTotalEntryConsumption());
		toReturn.put(new Integer(TBAPELTOTALENTRYLIMIT),getTbApElTotalEntryLimit());
		toReturn.put(new Integer(TBAPELINSTANTENTRYCONSUMPTION),getTbApElInstantEntryConsumption());
		toReturn.put(new Integer(TBAPELINSTANTENTRYLIMIT),getTbApElInstantEntryLimit());

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
				case TBAPELENTRYINDEX:
					toReturn.put(new Integer(TBAPELENTRYINDEX),getTbApElEntryIndex());
					break;
				case TBAPELENTRYNUMBER:
					toReturn.put(new Integer(TBAPELENTRYNUMBER),getTbApElEntryNumber());
					break;
				case TBAPELTOTALENTRYCONSUMPTION:
					toReturn.put(new Integer(TBAPELTOTALENTRYCONSUMPTION),getTbApElTotalEntryConsumption());
					break;
				case TBAPELTOTALENTRYLIMIT:
					toReturn.put(new Integer(TBAPELTOTALENTRYLIMIT),getTbApElTotalEntryLimit());
					break;
				case TBAPELINSTANTENTRYCONSUMPTION:
					toReturn.put(new Integer(TBAPELINSTANTENTRYCONSUMPTION),getTbApElInstantEntryConsumption());
					break;
				case TBAPELINSTANTENTRYLIMIT:
					toReturn.put(new Integer(TBAPELINSTANTENTRYLIMIT),getTbApElInstantEntryLimit());
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
				case TBAPELENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBAPELENTRYNUMBER:
					// This attribute is not Writable !!! 
					break;
				case TBAPELTOTALENTRYCONSUMPTION:
					//setTbApElTotalEntryConsumption((String)var);
					break;
				case TBAPELTOTALENTRYLIMIT:
					setTbApElTotalEntryLimit((String)var);
					break;
				case TBAPELINSTANTENTRYCONSUMPTION:
					// This attribute is not Writable !!! 
					break;
				case TBAPELINSTANTENTRYLIMIT:
					setTbApElInstantEntryLimit((String)var);
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbApElEntryIndex
	 */
	public Integer getTbApElEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbApElEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbApElEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbApElEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbApElEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApElEntryNumber
	 */
	public Integer getTbApElEntryNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbApElEntryNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbApElEntryNumber
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbApElEntryNumber(Integer value){
		// Fill up with necessary processing

		tbApElEntryNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApElTotalEntryConsumption
	 */
	public String getTbApElTotalEntryConsumption()
				throws AgentException{
		// Fill up with necessary processing

		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.TOTAL_E + " FROM apartment WHERE id = " + tbApElEntryIndex);
			
			if(rs.next()) {
				tbApElTotalEntryConsumption = rs.getString(CondominiumUtils.TOTAL_E);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		
		return tbApElTotalEntryConsumption;
	}


	/**
	 * Handles the SNMP Set Request for tbApElTotalEntryConsumption
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApElTotalEntryConsumption(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApElTotalEntryConsumption = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApElTotalEntryLimit
	 */
	public String getTbApElTotalEntryLimit()
				throws AgentException{
		// Fill up with necessary processing

		return tbApElTotalEntryLimit;
	}


	/**
	 * Handles the SNMP Set Request for tbApElTotalEntryLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApElTotalEntryLimit(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApElTotalEntryLimit = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApElInstantEntryConsumption
	 */
	public String getTbApElInstantEntryConsumption()
				throws AgentException{
		// Fill up with necessary processing
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.INSTANT_E + " FROM apartment WHERE id = " + tbApElEntryIndex);
			
			if(rs.next()) {
				tbApElInstantEntryConsumption = rs.getString(CondominiumUtils.INSTANT_E);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		
		return tbApElInstantEntryConsumption;
	}


	/**
	 * Handles the SNMP Set Request for tbApElInstantEntryConsumption
	 * @param value - The String value to be set
	 */
	public synchronized void setTbApElInstantEntryConsumption(String value){
		// Fill up with necessary processing

		tbApElInstantEntryConsumption = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApElInstantEntryLimit
	 */
	public String getTbApElInstantEntryLimit()
				throws AgentException{
		// Fill up with necessary processing

		return tbApElInstantEntryLimit;
	}


	/**
	 * Handles the SNMP Set Request for tbApElInstantEntryLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApElInstantEntryLimit(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApElInstantEntryLimit = value;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
