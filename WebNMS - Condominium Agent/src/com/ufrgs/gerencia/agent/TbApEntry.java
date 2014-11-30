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
 * tbApEntry group
 */

public class TbApEntry extends BaseTableEntry{

	protected Integer tbApEntryIndex = new Integer(1);
	protected Integer tbApEntryNumber = new Integer(1);
	protected String tbApEntryOwner = "tbApEntryOwner not initialized";
	protected Integer tbApEntryRooms = new Integer(1);
	protected Integer tbApEntryPeople = new Integer(1);
	protected Integer tbApEntrySector = new Integer(1);

	CondominioAgent agentName = null;

	/**
	 * Setter for the agent reference. 
	 * @param agentRef Reference of the agent.
	 */
	public void setAgentRef(CondominioAgent agentRef){
		agentName = agentRef;
	}

	final static int TBAPENTRYINDEX = 1;
	final static int TBAPENTRYNUMBER = 2;
	final static int TBAPENTRYOWNER = 3;
	final static int TBAPENTRYROOMS = 4;
	final static int TBAPENTRYPEOPLE = 5;
	final static int TBAPENTRYSECTOR = 6;

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

		toReturn.put(new Integer(TBAPENTRYINDEX),getTbApEntryIndex());
		toReturn.put(new Integer(TBAPENTRYNUMBER),getTbApEntryNumber());
		toReturn.put(new Integer(TBAPENTRYOWNER),getTbApEntryOwner());
		toReturn.put(new Integer(TBAPENTRYROOMS),getTbApEntryRooms());
		toReturn.put(new Integer(TBAPENTRYPEOPLE),getTbApEntryPeople());
		toReturn.put(new Integer(TBAPENTRYSECTOR),getTbApEntrySector());

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
				case TBAPENTRYINDEX:
					toReturn.put(new Integer(TBAPENTRYINDEX),getTbApEntryIndex());
					break;
				case TBAPENTRYNUMBER:
					toReturn.put(new Integer(TBAPENTRYNUMBER),getTbApEntryNumber());
					break;
				case TBAPENTRYOWNER:
					toReturn.put(new Integer(TBAPENTRYOWNER),getTbApEntryOwner());
					break;
				case TBAPENTRYROOMS:
					toReturn.put(new Integer(TBAPENTRYROOMS),getTbApEntryRooms());
					break;
				case TBAPENTRYPEOPLE:
					toReturn.put(new Integer(TBAPENTRYPEOPLE),getTbApEntryPeople());
					break;
				case TBAPENTRYSECTOR:
					toReturn.put(new Integer(TBAPENTRYSECTOR),getTbApEntrySector());
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
				case TBAPENTRYINDEX:
					// This attribute is not Writable !!! 
					break;
				case TBAPENTRYNUMBER:
					// setTbApEntryNumber((Integer)var);
					break;
				case TBAPENTRYOWNER:
					setTbApEntryOwner((String)var);
					break;
				case TBAPENTRYROOMS:
					// setTbApEntryRooms((Integer)var);
					break;
				case TBAPENTRYPEOPLE:
					// setTbApEntryPeople((Integer)var);
					break;
				case TBAPENTRYSECTOR:
					// setTbApEntrySector((Integer)var);
					break;
				default:

			}
		}
	}

	/**
	 * Handles the SNMP Get Request for tbApEntryIndex
	 */
	public Integer getTbApEntryIndex()
				throws AgentException{
		// Fill up with necessary processing

		return tbApEntryIndex;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntryIndex
	 * @param value - The Integer value to be set
	 */
	public synchronized void setTbApEntryIndex(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		tbApEntryIndex = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApEntryNumber
	 */
	public Integer getTbApEntryNumber()
				throws AgentException{
		// Fill up with necessary processing

		return tbApEntryNumber;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntryNumber
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApEntryNumber(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApEntryNumber = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApEntryOwner
	 */
	public String getTbApEntryOwner()
				throws AgentException{
		// Fill up with necessary processing

		return tbApEntryOwner;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntryOwner
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApEntryOwner(String value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApEntryOwner = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApEntryRooms
	 */
	public Integer getTbApEntryRooms()
				throws AgentException{
		// Fill up with necessary processing

		return tbApEntryRooms;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntryRooms
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApEntryRooms(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApEntryRooms = value;
	}


	/**
	 * Handles the SNMP Get Request for tbApEntryPeople
	 */
	public Integer getTbApEntryPeople()
				throws AgentException{
		// Fill up with necessary processing
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.AP_PEOPLE + " FROM apartment WHERE id = " + tbApEntryIndex);
			
			if(rs.next()) {
				tbApEntryPeople = rs.getInt(CondominiumUtils.AP_PEOPLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return tbApEntryPeople;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntryPeople
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApEntryPeople(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApEntryPeople = tbApEntryPeople;
	}


	/**
	 * Handles the SNMP Get Request for tbApEntrySector
	 */
	public Integer getTbApEntrySector()
				throws AgentException{
		// Fill up with necessary processing

		return tbApEntrySector;
	}


	/**
	 * Handles the SNMP Set Request for tbApEntrySector
	 * @param value - The Integer value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setTbApEntrySector(Integer value)
				 throws AgentException{
		// Fill up with necessary processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.intValue()>=-2147483648)&&(value.intValue()<=2147483647)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		tbApEntrySector = value;
	}


	/** This is A DummyMethod Generated,
	 *  If the Table does not have a RowStatus Column
	 */
	public void setrowStatus(Integer value){
	}

}
