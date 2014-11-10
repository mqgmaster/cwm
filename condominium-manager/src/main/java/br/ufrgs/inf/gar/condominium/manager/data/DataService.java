package br.ufrgs.inf.gar.condominium.manager.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;

import br.ufrgs.inf.gar.condominium.domain.Apartment;
import br.ufrgs.inf.gar.condominium.domain.Condominium;

public class DataService {
	
	public static final String CONDO_NAME = ".1.1.2.1.2.1.0";
	
	public static Condominium getCondominium() throws IOException {
		return new Condominium(
				SNMPManager.get(CONDO_NAME),
				SNMPManager.get(CONDO_NAME),
				SNMPManager.get(CONDO_NAME)
			);
	}
	
	public static List<Apartment> getAllApartments() throws IOException {
		List<Apartment> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				".1.3.6.1.2.1.4.20.1.1",
				".1.3.6.1.2.1.4.20.1.2",
				".1.3.6.1.2.1.4.20.1.3",
				".1.3.6.1.2.1.4.20.1.4",
				".1.3.6.1.2.1.4.20.1.5"
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			for (VariableBinding column : row.getColumns()) {
				System.out.println(column.getVariable().toString());
			}
		}
		return list;
	}
	
	public static void test() throws IOException {
		Iterator<TableEvent> iterator = SNMPManager.walk(
				".1.3.6.1.2.1.4.20.1.1",
				".1.3.6.1.2.1.4.20.1.2",
				".1.3.6.1.2.1.4.20.1.3",
				".1.3.6.1.2.1.4.20.1.4",
				".1.3.6.1.2.1.4.20.1.5"
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			for (VariableBinding column : row.getColumns()) {
				System.out.println(column.getVariable().toString());
			}
		}
	}
}
