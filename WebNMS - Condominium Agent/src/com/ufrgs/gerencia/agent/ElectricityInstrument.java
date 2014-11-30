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
 * @Version : 6.0.3 Sat Nov 29 15:51:16 BRST 2014
 * @Author  : WebNMS Agent Toolkit Java Edition
 */

// Any changes made to this file will be lost, if regenerated. 
// User code should be added within user tags for code merging support, if regenerated.

// Package Name (Dont Delete this comment)
package com.ufrgs.gerencia.agent;

// Agent Utility API Imports
import com.adventnet.utilities.common.AgentException;
import com.adventnet.utilities.common.CommonUtils;
import com.adventnet.utils.agent.InstrumentHandlerInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

public class ElectricityInstrument implements InstrumentHandlerInterface{

	protected Integer elApCount = new Integer(1);
	protected Integer elBulbCounter = new Integer(1);
	protected String elConsumption = "elConsumption not initialized";
	protected String elConsumptionLimit = "elConsumptionLimit not initialized";
	protected String elInstantConsumption = "elInstantConsumption not initialized";
	protected String elApConsumptionLimit = "elApConsumptionLimit not initialized";

	final static int ELAPCOUNT = 1;
	final static int ELBULBCOUNTER = 3;
	final static int ELCONSUMPTION = 5;
	final static int ELCONSUMPTIONLIMIT = 6;
	final static int ELINSTANTCONSUMPTION = 7;
	final static int ELAPCONSUMPTIONLIMIT = 8;

	static Integer failedSubId = new Integer(-1);

	/**
	*
	* @return the subId of the attribute for which Set could not be performed
	*/
	public Integer getFailedSubId(){
		return failedSubId;
	}

	/**
	*
	* @param subId failed SubId
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

		toReturn.put(new Integer(ELAPCOUNT),getElApCount());
		toReturn.put(new Integer(ELBULBCOUNTER),getElBulbCounter());
		toReturn.put(new Integer(ELCONSUMPTION),getElConsumption());
		toReturn.put(new Integer(ELCONSUMPTIONLIMIT),getElConsumptionLimit());
		toReturn.put(new Integer(ELINSTANTCONSUMPTION),getElInstantConsumption());
		toReturn.put(new Integer(ELAPCONSUMPTIONLIMIT),getElApConsumptionLimit());
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
			try{
				Integer key =(Integer) enumer.nextElement();
				switch(key.intValue()){
					case ELAPCOUNT:
						toReturn.put(new Integer(ELAPCOUNT),getElApCount());
						break;
					case ELBULBCOUNTER:
						toReturn.put(new Integer(ELBULBCOUNTER),getElBulbCounter());
						break;
					case ELCONSUMPTION:
						toReturn.put(new Integer(ELCONSUMPTION),getElConsumption());
						break;
					case ELCONSUMPTIONLIMIT:
						toReturn.put(new Integer(ELCONSUMPTIONLIMIT),getElConsumptionLimit());
						break;
					case ELINSTANTCONSUMPTION:
						toReturn.put(new Integer(ELINSTANTCONSUMPTION),getElInstantConsumption());
						break;
					case ELAPCONSUMPTIONLIMIT:
						toReturn.put(new Integer(ELAPCONSUMPTIONLIMIT),getElApConsumptionLimit());
						break;
					default:
				}
			} catch(Exception exp) {
				//exp.printStackTrace();
				// If Exception occurs while fetching the value of a attribute, it should be handled appropriately in this catch block itself and the
				// corresponding attribute should *not* be updated in the Hashtable returned by this method.
				// In the generated *RequestHandler file, appropriate Snmp Error Message will be updated for the missing attribute(s) while processing
				// GET/GET-NEXT/GET-BULK requests from the Snmp Managers.
			}
		} // end of while 
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
				case ELAPCOUNT:
					// This attribute is not Writable !!! 
					break;
				case ELBULBCOUNTER:
					// This attribute is not Writable !!! 
					break;
				case ELCONSUMPTION:
					// This attribute is not Writable !!! 
					break;
				case ELCONSUMPTIONLIMIT:
					setElConsumptionLimit((String)var);
					break;
				case ELINSTANTCONSUMPTION:
					// This attribute is not Writable !!! 
					break;
				case ELAPCONSUMPTIONLIMIT:
					setElApConsumptionLimit((String)var);
					break;
				default:

			}
		}
	}


	/**
	 * Handles the SNMP Get Request for elApCount
	 */
	public Integer getElApCount()
				 throws AgentException{
		// Fill up the stub with required processing

		return elApCount;
	}

	/**
	 * Handles the SNMP Set Request for elApCount
	 * @param value - The Integer value to be set
	 */
	public synchronized void setElApCount(Integer value){
		// Fill up the stub with required processing

		elApCount = elApCount;
	}

	/**
	 * Handles the SNMP Get Request for elBulbCounter
	 */
	public Integer getElBulbCounter()
				 throws AgentException{
		// Fill up the stub with required processing

		return elBulbCounter;
	}

	/**
	 * Handles the SNMP Set Request for elBulbCounter
	 * @param value - The Integer value to be set
	 */
	public synchronized void setElBulbCounter(Integer value){
		// Fill up the stub with required processing

		elBulbCounter = elBulbCounter;
	}

	/**
	 * Handles the SNMP Get Request for elConsumption
	 */
	public String getElConsumption()
				 throws AgentException{
		// Fill up the stub with required processing

		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.TOTAL_E + " FROM condominium");
			
			if(rs.next()) {
				elConsumption = rs.getString(CondominiumUtils.TOTAL_E);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		
		return elConsumption;
	}

	/**
	 * Handles the SNMP Set Request for elConsumption
	 * @param value - The String value to be set
	 */
	public synchronized void setElConsumption(String value){
		// Fill up the stub with required processing

		elConsumption = elConsumption;
	}

	/**
	 * Handles the SNMP Get Request for elConsumptionLimit
	 */
	public String getElConsumptionLimit()
				 throws AgentException{
		// Fill up the stub with required processing

		return elConsumptionLimit;
	}

	/**
	 * Handles the SNMP Set Request for elConsumptionLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setElConsumptionLimit(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		elConsumptionLimit = value;
	}

	/**
	 * Handles the SNMP Get Request for elInstantConsumption
	 */
	public String getElInstantConsumption()
				 throws AgentException{
		// Fill up the stub with required processing
		
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.INSTANT_E + " FROM condominium");
			
			if(rs.next()) {
				elInstantConsumption = rs.getString(CondominiumUtils.INSTANT_E);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		return elInstantConsumption;
	}

	/**
	 * Handles the SNMP Set Request for elInstantConsumption
	 * @param value - The String value to be set
	 */
	public synchronized void setElInstantConsumption(String value){
		// Fill up the stub with required processing

		elInstantConsumption = elInstantConsumption;
	}

	/**
	 * Handles the SNMP Get Request for elApConsumptionLimit
	 */
	public String getElApConsumptionLimit()
				 throws AgentException{
		// Fill up the stub with required processing

		return elApConsumptionLimit;
	}

	/**
	 * Handles the SNMP Set Request for elApConsumptionLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setElApConsumptionLimit(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		elApConsumptionLimit = value;
	}


}