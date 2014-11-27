package br.ufrgs.inf.gar.cwm.dash.condo;

import br.ufrgs.inf.gar.condo.domain.Employee;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class CondoInfoTable extends Table implements RefresherComponent {
	
	private static final String TABLE_TITLE = "Definições";

    public CondoInfoTable() {
        setCaption(TABLE_TITLE);
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        addStyleName(ValoTheme.TABLE_NO_HEADER);
        setSortEnabled(false);
        setImmediate(true);
        setSizeFull();

        BeanItemContainer<Employee> container = new BeanItemContainer<>(
        		Employee.class, DaoService.getAllEmployees());
        setContainerDataSource(container);
        setVisibleColumns(new Object[]{"name", "value", "action", "weekWorkload", "working"});
        setColumnHeaders("Nome", "Cargo", "Salário", "Carga Horária", "Status");
        
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

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		BeanItemContainer<Employee> container = (BeanItemContainer<Employee>) this.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DaoService.getAllEmployees());
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
