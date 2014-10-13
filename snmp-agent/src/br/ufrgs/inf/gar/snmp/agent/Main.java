package br.ufrgs.inf.gar.snmp.agent;

import java.io.IOException;

import org.snmp4j.smi.OID;

public class Main {
	
	// These are both standard in RFC-1213
	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	static final OID interfacesTable = new OID(".1.3.6.1.2.1.2.2.1");
		
	static Agent agent;
	
	public static void main(String[] args) {
		
		try {
			agent = new Agent("127.0.0.1/55555");
			agent.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		// Since BaseAgent registers some mibs by default we need to unregister
		// one before we register our own sysDescr. Normally you would
		// override that method and register the mibs that you need
		agent.unregisterManagedObject(agent.getSnmpv2MIB());		
		// Register a system description, use one from you product environment
		// to test with
		agent.registerManagedObject(
				MOScalarFactory.createReadOnly(sysDescr,"MySystemDescr"));
		
		while(true) {
			System.out.println("Agent running...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
