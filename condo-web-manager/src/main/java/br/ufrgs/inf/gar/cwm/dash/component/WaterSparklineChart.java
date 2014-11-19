package br.ufrgs.inf.gar.cwm.dash.component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private Label current;
	private String unit;

    public WaterSparklineChart(final String name, final String unit,
            final String prefix, final Color color) {
    	this.unit = unit;
        setSizeUndefined();
        addStyleName("spark");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        
        String totalWaterUsage = null;
		try {
			totalWaterUsage = DaoService.getCondominium().getTotalWaterUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		current = new Label(prefix + totalWaterUsage.substring(0, 4) + unit);
		current.setSizeUndefined();
        current.addStyleName(ValoTheme.LABEL_HUGE);
        addComponent(current);

        Label title = new Label(name);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_SMALL);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        addComponent(title);

        addComponent(buildSparkline(totalWaterUsage, color));

        Label highLow = new Label("Maximo <b>" + 0
                + "</b> &nbsp;&nbsp;&nbsp; Mínimo <b>"
                + 0 + "</b>", ContentMode.HTML);
        highLow.addStyleName(ValoTheme.LABEL_TINY);
        highLow.addStyleName(ValoTheme.LABEL_LIGHT);
        highLow.setSizeUndefined();
        addComponent(highLow);

    }

    private Component buildSparkline(final String totalWaterUsage, final Color color) {
        Chart spark = new Chart();
        spark.getConfiguration().setTitle("");
        spark.getConfiguration().getChart().setType(ChartType.SPLINE);
        spark.setWidth("120px");
        spark.setHeight("40px");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("", 0f));
        series.add(new DataSeriesItem("", Float.valueOf(totalWaterUsage.substring(0, 4))));
        
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

			@Override
			public void run() {
				Float consum;
				try {
					consum = DaoService.getCondominium().getTotalWaterUsageFloat();
					series.add(new DataSeriesItem("", consum));
					current.setValue(consum.toString().substring(0, 4) + unit);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}, 20000, 20000);

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
				} catch (InterruptedException e) {
				} catch (com.vaadin.ui.UIDetachedException e) {
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
