package br.ufrgs.inf.gar.cwm.dash.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.Employee;
import br.ufrgs.inf.gar.condo.domain.Garage;
import br.ufrgs.inf.gar.condo.domain.Lamp;
import br.ufrgs.inf.gar.condo.domain.Sector;

public class DaoService {
	
	public static final String AGENT_ADDRESS = "udp:127.0.0.1/16167";
	
	private static DaoService service = null;

	private static List<Sector> sectorCache = new ArrayList<>();
	private static List<Apartment> aptsCache = new ArrayList<>();
	
	public static void init() throws IOException {
		if (service != null) {
			return;
		}
		service = new DaoService();
		
		try {
			SNMPManager.start(AGENT_ADDRESS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DaoService get() {
		return service;
	}
	
	public Condominium getCondo() throws IOException {
		VariableBinding[] vars = SNMPManager.get(
				MIB.CondoOID.NAME,
				MIB.CondoOID.ADDRESS,
				MIB.CondoOID.MANAGER_NAME);
		return new Condominium(
				octetToASCII(vars[0].getVariable()), 
				octetToASCII(vars[1].getVariable()), 
				octetToASCII(vars[2].getVariable()));
	}
	
	public Condominium getCondoUsages() throws IOException {
		VariableBinding[] vars = SNMPManager.get(
				MIB.CondoOID.WATER_TOTAL_USAGE,
				MIB.CondoOID.WATER_INSTANT_USAGE,
				MIB.CondoOID.WATER_INSTANT_LIMIT,
				MIB.CondoOID.ELECTRIC_TOTAL_USAGE,
				MIB.CondoOID.ELECTRIC_INSTANT_USAGE,
				MIB.CondoOID.ELECTRIC_INSTANT_LIMIT,
				MIB.CondoOID.APT_ELECTRIC_INSTANT_LIMIT,
				MIB.CondoOID.APT_WATER_INSTANT_LIMIT
				);
		Condominium condo = new Condominium();
		
		condo.getTotalWaterUsage().set(octetToASCII(vars[0].getVariable()));
		condo.getInstantWaterUsage().set(octetToASCII(vars[1].getVariable()));
		condo.getInstantWaterLimit().set(octetToASCII(vars[2].getVariable()));
		condo.getTotalElectricUsage().set(octetToASCII(vars[3].getVariable()));
		condo.getInstantElectricUsage().set(octetToASCII(vars[4].getVariable()));
		condo.getInstantElectricLimit().set(octetToASCII(vars[5].getVariable()));
		condo.getAptInstantElectricLimit().set(octetToASCII(vars[6].getVariable()));
		condo.getAptInstantWaterLimit().set(octetToASCII(vars[7].getVariable()));
		return condo;
	}
	
	private String octetToASCII(Variable var) {
		return ((OctetString) var).toASCII(' ');
	}
	
	public List<Apartment> getAllApartmentsUsages() throws IOException {
		List<Apartment> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.ApartmentOID.ElectricTable.APT_NUMBER,
				MIB.ApartmentOID.ElectricTable.ELECTRIC_INSTANT_USAGE,
				MIB.ApartmentOID.ElectricTable.ELECTRIC_TOTAL_USAGE
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Apartment apt = new Apartment();
			apt.setNumber(rowColumns[0].getVariable().toInt());
			apt.getInstantElectricUsage().set(octetToASCII(rowColumns[1].getVariable()));
			apt.getTotalElectricUsage().set(octetToASCII(rowColumns[2].getVariable()));
			list.add(apt);
		}
		return list;
	}
	
	public List<Apartment> getAllApartmentsPeople() throws IOException {
		List<Apartment> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.ApartmentOID.Table.ID,
				MIB.ApartmentOID.Table.NUM_PEOPLE
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Apartment apt = new Apartment();
			apt.setId(rowColumns[0].getVariable().toInt());
			apt.setNumPeople(rowColumns[1].getVariable().toInt());
			list.add(apt);
		}
		return list;
	}
	
	public List<Apartment> getAllApartments() throws IOException {
		List<Apartment> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.ApartmentOID.Table.ID,
				MIB.ApartmentOID.Table.NUMBER,
				MIB.ApartmentOID.Table.OWNER_NAME,
				MIB.ApartmentOID.Table.NUM_PEOPLE,
				MIB.ApartmentOID.Table.NUM_ROOMS,
				MIB.ApartmentOID.Table.SECTOR_ID
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Apartment apt = new Apartment();
			apt.setId(rowColumns[0].getVariable().toInt());
			apt.setNumber(rowColumns[1].getVariable().toInt());
			apt.setOwnerName(octetToASCII(rowColumns[2].getVariable()));
			apt.setNumPeople(rowColumns[3].getVariable().toInt());
			apt.setNumRooms(rowColumns[4].getVariable().toInt());
			list.add(apt);
		}
		Collections.copy(aptsCache, list);
		return list;
	}
	
	public static List<Employee> getAllEmployees() {
		return Simulator.EMPS.get();
	}
	
	public static List<Employee> getAllEmployees2() throws IOException {
		List<Employee> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.EmployeeTableOID.ID,
				MIB.EmployeeTableOID.MONTH_WAGE,
				MIB.EmployeeTableOID.NAME,
				MIB.EmployeeTableOID.ROLE,
				MIB.EmployeeTableOID.WEEK_WORKLOAD,
				MIB.EmployeeTableOID.IS_WORKING
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Employee obj = new Employee();
			obj.setId(rowColumns[0].getVariable().toInt());
			obj.setMonthWage(rowColumns[1].getVariable().toInt());
			obj.setName(rowColumns[2].getVariable().toString());
			obj.setRole(rowColumns[3].getVariable().toString());
			obj.setWeekWorkload(rowColumns[4].getVariable().toInt());
			obj.setWorking(Boolean.valueOf(rowColumns[5].getVariable().toString()));
			list.add(obj);
		}
		return list;
	}
	
	public static List<Garage> getAllGarages() {
		return Simulator.GARS.get();
	}
	
	public static List<Garage> getAllGarages2() throws IOException {
		List<Garage> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.GarageTableOID.ID,
				MIB.GarageTableOID.NUMBER,
				MIB.GarageTableOID.APT_ID,
				MIB.GarageTableOID.IS_OCCUPIED
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Garage obj = new Garage();
			obj.setId(rowColumns[0].getVariable().toInt());
			obj.setNumber(rowColumns[1].getVariable().toInt());
			obj.setApartment(aptsCache.get(rowColumns[2].getVariable().toInt()));
			obj.setOccupied(Boolean.valueOf(rowColumns[3].getVariable().toString()));
			list.add(obj);
		}
		return list;
	}
	
	public static List<Lamp> getAllLamps() {
		return Simulator.LAMPS.get();
	}
	
	public static List<Lamp> getAllLamps2() throws IOException {
		List<Lamp> list = new ArrayList<>();
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.LampTableOID.ID,
				MIB.LampTableOID.IS_ON,
				MIB.LampTableOID.SECTOR_ID
				);
		while (iterator.hasNext()) {
			TableEvent row = iterator.next();
			VariableBinding[] rowColumns = row.getColumns();
			Lamp obj = new Lamp();
			obj.setId(rowColumns[0].getVariable().toInt());
			obj.setOn(Boolean.valueOf(rowColumns[1].getVariable().toString()));
			obj.setSector(sectorCache.get(rowColumns[2].getVariable().toInt()));
			list.add(obj);
		}
		return list;
	}
}
