package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LimitPanel extends Panel implements RefresherComponent {
	
	private static final String TITLE = "Limiares de consumo";

	private VerticalLayout layout = new VerticalLayout();
	
	private final BeanFieldGroup<Condominium> fieldGroup;

	@PropertyId("totalWaterLimit")
    private TextField waterUsageLimit;
    @PropertyId("totalElectricLimit")
    private TextField electricUsageLimit;

	public LimitPanel() {
		setCaption(TITLE);
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		
		fieldGroup = new BeanFieldGroup<Condominium>(Condominium.class);
        fieldGroup.bindMemberFields(this);
		
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        layout.addComponent(details);

        waterUsageLimit = new TextField("Água (litros)");
        waterUsageLimit.setImmediate(true);
        details.addComponent(waterUsageLimit);
        electricUsageLimit = new TextField("Eletricidade (kWh)");
        electricUsageLimit.setImmediate(true);
        details.addComponent(electricUsageLimit);

		setContent(layout);
		setSizeFull();
	}

	@Override
	public void run() {
		try {
			fieldGroup.setItemDataSource(DaoService.getCondominium());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getComponentId() {
		return TITLE;
	}
}
