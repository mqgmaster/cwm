package br.ufrgs.inf.gar.cwm.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.Employee;
import br.ufrgs.inf.gar.condo.domain.Garage;
import br.ufrgs.inf.gar.condo.domain.Lamp;
import br.ufrgs.inf.gar.condo.domain.Sector;

public class Simulator {
	
	private Condominium condo;
	private Sector sectorGarage;
	private Sector sectorFirstFloor;
	private Sector sectorSecondFloor;
	private final List<Apartment> apts = new ArrayList<>();
	private final List<Employee> emps = new ArrayList<>();
	private final List<Garage> gars = new ArrayList<>();
	private final List<Lamp> lamps = new ArrayList<>();
	final Random random = new Random();
	
	private int generateNumPeople(Random random) {
		return random.nextInt(4);
	}

	private float generateLightConsumption(Random random) {
		return random.nextFloat();
	};
	
	private float generateWaterConsumption(Random random) {
		return random.nextFloat() / 100;
	};

	public Simulator() {
		condo = new Condominium("Floresta", "Bento Gonçalves 133", "Silveira");
        
        sectorGarage = new Sector("Garagem", condo);
        sectorFirstFloor = new Sector("Primeiro andar", condo);
        sectorSecondFloor = new Sector("Segundo andar", condo);
        
        apts.add(new Apartment(101, "Silva", 2, sectorFirstFloor));
        apts.add(new Apartment(102, "Silva", 2, sectorFirstFloor));
        apts.add(new Apartment(103, "Bento", 3, sectorFirstFloor));
        apts.add(new Apartment(104, "Bento", 3, sectorFirstFloor));
        apts.add(new Apartment(201, "Silva", 2, sectorSecondFloor));
        apts.add(new Apartment(202, "Silva", 2, sectorSecondFloor));
        apts.add(new Apartment(203, "Bento", 2, sectorSecondFloor));
        apts.add(new Apartment(204, "Bento", 2, sectorSecondFloor));
        
        emps.add(new Employee("Juarez", "Porteiro", 1000, 40, condo));
        emps.add(new Employee("Roberto", "Faxineiro", 1000, 40, condo));
        emps.add(new Employee("Ronaldo", "Porteiro", 1000, 40, condo));
        
        gars.add(new Garage(1, apts.get(0), sectorGarage));
        gars.add(new Garage(2, apts.get(1), sectorGarage));
        
        lamps.add(new Lamp(sectorGarage));
        lamps.add(new Lamp(sectorGarage));
        lamps.add(new Lamp(sectorFirstFloor));
        lamps.add(new Lamp(sectorSecondFloor));
        
        condo.setLightConsumption(generateLightConsumption(random));  //ex: 0.449
        condo.setWaterConsumption(generateWaterConsumption(random));  //ex: 0.008
        if (random.nextInt(10) == 1) 
        	condo.setNumUnknownPeople(random.nextInt(10));
        else condo.setNumUnknownPeople(0);

        for (Apartment apt : apts) {
            apt.setLightConsumption(generateLightConsumption(random)); 
            apt.setWaterConsumption(generateWaterConsumption(random));
            apt.setNumPeople(generateNumPeople(random));
        }

        for (Employee employee : emps) {
            employee.setWorking(!employee.isWorking());
        }
        for (Garage garage : gars) {
            garage.setOccupied(!garage.isOccupied());
        }
	}

	public List<Apartment> getApts() {
		return apts;
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public Condominium getCondo() {
		return condo;
	}

	public List<Garage> getGars() {
		return gars;
	}

	public List<Lamp> getLamps() {
		return lamps;
	}
}
