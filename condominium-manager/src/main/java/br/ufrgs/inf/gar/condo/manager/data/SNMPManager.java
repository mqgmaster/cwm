package br.ufrgs.inf.gar.condo.manager.data;

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
	
	private static final String COMMUNITY = "public";
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
	public static String get(OID oid) throws IOException {
		ResponseEvent event = get(new OID[] { oid });
		return event.getResponse().get(0).getVariable().toString();
	}
	
	public static String get(String oid) throws IOException {
		return get(new OID(oid));
	}
	
	/**
	* Metodo GET que a partir de uma lista de OIDs, retorna
	* uma {@link ResponseEvent} contendo uma resposta para cada OID solicitada.
	* 
	* @param oids
	* @return {@link ResponseEvent}
	* @throws IOException
	*/
	private static ResponseEvent get(OID oids[]) throws IOException {
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
		target.setCommunity(new OctetString(COMMUNITY));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
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
		tableRet.setMaxNumColumnsPerPDU(30);
		return tableRet.getTable(getTarget(),columnsOids,null,null).iterator();
	}
}