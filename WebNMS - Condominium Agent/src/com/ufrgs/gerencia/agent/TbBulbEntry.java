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
 * tbBulbEntry group
 */

public class TbBulbEntry extends BaseTableEntry{

	protected Integer tbBulbEntryIndex  = new Integer(1);
	protected Integer tbBulbEntrySector = new Integer(1);
	protected Integer tbBulbEntryNumber = new Integer(1);
	protected Integer tbBulbEntryStatus = new Integer(1);

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBBULBENTRYINDEX = 1;
	final static int TBBULBENTRYSECTOR = 2;
	final static int TBBULBENTRYNUMBER = 3;
	final static int TBBULBENTRYSTATUS = 4;

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

		toReturn.put(new Integer(TBBULBENTRYINDEX),getTbBulbEntryIndex());
		toReturn.put(new Integer(TBBULBENTRYSECTOR),getTbBulbEntrySector());
		toReturn.put(new Integer(TBBULBENTRYNUMBER),getTbBulbEntryNumber());
		toReturn.put(new Integer(TBBULBENTRYSTATUS),getTbBulbEntryStatus());

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
				case TBBULBENTRYINDEX:
					toReturn.put(new Integer(TBBULBENTRYINDEX),getTbBulbEntryIndex());
					break;
				case TBBULBENTRYSECTOR:
					toReturn.put(new Integer(TBBULBENTRYSECTOR),getTbBulbEntrySector());
					break;
				case TBBULBENTRYNUMBER:
					toReturn.put(new Integer(TBBULBENTRYNUMBER),getTbBulbEntryNumber());
					break;
				case TBBULBENTRYSTATUS:
					toReturn.put(new Integer(TBBULBENTRYSTATUS),getTbBulbEntryStatus());
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
				case TBBULBENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBBULBENTRYSECTOR:
					// This attribute is not Writable !!! 
					break;
				case TBBULBENTRYNUMBER:
					// This attribute is not Writable !!! 
					break;
				case TBBULBENTRYSTATUS:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbBulbEntryIndex
	 */
	public Integer getTbBulbEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbBulbEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbBulbEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbBulbEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbBulbEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbBulbEntrySector
	 */
	public Integer getTbBulbEntrySector()
				throws AgentException{
		// Fill up with necessary processing

		return tbBulbEntrySector;
	}


	/**
	 * Handles the SNMP Set Request for tbBulbEntrySector
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbBulbEntrySector(Integer value){
		// Fill up with necessary processing

		tbBulbEntrySector = value;
	}


	/**
	 * Handles the SNMP Get Request for tbBulbEntryNumber
	 */
	public Integer getTbBulbEntryNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbBulbEntryNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbBulbEntryNumber
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbBulbEntryNumber(Integer value){
		// Fill up with necessary processing

		tbBulbEntryNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbBulbEntryStatus
	 */
	public Integer getTbBulbEntryStatus()
				throws AgentException{
		// Fill up with necessary processing
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.LP_STATUS + " FROM lamp WHERE id = " + tbBulbEntryIndex);
			
			if(rs.next()) {
				tbBulbEntryStatus = rs.getInt(CondominiumUtils.LP_STATUS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbBulbEntryStatus;
	}


	/**
	 * Handles the SNMP Set Request for tbBulbEntryStatus
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbBulbEntryStatus(Integer value){
		// Fill up with necessary processing

		tbBulbEntryStatus = tbBulbEntryStatus;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
