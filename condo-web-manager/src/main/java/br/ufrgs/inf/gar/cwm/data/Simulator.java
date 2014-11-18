package br.ufrgs.inf.gar.cwm.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.Employee;
import br.ufrgs.inf.gar.condo.domain.Garage;
import br.ufrgs.inf.gar.condo.domain.Lamp;
import br.ufrgs.inf.gar.condo.domain.Sector;

/**
 * Condominium Simulator
 * 
 * Cria um condominio na base de dados e se mantem ativo atualizando
 * o consumo de luz e agua, alem do numero de pessoas presentes nos locais.
 */
public class Simulator {

	
	private final static long INTERVAL_ONE = 5000;
	private final static long INTERVAL_TWO = 8000;
	public static final AtomicReference<Condominium> CONDO = new AtomicReference<>();
	public static final AtomicReference<Sector> SECTOR_GARAGE = new AtomicReference<>();
	public static final AtomicReference<Sector> SECTOR_FIRST_FLOOR = new AtomicReference<>();
	public static final AtomicReference<Sector> SECTOR_SECOND_FLOOR = new AtomicReference<>();
	public static final AtomicReference<List<Apartment>> APTS = new AtomicReference<>(new ArrayList<>());
	public static final AtomicReference<List<Employee>> EMPS = new AtomicReference<>(new ArrayList<>());
	public static final AtomicReference<List<Garage>> GARS = new AtomicReference<>(new ArrayList<>());
	public static final AtomicReference<List<Lamp>> LAMPS = new AtomicReference<>(new ArrayList<>());
	
    public static void start() {
        System.out.println( "test started" );
        
        createData();
        startUpdater();
    }

	private static void startUpdater() {
        final Thread threadOne = new Thread() {
			public void run() {
				final Random random = new Random();
				try {
					while (true) {
			            //acumulado de 5 segundos, devido ao interalo da thread
			            CONDO.get().setLightConsumption(generateLightConsumption(random));  //ex: 0.449
			            CONDO.get().setWaterConsumption(generateWaterConsumption(random));  //ex: 0.008
			            if (random.nextInt(10) == 1) 
			            	 CONDO.get().setNumUnknownPeople(random.nextInt(10));
			            else  CONDO.get().setNumUnknownPeople(0);

			            for (Apartment apt : APTS.get()) {
				            apt.setLightConsumption(generateLightConsumption(random)); 
				            apt.setWaterConsumption(generateWaterConsumption(random));
			            }
						Thread.sleep(INTERVAL_ONE);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("thread stopped");
					e.printStackTrace();
				}
			}
		};
		threadOne.start();
		
		final Thread thread = new Thread() {
			public void run() {
				final Random random = new Random();
				try {
					while (true) {
			            for (Apartment apt : APTS.get()) {
				            apt.setNumPeople(generateNumPeople(random));
			            }
			            
			            for (Employee employee : EMPS.get()) {
				            employee.setWorking(!employee.isWorking());
			            }
			            
			            for (Garage garage : GARS.get()) {
				            garage.setOccupied(!garage.isOccupied());
			            }
						Thread.sleep(INTERVAL_TWO);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("thread stopped");
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	private static int generateNumPeople(Random random) {
		return random.nextInt(4);
	}

	private static float generateLightConsumption(Random random) {
		return random.nextFloat();
	};
	
	private static float generateWaterConsumption(Random random) {
		return random.nextFloat() / 100;
	};

	private static void createData() {
		CONDO.set(new Condominium("Floresta", "Bento Gonçalves 133", "Silveira"));
        SECTOR_GARAGE.set(new Sector("Garagem", CONDO.get()));
        SECTOR_FIRST_FLOOR.set(new Sector("Primeiro andar", CONDO.get()));
        SECTOR_SECOND_FLOOR.set(new Sector("Segundo andar", CONDO.get()));
        
        APTS.get().add(new Apartment(101, "Silva", 2, SECTOR_FIRST_FLOOR.get()));
        APTS.get().add(new Apartment(102, "Silva", 2, SECTOR_FIRST_FLOOR.get()));
        APTS.get().add(new Apartment(103, "Bento", 3, SECTOR_FIRST_FLOOR.get()));
        APTS.get().add(new Apartment(104, "Bento", 3, SECTOR_FIRST_FLOOR.get()));
        APTS.get().add(new Apartment(201, "Silva", 2, SECTOR_SECOND_FLOOR.get()));
        APTS.get().add(new Apartment(202, "Silva", 2, SECTOR_SECOND_FLOOR.get()));
        APTS.get().add(new Apartment(203, "Bento", 2, SECTOR_SECOND_FLOOR.get()));
        APTS.get().add(new Apartment(204, "Bento", 2, SECTOR_SECOND_FLOOR.get()));
        
        EMPS.get().add(new Employee("Juarez", "Porteiro", 1000, 40, CONDO.get()));
        EMPS.get().add(new Employee("Roberto", "Faxineiro", 1000, 40, CONDO.get()));
        EMPS.get().add(new Employee("Ronaldo", "Porteiro", 1000, 40, CONDO.get()));
        
        GARS.get().add(new Garage(1, APTS.get().get(0), SECTOR_GARAGE.get()));
        GARS.get().add(new Garage(2, APTS.get().get(1), SECTOR_GARAGE.get()));
        
        LAMPS.get().add(new Lamp(SECTOR_GARAGE.get()));
        LAMPS.get().add(new Lamp(SECTOR_GARAGE.get()));
        LAMPS.get().add(new Lamp(SECTOR_FIRST_FLOOR.get()));
        LAMPS.get().add(new Lamp(SECTOR_SECOND_FLOOR.get()));
	}
}