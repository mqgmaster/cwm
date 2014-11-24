package br.ufrgs.inf.gar.cwm.dash.condo;

import br.ufrgs.inf.gar.condo.domain.Garage;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class GarageTable extends Table {

	@Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        switch((String) colId) {
	    	case "occupied" : 
	    		if (property != null && property.getValue() != null) {
	                if((Boolean) property.getValue()) {
	                	result = "Ocupado";
	                } else {
	                	result = "Livre";
	                }
	            } else {
	                result = "?";
	            }
	    		break;
	    	case "apartment.number" :
	    		if (property != null && property.getValue() == null) {
	                	result = "Visitantes";
	            } else {
	                result = property.getValue().toString();
	            }
	    	default : break;
	    }
        return result;
    }
	
    public GarageTable() {
        setCaption("Vagas de Estacionamento");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setSizeFull();

        BeanItemContainer<Garage> container = new BeanItemContainer<>(
        		Garage.class, DaoService.getAllGarages());
        container.addNestedContainerProperty("apartment.number");
        setContainerDataSource(container);
        setVisibleColumns(new Object[]{"number", "apartment.number", "occupied"});
        setColumnHeaders("Número", "Proprietário", "Situação");
    }
}
