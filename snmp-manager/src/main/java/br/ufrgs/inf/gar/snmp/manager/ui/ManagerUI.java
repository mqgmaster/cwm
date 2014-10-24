package br.ufrgs.inf.gar.snmp.manager.ui;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import br.ufrgs.inf.gar.snmp.manager.service.SNMPManager;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.apartment.AptStatsTab;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.condominium.CondominiumLightTab;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.home.HomeTab;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.service.TabService;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

@Theme("mytheme")
@SuppressWarnings("serial")
public class ManagerUI extends UI {
	
	public static final String AGENT_ADDRESS = "udp:127.0.0.1/161";

	private static final String BROWSER_TITLE = "SNMP Manager";
	
	/**
	* Porta 161 é usada para gets and sets
	* Porta 162 é usada para traps
	*/
	private final SNMPManager manager = new SNMPManager(AGENT_ADDRESS);
	private final ManagerLayout layout = new ManagerLayout();
	private final TabService tabService = new TabService(layout.getTabs());
	
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ManagerUI.class, 
    	widgetset = "br.ufrgs.inf.gar.snmp.manager.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    	Page.getCurrent().setTitle(BROWSER_TITLE);
        setContent(layout);
        tabService.openTab(new HomeTab(manager));
        layout.getAptosButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new AptStatsTab(manager));
	        }
	    });
        
        layout.getHomeButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new HomeTab(manager));
	        }
	    });
        
        layout.getCondominiumButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new CondominiumLightTab(manager));
	        }
	    });
        
        try {
			manager.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
