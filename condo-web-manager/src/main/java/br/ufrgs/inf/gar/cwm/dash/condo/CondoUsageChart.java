package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.DateTimeLabelFormats;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.YAxis;

@SuppressWarnings("serial")
public class CondoUsageChart extends Chart implements RefresherComponent {

	private static final String TITLE_CHART = "Consumo diário";
	private static final String ELECTRIC_SERIES_NAME = "Eletricidade";
	private static final String WATER_SERIES_NAME = "Água";
	private final DataSeries electricSeries = new DataSeries();
	private final DataSeries waterSeries = new DataSeries();
	private int monthCounter = 1;
	private int dayCounter = 1;
	private final DateFormat df = new SimpleDateFormat("yyyy,MM,dd");

	public CondoUsageChart() {
		setCaption(TITLE_CHART);
		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.SPLINE);
		configuration.getTitle().setText(null);
		configuration.getTooltip().setFormatter("");

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.DATETIME);
		xAxis.setDateTimeLabelFormats(
                new DateTimeLabelFormats("%e. %b", "%b"));

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle("");
		yAxis.setMin(0);

		Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { "
                + "var unit = { 'Eletricidade': 'kWh', 'Água': 'L' }[this.series.name];"
                + "return '<b>'+ this.series.name +'</b><br/>\' + Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' '+ unit; }");
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
			electricSeries.add(new DataSeriesItem(getDate(), eletric));
			waterSeries.add(new DataSeriesItem(getDate(), water));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dayCounter++;
		
		configuration.addSeries(electricSeries);
		configuration.addSeries(waterSeries);

		drawChart(configuration);
	}
	
	private Date getDate() {
        df.setTimeZone(TimeZone.getTimeZone("EET"));
        try {
            return df.parse("2014," + monthCounter + "," + dayCounter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
						getDate(), eletric), true, true);
				waterSeries.add(new DataSeriesItem(
						getDate(), water), true, true);
			} else {
				electricSeries.add(new DataSeriesItem(
						getDate(), eletric));
				waterSeries.add(new DataSeriesItem(
						getDate(), water));
			}
			if (dayCounter > 29) {
				dayCounter = 1;
				if (monthCounter > 12) {
					monthCounter = 1;
				} else {
					monthCounter++;
				}
			} else {
				dayCounter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getComponentId() {
		return TITLE_CHART;
	}
}
