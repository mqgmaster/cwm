package br.ufrgs.inf.gar.snmp.manager.ui.tab.aptos;

import br.ufrgs.inf.gar.snmp.manager.service.SNMPManager;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ContainerDataSeries;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;

public class AptosTab {
	
	private HorizontalLayout lo;

	public AptosTab(SNMPManager manager) {
        lo = new HorizontalLayout();
        ContainerDataSeries container = createContainer();
        Component table = createTable(container.getVaadinContainer());
        Component chart = createChart(container);
        lo.setCaption("Apartamentos");
        lo.setMargin(true);

        table.setSizeFull();
        chart.setSizeFull();

        lo.setWidth("100%");
        lo.setHeight("450px");
        lo.addComponents(table);
        lo.addComponent(chart);
    }

    @SuppressWarnings("unchecked")
    private ContainerDataSeries createContainer() {
        IndexedContainer vaadinContainer = new IndexedContainer();
        ContainerDataSeries container = new ContainerDataSeries(vaadinContainer);
        vaadinContainer.addContainerProperty("Apartamento", String.class, null);
        vaadinContainer.addContainerProperty("Consumo", Number.class, null);
        vaadinContainer.addContainerProperty("Cor", Color.class, null);

        container.setName("Consumo por Apartamento");

        container.setYPropertyId("Consumo");
        container.setNamePropertyId("Apartamento");
        container.addAttributeToPropertyIdMapping("Cor", "Cor");

        String[] names = new String[] { "101", "102", "103", "104", "105" };
        Number[] values = new Number[] { 10.0, 15.0, 20.0, 25.0, 30.0 };
        Color[] colors = new VaadinTheme().getColors();

        for (int i = 0; i < names.length; i++) {
            Item ie = vaadinContainer.addItem(i);
            ie.getItemProperty("Apartamento").setValue(names[i]);
            ie.getItemProperty("Consumo").setValue(values[i]);
            ie.getItemProperty("Cor").setValue(colors[i]);
        }

        return container;
    }

    private Component createTable(Container container) {
        Table t = new Table();
        t.setContainerDataSource(container);
        t.setImmediate(true);
        return t;
    }

    public static Chart createChart(Series container) {
        final Chart chart = new Chart();

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.PIE);
        configuration.getTitle().setText("Consumo por Apartamento");

        configuration.setSeries(container);

        chart.drawChart(configuration);

        return chart;
    }

	public HorizontalLayout getLayout() {
		return lo;
	}
}