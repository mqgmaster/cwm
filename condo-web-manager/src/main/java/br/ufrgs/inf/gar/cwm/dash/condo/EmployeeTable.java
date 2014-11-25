package br.ufrgs.inf.gar.cwm.dash.condo;

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
        setCaption("Empregados");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setImmediate(true);
        setSizeFull();

        BeanItemContainer<Employee> container = new BeanItemContainer<>(
        		Employee.class, DaoService.getAllEmployees());
        setContainerDataSource(container);
        setVisibleColumns(new Object[]{"name", "role", "monthWage", "weekWorkload", "working"});
        setColumnHeaders("Nome", "Cargo", "Salário", "Carga Horária", "Status");
        
        this.setTableFieldFactory(new FieldFactory());
    }
    
    class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            String prop = (String) propertyId;
            if ("working".equals(prop)) { // propertyId of the column you wish to change
                AbstractField<?> f = (AbstractField<?>) super.createField(container, itemId, propertyId, uiContext); 
                // casting to AbstractField to set the field to immediate mode
                f.setImmediate(true);
                return f;
            }
            return super.createField(container, itemId, propertyId, uiContext);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		BeanItemContainer<Employee> container = (BeanItemContainer<Employee>) this.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DaoService.getAllEmployees());
	}
}
