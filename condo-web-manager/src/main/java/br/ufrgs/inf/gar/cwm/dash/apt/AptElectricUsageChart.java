package br.ufrgs.inf.gar.cwm.dash.apt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import br.ufrgs.inf.gar.condo.domain.Apartment;
import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.UsageValue;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherComponent;
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
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;

@SuppressWarnings("serial")
public class AptElectricUsageChart extends Chart implements RefresherComponent {

	private static final String TITLE_CHART = "Consumo de luz diário (em kWh)";
	private final HashMap<Integer, DataSeries> hash = new HashMap<Integer, DataSeries>();
	private Date currentDate;
	private int monthCounter = 1;
	private int dayCounter = 0;
	private final DateFormat df = new SimpleDateFormat("yyyy,MM,dd");

	public AptElectricUsageChart() {
		setCaption(TITLE_CHART);
		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.SPLINE);
		configuration.getTitle().setText(null);
		configuration.getCredits().setEnabled(false);

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.DATETIME);
		xAxis.setDateTimeLabelFormats(
                new DateTimeLabelFormats("%e. %b", "%b"));

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle("");
		yAxis.setMin(0);
		
		incrementDateCounter();
		
		Condominium condo = null;
		try {
			condo = DaoService.get().getCondoUsages();
		
			DataSeries series;
			boolean isShowed = true;
			for (Apartment apt : DaoService.get().getAllApartmentsUsages()) {
				series = new DataSeries(String.valueOf(apt.getNumber()));
				series.setPlotOptions(new PlotOptionsSpline());
				if (isShowed) {
					series.setVisible(true);
					isShowed = false;
				} else {
					series.setVisible(false);
				}
				hash.put(apt.getId(), series);
				addUsageItemsSeries(apt.getInstantElectricUsage(), condo.getAptInstantElectricLimit(), series, false);
				configuration.addSeries(hash.get(apt.getId()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		incrementDateCounter();

		drawChart(configuration);
	}
	
	private Date parseDate() {
        df.setTimeZone(TimeZone.getTimeZone("EET"));
        try {
            return df.parse("2014," + monthCounter + "," + dayCounter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	private void incrementDateCounter() {
		if (dayCounter > 27) {
			dayCounter = 1;
			if (monthCounter > 11) {
				monthCounter = 1;
			} else {
				monthCounter++;
			}
		} else {
			dayCounter++;
		}
		currentDate = parseDate();
	}
	
	private Float normalizeUsage(final String value) {
		if(value.length() > 3) {
			return Float.valueOf(value.substring(0, 4));
		} else {
			return Float.valueOf(value);
		}
	}
	
	private void addUsageItemsSeries(final UsageValue usage, final UsageValue limit, DataSeries series, Boolean shift) {
		DataSeriesItem item = new DataSeriesItem(currentDate, normalizeUsage(usage.toString()));
		if (usage.toFloat() > 0.8f) {
			item.setColor(SolidColor.RED);
		}
		series.add(item, true, shift);
	}

	@Override
	public void run() {
		try {
			Condominium condo = DaoService.get().getCondoUsages();
			UsageValue electricLimit = condo.getAptInstantElectricLimit();
			
			for (Apartment apt : DaoService.get().getAllApartmentsUsages()) {
				UsageValue electric = apt.getInstantElectricUsage();
				
				DataSeries series = hash.get(apt.getId());
				if (series.size() > 29) {
					addUsageItemsSeries(electric, electricLimit, series, true);
				} else {
					addUsageItemsSeries(electric, electricLimit, series, false);
				}
			}
			incrementDateCounter();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getComponentId() {
		return TITLE_CHART;
	}
}
