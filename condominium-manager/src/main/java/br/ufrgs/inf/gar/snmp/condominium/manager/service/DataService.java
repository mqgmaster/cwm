package br.ufrgs.inf.gar.snmp.condominium.manager.service;

import java.io.IOException;

import br.ufrgs.inf.gar.snmp.condominium.domain.Condominium;

public class DataService {
	
	public static final String CONDO_NAME = ".1.1.2.1.2.1.0";
	
	public static Condominium getCondominium() throws IOException {
		return new Condominium(
				SNMPManager.get(CONDO_NAME),
				SNMPManager.get(CONDO_NAME),
				SNMPManager.get(CONDO_NAME)
			);
	}

}
