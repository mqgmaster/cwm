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
 * tbGgEntry group
 */

public class TbGgEntry extends BaseTableEntry{

	protected Integer tbGgEntryIndex    = new Integer(1);
	protected Integer tbGgEntryNumber   = new Integer(1);
	protected Integer tbGgEntryApNumber = new Integer(1);
	protected Integer tbGgEntryOccupied = new Integer(1);
	protected Integer tbGgEntrySector   = new Integer(1);

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBGGENTRYINDEX = 1;
	final static int TBGGENTRYNUMBER = 2;
	final static int TBGGENTRYAPNUMBER = 3;
	final static int TBGGENTRYOCCUPIED = 4;
	final static int TBGGENTRYSECTOR = 5;

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

		toReturn.put(new Integer(TBGGENTRYINDEX),getTbGgEntryIndex());
		toReturn.put(new Integer(TBGGENTRYNUMBER),getTbGgEntryNumber());
		toReturn.put(new Integer(TBGGENTRYAPNUMBER),getTbGgEntryApNumber());
		toReturn.put(new Integer(TBGGENTRYOCCUPIED),getTbGgEntryOccupied());
		toReturn.put(new Integer(TBGGENTRYSECTOR),getTbGgEntrySector());

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
				case TBGGENTRYINDEX:
					toReturn.put(new Integer(TBGGENTRYINDEX),getTbGgEntryIndex());
					break;
				case TBGGENTRYNUMBER:
					toReturn.put(new Integer(TBGGENTRYNUMBER),getTbGgEntryNumber());
					break;
				case TBGGENTRYAPNUMBER:
					toReturn.put(new Integer(TBGGENTRYAPNUMBER),getTbGgEntryApNumber());
					break;
				case TBGGENTRYOCCUPIED:
					toReturn.put(new Integer(TBGGENTRYOCCUPIED),getTbGgEntryOccupied());
					break;
				case TBGGENTRYSECTOR:
					toReturn.put(new Integer(TBGGENTRYSECTOR),getTbGgEntrySector());
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
				case TBGGENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBGGENTRYNUMBER:
					// This attribute is not Writable !!! 
					break;
				case TBGGENTRYAPNUMBER:
					setTbGgEntryApNumber((Integer)var);
					break;
				case TBGGENTRYOCCUPIED:
					// This attribute is not Writable !!! 
					break;
				case TBGGENTRYSECTOR:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbGgEntryIndex
	 */
	public Integer getTbGgEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbGgEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbGgEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbGgEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbGgEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbGgEntryNumber
	 */
	public Integer getTbGgEntryNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbGgEntryNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbGgEntryNumber
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbGgEntryNumber(Integer value){
		// Fill up with necessary processing

		tbGgEntryNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbGgEntryApNumber
	 */
	public Integer getTbGgEntryApNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbGgEntryApNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbGgEntryApNumber
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbGgEntryApNumber(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbGgEntryApNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbGgEntryOccupied
	 */
	public Integer getTbGgEntryOccupied()
				throws AgentException{
		// Fill up with necessary processing
		
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.GG_STATUS + " FROM garage WHERE id = " + tbGgEntryIndex);
			
			if(rs.next()) {
				tbGgEntryOccupied = rs.getInt(CondominiumUtils.GG_STATUS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbGgEntryOccupied;
	}


	/**
	 * Handles the SNMP Set Request for tbGgEntryOccupied
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbGgEntryOccupied(Integer value){
		// Fill up with necessary processing
		
		tbGgEntryOccupied = tbGgEntryOccupied;
	}


	/**
	 * Handles the SNMP Get Request for tbGgEntrySector
	 */
	public Integer getTbGgEntrySector()
				throws AgentException{
		// Fill up with necessary processing

		return tbGgEntrySector;
	}


	/**
	 * Handles the SNMP Set Request for tbGgEntrySector
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbGgEntrySector(Integer value){
		// Fill up with necessary processing

		tbGgEntrySector = value;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
