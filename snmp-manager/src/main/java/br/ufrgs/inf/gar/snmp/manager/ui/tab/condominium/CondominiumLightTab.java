package br.ufrgs.inf.gar.snmp.manager.ui.tab.condominium;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrgs.inf.gar.snmp.manager.service.SNMPManager;
import br.ufrgs.inf.gar.snmp.manager.ui.tab.generic.ManagerTab;

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
public class CondominiumLightTab implements ManagerTab<CondominiumLightLayout> {

	private static final String CHART_SIZE = "300px";
	private static final String CHART_TITLE = "Consumo de Eletricidade em Tempo Real";
	private CondominiumLightLayout layout = new CondominiumLightLayout();

	public CondominiumLightTab(SNMPManager manager) {
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
		// Until reliable push available in our demo servers
		UI.getCurrent().setPollInterval(interval);

		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(initialPause);
					while (true) {
						Future<Void> future = component.getUI().access(task);
						future.get();
						Thread.sleep(interval);
					}
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
					Logger.getLogger(getClass().getName())
							.log(Level.WARNING,
									"Stopping repeating command due to an exception",
									e);
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

	@Override
	public CondominiumLightLayout getLayout() {
		return layout;
	}
}
