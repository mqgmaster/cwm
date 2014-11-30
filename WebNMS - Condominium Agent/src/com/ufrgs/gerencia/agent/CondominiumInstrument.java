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

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class CondominiumInstrument implements InstrumentHandlerInterface{

	protected String cmName     = "cmName not initialized";
	protected String cmAddress  = "cmAddress not initialized";
	protected String cmManager  = "cmManager not initialized";
	protected Integer cmUPeople = new Integer(1);

	final static int CMNAME = 1;
	final static int CMADDRESS = 2;
	final static int CMMANAGER = 3;
	final static int CMUPEOPLE = 4;

	static Integer failedSubId = new Integer(-1);

	/**
	* Try to connect to the database
	*
	*/
	public Connection getConnection() {
	     try {
	          return DriverManager.getConnection("jdbc:mysql://localhost/condominium", "root", "");
	     }
	     catch(SQLException e) {
	          System.out.println("Problemas na conexao com o banco de dados: " + e);
	     }
	     return null;
	}

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

		toReturn.put(new Integer(CMNAME),getCmName());
		toReturn.put(new Integer(CMADDRESS),getCmAddress());
		toReturn.put(new Integer(CMMANAGER),getCmManager());
		toReturn.put(new Integer(CMUPEOPLE),getCmUPeople());
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
					case CMNAME:
						toReturn.put(new Integer(CMNAME),getCmName());
						break;
					case CMADDRESS:
						toReturn.put(new Integer(CMADDRESS),getCmAddress());
						break;
					case CMMANAGER:
						toReturn.put(new Integer(CMMANAGER),getCmManager());
						break;
					case CMUPEOPLE:
						toReturn.put(new Integer(CMUPEOPLE),getCmUPeople());
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
				case CMNAME:
					// This attribute is not Writable !!! 
					break;
				case CMADDRESS:
					// This attribute is not Writable !!! 
					break;
				case CMMANAGER:
					setCmManager((String)var);
					break;
				case CMUPEOPLE:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}


	/**
	 * Handles the SNMP Get Request for cmName
	 */
	public String getCmName()
				 throws AgentException{
		// Fill up the stub with required processing

		return cmName;
	}

	/**
	 * Handles the SNMP Set Request for cmName
	 * @param value - The String value to be set
	 */
	public synchronized void setCmName(String value){
		// Fill up the stub with required processing

		cmName = value;
	}

	/**
	 * Handles the SNMP Get Request for cmAddress
	 */
	public String getCmAddress()
				 throws AgentException{
		// Fill up the stub with required processing

		return cmAddress;
	}

	/**
	 * Handles the SNMP Set Request for cmAddress
	 * @param value - The String value to be set
	 */
	public synchronized void setCmAddress(String value){
		// Fill up the stub with required processing

		cmAddress = value;
	}

	/**
	 * Handles the SNMP Get Request for cmManager
	 */
	public String getCmManager()
				 throws AgentException{
		// Fill up the stub with required processing

		return cmManager;
	}

	/**
	 * Handles the SNMP Set Request for cmManager
	 * @param value - The String value to be set
	 * @throws AgentException on error
	 */
	public synchronized void setCmManager(String value)
				 throws AgentException{
		// Fill up the stub with required processing

		if(value == null){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("", CommonUtils.WRONGVALUE);
		}
		cmManager = value;
	}

	/**
	 * Handles the SNMP Get Request for cmUPeople
	 */
	public Integer getCmUPeople()
				 throws AgentException{
		// Fill up the stub with required processing

		return cmUPeople;
	}

	/**
	 * Handles the SNMP Set Request for cmUPeople
	 * @param value - The Integer value to be set
	 */
	public synchronized void setCmUPeople(Integer value){
		// Fill up the stub with required processing

		cmUPeople = value;
	}


}
