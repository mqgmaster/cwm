package br.ufrgs.inf.gar.cwm.dash.condo;

import br.ufrgs.inf.gar.condo.domain.Lamp;
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
public final class LampTable extends Table implements RefresherComponent {
	
	private static final String TABLE_TITLE = "Lâmpadas";

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
        setCaption(TABLE_TITLE);
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
        
        this.setTableFieldFactory(new FieldFactory());
    }
    
    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            String prop = (String) propertyId;
            if ("working".equals(prop)) { 
                AbstractField<?> f = (AbstractField<?>) super.createField(container, itemId, propertyId, uiContext); 
                f.setImmediate(true);
                return f;
            }
            return super.createField(container, itemId, propertyId, uiContext);
        }
    }

	@Override
	public void run() {
		this.removeAllItems();
		this.addItems(DaoService.getAllLamps());
	}
	
	@Override
	public String getComponentId() {
		return TABLE_TITLE;
	}
}
