package br.ufrgs.inf.gar.snmp.manager.ui.tab.home;

import java.io.IOException;

import br.ufrgs.inf.gar.snmp.manager.service.SNMPManager;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.ManagerTab;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class HomeTab implements ManagerTab<HomeLayout> {
	
	private HomeLayout layout = new HomeLayout();
	
	public HomeTab(final SNMPManager manager) {
		
		/**
		* OID - .1.3.6.1.2.1.1.4.0 => sysContact
		* => MIB explorer/ browser é util para confirmar isso
		*/
		layout.getOidField().setValue(".1.3.6.1.2.1.1.4.0");
		layout.getOidButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            try {
	            	layout.getBaseLayout().addComponent(new Label(
	            			manager.getAsString(layout.getOidField().getValue())));
				} catch (IOException e) {
					layout.getBaseLayout().addComponent(new Label("error?"));
					e.printStackTrace();
				}
	        }
	    });
	}

	@Override
	public HomeLayout getLayout() {
		return layout;
	}
}
