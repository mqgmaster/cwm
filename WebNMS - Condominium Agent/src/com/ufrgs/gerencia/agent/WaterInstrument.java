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
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.adventnet.utilities.common.AgentException;
import com.adventnet.utilities.common.CommonUtils;
import com.adventnet.utils.agent.InstrumentHandlerInterface;

public class WaterInstrument implements InstrumentHandlerInterface{

	protected Integer wtApCount = new Integer(1);
	protected String wtIConsumption = "wtIConsumption not initialized";
	protected String wtIConsuptionLimit = "wtIConsuptionLimit not initialized";
	protected String wtAConsumption = "wtAConsumption not initialized";
	protected String wtAConsumptionLimit = "wtAConsumptionLimit not initialized";
	protected String wtApConsumptionLimit = "wtApConsumptionLimit not initialized";

	final static int WTAPCOUNT = 1;
	final static int WTICONSUMPTION = 3;
	final static int WTICONSUPTIONLIMIT = 4;
	final static int WTACONSUMPTION = 5;
	final static int WTACONSUMPTIONLIMIT = 6;
	final static int WTAPCONSUMPTIONLIMIT = 7;

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

		toReturn.put(new Integer(WTAPCOUNT),getWtApCount());
		toReturn.put(new Integer(WTICONSUMPTION),getWtIConsumption());
		toReturn.put(new Integer(WTICONSUPTIONLIMIT),getWtIConsuptionLimit());
		toReturn.put(new Integer(WTACONSUMPTION),getWtAConsumption());
		toReturn.put(new Integer(WTACONSUMPTIONLIMIT),getWtAConsumptionLimit());
		toReturn.put(new Integer(WTAPCONSUMPTIONLIMIT),getWtApConsumptionLimit());
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
					case WTAPCOUNT:
						toReturn.put(new Integer(WTAPCOUNT),getWtApCount());
						break;
					case WTICONSUMPTION:
						toReturn.put(new Integer(WTICONSUMPTION),getWtIConsumption());
						break;
					case WTICONSUPTIONLIMIT:
						toReturn.put(new Integer(WTICONSUPTIONLIMIT),getWtIConsuptionLimit());
						break;
					case WTACONSUMPTION:
						toReturn.put(new Integer(WTACONSUMPTION),getWtAConsumption());
						break;
					case WTACONSUMPTIONLIMIT:
						toReturn.put(new Integer(WTACONSUMPTIONLIMIT),getWtAConsumptionLimit());
						break;
					case WTAPCONSUMPTIONLIMIT:
						toReturn.put(new Integer(WTAPCONSUMPTIONLIMIT),getWtApConsumptionLimit());
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
				case WTAPCOUNT:
					// This attribute is not Writable !!! 
					break;
				case WTICONSUMPTION:
					// This attribute is not Writable !!! 
					break;
				case WTICONSUPTIONLIMIT:
					setWtIConsuptionLimit((String)var);
					break;
				case WTACONSUMPTION:
					// This attribute is not Writable !!! 
					break;
				case WTACONSUMPTIONLIMIT:
					setWtAConsumptionLimit((String)var);
					break;
				case WTAPCONSUMPTIONLIMIT:
					setWtApConsumptionLimit((String)var);
					break;
				default:

			}
		}
	}


	/**
	 * Handles the SNMP Get Request for wtApCount
	 */
	public Integer getWtApCount()
				 throws AgentException{
		// Fill up the stub with required processing

		return wtApCount;
	}

	/**
	 * Handles the SNMP Set Request for wtApCount
	 * @param value - The Integer value to be set
	 */
	public synchronized void setWtApCount(Integer value){
		// Fill up the stub with required processing

		wtApCount = wtApCount;
	}

	/**
	 * Handles the SNMP Get Request for wtIConsumption
	 */
	public String getWtIConsumption()
				 throws AgentException{
		// Fill up the stub with required processing

		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.INSTANT_W + " FROM condominium");
			
			if(rs.next()) {
				wtIConsumption = rs.getString(CondominiumUtils.INSTANT_W);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		
		return wtIConsumption;
	}

	/**
	 * Handles the SNMP Set Request for wtIConsumption
	 * @param value - The String value to be set
	 */
	public synchronized void setWtIConsumption(String value){
		// Fill up the stub with required processing

		wtIConsumption = wtIConsumption;
	}

	/**
	 * Handles the SNMP Get Request for wtIConsuptionLimit
	 */
	public String getWtIConsuptionLimit()
				 throws AgentException{
		// Fill up the stub with required processing

		return wtIConsuptionLimit;
	}

	/**
	 * Handles the SNMP Set Request for wtIConsuptionLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setWtIConsuptionLimit(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		wtIConsuptionLimit = value;
	}

	/**
	 * Handles the SNMP Get Request for wtAConsumption
	 */
	public String getWtAConsumption()
				 throws AgentException{
		// Fill up the stub with required processing
		
		try {
			Connection con = DBConnection.getMySQLConnection();
			ResultSet  rs  = con.createStatement().executeQuery("SELECT " + CondominiumUtils.TOTAL_W + " FROM condominium");
			
			if(rs.next()) {
				wtAConsumption = rs.getString(CondominiumUtils.TOTAL_W);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeMySQLConnection();
		}
		
		return wtAConsumption;
	}

	/**
	 * Handles the SNMP Set Request for wtAConsumption
	 * @param value - The String value to be set
	 */
	public synchronized void setWtAConsumption(String value){
		// Fill up the stub with required processing

		wtAConsumption = wtAConsumption;
	}

	/**
	 * Handles the SNMP Get Request for wtAConsumptionLimit
	 */
	public String getWtAConsumptionLimit()
				 throws AgentException{
		// Fill up the stub with required processing

		return wtAConsumptionLimit;
	}

	/**
	 * Handles the SNMP Set Request for wtAConsumptionLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setWtAConsumptionLimit(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		wtAConsumptionLimit = value;
	}

	/**
	 * Handles the SNMP Get Request for wtApConsumptionLimit
	 */
	public String getWtApConsumptionLimit()
				 throws AgentException{
		// Fill up the stub with required processing

		return wtApConsumptionLimit;
	}

	/**
	 * Handles the SNMP Set Request for wtApConsumptionLimit
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setWtApConsumptionLimit(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		wtApConsumptionLimit = value;
	}


}