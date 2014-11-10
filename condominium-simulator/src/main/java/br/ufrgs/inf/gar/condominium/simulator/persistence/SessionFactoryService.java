package br.ufrgs.inf.gar.condominium.simulator.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
/**
 * @author Mauricio Quatrin Guerreiro
 */
 
public class SessionFactoryService {
     
    private static final String RESOURCE_CONFIG_FILE = "hibernate.cfg.xml";
     
    private static final SessionFactory sessionFactory = build();
     
    private static SessionFactory build() {
        Configuration configuration = new Configuration().configure(RESOURCE_CONFIG_FILE);
         
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
     
    public static SessionFactory get() {
        return sessionFactory;
    }
}