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

public class TbApElectricityXMLToVector implements UpdateListener{

	final static int TBAPELENTRYINDEX = 1;
	final static int TBAPELENTRYNUMBER = 2;
	final static int TBAPELTOTALENTRYCONSUMPTION = 3;
	final static int TBAPELTOTALENTRYLIMIT = 4;
	final static int TBAPELINSTANTENTRYCONSUMPTION = 5;
	final static int TBAPELINSTANTENTRYLIMIT = 6;
	final static String [] columnNames = {"tbApElEntryIndex" , "tbApElEntryNumber" , "tbApElTotalEntryConsumption" , "tbApElTotalEntryLimit" , "tbApElInstantEntryConsumption" , "tbApElInstantEntryLimit"  };

	private String dirStr = null;
	private String fileName = null;
	private String name = null;

	private Vector tableVector = null;
	private TbApElectricityRequestHandler handler = null;
	private CondominioAgent agentName;

	public static int getColumnId(String name){

		for(int i=0;i<columnNames.length;i++){
			if(columnNames[i].equals(name)){
				return (i+1);
			}
		}
		return -1;
	}

	public String getColumnValue(TbApElEntry entry, String columnName){

		String toRet = null;
		int columnId = getColumnId(columnName);
		try{
			switch(columnId){
				case TBAPELENTRYINDEX:
					toRet = ""+entry.getTbApElEntryIndex();
					break;
				case TBAPELENTRYNUMBER:
					toRet = ""+entry.getTbApElEntryNumber();
					break;
				case TBAPELTOTALENTRYCONSUMPTION:
					toRet = ""+entry.getTbApElTotalEntryConsumption();
					break;
				case TBAPELTOTALENTRYLIMIT:
					toRet = ""+entry.getTbApElTotalEntryLimit();
					break;
				case TBAPELINSTANTENTRYCONSUMPTION:
					toRet = ""+entry.getTbApElInstantEntryConsumption();
					break;
				case TBAPELINSTANTENTRYLIMIT:
					toRet = ""+entry.getTbApElInstantEntryLimit();
					break;
			}
		}catch(Exception e){
			System.out.println("Exception"+e.getMessage());
		}
		return toRet;
	}

	public TbApElectricityXMLToVector(String dirStr, String fileName, CondominioAgent agentRef){

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
			 this.fileName = "TbApElectricity.xml";
		}
		else{
			this.fileName = fileName;
		}
		this.name = dirStr + File.separator + fileName;
		tableVector = new Vector();
	}

	public void readFromFile() throws Exception{

		TbApElEntry entry = null;

		new RandomAccessFile(name, "r");

		XMLDataReader xmlReader = new XMLDataReader(name.trim(), false);
		Vector children = xmlReader.getRootChildNodes();

		for(Enumeration en = children.elements(); en.hasMoreElements();){

			XMLNode rowNode = (XMLNode)en.nextElement();

			if(rowNode.getNodeType() == XMLNode.ELEMENT){
				if(rowNode.getNodeName().equalsIgnoreCase("row")){
					entry = agentName.getTbApElEntryInstance();
					for(Enumeration e = rowNode.children();e.hasMoreElements();){

						XMLNode columnNode = (XMLNode )e.nextElement();
						Hashtable indhash = columnNode.getAttributeList();

						String columnName = (String )indhash.get("name");
						String columnValue = (String )indhash.get("value");

						int[] ins = {0};
						int columnId = getColumnId(columnName);
						switch(columnId){
							case TBAPELENTRYINDEX:	  
								ins[0] = Integer.parseInt(columnValue);
								ins = utils.addIntArrays(entry.getInstanceOID(),ins);
								entry.setInstanceOID(ins);
								entry.tbApElEntryIndex = new Integer(columnValue);
								break;
							case TBAPELENTRYNUMBER:	  
								entry.tbApElEntryNumber = new Integer(columnValue);
								break;
							case TBAPELTOTALENTRYCONSUMPTION:	  
								entry.tbApElTotalEntryConsumption = new String(columnValue);
								break;
							case TBAPELTOTALENTRYLIMIT:	  
								entry.tbApElTotalEntryLimit = new String(columnValue);
								break;
							case TBAPELINSTANTENTRYCONSUMPTION:	  
								entry.tbApElInstantEntryConsumption = new String(columnValue);
								break;
							case TBAPELINSTANTENTRYLIMIT:	  
								entry.tbApElInstantEntryLimit = new String(columnValue);
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

		TbApElEntry ent = null;

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

			ent = (TbApElEntry )tableVector.elementAt(i);

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

	public void setTableRequestHandler(TbApElectricityRequestHandler handler){

		this.handler = handler;
		// Sort the entries read from the file
		utils.sort(tableVector);
		this.handler.setTableVector(tableVector);
	}

}
