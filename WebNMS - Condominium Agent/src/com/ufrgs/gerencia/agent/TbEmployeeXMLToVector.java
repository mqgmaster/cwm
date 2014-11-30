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

// SNMP Agent API Imports
import com.adventnet.snmp.snmp2.agent.AgentUtil;

// Agent Utility API Imports
import com.adventnet.utils.agent.TableEntry;
import com.adventnet.utils.agent.utils;
import com.adventnet.utilities.common.UpdateListener;

// WebNMS XML Utility API Imports
import com.adventnet.utilities.xml.dom.XMLDataReader;
import com.adventnet.utilities.xml.dom.XMLDataWriter;
import com.adventnet.utilities.xml.dom.XMLNode;

// Java Imports
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class TbEmployeeXMLToVector implements UpdateListener{

	final static int TBEPENTRYINDEX = 1;
	final static int TBEPENTRYNAME = 2;
	final static int TBEPENTRYROLE = 3;
	final static int TBEPENTRYWAGE = 4;
	final static int TBEPENTRYWORKLOAD = 5;
	final static int TBEPENTRYWORKING = 6;
	final static int TBEPENTRYCONDOMINIUM = 7;
	final static String [] columnNames = {"tbEpEntryIndex" , "tbEpEntryName" , "tbEpEntryRole" , "tbEpEntryWage" , "tbEpEntryWorkLoad" , "tbEpEntryWorking" , "tbEpEntryCondominium"  };

	private String dirStr = null;
	private String fileName = null;
	private String name = null;

	private Vector tableVector = null;
	private TbEmployeeRequestHandler handler = null;
	private CondominioAgent agentName;

	public static int getColumnId(String name){

		for(int i=0;i<columnNames.length;i++){
			if(columnNames[i].equals(name)){
				return (i+1);
			}
		}
		return -1;
	}

	public String getColumnValue(TbEpEntry entry, String columnName){

		String toRet = null;
		int columnId = getColumnId(columnName);
		try{
			switch(columnId){
				case TBEPENTRYINDEX:
					toRet = ""+entry.getTbEpEntryIndex();
					break;
				case TBEPENTRYNAME:
					toRet = ""+entry.getTbEpEntryName();
					break;
				case TBEPENTRYROLE:
					toRet = ""+entry.getTbEpEntryRole();
					break;
				case TBEPENTRYWAGE:
					toRet = ""+entry.getTbEpEntryWage();
					break;
				case TBEPENTRYWORKLOAD:
					toRet = ""+entry.getTbEpEntryWorkLoad();
					break;
				case TBEPENTRYWORKING:
					toRet = ""+entry.getTbEpEntryWorking();
					break;
				case TBEPENTRYCONDOMINIUM:
					toRet = ""+entry.getTbEpEntryCondominium();
					break;
			}
		}catch(Exception e){
			System.out.println("Exception"+e.getMessage());
		}
		return toRet;
	}

	public TbEmployeeXMLToVector(String dirStr, String fileName, CondominioAgent agentRef){

		agentName = agentRef;
		if(dirStr == null){
			this.dirStr = "xmlFiles"  + File.separator + "CONDOMINIO-MIB";
		}
		else{
			this.dirStr = dirStr;
		}

		File f = new File(dirStr);
		if(!f.exists()){ // If the Directory does not exist, create it.
			f.mkdirs();
		}
		 if (fileName == null){
			 this.fileName = "TbEmployee.xml";
		}
		else{
			this.fileName = fileName;
		}
		this.name = dirStr + File.separator + fileName;
		tableVector = new Vector();
	}

	public void readFromFile() throws Exception{

		TbEpEntry entry = null;

		new RandomAccessFile(name, "r");

		XMLDataReader xmlReader = new XMLDataReader(name.trim(), false);
		Vector children = xmlReader.getRootChildNodes();

		for(Enumeration en = children.elements(); en.hasMoreElements();){

			XMLNode rowNode = (XMLNode)en.nextElement();

			if(rowNode.getNodeType() == XMLNode.ELEMENT){
				if(rowNode.getNodeName().equalsIgnoreCase("row")){
					entry = agentName.getTbEpEntryInstance();
					for(Enumeration e = rowNode.children();e.hasMoreElements();){

						XMLNode columnNode = (XMLNode )e.nextElement();
						Hashtable indhash = columnNode.getAttributeList();

						String columnName = (String )indhash.get("name");
						String columnValue = (String )indhash.get("value");

						int[] ins = {0};
						int columnId = getColumnId(columnName);
						switch(columnId){
							case TBEPENTRYINDEX:	  
								ins[0] = Integer.parseInt(columnValue);
								ins = utils.addIntArrays(entry.getInstanceOID(),ins);
								entry.setInstanceOID(ins);
								entry.tbEpEntryIndex = new Integer(columnValue);
								break;
							case TBEPENTRYNAME:	  
								entry.tbEpEntryName = new String(columnValue);
								break;
							case TBEPENTRYROLE:	  
								entry.tbEpEntryRole = new String(columnValue);
								break;
							case TBEPENTRYWAGE:	  
								entry.tbEpEntryWage = new Integer(columnValue);
								break;
							case TBEPENTRYWORKLOAD:	  
								entry.tbEpEntryWorkLoad = new Integer(columnValue);
								break;
							case TBEPENTRYWORKING:	  
								entry.tbEpEntryWorking = new Integer(columnValue);
								break;
							case TBEPENTRYCONDOMINIUM:	  
								entry.tbEpEntryCondominium = new Integer(columnValue);
								break;
						} //end of switch
					} // End While (strtok.hasMoreElements())

					boolean flag = false;
					// Checking for duplicate instance while reading from the file
					for(int i=0;i<tableVector.size();i++){
						int[] oldOID = ((TableEntry )tableVector.elementAt(i)).getInstanceOID();
						if(AgentUtil.compareTo(oldOID, entry.getInstanceOID()) == 0){
							tableVector.setElementAt(entry,i);
							flag = true;
							break;
						}
					}
					if(!flag){
						tableVector.addElement(entry);
					}
				}
			} // End of if loop
		}
	}

	public void writeIntoFile(){

		TbEpEntry ent = null;

		byte[] date = null;

		if(handler != null){
			tableVector = handler.getTableVector();
		}
		utils.sort(tableVector);

		XMLNode rootNode = new XMLNode();
		rootNode.setNodeName("Table");
		rootNode.setNodeType(XMLNode.ELEMENT);

		for(int i = 0;i < tableVector.size();i++){

			XMLNode rowNode = new XMLNode();
			rowNode.setNodeName("row");
			rowNode.setNodeType(XMLNode.ELEMENT);
			rootNode.addChildNode(rowNode);

			ent = (TbEpEntry )tableVector.elementAt(i);

			for(int j=0;j<columnNames.length;j++){

				XMLNode columnNode = new XMLNode();
				columnNode.setNodeName("column");
				columnNode.setNodeType(XMLNode.ELEMENT);
				rowNode.addChildNode(columnNode);

				columnNode.setAttribute("name", columnNames[j]);
				columnNode.setAttribute("value", getColumnValue(ent, columnNames[j]));
			}
		}
		XMLDataWriter writer = new XMLDataWriter(name, rootNode);
	}

	public void setTableRequestHandler(TbEmployeeRequestHandler handler){

		this.handler = handler;
		// Sort the entries read from the file
		utils.sort(tableVector);
		this.handler.setTableVector(tableVector);
	}

}
