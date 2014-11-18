package br.ufrgs.inf.gar.cwm.dash.component;

import br.ufrgs.inf.gar.condo.domain.Employee;
import br.ufrgs.inf.gar.cwm.data.DaoService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class EmployeeTable extends Table {

    public EmployeeTable() {
        setCaption("Empregados");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSizeFull();

        //Collections.sort(employees);

        setContainerDataSource(new BeanItemContainer<Employee>(
        		Employee.class, DaoService.getAllEmployees()));

        setVisibleColumns("name", "role", "isWorking");
        setColumnHeaders("Nome", "Área", "Ativo");
    }

}
