package br.ufrgs.inf.gar.cwm.dash.condo;

import br.ufrgs.inf.gar.condo.domain.UsageValue;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DashStyle;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class UsageWeekChart extends VerticalLayout {

	private final DataSeries series = new DataSeries();
	private final Label current = new Label();
	private final Label highLow = new Label();
	private final Chart spark = new Chart();
	protected float weekConsum;
	protected float endWeek = -1f;
	private float max = 0f;
	private float min = 0f;
	protected float initWeek;

	public UsageWeekChart(String chartTitle, Color color) {
		setSizeUndefined();
	    addStyleName("spark");
	    setDefaultComponentAlignment(Alignment.TOP_CENTER);
	    
		final Configuration configuration = new Configuration();
		configuration.getChart().setType(ChartType.LINE);
		configuration.getTitle().setText(null);
		configuration.getLegend().setEnabled(false);
		configuration.getCredits().setEnabled(false);
		configuration.getTooltip().setEnabled(false);

		Axis xAxis = configuration.getxAxis();
		xAxis.setType(AxisType.LINEAR);

		YAxis yAxis = configuration.getyAxis();
		yAxis.setTitle("");
		yAxis.setMin(0);
		
        SolidColor transparent = new SolidColor(0, 0, 0, 0);

        xAxis.setLabels(new Labels(false));
        xAxis.setTickWidth(0);
        xAxis.setLineWidth(0);

        yAxis.setTitle(new Title(""));
        yAxis.setAlternateGridColor(transparent);
        yAxis.setLabels(new Labels(false));
        yAxis.setLineWidth(0);
        yAxis.setGridLineWidth(0);

		PlotOptionsSpline opts = new PlotOptionsSpline();
		opts.setAllowPointSelect(false);
        opts.setColor(color);
        opts.setDataLabels(new Labels(false));
        opts.setLineWidth(2);
        opts.setShadow(false);
        opts.setDashStyle(DashStyle.SOLID);
        opts.setEnableMouseTracking(false);
		series.setPlotOptions(opts);
		configuration.addSeries(series);
		
		setCurrentUsage(new UsageValue(0f));
		current.setSizeUndefined();
        current.addStyleName(ValoTheme.LABEL_HUGE);
        addComponent(current);

        Label title = new Label(chartTitle);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_SMALL);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        addComponent(title);

        addComponent(spark);

        highLow.setContentMode(ContentMode.HTML);
        printMaxMinUsage();
        highLow.addStyleName(ValoTheme.LABEL_TINY);
        highLow.addStyleName(ValoTheme.LABEL_LIGHT);
        highLow.setSizeUndefined();
        addComponent(highLow);

        spark.setWidth("180px");
		spark.setHeight("40px");
		spark.drawChart(configuration);
		
		setInitialUsage();
	}

	protected abstract void setInitialUsage();
	
	protected void updateMaxMinUsage() {
		max = series.get(0).getY().floatValue();
		min = series.get(0).getY().floatValue();
		
		for (DataSeriesItem item : series.getData()) {
			if (item.getY().floatValue() > max) {
				max = item.getY().floatValue();
			} else if (item.getY().floatValue() < min) {
				min = item.getY().floatValue();
			}
		}
		printMaxMinUsage();
	}

	private void printMaxMinUsage() {
		highLow.setValue("Máximo <b>" + normalizeUsage(new UsageValue(max))
                + "</b> &nbsp;&nbsp;&nbsp; Mínimo <b>"
                + normalizeUsage(new UsageValue(min)) + "</b>");
	}
	
	protected void setCurrentUsage(UsageValue totalWaterUsage) {
		current.setValue(normalizeUsage(totalWaterUsage) + getUsageUnit());
	}
	
	protected abstract String getUsageUnit();

	private Float normalizeUsage(final UsageValue value) {
		if(value.toString().length() > 3) {
			return Float.valueOf(value.toString().substring(0, 4));
		} else {
			return value.toFloat();
		}
	}
	
	protected void addUsageItemsSeries(final UsageValue usage, final UsageValue limit, Boolean shift) {
		DataSeriesItem item = new DataSeriesItem(System.currentTimeMillis(), normalizeUsage(usage));
		if (usage.toFloat() > limit.toFloat() * 7) {
			item.setColor(SolidColor.RED);
		}
		series.add(item, true, shift);
	}

	protected DataSeries getSeries() {
		return series;
	}
}
