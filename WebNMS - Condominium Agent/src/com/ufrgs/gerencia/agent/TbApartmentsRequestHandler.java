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
import com.adventnet.snmp.snmp2.SnmpInt;
import com.adventnet.snmp.snmp2.SnmpNull;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.adventnet.snmp.snmp2.SnmpVarBind;
import com.adventnet.snmp.snmp2.SnmpString;

// SNMP Agent API Imports
import com.adventnet.snmp.snmp2.agent.AgentNode;
import com.adventnet.snmp.snmp2.agent.AgentSnmpException;
import com.adventnet.snmp.snmp2.agent.BaseTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.AgentUtil;
import com.adventnet.snmp.snmp2.agent.ChangeEvent;
import com.adventnet.snmp.snmp2.agent.VarBindRequestEvent;
import com.adventnet.snmp.snmp2.agent.VarBindAndFailure;

// Agent Utility API Imports
import com.adventnet.utils.agent.AgentTableModel;
import com.adventnet.utils.agent.RowStatusHandlerInterface;
import com.adventnet.utils.agent.utils;
import com.adventnet.utilities.common.AgentException;

// Java Imports
import java.io.File; 
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;


/** 
 * Handles all requests under
 * tbApartments group
 */ 

public class TbApartmentsRequestHandler extends BaseTableRequestHandler{

	final static int TBAPENTRYINDEX = 1;
	final static int TBAPENTRYNUMBER = 2;
	final static int TBAPENTRYOWNER = 3;
	final static int TBAPENTRYROOMS = 4;
	final static int TBAPENTRYPEOPLE = 5;
	final static int TBAPENTRYSECTOR = 6;


	private final static int[]tbApartmentsOidRep = {1,3,6,1,4,1,12619,1,1,2,1};

	/**
	 * To get OID array of this Table Group
	* @return OID array of this Table Group
	*/
	public static int[] getTbApartmentsOidRep(){
		return tbApartmentsOidRep;
	}

	// This is generated to preserve the old API
	public int[] getOidRep(){
		return getTbApartmentsOidRep();
	}
	CondominioAgent agentName = null;

	// For Atomic SET handling
	private static final int REMOVE_ENTRY = -1;

	/**
	* Used for retreiving Sub-Ids of the Attributes in this Table Group
	* @return array of Sub-Ids of the Attributes in this Table group
	**/
	protected int[] getSubidList(){
		int[] subidList = {1,2,3,4,5,6};
		return subidList;
	}

	public TbApartmentsRequestHandler(CondominioAgent agentRef){

		tModelComplete =  new AgentTableModel();
		agentName = agentRef;

		TbApartmentsXMLToVector xmlToVector = new TbApartmentsXMLToVector(CondominioAgent.agentDir + File.separator + "xmlFiles" + File.separator +  "CONDOMINIO-MIB", "TbApartments.xml", agentName);
		try{
			xmlToVector.readFromFile();
		}catch(Exception e){
			//e.printStackTrace();
		}
		this.addUpdateListener(xmlToVector);
		xmlToVector.setTableRequestHandler(this);
		super.setWriteEachTime(false);
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
			objectHash.put("TBAPENTRYINDEX",new Integer(1));
			objectHash.put("TBAPENTRYNUMBER",new Integer(2));
			objectHash.put("TBAPENTRYOWNER",new Integer(3));
			objectHash.put("TBAPENTRYROOMS",new Integer(4));
			objectHash.put("TBAPENTRYPEOPLE",new Integer(5));
			objectHash.put("TBAPENTRYSECTOR",new Integer(6));
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
			objectTypeHash.put("TBAPENTRYINDEX",new Integer(2));
			objectTypeHash.put("TBAPENTRYNUMBER",new Integer(2));
			objectTypeHash.put("TBAPENTRYOWNER",new Integer(4));
			objectTypeHash.put("TBAPENTRYROOMS",new Integer(2));
			objectTypeHash.put("TBAPENTRYPEOPLE",new Integer(2));
			objectTypeHash.put("TBAPENTRYSECTOR",new Integer(2));
		}
		return objectTypeHash;
	}


	/**
	 * This method along with processGetRequest, will be used for processing the incoming GET Requests
	* @param pe VarbindRequestEvent
	* @param entry Entry Object, which contains getter/setter methods for the attributes in this Table Group
	* @param attributesList List of SnmpVarbinds of Attributes for which GET Request is to be perfortmed
	* @param varbindIndexHash Hashtable for storing the index of the incoming varbinds
	* @return List of Objects, for which GET is failed
	*/
	protected Vector processReadRequest(VarBindRequestEvent pe,
					TbApEntry entry,Vector attributesList,
					Hashtable varbindIndexHash){

		SnmpVarBind varb = null;
		Vector failedList = new Vector();
		int columnID=0;
		int index = 0;

		for(int i=0;i<attributesList.size();i++){
			varb = (SnmpVarBind)attributesList.elementAt(i);
			int[] oid = (int[]) varb.getObjectID().toValue();

			if (oid.length < tbApartmentsOidRep.length + 1){
				columnID =TBAPENTRYINDEX;
			}
			else{
				columnID = oid[tbApartmentsOidRep.length];
			}

			try {
				Object toReturn = null;

				switch(columnID){
					case TBAPENTRYINDEX:
						toReturn = entry.getTbApEntryIndex();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var0 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var0);
						break;

					case TBAPENTRYNUMBER:
						toReturn = entry.getTbApEntryNumber();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var1 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var1);
						break;

					case TBAPENTRYOWNER:
						toReturn = entry.getTbApEntryOwner();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						// Generating for other TC
						SnmpString var2 = new SnmpString((String )toReturn);
						varb.setVariable(var2);
						break;

					case TBAPENTRYROOMS:
						toReturn = entry.getTbApEntryRooms();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var3 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var3);
						break;

					case TBAPENTRYPEOPLE:
						toReturn = entry.getTbApEntryPeople();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var4 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var4);
						break;

					case TBAPENTRYSECTOR:
						toReturn = entry.getTbApEntrySector();
						if(toReturn == null){
							AgentUtil.throwNoSuchInstance(pe.getVersion());
						}
						SnmpInt var5 = new SnmpInt(((Integer )toReturn).intValue());
						varb.setVariable(var5);
						break;

					default:
						AgentUtil.throwNoSuchInstance(pe.getVersion());
				} //end of switch
			}catch(Exception exp){
				index =((Integer)varbindIndexHash.get(varb)).intValue();
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(), ae);
					failedList.addElement(new VarBindAndFailure(ase,index));
				}
				else if (exp instanceof AgentSnmpException) {
					failedList.addElement(new VarBindAndFailure((AgentSnmpException)exp,index));
				}
				else {
					failedList.addElement(new VarBindAndFailure(
						new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,index));
				}
				utils.handleError(exp);
			}
			SnmpOID objectId 
				= AgentUtil.getColumnarOid(tbApartmentsOidRep,columnID,entry.getInstanceOID());
			varb.setObjectID(objectId);
		}
		return failedList;
	}

	/**
	* This method along with the ProcessSetRequest is used for processing the Incoming Set Requests
	* @param instanceString Index of the row in which SET Request is to be performed
	* @param list List of SnmpVarbinds of Attributes for which SET is performed
	* @param pe VarbindRequest Event
	* @param varbindIndexHash Hashtable for storing the index of the incoming varbinds
	* @return  List of Objects, for which SET is failed
	*/
	public Vector processWriteRequest(String instanceString,
					Vector list,VarBindRequestEvent pe,
					Hashtable varbindIndexHash){

		SnmpVar[] indexVar = null;
		Hashtable trapHash = new Hashtable();

		Vector columnsList = new Vector();
		TbApEntry entry = null;
		SnmpVarBind varb = null;
		Hashtable toSendHash = new Hashtable();
		Vector failedList = new Vector();
		Hashtable indexHash = new Hashtable();
		Integer index = new Integer(0);
		boolean newRow = false;

		int[] instArray = utils.stringToIntArray(instanceString);

		try{
			indexVar = new SnmpVar[1];
			byte[] type = {
				SnmpAPI.INTEGER
			  };

			// Resolving the Index
			indexVar = AgentUtil.resolveIndex(instArray, type );
			if(indexVar == null){
				AgentUtil.throwWrongValue(pe.getVersion());
			}
		}catch(Exception ex){
			varb = (SnmpVarBind)list.elementAt(0);
			index =(Integer)varbindIndexHash.get(varb);
			failedList.addElement(new VarBindAndFailure((AgentSnmpException)ex,index.intValue()));
			return failedList;
		}

		entry = (TbApEntry )tModelComplete.get(instArray);

		if(entry == null && !pe.isRollback()) {
			entry =  agentName.getTbApEntryInstance();
			entry.setAgentRef(agentName);
			entry.setInstanceOID(instArray);
			try{
				entry.setTbApEntryIndex((Integer )indexVar[0].getVarObject());
				addRow(entry);
				newRow = true;
			}catch(Exception exp){
				varb = (SnmpVarBind)list.elementAt(0);
				index =(Integer)varbindIndexHash.get(varb);
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(), ae);
					failedList.addElement(new VarBindAndFailure(ase,index.intValue()));
					utils.handleError(ase);
				}
				else if(exp instanceof AgentSnmpException){
					utils.handleError(exp);
					failedList.addElement(new VarBindAndFailure((AgentSnmpException)exp,index.intValue()));
				}
				return failedList;
			}
		}

		int size = list.size();
		for(int i=0;i<size;i++){
			try{
				varb = (SnmpVarBind)list.elementAt(i);
				SnmpVar var =  varb.getVariable();
				// For Atomic SET handling
				if(var instanceof SnmpInt) {
					Integer value = (Integer )var.getVarObject();
					if(value.intValue() == REMOVE_ENTRY) {
						if(entry != null && pe.isRollback()){
							remove(entry);
						}
						return failedList;
					}
				}
				int[] oid = (int[]) varb.getObjectID().toValue();
				int columnID = oid[tbApartmentsOidRep.length];
				index =(Integer)varbindIndexHash.get(varb);
				indexHash.put(new Integer(columnID),index);

				switch(columnID){
					case TBAPENTRYINDEX:
						AgentUtil.throwNotWritable(pe.getVersion());

						break;

					case TBAPENTRYNUMBER:
						// Check for the proper instance
						if(!(var instanceof SnmpInt )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						Integer tbApEntryNumberStr = (Integer )var.getVarObject();
						toSendHash.put(new Integer(TBAPENTRYNUMBER),tbApEntryNumberStr);

						columnsList.removeElement(new Integer(TBAPENTRYNUMBER));
						columnsList.addElement(new Integer(TBAPENTRYNUMBER));
						break;

					case TBAPENTRYOWNER:
						// Check for the proper instance
						if(!(var instanceof SnmpString )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						String tbApEntryOwnerStr = (String )var.getVarObject();
						toSendHash.put(new Integer(TBAPENTRYOWNER),tbApEntryOwnerStr);

						columnsList.removeElement(new Integer(TBAPENTRYOWNER));
						columnsList.addElement(new Integer(TBAPENTRYOWNER));
						break;

					case TBAPENTRYROOMS:
						// Check for the proper instance
						if(!(var instanceof SnmpInt )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						Integer tbApEntryRoomsStr = (Integer )var.getVarObject();
						toSendHash.put(new Integer(TBAPENTRYROOMS),tbApEntryRoomsStr);

						columnsList.removeElement(new Integer(TBAPENTRYROOMS));
						columnsList.addElement(new Integer(TBAPENTRYROOMS));
						break;

					case TBAPENTRYPEOPLE:
						// Check for the proper instance
						if(!(var instanceof SnmpInt )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						Integer tbApEntryPeopleStr = (Integer )var.getVarObject();
						toSendHash.put(new Integer(TBAPENTRYPEOPLE),tbApEntryPeopleStr);

						columnsList.removeElement(new Integer(TBAPENTRYPEOPLE));
						columnsList.addElement(new Integer(TBAPENTRYPEOPLE));
						break;

					case TBAPENTRYSECTOR:
						// Check for the proper instance
						if(!(var instanceof SnmpInt )){
							AgentUtil.throwWrongType(pe.getVersion());
						}
						Integer tbApEntrySectorStr = (Integer )var.getVarObject();
						toSendHash.put(new Integer(TBAPENTRYSECTOR),tbApEntrySectorStr);

						columnsList.removeElement(new Integer(TBAPENTRYSECTOR));
						columnsList.addElement(new Integer(TBAPENTRYSECTOR));
						break;

					default:
						AgentUtil.throwNoCreation(pe.getVersion());
				} //end of switch
			}catch(Exception exp){
				if(pe.isRollback()){
					toSendHash.clear();
				}
				// Removing Row if this is a newly added row with multi-var set and one set fails
				if (newRow && ! pe.isRollback()&& size==1) {
					try {
						toSendHash.clear();
						remove(entry);
					} catch (Exception ee) {
					}
				}

				utils.handleError(exp);
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(), ae);
					failedList.addElement(new VarBindAndFailure(ase,index.intValue()));
					break;
				}
				else if(exp instanceof AgentSnmpException){
					failedList.addElement(new VarBindAndFailure((AgentSnmpException)exp,index.intValue()));
					break;
				}
				failedList.addElement(new VarBindAndFailure(
						new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,index.intValue()));
				break;
			}
		}

		if(toSendHash.size() > 0){
			try{
				entry.setAttributes(toSendHash,columnsList);
			}catch(Exception exp){
				Integer temp = entry.getFailedSubId();
				temp = (Integer)indexHash.get(temp);
				if(exp instanceof AgentException){
					AgentException ae = (AgentException )exp;
					AgentSnmpException ase = AgentUtil.convertToAgentSnmpException(pe.getVersion(),ae);
					failedList.addElement(new VarBindAndFailure(ase,temp.intValue()));
					if (newRow && ! pe.isRollback()&& size==1) {
						try {
							remove(entry);
						} catch (Exception ee) { }
					}
				}
				else {
					failedList.addElement(new VarBindAndFailure(
							new AgentSnmpException("Exception occurred: ", SnmpAPI.SNMP_ERR_GENERR) ,temp.intValue()));
				}
			}
		}
		return failedList;
	}


	/**
	* This method along with processReadRequest is used for processing the GET Requests
	* @param varbinds List of SnmpVarbinds for which GET Request is to be performed
	* @param nodes List of Agent Nodes for which GET Request is performed
	* @param pe VarbindRequest Event
	*/
	protected void processGetRequest(Vector varbinds,
 					 Vector nodes,
					 VarBindRequestEvent pe) {

		TbApEntry entry = null;
		SnmpVarBind varb = null;
		AgentNode node = null;
		Hashtable instanceAttributesMap = new Hashtable();
		Vector instanceList = new Vector();
		String insString = null;
		int instArray[] = null;
		Vector attributesList = null;
		Vector failedList = new Vector();
		Hashtable varbindIndexHash = new Hashtable();
		Vector readFailedList = new Vector();
		int[] oid = null;
		int columnID = 0;
		int index = 0;

		int varSize = varbinds.size();
		for (int i = 0; i < varSize; i++) {
			varb = (SnmpVarBind) varbinds.elementAt(i);
			node = (AgentNode)nodes.elementAt(i);
			varbindIndexHash.put(varb,new Integer(i));
			oid = (int[]) varb.getObjectID().toValue();

			ChangeEvent ce = createChangeEvent(SnmpAPI.GET_REQ_MSG, node.getSubId(), varb, oid);
			// Change event will be fired for those who implements ChangeListener
			fireChangeEvent(ce);

			try{
				if (oid.length < tbApartmentsOidRep.length + 2) {
					AgentUtil.throwNoSuchInstance(pe.getVersion());
				}
				instArray = AgentUtil.getInstance(oid,tbApartmentsOidRep.length + 1);
				insString = utils.intArrayToString(instArray);

				if (! instanceAttributesMap.containsKey(insString)) {
					instanceList.addElement(insString);
					attributesList = new Vector();
					attributesList.addElement(varb);
					instanceAttributesMap.put(insString, attributesList);
				}
				else {
					attributesList = (Vector) instanceAttributesMap.get(insString);
					attributesList.addElement(varb);
					instanceAttributesMap.put(insString, attributesList);
				}
			}
			catch (AgentSnmpException e) {
				failedList.addElement(new VarBindAndFailure(e,i));
			}
		} 

		for(int j=0;j<instanceList.size();j++){

			insString = (String)instanceList.elementAt(j);
			instArray=utils.stringToIntArray(insString);
			entry = (TbApEntry)tModelComplete.get(instArray);
			attributesList = (Vector)instanceAttributesMap.get(insString);

			if (entry == null) {
				for(int k=0;k<attributesList.size();k++){
					varb=(SnmpVarBind)attributesList.elementAt(k);
					try{
						// For Atomic SET handling
						varb.setVariable(new SnmpInt(REMOVE_ENTRY));
						AgentUtil.throwNoSuchInstance(pe.getVersion());
					}
					catch (AgentSnmpException e) {
						index =((Integer)varbindIndexHash.get(varb)).intValue();
						failedList.addElement(new VarBindAndFailure(e,index));
					}
				}
			}
			else{
				readFailedList = processReadRequest(pe,entry,attributesList,varbindIndexHash);
				for(int a=0;a<readFailedList.size();a++){
					VarBindAndFailure vbfail = (VarBindAndFailure)readFailedList.elementAt(a);
					failedList.addElement(vbfail);
				}
			}
		}
		if(! pe.isRollback()){
			pe.setFailedList(failedList);
		}
	}

	/**
	* This method along with processWriteRequest is used for processing the incoming SET Requests
	* @param varbinds List of SnmpVarbinds for which SET Request is performed
	* @param nodes List of Agent Nodes for which SET Request is performed
	* @param pe VarbindRequest Event
	*/
	protected void processSetRequest(Vector varbinds,
 					 Vector nodes,
					 VarBindRequestEvent pe) {

		SnmpVarBind varb = null;
		AgentNode node = null;
		Hashtable instanceAttributesMap = new Hashtable();
		Vector instanceList = new Vector();
		String insString = null;
		int instArray[] = null;
		Vector attributesList = null;
		Vector failedList = new Vector();
		Hashtable varbindIndexHash = new Hashtable();
		Vector writeFailedList = new Vector();
		int[] oid = null;

		int varSize = varbinds.size();
		for (int i = 0; i < varSize; i++) {
			varb = (SnmpVarBind) varbinds.elementAt(i);
			node = (AgentNode)nodes.elementAt(i);
			varbindIndexHash.put(varb,new Integer(i));
			oid = (int[]) varb.getObjectID().toValue();

			ChangeEvent ce = createChangeEvent(SnmpAPI.SET_REQ_MSG, node.getSubId(), varb, oid);
			// Change event will be fired for those who implements ChangeListener
			fireChangeEvent(ce);

			try{
				if (oid.length < tbApartmentsOidRep.length + 2) {
					AgentUtil.throwNoCreation(pe.getVersion());
				}
				instArray = AgentUtil.getInstance(oid,tbApartmentsOidRep.length + 1);
				insString = utils.intArrayToString(instArray);

				if (! instanceAttributesMap.containsKey(insString)) {
					instanceList.addElement(insString);
					attributesList = new Vector();
					attributesList.addElement(varb);
					instanceAttributesMap.put(insString, attributesList);
				}
				else {
					attributesList = (Vector) instanceAttributesMap.get(insString);
					attributesList.addElement(varb);
					instanceAttributesMap.put(insString, attributesList);
				}
			}
			catch (AgentSnmpException e) {
				failedList.addElement(new VarBindAndFailure(e,i));
				pe.setFailedList(failedList);
				break;
			}
		} 

		for(int a=0;a < instanceList.size();a++){
			insString = (String)instanceList.elementAt(a);
			attributesList = (Vector) instanceAttributesMap.get(insString);

			writeFailedList = processWriteRequest(insString,attributesList,pe,varbindIndexHash);

			int size = writeFailedList.size();
			if(size>0){
				for(int z=0;z<size;z++){
					VarBindAndFailure vbfail = (VarBindAndFailure)writeFailedList.elementAt(z);
					failedList.addElement(vbfail);
				}
				break;
			}
		}
		if(listener != null){
			listener.writeIntoFile();
		}
		pe.setFailedList(failedList);
	}

	/**
	* This method is used for processing the incoming GETNEXT Requests
	* @param varbinds List of varbinds for which the GETNEXT Request is to be performed
	* @param nodes List of AgentNodes for which GETNEXT Request is to be performed
	* @param pe VarbindRequest Event
	*/
	protected void processGetNextRequest(Vector varbinds,
 					 Vector nodes,
					 VarBindRequestEvent pe) {

		TbApEntry entry = null;
		SnmpVarBind varb = null;
		AgentNode node = null;
		Hashtable instanceAttributesMap = new Hashtable();
		Vector instanceList = new Vector();
		String insString = null;
		int instArray[] = null;
		Vector attributesList = null;
		Vector failedList = new Vector();
		Hashtable varbindIndexHash = new Hashtable();
		Vector readFailedList = new Vector();
		Vector firstVector = new Vector();
		int[] oid = null;
		int index = 0;

		int varSize = varbinds.size();
		for (int i = 0; i < varSize; i++) {
			varb = (SnmpVarBind) varbinds.elementAt(i);
			node = (AgentNode)nodes.elementAt(i);
			varbindIndexHash.put(varb,new Integer(i));
			oid = (int[]) varb.getObjectID().toValue();

			ChangeEvent ce = createChangeEvent(SnmpAPI.GETNEXT_REQ_MSG, node.getSubId(), varb, oid);
			// Change event will be fired for those who implements ChangeListener
			fireChangeEvent(ce);

			if (oid.length < tbApartmentsOidRep.length + 2) {
				firstVector.addElement(varb);
				continue;
			}
			instArray = AgentUtil.getInstance(oid,tbApartmentsOidRep.length + 1);
			insString = utils.intArrayToString(instArray);

			if (! instanceAttributesMap.containsKey(insString)) {
				instanceList.addElement(insString);
				attributesList = new Vector();
				attributesList.addElement(varb);
				instanceAttributesMap.put(insString, attributesList);
			}
			else {
				attributesList = (Vector) instanceAttributesMap.get(insString);
				attributesList.addElement(varb);
				instanceAttributesMap.put(insString, attributesList);
			}
		} 

		if(firstVector.size() > 0){
			instanceList.addElement("FIRST");
			instanceAttributesMap.put("FIRST", firstVector);
		}

		for(int j=0; j<instanceList.size() ; j++){
			insString = (String)instanceList.elementAt(j);
			attributesList = (Vector)instanceAttributesMap.get(insString);

			if(insString.equals("FIRST")){
				entry = ( TbApEntry )tModelComplete.getFirstEntry();
			}
			else{
				instArray=utils.stringToIntArray(insString);
				entry = (TbApEntry)tModelComplete.getNext(instArray);
			}
			while(entry != null){
				readFailedList = processReadRequest(pe,entry,attributesList,varbindIndexHash);
				if(readFailedList.size()>0){
					entry = (TbApEntry )tModelComplete.getNext(entry.getInstanceOID());
				}
				else{
					break;
				}
			}
			if (entry == null) {
				for(int k=0;k<attributesList.size();k++){
					varb=(SnmpVarBind)attributesList.elementAt(k);
					try{
						AgentUtil.throwNoNextObject();
					}
					catch (AgentSnmpException e) {
						index =((Integer)varbindIndexHash.get(varb)).intValue();
						failedList.addElement(new VarBindAndFailure(e,index));
					}
				}
			}
		}
		pe.setFailedList(failedList);
	}


	/**
	 * Used for remvoing a row from this Table
	* @param entry Entry object representing a row in this Table
	* @throws AgentSnmpException Exception is thrown, if row cannot be deleted
	*/
	private void remove(TbApEntry entry)
				throws AgentSnmpException{
		tModelComplete.deleteRow(entry);
	}

	/**
	 * Used for adding a row to this table
	* @param entry Entry object representing a row in this Table
	* @throws AgentSnmpException Exception is thrown, if row is not added properly
	*/
	private void addRow(TbApEntry entry)
				throws AgentSnmpException{
		tModelComplete.addRow(entry);
	}

	/**
	 * The Method to Add a New Row in this Table,
	 * with out giving the instance.
	 */
	public boolean createAndAddNewTbApEntry(
					Integer valueForTbApEntryIndex,
					Integer valueForTbApEntryNumber,
					String valueForTbApEntryOwner,
					Integer valueForTbApEntryRooms,
					Integer valueForTbApEntryPeople,
					Integer valueForTbApEntrySector){

		String instString = createInstanceForTbApEntry(
						valueForTbApEntryIndex);
		return createAndAddTbApEntry(instString,
 					valueForTbApEntryIndex,
 					valueForTbApEntryNumber,
 					valueForTbApEntryOwner,
 					valueForTbApEntryRooms,
 					valueForTbApEntryPeople,
 					valueForTbApEntrySector);
	}

	/**
	 * The Method to create the Instance OID for this entry.
	 */
	public String createInstanceForTbApEntry(
					Integer valueForTbApEntryIndex){

		int[] inst = null;
		int[] ins = null;
		int insInt = valueForTbApEntryIndex.intValue();
		ins = new int[]{insInt};
		inst = utils.addIntArrays(inst, ins);
		return utils.intArrayToString(inst);
	}

	/**
	 * The Method to Add a New Row in this Table,
	 * By giving the instance value String.
	 */
	public boolean createAndAddTbApEntry(String instString, 
					Integer valueForTbApEntryIndex, 
					Integer valueForTbApEntryNumber, 
					String valueForTbApEntryOwner, 
					Integer valueForTbApEntryRooms, 
					Integer valueForTbApEntryPeople, 
					Integer valueForTbApEntrySector){

		int[] inst = utils.stringToIntArray(instString);
		if(tModelComplete.tableGet(inst) != null){
			return false;
		}
		TbApEntry entry = agentName.getTbApEntryInstance();
		try{
			entry.setInstanceOID(inst);
			entry.setTbApEntryIndex(valueForTbApEntryIndex);
			entry.setTbApEntryNumber(valueForTbApEntryNumber);
			entry.setTbApEntryOwner(valueForTbApEntryOwner);
			entry.setTbApEntryRooms(valueForTbApEntryRooms);
			entry.setTbApEntryPeople(valueForTbApEntryPeople);
			entry.setTbApEntrySector(valueForTbApEntrySector);
			addRow(entry);
		}
		catch(Exception ee){
			ee.printStackTrace();
		}
		return true;
	}

}
