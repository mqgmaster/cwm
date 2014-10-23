package br.ufrgs.inf.gar.snmp.manager.ui;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import br.ufrgs.inf.gar.snmp.manager.service.SNMPManager;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.aptos.AptosTab;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.home.HomeTab;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

@Theme("mytheme")
@SuppressWarnings("serial")
public class ManagerUI extends UI {
	
	public static final String AGENT_ADDRESS = "udp:127.0.0.1/161";
	
	/**
	* Porta 161 é usada para gets and sets
	* Porta 162 é usada para traps
	*/
	final SNMPManager manager = new SNMPManager(AGENT_ADDRESS);
	final ManagerLayout layout = new ManagerLayout();
	
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ManagerUI.class, 
    	widgetset = "br.ufrgs.inf.gar.snmp.manager.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(layout);
        layout.getTabs().addTab((new HomeTab(manager)).getLayout());
        layout.getAptosButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	layout.getTabs().removeAllComponents();
	        	layout.getTabs().addTab((new AptosTab(manager)).getLayout());
	        }
	    });
        
        layout.getHomeButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	layout.getTabs().removeAllComponents();
	        	layout.getTabs().addTab((new HomeTab(manager)).getLayout());
	        }
	    });
        
        try {
			manager.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
