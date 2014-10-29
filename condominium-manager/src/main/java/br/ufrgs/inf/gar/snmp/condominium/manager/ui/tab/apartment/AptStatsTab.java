package br.ufrgs.inf.gar.snmp.condominium.manager.ui.tab.apartment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.ufrgs.inf.gar.snmp.condominium.manager.ui.tab.generic.AbstractTab;

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
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class AptStatsTab extends AbstractTab<AptStatsLayout> {
	
	private static final String CHART_NAME = "Consumo por Apartamento";
	private static final String COLOR = "Cor";
	private static final String CONSUMPTION = "Consumo";
	private static final String APARTMENT = "Apartamento";
	
	String[] apts = new String[] { 
			"101", 
			"102", 
			"103", 
			"104", 
			"201",
			"202",
			"203",
			"204"};
	Number[] values = new Number[] { 
			9.0, 
			11.0, 
			12.0, 
			8.0, 
			14.0,
			20.0,
			16.0,
			10.0};
	
	public AptStatsTab() {
		super(AptStatsLayout.class);
		
        ContainerDataSeries container = createContainer();
        Component table = createTable(container.getVaadinContainer());
        Component chart = createChart(container);

        table.setSizeFull();
        chart.setSizeFull();

        layout.getBaseLayout().addComponents(table);
        layout.getChartPanelLayout().addComponent(chart);
    }

    @SuppressWarnings("unchecked")
    private ContainerDataSeries createContainer() {
        IndexedContainer vaadinContainer = new IndexedContainer();
        ContainerDataSeries container = new ContainerDataSeries(vaadinContainer);
        vaadinContainer.addContainerProperty(APARTMENT, String.class, null);
        vaadinContainer.addContainerProperty(CONSUMPTION, Number.class, null);
        vaadinContainer.addContainerProperty(COLOR, Color.class, null);

        container.setName(CHART_NAME);

        container.setYPropertyId(CONSUMPTION);
        container.setNamePropertyId(APARTMENT);
        container.addAttributeToPropertyIdMapping(COLOR, COLOR);

        Color[] colors = new VaadinTheme().getColors();

        List<Number> list = Arrays.asList(values);
        Collections.shuffle(list);
        for (int i = 0; i < apts.length; i++) {
            Item ie = vaadinContainer.addItem(i);
            ie.getItemProperty(APARTMENT).setValue(apts[i]);
            ie.getItemProperty(CONSUMPTION).setValue(list.get(i));
            ie.getItemProperty(COLOR).setValue(colors[i]);
        }

        return container;
    }

    private Component createTable(Container container) {
        Table t = new Table();
        t.setContainerDataSource(container);
        t.setImmediate(true);
        return t;
    }

    private Chart createChart(Series container) {
        final Chart chart = new Chart();

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.PIE);
        configuration.getTitle().setText(CHART_NAME);

        configuration.setSeries(container);

        chart.drawChart(configuration);

        return chart;
    }
}