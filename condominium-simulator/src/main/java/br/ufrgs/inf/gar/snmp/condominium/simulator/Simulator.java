package br.ufrgs.inf.gar.snmp.condominium.simulator;

import java.util.Random;

import org.hibernate.Session;

import br.ufrgs.inf.gar.snmp.condominium.simulator.domain.Condominium;
import br.ufrgs.inf.gar.snmp.condominium.simulator.persistence.SessionFactoryService;

/**
 * Hello world!
 *
 */
public class Simulator {
	
	private static final long INTERVAL = 5000;
	
    public static void main( String[] args ) {
        System.out.println( "test started" );
        
        final Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        final Integer condoId = (Integer) session.save(new Condominium("test", "Place", "jiarez"));
        session.getTransaction().commit();
        final Random ramdom = new Random();
        
        final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(INTERVAL);
					while (true) {
						session.beginTransaction();
			            Condominium condo = (Condominium) session.get(Condominium.class, condoId);
			            condo.setLightConsumption(ramdom.nextFloat() * 10);
			            session.save(condo);
			            session.getTransaction().commit();
						System.out.println("thread running");
						Thread.sleep(INTERVAL);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("thread stopped");
					e.printStackTrace();
				}
			};
		};
		thread.start();
    }
}
