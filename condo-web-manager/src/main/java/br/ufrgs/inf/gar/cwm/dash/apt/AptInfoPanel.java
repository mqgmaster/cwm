package br.ufrgs.inf.gar.cwm.dash.apt;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AptInfoPanel extends Panel {
	
	private static final String TITLE = "Definições";

	private final VerticalLayout layout = new VerticalLayout();
	
    private TextField ownerName;
    private TextField numRooms;
    private TextField numPeople;

	public AptInfoPanel(Integer apartmentId) {
		setCaption(TITLE);
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        layout.addComponent(details);
        
        Apartment apt = null;
        try {
        	apt = DaoService.getApartment(apartmentId);
		} catch (IOException e) {
			e.printStackTrace();
		}

        ownerName = new TextField("Proprietário");
        ownerName.setImmediate(true);
        ownerName.setValue(apt.getOwnerName());
        details.addComponent(ownerName);
        
        numRooms = new TextField("Número de quartos");
        numRooms.setImmediate(true);
        numRooms.setValue(String.valueOf(apt.getNumRooms()));
        details.addComponent(numRooms);
        
        numPeople = new TextField("Pessoas presentes");
        numPeople.setImmediate(true);
        numPeople.setValue(String.valueOf(apt.getNumPeople()));
        details.addComponent(numPeople);
        
		setContent(layout);
		setSizeFull();
	}
}
