package br.ufrgs.inf.gar.cwm.dash.component;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.gar.cwm.dash.DashboardUI;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;
import br.ufrgs.inf.gar.cwm.dash.domain.Apt;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;

@SuppressWarnings("serial")
public class TopSixCondosChart extends Chart {

    public TopSixCondosChart() {
        super(ChartType.PIE);

        setCaption("Popular Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        getConfiguration().getChart().setAnimation(false);
        setWidth("100%");
        setHeight("90%");

        DataSeries series = new DataSeries();

        List<Apt> movies = new ArrayList<Apt>(DashboardUI.getDataProvider()
                .getMovies());
        for (int i = 0; i < 6; i++) {
            Apt movie = movies.get(i);
            DataSeriesItem item = new DataSeriesItem(movie.getTitle(),
                    movie.getScore());
            series.add(item);
            item.setColor(DummyDataGenerator.chartColors[5 - i]);
        }
        getConfiguration().setSeries(series);

        PlotOptionsPie opts = new PlotOptionsPie();
        opts.setBorderWidth(0);
        opts.setShadow(false);
        opts.setAnimation(false);
        getConfiguration().setPlotOptions(opts);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);
    }

}
