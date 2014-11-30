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

// SNMP API Imports
import com.adventnet.snmp.snmp2.SnmpAPI;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.adventnet.snmp.snmp2.SnmpVarBind;
import com.adventnet.snmp.snmp2.SnmpString;
import com.adventnet.snmp.snmp2.SnmpInt;

// SNMP Agent API Imports
import com.adventnet.snmp.snmp2.agent.AgentNode;
import com.adventnet.snmp.snmp2.agent.AgentSnmpException;
import com.adventnet.snmp.snmp2.agent.AgentUtil;
import com.adventnet.snmp.snmp2.agent.SimpleRequestHandler;
import com.adventnet.snmp.snmp2.agent.VarBindRequestEvent;
import com.adventnet.snmp.snmp2.agent.VarBindAndFailure;

// Agent Utility API Imports
import com.adventnet.utils.agent.utils;
import  com.adventnet.utils.agent.ScalarModelListener;
import com.adventnet.utilities.common.AgentException;

// Java Imports
import java.util.Hashtable;
import java.util.Vector;


/** 
 * Handles all requests under
 * condominium group
 */ 

public class CondominiumRequestHandler extends SimpleRequestHandler{

	final static int CMNAME = 1;
	final static int CMADDRESS = 2;
	final static int CMMANAGER = 3;
	final static int CMUPEOPLE = 4;

	private final static int[]condominiumOidRep = {1,3,6,1,4,1,12619,1,2};

	/**
	 * To get OID array of this Scalar Group
	* @return OID array of this Scalar Group
	*/
	public static int[] getCondominiumOidRep(){
		return condominiumOidRep;
	}

	// This is generated to preserve the old API
	public int[] getOidRep(){
		return getCondominiumOidRep();
	}
	public CondominioAgent agentName;

	/**
	* Used for retreiving Sub-Ids of the Attributes in this Scalar Group
	* @return array of Sub-Ids of the Attributes in this Scalar group
	*/
	protected int[] getSubidList(){
		int[] subidList = {1,2,3,4};
		return subidList;
	}

	CondominiumInstrument instrument = null;

	public void setCondominiumInstrument(CondominiumInstrument instrument){
		this.instrument = instrument;
		this.addInstrumentHandler(instrument);
	}

	public CondominiumRequestHandler(CondominioAgent agentRef){
		agentName = agentRef;
	}


	private Hashtable objectHash;
	private Hashtable objectTypeHash;

	/**
	* Used for retreiving Sub-Ids of the Attributes in this Scalar Group
 	* @return Hashtable having AttributeName(String) - SubId(Integer) pairs
	*/
	public Hashtable getObjectHash(){
		if(objectHash == null){
			objectHash = new Hashtable();
			objectHash.put("CMNAME",new Integer(1));
			objectHash.put("CMADDRESS",new Integer(2));
			objectHash.put("CMMANAGER",new Integer(3));
			objectHash.put("CMUPEOPLE",new Integer(4));
		}
		return objectHash;
	}

	/**
	* Used for retreiving Syntax-Types of the Attributes in this Scalar Group
	* @return Hashtable having AttributeName(String) - Syntax Type(Integer) pairs
	*/
	public Hashtable getObjectTypeHash(){
		if(objectTypeHash == null){
			objectTypeHash = new Hashtable();
			objectTypeHash.put("CMNAME",new Integer(4));
			objectTypeHash.put("CMADDRESS",new Integer(4));
			objectTypeHash.put("CMMANAGER",new Integer(4));
			objectTypeHash.put("CMUPEOPLE",new Integer(2));
		}
		return objectTypeHash;
	}


	/**
	* This method is used for processing the incoming GET Requests
	* @param listOfVarbinds List of SnmpVarbinds for which GET Request is to be performed
	* @param listOfNodes List of AgentNodes for which GET Request is to be performed
	* @param pe VarbindRequestEvent
	*/
	protected void processGetRequest(Vector listOfVarbinds,
					 Vector listOfNodes,
					 VarBindRequestEvent pe) {
		SnmpVarBind varb = null;
		AgentNode node = null;
		Vector failedList = new Vector();
		Vector listOfColumns = new Vector();
		int nodeSize = listOfNodes.size();

		for(int i=0;i<nodeSize;i++){
			try{
				node = (AgentNode)listOfNodes.elementAt(i);
				varb =(SnmpVarBind) listOfVarbinds.elementAt(i);
				if(node == null){
					AgentUtil.throwNoSuchInstance(pe.getVersion());
				}
				int[] oid = (int[] )varb.getObjectID().toValue();
				if(oid.length != condominiumOidRep.length + 2){
					AgentUtil.throwNoSuchInstance(pe.getVersion());
				}

				int[] inst = AgentUtil.getInstance(oid,condominiumOidRep.length);
				if(inst[1] != 0){
					AgentUtil.throwNoSuchInstance(pe.getVersion());
				}
				listOfColumns.addElement(new Integer(node.getSubId()));
			}catch(Exception exp){
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,i));
				}
				else if(exp instanceof AgentSnmpException){
					failedList.addElement(new VarBindAndFailure((AgentSnmpException )exp,i));
				}
				else{
					failedList.addElement(new VarBindAndFailure(
							new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,i));
				}
				utils.handleError(exp);
				listOfColumns.addElement(new Integer(-1));
			}
		} // end of for loop
		if(listOfColumns.size() > 0) {
			getValuesFromInstrument(pe,listOfVarbinds,listOfColumns,failedList);
		}
		if(!pe.isRollback()){
			pe.setFailedList(failedList);
		}
	}

	/**
	* This method is used for processing the incoming SET Requests
	* @param listOfVarbinds List of SnmpVarbinds for which SET Request is to be performed
	* @param listOfNodes List of Agent Nodes for which SET Request is to be performed
	* @param pe VarbindRequest Event
	*/
	protected void processSetRequest(Vector listOfVarbinds,
					 Vector listOfNodes,
					 VarBindRequestEvent pe) {
		SnmpVarBind varb = null;
		AgentNode node = null;
		Vector failedList = new Vector();
		Hashtable toSendHash = new Hashtable();
		Vector indexList = new Vector();
		Hashtable indexHash = new Hashtable();
		Vector trapVector = new Vector();

		int nodeSize = listOfNodes.size();

		for(int i=0;i<nodeSize;i++){
			try{
				node = (AgentNode)listOfNodes.elementAt(i);
				varb =(SnmpVarBind) listOfVarbinds.elementAt(i);
				if(node == null){
					AgentUtil.throwNoCreation(pe.getVersion());
				}
				int[] oid = (int[] )varb.getObjectID().toValue();
				if(oid.length != condominiumOidRep.length + 2){
					AgentUtil.throwNoCreation(pe.getVersion());
				}

				int[] inst = AgentUtil.getInstance(oid,condominiumOidRep.length);
				if(inst[1] != 0){
					AgentUtil.throwNoCreation(pe.getVersion());
				}
				indexHash.put(new Integer(node.getSubId()),new Integer(i));
				SnmpVar var =  varb.getVariable();
				switch(node.getSubId()){
					case CMNAME:	  
						AgentUtil.throwNotWritable(pe.getVersion());

						break;

					case CMADDRESS:	  
						AgentUtil.throwNotWritable(pe.getVersion());

						break;

					case CMMANAGER:	  
						// Check for the proper instance
						if(!(var instanceof SnmpString )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						String cmManagerStr = (String )var.getVarObject();
						toSendHash.put(new Integer(CMMANAGER),cmManagerStr);

						indexList.removeElement(new Integer(CMMANAGER));
						indexList.addElement(new Integer(CMMANAGER));
						break;

					case CMUPEOPLE:	  
						AgentUtil.throwNotWritable(pe.getVersion());

						break;

					default:
						AgentUtil.throwNoSuchInstance(pe.getVersion());
				}
			}catch(Exception exp){
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,i));
				}
				else if(exp instanceof AgentSnmpException){
					failedList.addElement(new VarBindAndFailure((AgentSnmpException )exp,i));
				}
				else{
					failedList.addElement(new VarBindAndFailure(
							new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,i));
				}
				utils.handleError(exp);
				break;
			}
		}

		if(toSendHash.size() > 0){
			try{
				instrumentHandler.setAttributes(toSendHash,indexList);
			}catch(Exception exp){
				Integer temp = instrumentHandler.getFailedSubId();
				temp = (Integer)indexHash.get(temp);
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,temp.intValue()));
				}
				else{
					failedList.addElement(new VarBindAndFailure(
							new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,temp.intValue()));
				}
				pe.setFailedList(failedList);
				return;
			}
		}
		pe.setFailedList(failedList);
	}

	/**
	* This method will be used for processing the Incoming GETNEXT Requests
	* @param listOfVarbinds List of SnmpVarbinds for which GETNEXT Request is to be performed
	* @param listOfNodes List of AgentNodes for which GETNEXT Request is to be performed
	* @param pe VarbindRequest Event
	*/
	protected void processGetNextRequest(Vector listOfVarbinds,
					 Vector listOfNodes,
					 VarBindRequestEvent pe) {
		SnmpVarBind varb = null;
		AgentNode node = null;
		Vector failedList = new Vector();
		Vector listOfColumns = new Vector();
		//Vector varbVector = new Vector();
		int columnID;

		int nodeSize = listOfNodes.size();

		for(int i=0;i<nodeSize;i++){
			try{
				node = (AgentNode)listOfNodes.elementAt(i);
				varb =(SnmpVarBind) listOfVarbinds.elementAt(i);
				if(node == null){
					AgentUtil.throwNoNextObject();
				}
				int[] oid = (int[] )varb.getObjectID().toValue();
				columnID=node.getSubId();
				if(oid.length >= condominiumOidRep.length + 2){
					AgentUtil.throwNoNextObject();
				}
				node = node.getNearestLeafCell(oid);
				listOfColumns.addElement(new Integer(node.getSubId()));
			}catch(Exception exp){
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,i));
				}
				else if(exp instanceof AgentSnmpException){
					failedList.addElement(new VarBindAndFailure((AgentSnmpException )exp,i));
				}
				utils.handleError(exp);
				listOfColumns.addElement(new Integer(-1));
			}
		} // end of for loop 
		if(listOfColumns.size() > 0) {
			getValuesFromInstrument(pe,listOfVarbinds,listOfColumns,failedList);
		}
		pe.setFailedList(failedList);
	}

	/**
	* This method is used for getting the attribute values from Instrument file,while processing GET/GETNEXT requests
	* @param pe VarbindRequest Event
	* @param varbVector List of varbinds for which value is to be retreived
	* @return List of Objects for which GETNEXT is failed
	*/
	public void getValuesFromInstrument(VarBindRequestEvent pe, Vector varbVector,Vector columns,Vector failedList) {
		Hashtable valuesHash = new Hashtable();
		int size = columns.size();
		try{
			valuesHash = instrumentHandler.getAttributes(columns.elements());
		}catch(Exception exp) {
			// Exceptions occured during the getAttributes(Enumeration) call will be handled inside that method itself.
			// For missing values in 'valuesHash' , appropriate Snmp Error message will be propagated from inside the 'for loop' below.
		}
		SnmpVarBind varb=null;
		int columnID=0;

		for(int j=0; j< size; j++){
			try{
				Integer key = (Integer)columns.elementAt(j);
				if(key.equals(new Integer(-1))) {
					continue;
				}
				if( ! valuesHash.containsKey(key)) {
					AgentUtil.throwNoSuchInstance(pe.getVersion());
				}
				columnID = key.intValue();
				varb=(SnmpVarBind)varbVector.elementAt(j);
				Object toReturn  = null;

				switch(columnID){
					case CMNAME:	  
						toReturn = valuesHash.get(new Integer(CMNAME));
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						// Generating for other TC
						SnmpString var0 = new SnmpString((String )toReturn);
						varb.setVariable(var0);
						break;

					case CMADDRESS:	  
						toReturn = valuesHash.get(new Integer(CMADDRESS));
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						// Generating for other TC
						SnmpString var1 = new SnmpString((String )toReturn);
						varb.setVariable(var1);
						break;

					case CMMANAGER:	  
						toReturn = valuesHash.get(new Integer(CMMANAGER));
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						// Generating for other TC
						SnmpString var2 = new SnmpString((String )toReturn);
						varb.setVariable(var2);
						break;

					case CMUPEOPLE:	  
						toReturn = valuesHash.get(new Integer(CMUPEOPLE));
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var3 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var3);
						break;

					default:
						AgentUtil.throwNoSuchInstance(pe.getVersion());
				} //end of switch
			}catch(Exception exp){
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,j));
				}
				else if(exp instanceof AgentSnmpException){
					failedList.addElement(new VarBindAndFailure((AgentSnmpException )exp,j));
				}
				else{
					failedList.addElement(new VarBindAndFailure(
							new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,j));
				}
				utils.handleError(exp);
			}
			varb.setObjectID(AgentUtil.getScalarSnmpOID(condominiumOidRep, columnID));
		}
	}

}
