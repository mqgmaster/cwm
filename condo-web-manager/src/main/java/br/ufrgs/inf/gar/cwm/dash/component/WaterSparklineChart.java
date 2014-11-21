package br.ufrgs.inf.gar.cwm.dash.component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;
import br.ufrgs.inf.gar.cwm.data.DaoService;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DashStyle;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class WaterSparklineChart extends VerticalLayout {
	
	private static final String TITLE = "Consumo de Água semanal";
	private final Label current = new Label();
	private final Label highLow = new Label();
	private static final String UNIT = "L";
	private final Color color = DummyDataGenerator.chartColors[0];

    public WaterSparklineChart() {
        setSizeUndefined();
        addStyleName("spark");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        
        Float totalWaterUsage = null;
		try {
			totalWaterUsage = DaoService.getCondominium().getTotalWaterUsageFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		setCurrentUsage(0f);
		current.setSizeUndefined();
        current.addStyleName(ValoTheme.LABEL_HUGE);
        addComponent(current);

        Label title = new Label(TITLE);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_SMALL);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        addComponent(title);

        addComponent(buildSparkline(totalWaterUsage, color));

        highLow.setContentMode(ContentMode.HTML);
        setMaxMinUsage(0f, 0f);
        highLow.addStyleName(ValoTheme.LABEL_TINY);
        highLow.addStyleName(ValoTheme.LABEL_LIGHT);
        highLow.setSizeUndefined();
        addComponent(highLow);

    }

	private void setMaxMinUsage(final Float max, final Float min) {
		highLow.setValue("Máximo <b>" + getStandardFloatString(max)
                + "</b> &nbsp;&nbsp;&nbsp; Mínimo <b>"
                + getStandardFloatString(min) + "</b>");
	}
	
	private String getStandardFloatString(final Float value) {
		String val = value.toString();
		if(val.toString().length() > 3) {
			return val.toString().substring(0, 4);
		} else {
			return val.toString();
		}
	}

	private void setCurrentUsage(Float totalWaterUsage) {
		current.setValue(getStandardFloatString(totalWaterUsage) + UNIT);
	}

    private Component buildSparkline(final Float totalWaterUsage, final Color color) {
        Chart spark = new Chart();
        spark.getConfiguration().setTitle("");
        spark.getConfiguration().getChart().setType(ChartType.SPLINE);
        spark.setWidth("120px");
        spark.setHeight("40px");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("", 0f));
        
        spark.getConfiguration().setSeries(series);
        spark.getConfiguration().getTooltip().setEnabled(false);

        Configuration conf = series.getConfiguration();
        Legend legend = new Legend();
        legend.setEnabled(false);
        conf.setLegend(legend);

        Credits c = new Credits("");
        spark.getConfiguration().setCredits(c);

        PlotOptionsLine opts = new PlotOptionsLine();
        opts.setColor(color);
        opts.setDataLabels(new Labels(false));
        opts.setLineWidth(1);
        opts.setShadow(false);
        opts.setDashStyle(DashStyle.SOLID);
        opts.setMarker(new Marker(false));
        opts.setEnableMouseTracking(false);
        spark.getConfiguration().setPlotOptions(opts);

        XAxis xAxis = spark.getConfiguration().getxAxis();
        YAxis yAxis = spark.getConfiguration().getyAxis();

        SolidColor transparent = new SolidColor(0, 0, 0, 0);

        xAxis.setLabels(new Labels(false));
        xAxis.setTickWidth(0);
        xAxis.setLineWidth(0);

        yAxis.setTitle(new Title(""));
        yAxis.setAlternateGridColor(transparent);
        yAxis.setLabels(new Labels(false));
        yAxis.setLineWidth(0);
        yAxis.setGridLineWidth(0);

        runWhileAttached(this, new Runnable() {

        	float weekConsum;
        	float initWeek = totalWaterUsage;
        	float endWeek = -2f;
        	float max = 0f;
        	float min = 0f;
        	
			@Override
			public void run() {
				try {
					if (endWeek == -1f) {
						endWeek = DaoService.getCondominium().getTotalWaterUsageFloat();
						
						weekConsum = endWeek - initWeek;
						System.out.println(endWeek + " - " + initWeek);
						if (series.size() > 4) {
							series.add(new DataSeriesItem("", weekConsum), true, true);
						} else {
							series.add(new DataSeriesItem("", weekConsum));
						}
						setCurrentUsage(weekConsum);
						
						initWeek = endWeek;
						endWeek = -2f;
						
						max = series.get(0).getY().floatValue();
						min = series.get(0).getY().floatValue();
						
						for (DataSeriesItem item : series.getData()) {
							if (item.getY().floatValue() > max) {
								max = item.getY().floatValue();
							} else if (item.getY().floatValue() < min) {
								min = item.getY().floatValue();
							}
						}
						setMaxMinUsage(max, min);
					} else {
						endWeek = -1f;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 4000, 2000);

        return spark;
    }
    
    public static void runWhileAttached(final Component component,
			final Runnable task, final int interval, final int initialPause) {
		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(initialPause);
					while (true) {
						UI.getCurrent().access(task);
						Thread.sleep(interval);
					}
				} catch (Exception e) {
					Logger.getLogger(getClass().getName())
							.log(Level.WARNING,
									"Unexpected exception while running scheduled update",
									e);
				}
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Thread stopped");
			};
		};
		thread.start();
	}
}
