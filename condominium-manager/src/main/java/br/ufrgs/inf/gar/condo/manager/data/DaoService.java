package br.ufrgs.inf.gar.condo.manager.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;

import br.ufrgs.inf.gar.condominium.domain.Apartment;
import br.ufrgs.inf.gar.condominium.domain.Condominium;
import br.ufrgs.inf.gar.condominium.domain.Employee;
import br.ufrgs.inf.gar.condominium.domain.Garage;
import br.ufrgs.inf.gar.condominium.domain.Lamp;

public class DaoService {
	
	private static final Simulator SIMULATOR = new Simulator();
	
	public static Condominium getCondominium2() throws IOException {
		return new Condominium(
				SNMPManager.get(MIB.CondoOID.NAME),
				SNMPManager.get(MIB.CondoOID.ADDRESS),
				SNMPManager.get(MIB.CondoOID.MANAGER_NAME)
			);
	}
	
	public static Condominium getCondominium() throws IOException {
		return SIMULATOR.getCondo();
	}
	
	public static List<Apartment> getAllApartments2() throws IOException {
		List<Apartment> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.ApartmentOID.Table.ID,
				MIB.ApartmentOID.Table.NUM_PEOPLE,
				MIB.ApartmentOID.Table.NUM_ROOMS,
				MIB.ApartmentOID.Table.NUMBER,
				MIB.ApartmentOID.Table.OWNER_NAME
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			for (VariableBinding column : row.getColumns()) {
				System.out.println(column.getVariable().toString());
			}
		}
		return list;
	}
	
	public static List<Apartment> getAllApartments() throws IOException {
		return SIMULATOR.getApts();
	}
	
	public static List<Employee> getAllEmployees() {
		return SIMULATOR.getEmps();
	}
	
	public static List<Garage> getAllGarages() {
		return SIMULATOR.getGars();
	}
	
	public static List<Lamp> getAllLamps() {
		return SIMULATOR.getLamps();
	}
}
