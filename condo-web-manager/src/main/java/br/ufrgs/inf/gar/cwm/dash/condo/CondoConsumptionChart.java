package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrgs.inf.gar.cwm.dash.data.DaoService;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotLine;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class CondoConsumptionChart extends Chart {

	private static final String TITLE_CHART = "Consumo em Tempo Real";

	public CondoConsumptionChart() {
		setCaption(TITLE_CHART);
		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.SPLINE);
		configuration.getTitle().setText(null);

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.DATETIME);
		xAxis.setTickPixelInterval(150);

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle(new Title("Potência kWh"));
		yAxis.setPlotLines(new PlotLine(0, 1, new SolidColor("#808080")));

		configuration.getTooltip().setEnabled(false);
		configuration.getLegend().setEnabled(false);

		final DataSeries series = new DataSeries();
		series.setPlotOptions(new PlotOptionsSpline());
		float consum;
		try {
			consum = DaoService.getCondominium().getInstantElectricUsageFloat();
			series.add(new DataSeriesItem(
					System.currentTimeMillis(), consum));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		runWhileAttached(this, new Runnable() {

			@Override
			public void run() {
				float consum;
				try {
					consum = DaoService.getCondominium().getInstantElectricUsageFloat();
					if (series.size() > 29) {
						series.add(new DataSeriesItem(
								System.currentTimeMillis(), consum), true, true);
					} else {
						series.add(new DataSeriesItem(
								System.currentTimeMillis(), consum));
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}, 2000, 2000);

		configuration.setSeries(series);

		drawChart(configuration);
	}

	public static void runWhileAttached(final Component component,
			final Runnable task, final int interval, final int initialPause) {
		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(initialPause);
					while (true) {
						UI.getCurrent().access(task);
						System.out.println("condo_consum_real_time");
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
