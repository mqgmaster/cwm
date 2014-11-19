package br.ufrgs.inf.gar.cwm.dash.component;

import br.ufrgs.inf.gar.condo.domain.Lamp;
import br.ufrgs.inf.gar.cwm.data.DaoService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class LampTable extends Table {

    public LampTable() {
        setCaption("Lâmpadas");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setSizeFull();

        //Collections.sort(employees);

        setContainerDataSource(new BeanItemContainer<Lamp>(
        		Lamp.class, DaoService.getAllLamps()));

       // setVisibleColumns(new Object[]{"name", "role", "isWorking"});
       // setColumnHeaders("Nome", "Área", "Ativo");
    }

}
