package br.ufrgs.inf.gar.cwm.dash.apt;

import java.io.IOException;
import java.util.List;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherComponent;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ApartmentTable extends Table implements RefresherComponent {
	
	private static final String TABLE_TITLE = "Lista de Apartamentos";

    public ApartmentTable() {
        setCaption(TABLE_TITLE);
        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setImmediate(true);
        setSizeFull();

        BeanItemContainer<Apartment> container = null;
		try {
			container = new BeanItemContainer<>(
					Apartment.class, DaoService.get().getAllApartments());
		} catch (Exception e) {
			e.printStackTrace();
		}
        setContainerDataSource(container);
        setVisibleColumns(new Object[]{"number", "ownerName", "numRooms", "numPeople"});
        setColumnHeaders("Número", "Proprietário", "Quartos", "Pessoas presentes");
        
        this.setTableFieldFactory(new FieldFactory());
    }
    
    private class FieldFactory extends DefaultFieldFactory {
        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            String prop = (String) propertyId;
            if ("numPeople".equals(prop)) { 
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
		BeanItemContainer<Apartment> container = (BeanItemContainer<Apartment>) this.getContainerDataSource();
		try {
			List<Apartment> list = DaoService.get().getAllApartmentsPeople();
			for (Apartment apt : list) {
				BeanItem<Apartment> bean = container.getItem(apt.getId());
				bean.getBean().setNumPeople(apt.getNumPeople());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getComponentId() {
		return TABLE_TITLE;
	}
}
