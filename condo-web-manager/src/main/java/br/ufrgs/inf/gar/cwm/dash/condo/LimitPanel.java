package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LimitPanel extends Panel {
	
	private static final String TITLE = "Limiares de consumo";

	private final VerticalLayout layout = new VerticalLayout();
	private final Button ok = new Button("Salvar");
	
    private TextField waterUsageLimit;
    private TextField electricUsageLimit;

	public LimitPanel() {
		setCaption(TITLE);
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        layout.addComponent(details);
        
        Condominium condo = null;
        try {
        	condo = DaoService.get().getCondoUsages();
		} catch (IOException e) {
			e.printStackTrace();
		}

        waterUsageLimit = new TextField("Água (litros)");
        waterUsageLimit.setImmediate(true);
        waterUsageLimit.setValue(condo.getInstantWaterLimit().toString());
        details.addComponent(waterUsageLimit);
        
        electricUsageLimit = new TextField("Eletricidade (kWh)");
        electricUsageLimit.setImmediate(true);
        electricUsageLimit.setValue(condo.getInstantElectricLimit().toString());
        details.addComponent(electricUsageLimit);
        
        FocusListener focus = new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				ok.setEnabled(true);
			}
		};

		BlurListener blur = new BlurListener() {
			@Override
			public void blur(BlurEvent event) {
				ok.setEnabled(false);
			}
		};
		
		waterUsageLimit.addBlurListener(blur);
		electricUsageLimit.addBlurListener(blur);
		        
        waterUsageLimit.addFocusListener(focus);
        electricUsageLimit.addFocusListener(focus);
        
        layout.addComponent(buildFooter());

		setContent(layout);
		setSizeFull();
	}
	
	private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        ok.setEnabled(false);
        ok.setClickShortcut(KeyCode.ENTER);
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
        		try {
        			Condominium condo = DaoService.get().getCondoUsages();
        			condo.getInstantElectricLimit().set(electricUsageLimit.getValue());
        			condo.getInstantWaterLimit().set(waterUsageLimit.getValue());
                    
                    Notification success = new Notification(
                            "Limiares atualizados com sucesso");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());

                   // DashboardEventBus.post(new LimitChangedEvent());
                } catch (Exception e) {
                	 Notification success = new Notification(
                             "Erro ao atualizar limiares. Verifique seus dados.");
                     success.setDelayMsec(2000);
                     success.setStyleName("bar error small");
                     success.setPosition(Position.BOTTOM_CENTER);
                     success.show(Page.getCurrent());
                }
            }
        });
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.MIDDLE_CENTER);
        return footer;
    }
}
