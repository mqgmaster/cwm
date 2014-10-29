package br.ufrgs.inf.gar.snmp.condominium.simulator.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
/**
 * @author Mauricio Quatrin Guerreiro
 */
 
public class SessionFactoryService {
     
    private static final String RESOURCE_CONFIG_FILE = "/resource/hibernate.cfg.xml";
    private static final String PROPERTY_URL = "hibernate.connection.url";
    private static final String PROPERTY_USERNAME = "hibernate.connection.username";
    private static final String PROPERTY_PASSWORD = "hibernate.connection.password";
    private static final String PROPERTY_URL_VALUE_PREFIX = "jdbc:mysql://";
    private static final String PORT_PREFIX = ":";
    private static final String DB_NAME_PREFIX = "/";
     
    private static final SessionFactory sessionFactory = build();
     
    private static SessionFactory build() {
        Configuration configuration = new Configuration().configure(RESOURCE_CONFIG_FILE);
         
        configuration.setProperty(PROPERTY_URL, getUrlPropertyValue());
        configuration.setProperty(PROPERTY_USERNAME, Settings.db().getUsername());
        configuration.setProperty(PROPERTY_PASSWORD, Settings.db().getPassword());
         
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        configuration.setInterceptor(new DynamicNamingInterceptor());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
     
    private static String getUrlPropertyValue() {
        return PROPERTY_URL_VALUE_PREFIX + 
                Settings.db().getHost() + 
                PORT_PREFIX +
                Settings.db().getPort() + 
                DB_NAME_PREFIX +
                Settings.db().getName();
    }
 
    public static SessionFactory get() {
        return sessionFactory;
    }
}