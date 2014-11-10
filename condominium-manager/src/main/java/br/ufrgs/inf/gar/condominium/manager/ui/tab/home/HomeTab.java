package br.ufrgs.inf.gar.condominium.manager.ui.tab.home;

import java.io.IOException;

import br.ufrgs.inf.gar.condominium.manager.data.DataService;
import br.ufrgs.inf.gar.condominium.manager.data.SNMPManager;
import br.ufrgs.inf.gar.condominium.manager.ui.tab.generic.AbstractTab;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class HomeTab extends AbstractTab<HomeLayout> {
	
	public HomeTab() {
		super(HomeLayout.class);
		
		/**
		* OID - .1.3.6.1.2.1.1.4.0 => sysContact
		* => MIB explorer/ browser é util para confirmar isso
		*/
		layout.getOidField().setValue(".1.3.6.1.2.1.1.4.0");
		layout.getOidButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            try {
	            	layout.getResponseLabel().setValue(
	            			SNMPManager.get(layout.getOidField().getValue()));
	            	DataService.test();
				} catch (IOException e) {
					layout.getResponseLabel().setValue("error?");
					e.printStackTrace();
				}
	        }
	    });
	}
}
