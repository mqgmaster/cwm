package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.YAxis;

@SuppressWarnings("serial")
public class CondoUsageChart extends Chart implements RefresherComponent {

	private static final String TITLE_CHART = "Consumo em Tempo Real";
	private static final String ELECTRIC_SERIES_NAME = "Eletricidade";
	private static final String WATER_SERIES_NAME = "Água";
	final DataSeries electricSeries = new DataSeries();
	final DataSeries waterSeries = new DataSeries();

	public CondoUsageChart() {
		setCaption(TITLE_CHART);
		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.SPLINE);
		configuration.getTitle().setText(null);

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.DATETIME);
		xAxis.setTickPixelInterval(150);

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle("");
		yAxis.setMin(0);

		Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { "
                + "var unit = { 'Eletricidade': 'kWh', 'Água': 'L' }[this.series.name];"
                + "return ''+ this.series.name +': '+ this.y +' '+ unit; }");
        configuration.setTooltip(tooltip);

		electricSeries.setName(ELECTRIC_SERIES_NAME);
		electricSeries.setPlotOptions(new PlotOptionsSpline());
		waterSeries.setName(WATER_SERIES_NAME);
		waterSeries.setPlotOptions(new PlotOptionsSpline());
		float eletric;
		float water;
		try {
			eletric = getInstantEletricUsage();
			water = getInstantWaterUsage();
			electricSeries.add(new DataSeriesItem(System.currentTimeMillis(), eletric));
			waterSeries.add(new DataSeriesItem(System.currentTimeMillis(), water));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		configuration.addSeries(electricSeries);
		configuration.addSeries(waterSeries);

		drawChart(configuration);
	}
	
	private Float getStandardFloat(final String value) {
		if(value.length() > 3) {
			return Float.valueOf(value.substring(0, 4));
		} else {
			return Float.valueOf(value);
		}
	}
	
	private Float getInstantEletricUsage() throws IOException {
		return getStandardFloat(DaoService.getCondominium().getInstantElectricUsage());
	}
	
	private Float getInstantWaterUsage() throws IOException {
		return getStandardFloat(DaoService.getCondominium().getInstantWaterUsage());
	}

	@Override
	public void run() {
		float eletric;
		float water;
		try {
			eletric = getInstantEletricUsage();
			water = getInstantWaterUsage();
			if (electricSeries.size() > 29) {
				electricSeries.add(new DataSeriesItem(
						System.currentTimeMillis(), eletric), true, true);
				waterSeries.add(new DataSeriesItem(
						System.currentTimeMillis(), water), true, true);
			} else {
				electricSeries.add(new DataSeriesItem(
						System.currentTimeMillis(), eletric));
				waterSeries.add(new DataSeriesItem(
						System.currentTimeMillis(), water));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(Object another) {
		return RefresherComponent.equalsAnother(this, another);
	}

	@Override
	public String getComponentId() {
		return TITLE_CHART;
	}
}
