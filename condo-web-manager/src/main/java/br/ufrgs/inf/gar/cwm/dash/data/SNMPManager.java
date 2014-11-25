package br.ufrgs.inf.gar.cwm.dash.data;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

/**
* Gerente SNMP.
*/
public class SNMPManager {
	
	private static final int WALK_MAX_COLUMNS = 30;
	private static final int MAX_RETRIES = 2;
	private static final int RESPONSE_TIME_OUT = 1500;
	private static final OctetString COMMUNITY = new OctetString("public");
	private static final int SNMP_VERSION = SnmpConstants.version2c;
	private static Snmp snmp;
	private static String ipAddress = null;
	
	private SNMPManager() {
	}
	
	/**
	* Inicia a sessão SNMP. Chama o método {@link #listen()} para aguardar respostas, 
	* pois a comunicação é assincrona.
	* 
	* @throws IOException
	*/
	public static void start(String ipAddress) throws IOException {
		if (snmp != null) {
			throw new UnexpectedException("Servidor já iniciado, operacao ilegal.");
		}
		SNMPManager.ipAddress = ipAddress;
		TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}
	
	/**
	* Faz uma requisição ao agente solicitando o valor associado ao objeto gerenciado, identificado
	* pelo OID passado como parametro.
	* 
	* @param oid
	* @return Valor associado ao objeto gerenciado.
	* @throws IOException
	*/
	public static VariableBinding[] get(OID...oids) throws IOException {
		ResponseEvent event = snmpGet(oids);
		return event.getResponse().toArray();
	}
	
	public static VariableBinding[] get(String...oids) throws IOException {
		List<OID> list = new ArrayList<OID>();
		for (String oid : oids) {
			list.add(new OID(oid));
		}
		OID[] oidsVector = new OID[list.size()];
		oidsVector = list.toArray(oidsVector);
		return get(oidsVector);
	}
	
	/**
	* Metodo GET que a partir de uma lista de OIDs, retorna
	* uma {@link ResponseEvent} contendo uma resposta para cada OID solicitada.
	* 
	* @param oids
	* @return {@link ResponseEvent}
	* @throws IOException
	*/
	private static ResponseEvent snmpGet(OID...oids) throws IOException {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GET);
		ResponseEvent event = snmp.send(pdu, getTarget(), null);
		if(event != null) {
			return event;
		}
		throw new RuntimeException("GET timed out");
	}
	
	/**
	* Constrói um {@link Target}, contendo informacao sobre onde e como
	* obter os dados.
	* 
	* @return {@link Target}
	*/
	private static Target getTarget() {
		Address targetAddress = GenericAddress.parse(ipAddress);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(COMMUNITY);
		target.setAddress(targetAddress);
		target.setRetries(MAX_RETRIES);
		target.setTimeout(RESPONSE_TIME_OUT);
		target.setVersion(SNMP_VERSION);
		return target;
	}

	public static Iterator<TableEvent> walk(String...columnsOids) throws IOException {
		List<OID> list = new ArrayList<OID>();
		for (String oid : columnsOids) {
			list.add(new OID(oid));
		}
		OID[] oids = new OID[list.size()];
		oids = list.toArray(oids);
		return walk(oids);
	}
	
	public static Iterator<TableEvent> walk(OID...columnsOids) throws IOException {
		DefaultPDUFactory localfactory=new DefaultPDUFactory();
		TableUtils tableRet=new TableUtils(snmp,localfactory);
		tableRet.setMaxNumColumnsPerPDU(WALK_MAX_COLUMNS);
		return tableRet.getTable(getTarget(),columnsOids,null,null).iterator();
	}
}