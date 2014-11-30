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
 * tbApWtEntry group
 */

public class TbApWtEntry extends BaseTableEntry{

	protected Integer tbApWtEntryIndex = new Integer(1);
	protected Integer tbApWtEntryNumber = new Integer(1);
	protected String tbApWtEntryTotalConsuption = "tbApWtEntryTotalConsuption not initialized";
	protected String tbApWtEntryTotalLimit = "tbApWtEntryTotalLimit not initialized";
	protected String tbApWtEntryInstantConsuption = "tbApWtEntryInstantConsuption not initialized";
	protected String tbApWtEntryInstantLimit = "tbApWtEntryInstantLimit not initialized";

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBAPWTENTRYINDEX = 1;
	final static int TBAPWTENTRYNUMBER = 2;
	final static int TBAPWTENTRYTOTALCONSUPTION = 3;
	final static int TBAPWTENTRYTOTALLIMIT = 4;
	final static int TBAPWTENTRYINSTANTCONSUPTION = 5;
	final static int TBAPWTENTRYINSTANTLIMIT = 6;

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

		toReturn.put(new Integer(TBAPWTENTRYINDEX),getTbApWtEntryIndex());
		toReturn.put(new Integer(TBAPWTENTRYNUMBER),getTbApWtEntryNumber());
		toReturn.put(new Integer(TBAPWTENTRYTOTALCONSUPTION),getTbApWtEntryTotalConsuption());
		toReturn.put(new Integer(TBAPWTENTRYTOTALLIMIT),getTbApWtEntryTotalLimit());
		toReturn.put(new Integer(TBAPWTENTRYINSTANTCONSUPTION),getTbApWtEntryInstantConsuption());
		toReturn.put(new Integer(TBAPWTENTRYINSTANTLIMIT),getTbApWtEntryInstantLimit());

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
				case TBAPWTENTRYINDEX:
					toReturn.put(new Integer(TBAPWTENTRYINDEX),getTbApWtEntryIndex());
					break;
				case TBAPWTENTRYNUMBER:
					toReturn.put(new Integer(TBAPWTENTRYNUMBER),getTbApWtEntryNumber());
					break;
				case TBAPWTENTRYTOTALCONSUPTION:
					toReturn.put(new Integer(TBAPWTENTRYTOTALCONSUPTION),getTbApWtEntryTotalConsuption());
					break;
				case TBAPWTENTRYTOTALLIMIT:
					toReturn.put(new Integer(TBAPWTENTRYTOTALLIMIT),getTbApWtEntryTotalLimit());
					break;
				case TBAPWTENTRYINSTANTCONSUPTION:
					toReturn.put(new Integer(TBAPWTENTRYINSTANTCONSUPTION),getTbApWtEntryInstantConsuption());
					break;
				case TBAPWTENTRYINSTANTLIMIT:
					toReturn.put(new Integer(TBAPWTENTRYINSTANTLIMIT),getTbApWtEntryInstantLimit());
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
				case TBAPWTENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBAPWTENTRYNUMBER:
					// This attribute is not Writable !!! 
					break;
				case TBAPWTENTRYTOTALCONSUPTION:
					// This attribute is not Writable !!! 
					break;
				case TBAPWTENTRYTOTALLIMIT:
					setTbApWtEntryTotalLimit((String)var);
					break;
				case TBAPWTENTRYINSTANTCONSUPTION:
					// This attribute is not Writable !!! 
					break;
				case TBAPWTENTRYINSTANTLIMIT:
					setTbApWtEntryInstantLimit((String)var);
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbApWtEntryIndex
	 */
	public Integer getTbApWtEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbApWtEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbApWtEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbApWtEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApWtEntryNumber
	 */
	public Integer getTbApWtEntryNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbApWtEntryNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryNumber
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbApWtEntryNumber(Integer value){
		// Fill up with necessary processing

		tbApWtEntryNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApWtEntryTotalConsuption
	 */
	public String getTbApWtEntryTotalConsuption()
				throws AgentException{
		// Fill up with necessary processing

		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.TOTAL_W + " FROM apartment WHERE id = " + tbApWtEntryIndex);
			
			if(rs.next()) {
				tbApWtEntryTotalConsuption = rs.getString(CondominiumUtils.TOTAL_W);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbApWtEntryTotalConsuption;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryTotalConsuption
	 * @param value - The String value to be set
	 */
	public synchronized void setTbApWtEntryTotalConsuption(String value){
		// Fill up with necessary processing

		tbApWtEntryTotalConsuption = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApWtEntryTotalLimit
	 */
	public String getTbApWtEntryTotalLimit()
				throws AgentException{
		// Fill up with necessary processing

		return tbApWtEntryTotalLimit;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryTotalLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApWtEntryTotalLimit(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApWtEntryTotalLimit = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApWtEntryInstantConsuption
	 */
	public String getTbApWtEntryInstantConsuption()
				throws AgentException{
		// Fill up with necessary processing
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.INSTANT_W + " FROM apartment WHERE id = " + tbApWtEntryIndex);
			
			if(rs.next()) {
				tbApWtEntryInstantConsuption = rs.getString(CondominiumUtils.INSTANT_W);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbApWtEntryInstantConsuption;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryInstantConsuption
	 * @param value - The String value to be set
	 */
	public synchronized void setTbApWtEntryInstantConsuption(String value){
		// Fill up with necessary processing

		tbApWtEntryInstantConsuption = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApWtEntryInstantLimit
	 */
	public String getTbApWtEntryInstantLimit()
				throws AgentException{
		// Fill up with necessary processing

		return tbApWtEntryInstantLimit;
	}


	/**
	 * Handles the SNMP Set Request for tbApWtEntryInstantLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApWtEntryInstantLimit(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApWtEntryInstantLimit = value;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
