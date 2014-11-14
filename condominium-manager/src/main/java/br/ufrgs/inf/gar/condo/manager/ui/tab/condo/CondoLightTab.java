package br.ufrgs.inf.gar.condo.manager.ui.tab.condo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrgs.inf.gar.condo.manager.ui.tab.generic.AbstractTab;

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
public class CondoLightTab extends AbstractTab<CondoLightLayout> {

	private static final String CHART_SIZE = "300px";
	private static final String CHART_TITLE = "Consumo de Eletricidade em Tempo Real";

	public CondoLightTab() {
		super(CondoLightLayout.class);
		
		final Random random = new Random();

		final Chart chart = new Chart();
		chart.setHeight(CHART_SIZE);

		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.SPLINE);
		configuration.getTitle().setText(CHART_TITLE);

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.DATETIME);
		xAxis.setTickPixelInterval(150);

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle(new Title("Potência"));
		yAxis.setPlotLines(new PlotLine(0, 1, new SolidColor("#808080")));

		configuration.getTooltip().setEnabled(false);
		configuration.getLegend().setEnabled(false);

		final DataSeries series = new DataSeries();
		series.setPlotOptions(new PlotOptionsSpline());
		series.setName("Random data");
		for (int i = -19; i <= 0; i++) {
			series.add(new DataSeriesItem(
					System.currentTimeMillis() + i * 1000, random.nextDouble()));
		}
		runWhileAttached(chart, new Runnable() {

			@Override
			public void run() {
				final long x = System.currentTimeMillis();
				final double y = random.nextDouble();
				series.add(new DataSeriesItem(x, y), true, true);
			}
		}, 5000, 5000);

		configuration.setSeries(series);

		chart.drawChart(configuration);
		
		layout.getLivePanelLayout().addComponent(chart);
	}

	public static void runWhileAttached(final Component component,
			final Runnable task, final int interval, final int initialPause) {
		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(initialPause);
					while (true) {
						UI.getCurrent().access(task);
						System.out.println("thread running");
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
