package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
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
        List<CondoInfo> list = new ArrayList<>();
        list.add(new CondoInfo("Limite de agua", "1512"));
        list.add(new CondoInfo("Limite de luz", "1512"));

        BeanItemContainer<CondoInfo> container = new BeanItemContainer<>(
        		CondoInfo.class, list);
        setContainerDataSource(container);
        this.setTableFieldFactory(new FieldFactory());
    }
    
    public class CondoInfo implements Serializable {
    	private String name;
    	private TextField value;
    	
    	public CondoInfo(String name, String value) {
    		this.name = name;
    		this.value = new TextField();
    		this.value.setValue(value);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public TextField getValue() {
			return value;
		}
		public void setValue(TextField value) {
			this.value = value;
		}
    }
    
    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
    		return super.createField(container, itemId, propertyId, uiContext);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
	}

	@Override
	public String getComponentId() {
		return TABLE_TITLE;
	}
}
