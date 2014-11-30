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

public class GarageInstrument implements InstrumentHandlerInterface{

	protected Integer ggCount = new Integer(1);

	final static int GGCOUNT = 1;

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

		toReturn.put(new Integer(GGCOUNT),getGgCount());
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
					case GGCOUNT:
						toReturn.put(new Integer(GGCOUNT),getGgCount());
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
				case GGCOUNT:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}


	/**
	 * Handles the SNMP Get Request for ggCount
	 */
	public Integer getGgCount()
				 throws AgentException{
		// Fill up the stub with required processing

		return ggCount;
	}

	/**
	 * Handles the SNMP Set Request for ggCount
	 * @param value - The Integer value to be set
	 */
	public synchronized void setGgCount(Integer value){
		// Fill up the stub with required processing

		ggCount = value;
	}


}