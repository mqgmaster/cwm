package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;
import java.text.ParseException;

import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

import br.ufrgs.inf.gar.cwm.dash.data.SNMPManager;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AdvancedPanel extends VerticalLayout {
	
	private FormLayout form = new FormLayout();
	
	public AdvancedPanel() {
		form = new FormLayout();
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		
		TextField oid = new TextField("OID");
        form.addComponent(oid);
        TextField value = new TextField("Valor");
        form.addComponent(value);
        addComponent(form);
        
        Label result = new Label();
        addComponent(result);
        
        Button consult = new Button("Consultar");
        addComponent(consult);
        
        Button set = new Button("Setar");
        addComponent(set);
        
        consult.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Variable var = null;
				try {
					var = SNMPManager.get(oid.getValue())[0].getVariable();
					result.setValue(((OctetString) var).toASCII(' '));		
				} catch (ClassCastException e) {
					result.setValue(((Integer32) var).toString());		
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		});

        set.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					result.setValue(String.valueOf(SNMPManager.set(oid.getValue(), value.getValue())));
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
