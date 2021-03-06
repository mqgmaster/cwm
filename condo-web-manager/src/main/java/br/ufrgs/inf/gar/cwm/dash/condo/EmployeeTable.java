package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Employee;
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
public final class EmployeeTable extends Table implements RefresherComponent {
	
	private static final String TABLE_TITLE = "Empregados";

	@Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        if (colId.equals("working")) {
            if (property != null && property.getValue() != null) {
                if((Boolean) property.getValue()) {
                	result = "Trabalhando";
                } else {
                	result = "Não presente";
                }
            } else {
                result = "?";
            }
        }
        return result;
    }
	
    public EmployeeTable() {
        setCaption(TABLE_TITLE);
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setImmediate(true);
        setSizeFull();

        BeanItemContainer<Employee> container;
		try {
			container = new BeanItemContainer<>(
					Employee.class, DaoService.get().getAllEmployees());
			setContainerDataSource(container);
			setVisibleColumns(new Object[]{"name", "role", "monthWage", "weekWorkload", "working"});
	        setColumnHeaders("Nome", "Cargo", "Salário", "Carga Horária", "Status");
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		
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
		try {
			this.addItems(DaoService.get().getAllEmployees());
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getComponentId() {
		return TABLE_TITLE;
	}
}
