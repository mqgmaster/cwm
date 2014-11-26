package br.ufrgs.inf.gar.cwm.dash.condo;

import br.ufrgs.inf.gar.condo.domain.Garage;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class GarageTable extends Table implements RefresherComponent {

	private static final String TABLE_TITLE = "Vagas de Estacionamento";

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
        setCaption(TABLE_TITLE);
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
        
        this.setTableFieldFactory(new FieldFactory());
    }
    
    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            String prop = (String) propertyId;
            if ("occupied".equals(prop)) { 
                AbstractField<?> f = (AbstractField<?>) super.createField(container, itemId, propertyId, uiContext); 
                f.setImmediate(true);
                return f;
            }
            return super.createField(container, itemId, propertyId, uiContext);
        }
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void run() {
		BeanItemContainer<Garage> container = (BeanItemContainer<Garage>) this.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DaoService.getAllGarages());
	}
    
    @Override
	public boolean equals(Object another) {
		return RefresherComponent.equalsAnother(this, another);
	}

	@Override
	public String getComponentId() {
		return TABLE_TITLE;
	}
}
