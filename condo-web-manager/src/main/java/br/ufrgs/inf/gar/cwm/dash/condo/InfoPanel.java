package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class InfoPanel extends Panel {
	
	private static final String TITLE = "Informações adicionais";

	private final VerticalLayout layout = new VerticalLayout();
	
	private TextField name;
    private TextField address;
    private TextField managerName;

	public InfoPanel() {
		setCaption(TITLE);
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        layout.addComponent(details);
        
        Condominium condo = null;
        try {
        	condo = DaoService.get().getCondo();
		} catch (IOException e) {
			e.printStackTrace();
		}

        name = new TextField("Nome do Condomínio");
        name.setImmediate(true);
        name.setValue(condo.getName());
        name.setReadOnly(true);
        details.addComponent(name);
        
        address = new TextField("Endereço");
        address.setImmediate(true);
        address.setValue(condo.getAddress());
        address.setReadOnly(true);
        details.addComponent(address);
        
        managerName = new TextField("Síndico");
        managerName.setImmediate(true);
        managerName.setValue(condo.getManagerName());
        managerName.setReadOnly(true);
        details.addComponent(managerName);
        
		setContent(layout);
		setSizeFull();
	}
}
