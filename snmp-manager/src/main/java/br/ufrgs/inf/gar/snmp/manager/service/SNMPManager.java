package br.ufrgs.inf.gar.snmp.manager.service;

import java.io.IOException;

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
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
* Gerente SNMP.
*/
public class SNMPManager {
	
	/**
	* OID - .1.3.6.1.2.1.1.4.0 => sysContact
	* => MIB explorer/ browser é util para confirmar isso
	*/
	public static final OID OID_SYS_CONTACT = new OID(".1.3.6.1.2.1.1.4.0");

	Snmp snmp = null;
	String address = null;

	/**
	* Construtor
	* 
	* @param add IP do agente
	*/
	public SNMPManager(String add)	{
		address = add;
	}
	
	/**
	* Inicia a sessão SNMP. Chama o método {@link #listen()} para aguardar respostas, 
	* pois a comunicação é assincrona.
	* @throws IOException
	*/
	public void start() throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}
	
	/**
	* Faz uma requisição ao agente solicitando o valor associado ao objeto gerenciado, identificado
	* pelo OID passado como parametro.
	* @param oid
	* @return Valor associado ao objeto gerenciado.
	* @throws IOException
	*/
	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(new OID[] { oid });
		return event.getResponse().get(0).getVariable().toString();
	}
	
	/**
	* This method is capable of handling multiple OIDs
	* @param oids
	* @return
	* @throws IOException
	*/
	public ResponseEvent get(OID oids[]) throws IOException {
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
	* This method returns a Target, which contains information about
	* where the data should be fetched and how.
	* @return
	*/
	private Target getTarget() {
		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	public String getAsString(String value) throws IOException {
		return getAsString(new OID(value));
	}
}