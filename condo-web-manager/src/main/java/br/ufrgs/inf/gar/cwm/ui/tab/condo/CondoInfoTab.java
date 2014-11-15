package br.ufrgs.inf.gar.cwm.ui.tab.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.cwm.data.DaoService;
import br.ufrgs.inf.gar.cwm.ui.tab.generic.AbstractTab;

@SuppressWarnings("serial")
public class CondoInfoTab extends AbstractTab<CondoInfoLayout>{

	public CondoInfoTab() {
		super(CondoInfoLayout.class);
		
		configureTable();
	}

	private void configureTable() {
		Condominium condo = null;
		try {
			condo = DaoService.getCondominium();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getLayout().getTableInfo().addContainerProperty("Property", String.class, null);
		this.getLayout().getTableInfo().addContainerProperty("Value", String.class, null);
        addItemToTable("Name", condo.getName());
        addItemToTable("Address", condo.getAddress());
        addItemToTable("Manager", condo.getManagerName());
	}

	private void addItemToTable(String attributeName, String value) {
		Object id = this.getLayout().getTableInfo().addItem();
		
		this.getLayout().getTableInfo().getItem(id).getItemProperty("Property").setValue(attributeName);
		this.getLayout().getTableInfo().getItem(id).getItemProperty("Value").setValue(value);
	}
}
