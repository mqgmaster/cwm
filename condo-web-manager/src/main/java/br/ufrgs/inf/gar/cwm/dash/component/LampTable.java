package br.ufrgs.inf.gar.cwm.dash.component;

import br.ufrgs.inf.gar.condo.domain.Lamp;
import br.ufrgs.inf.gar.cwm.data.DaoService;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class LampTable extends Table {

	@Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        switch((String) colId) {
        	case "id" : 
        		if (property != null && property.getValue() != null) {
                    result = String.valueOf((Integer) property.getValue() + 1);
                } else {
                    result = "?";
                }
        		break;
        	case "on" :
        		if (property != null && property.getValue() != null) {
                    if((Boolean) property.getValue()) {
                    	result = "Ligada";
                    } else {
                    	result = "Desligada";
                    }
                } else {
                    result = "?";
                }
        	default : break;
        }
        return result;
    }
	
    public LampTable() {
        setCaption("Lâmpadas");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setSizeFull();

        BeanItemContainer<Lamp> container = new BeanItemContainer<>(
        		Lamp.class, DaoService.getAllLamps());
        container.addNestedContainerProperty("sector.name");
        setContainerDataSource(container);
        setVisibleColumns(new Object[]{"id", "sector.name", "on"});
        setColumnHeaders("Número", "Setor", "Situação");
    }

}
