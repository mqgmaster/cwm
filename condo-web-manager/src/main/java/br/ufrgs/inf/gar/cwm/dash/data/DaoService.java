package br.ufrgs.inf.gar.cwm.dash.data;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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

	private static Sector[] sectorCache = {new Sector("Garagem", null)};
	
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
		TableEvent row;
		VariableBinding[] rowColumns;
		Apartment apt;
		Iterator<TableEvent> iterator = SNMPManager.walk(
				MIB.ApartmentOID.ElectricTable.ID,
				MIB.ApartmentOID.ElectricTable.APT_NUMBER,
				MIB.ApartmentOID.ElectricTable.ELECTRIC_INSTANT_USAGE,
				MIB.ApartmentOID.ElectricTable.ELECTRIC_TOTAL_USAGE
				);
		while (iterator.hasNext()) {
			row = iterator.next();
			rowColumns = row.getColumns();
			apt = new Apartment();
			apt.setId(rowColumns[0].getVariable().toInt());
			apt.setNumber(rowColumns[1].getVariable().toInt());
			apt.getInstantElectricUsage().set(octetToASCII(rowColumns[2].getVariable()));
			apt.getTotalElectricUsage().set(octetToASCII(rowColumns[3].getVariable()));
			list.add(apt);
		}
		iterator = SNMPManager.walk(
				MIB.ApartmentOID.WaterTable.ID,
				MIB.ApartmentOID.WaterTable.WATER_INSTANT_USAGE,
				MIB.ApartmentOID.WaterTable.WATER_TOTAL_USAGE
				);
		while (iterator.hasNext()) {
			row = iterator.next();
			rowColumns = row.getColumns();
			for (Apartment ap : list) {
				if (ap.getId().equals(rowColumns[0].getVariable().toInt())) {
					ap.getInstantWaterUsage().set(octetToASCII(rowColumns[1].getVariable()));
					ap.getTotalWaterUsage().set(octetToASCII(rowColumns[2].getVariable()));
				}
			}
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
		return list;
	}
	
	public List<Employee> getAllEmployees() throws IOException {
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
			obj.setName(octetToASCII(rowColumns[2].getVariable()));
			obj.setRole(octetToASCII(rowColumns[3].getVariable()));
			obj.setWeekWorkload(rowColumns[4].getVariable().toInt());
			obj.setWorking(toBoolean(rowColumns[5].getVariable()));
			list.add(obj);
		}
		return list;
	}
	
	public List<Garage> getAllGarages() throws IOException {
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
			VariableBinding[] vars = SNMPManager.get(MIB.ApartmentOID.Table.NUMBER + "." + rowColumns[2].getVariable().toInt());
			Apartment apt = new Apartment();
			apt.setId(rowColumns[2].getVariable().toInt());
			apt.setNumber(vars[0].getVariable().toInt());
			obj.setApartment(apt);
			obj.setOccupied(toBoolean(rowColumns[3].getVariable()));
			list.add(obj);
		}
		return list;
	}
	
	public List<Lamp> getAllLamps() throws IOException {
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
			obj.setOn(toBoolean(rowColumns[1].getVariable()));
			obj.setSector(sectorCache[0]);
			list.add(obj);
		}
		return list;
	}
	
	private boolean toBoolean(Variable var) {
		if (var.toInt() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCondoUsageLimits(Condominium condo) throws IOException, ParseException {
		SNMPManager.set(MIB.CondoOID.WATER_INSTANT_LIMIT, condo.getInstantWaterLimit().toString());
		SNMPManager.set(MIB.CondoOID.ELECTRIC_INSTANT_LIMIT, condo.getInstantElectricLimit().toString());
	}
	
	public void setAptsUsageLimits(Condominium condo) throws IOException, ParseException {
		SNMPManager.set(MIB.CondoOID.APT_WATER_INSTANT_LIMIT, condo.getAptInstantWaterLimit().toString());
		SNMPManager.set(MIB.CondoOID.APT_ELECTRIC_INSTANT_LIMIT, condo.getAptInstantElectricLimit().toString());
	}
}
