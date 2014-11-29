package br.ufrgs.inf.gar.cwm.dash.component;

import java.util.ArrayList;
import java.util.List;

import br.ufrgs.inf.gar.cwm.dash.DashboardUI;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;
import br.ufrgs.inf.gar.cwm.dash.domain.Apt;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.Series;

@SuppressWarnings("serial")
public class TopGrossingAptsChart extends Chart {

    public TopGrossingAptsChart() {
        setCaption("Top Grossing Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.BAR);
        getConfiguration().getChart().setAnimation(false);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        getConfiguration().getyAxis().setTitle("");
        setSizeFull();

        List<Apt> movies = new ArrayList<Apt>(DashboardUI.getDataProvider()
                .getMovies());

        List<Series> series = new ArrayList<Series>();
        for (int i = 0; i < 6; i++) {
            Apt movie = movies.get(i);
            PlotOptionsBar opts = new PlotOptionsBar();
            opts.setColor(DummyDataGenerator.chartColors[5 - i]);
            opts.setBorderWidth(0);
            opts.setShadow(false);
            opts.setPointPadding(0.4);
            opts.setAnimation(false);
            ListSeries item = new ListSeries(movie.getTitle(), movie.getScore());
            item.setPlotOptions(opts);
            series.add(item);

        }
        getConfiguration().setSeries(series);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);

        PlotOptionsBar opts = new PlotOptionsBar();
        opts.setGroupPadding(0);
        getConfiguration().setPlotOptions(opts);

    }
}
