package br.ufrgs.inf.gar.snmp.condominium.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;

import br.ufrgs.inf.gar.snmp.condominium.simulator.domain.Apartment;
import br.ufrgs.inf.gar.snmp.condominium.simulator.domain.Condominium;
import br.ufrgs.inf.gar.snmp.condominium.simulator.domain.Employee;
import br.ufrgs.inf.gar.snmp.condominium.simulator.domain.Garage;
import br.ufrgs.inf.gar.snmp.condominium.simulator.persistence.SessionFactoryService;

/**
 * Condominium Simulator
 * 
 * Cria um condominio na base de dados e se mantem ativo atualizando
 * o consumo de luz e agua, alem do numero de pessoas presentes nos locais.
 *
 */
public class Simulator {
	
	private static final long INTERVAL_ONE = 5000;
	private static final long INTERVAL_TWO = 8000;
	private static Integer condominiumId = 7;
	private static final List<Integer> APTS = new ArrayList<Integer>();
	private static final List<Integer> EMPS = new ArrayList<Integer>();
	private static final List<Integer> GARS = new ArrayList<Integer>();
	
    public static void main( String[] args ) {
        System.out.println( "test started" );
        
        createData();
        startUpdater();
    }

	private static void startUpdater() {
        final Thread threadOne = new Thread() {
			public void run() {
				final Random random = new Random();
				Session session;
				try {
					while (true) {
						session = SessionFactoryService.get().openSession();
						session.beginTransaction();
			            Condominium condo = (Condominium) session.get(Condominium.class, condominiumId);
			            //acumulado de 5 segundos, devido ao interalo da thread
			            condo.setLightConsumption(generateLightConsumption(random));  //ex: 0.449
			            condo.setWaterConsumption(generateWaterConsumption(random));  //ex: 0.008
			            session.save(condo);
			            session.getTransaction().commit();
			            
			            session.beginTransaction();	
			            for (Integer aptId : APTS) {
				            Apartment apt = (Apartment) session.get(Apartment.class, aptId);
				            apt.setLightConsumption(generateLightConsumption(random)); 
				            apt.setWaterConsumption(generateWaterConsumption(random));
				            session.save(apt);
			            }
			            session.getTransaction().commit();
			            session.close();
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
				Session session;
				try {
					while (true) {
						session = SessionFactoryService.get().openSession();
						session.beginTransaction();
			            for (Integer aptId : APTS) {
				            Apartment apt = (Apartment) session.get(Apartment.class, aptId);
				            apt.setNumPeople(generateNumPeople(random));
				            session.save(apt);
			            }
			            session.getTransaction().commit();
			            
			            session.beginTransaction();
			            for (Integer employeeId : EMPS) {
				            Employee employee = (Employee) session.get(Employee.class, employeeId);
				            employee.setWorking(!employee.isWorking());
				            session.save(employee);
			            }
			            session.getTransaction().commit();
			            
			            session.beginTransaction();
			            for (Integer garageId : GARS) {
				            Garage garage = (Garage) session.get(Garage.class, garageId);
				            garage.setOccupied(!garage.isOccupied());
				            session.save(garage);
			            }
			            session.getTransaction().commit();
			            session.close();
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
		final Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
		condominiumId = (Integer) session.save(new Condominium("Floresta", "Bento Gonçalves 133", "Silveira"));
        Condominium condo = (Condominium) session.get(Condominium.class, condominiumId);
        
        APTS.add((Integer) session.save(new Apartment(101, "Silva", 2, condo)));
        APTS.add((Integer) session.save(new Apartment(102, "Silva", 2, condo)));
        APTS.add((Integer) session.save(new Apartment(103, "Bento", 3, condo)));
        APTS.add((Integer) session.save(new Apartment(104, "Bento", 3, condo)));
        APTS.add((Integer) session.save(new Apartment(201, "Silva", 2, condo)));
        APTS.add((Integer) session.save(new Apartment(202, "Silva", 2, condo)));
        APTS.add((Integer) session.save(new Apartment(203, "Bento", 2, condo)));
        APTS.add((Integer) session.save(new Apartment(204, "Bento", 2, condo)));
        
        EMPS.add((Integer) session.save(new Employee("Juarez", "Porteiro", 1000, 40, condo)));
        EMPS.add((Integer) session.save(new Employee("Roberto", "Faxineiro", 1000, 40, condo)));
        EMPS.add((Integer) session.save(new Employee("Ronaldo", "Porteiro", 1000, 40, condo)));
        
        GARS.add((Integer) session.save(new Garage(1, (Apartment) session.get(Apartment.class, APTS.get(0)), condo)));
        GARS.add((Integer) session.save(new Garage(2, (Apartment) session.get(Apartment.class, APTS.get(1)), condo)));
        
        session.getTransaction().commit();
        session.close();
	}
}
