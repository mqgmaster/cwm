package br.ufrgs.inf.gar.cwm.dash.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.vaadin.maddon.ListContainer;

import br.ufrgs.inf.gar.cwm.dash.DashboardUI;
import br.ufrgs.inf.gar.cwm.dash.domain.Apt;
import br.ufrgs.inf.gar.cwm.dash.domain.AptRevenue;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ConsumView extends VerticalLayout implements View {

    private final Timeline timeline;
    private ComboBox movieSelect;

    private static final Color[] COLORS = new Color[] {
            new Color(52, 154, 255), new Color(242, 81, 57),
            new Color(255, 201, 35), new Color(83, 220, 164) };
    private int colorIndex = -1;

    public ConsumView() {
        setSizeFull();
        addStyleName("sales");

        addComponent(buildHeader());

        timeline = buildTimeline();
        addComponent(timeline);
        setExpandRatio(timeline, 1);

        initMovieSelect();
        // Add first 4 by default
        List<Apt> subList = new ArrayList<Apt>(DashboardUI
                .getDataProvider().getMovies()).subList(0, 4);
        for (Apt m : subList) {
            addDataSet(m);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        if (timeline.getGraphDatasources().size() > 0) {
            timeline.setVisibleDateRange(calendar.getTime(), new Date());
        }
    }

    private void initMovieSelect() {
        Collection<Apt> movies = DashboardUI.getDataProvider().getMovies();
        Container movieContainer = new ListContainer<Apt>(Apt.class, movies);
        movieSelect.setContainerDataSource(movieContainer);
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label titleLabel = new Label("Revenue by Movie");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponents(titleLabel, buildToolbar());

        return header;
    }

    private Component buildToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("toolbar");
        toolbar.setSpacing(true);

        movieSelect = new ComboBox();
        movieSelect.setItemCaptionPropertyId("title");
        movieSelect.addShortcutListener(new ShortcutListener("Add",
                KeyCode.ENTER, null) {
            @Override
            public void handleAction(final Object sender, final Object target) {
                addDataSet((Apt) movieSelect.getValue());
            }
        });

        final Button add = new Button("Add");
        add.setEnabled(false);
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);

        CssLayout group = new CssLayout(movieSelect, add);
        group.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        toolbar.addComponent(group);

        movieSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                add.setEnabled(event.getProperty().getValue() != null);
            }
        });

        final Button clear = new Button("Clear");
        clear.addStyleName("clearbutton");
        clear.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                timeline.removeAllGraphDataSources();
                initMovieSelect();
                clear.setEnabled(false);
            }
        });
        toolbar.addComponent(clear);

        add.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                addDataSet((Apt) movieSelect.getValue());
                clear.setEnabled(true);
            }
        });

        return toolbar;
    }

    private Timeline buildTimeline() {
        Timeline result = new Timeline();
        result.setDateSelectVisible(false);
        result.setChartModesVisible(false);
        result.setGraphShadowsEnabled(false);
        result.setZoomLevelsVisible(false);
        result.setSizeFull();
        result.setNoDataSourceCaption("<span class=\"v-label h2 light\">Add a data set from the dropdown above</span>");
        return result;
    }

    private void addDataSet(final Apt movie) {
        movieSelect.removeItem(movie);
        movieSelect.setValue(null);

        Collection<AptRevenue> dailyRevenue = DashboardUI.getDataProvider()
                .getDailyRevenuesByMovie(movie.getId());

        ListContainer<AptRevenue> dailyRevenueContainer = new TempMovieRevenuesContainer(
                dailyRevenue);

        dailyRevenueContainer.sort(new Object[] { "timestamp" },
                new boolean[] { true });

        timeline.addGraphDataSource(dailyRevenueContainer, "timestamp",
                "revenue");
        colorIndex = (colorIndex >= COLORS.length - 1 ? 0 : ++colorIndex);
        timeline.setGraphOutlineColor(dailyRevenueContainer, COLORS[colorIndex]);
        timeline.setBrowserOutlineColor(dailyRevenueContainer,
                COLORS[colorIndex]);
        timeline.setBrowserFillColor(dailyRevenueContainer,
                COLORS[colorIndex].brighter());
        timeline.setGraphCaption(dailyRevenueContainer, movie.getTitle());
        timeline.setEventCaptionPropertyId("date");
        timeline.setVerticalAxisLegendUnit(dailyRevenueContainer, "$");
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }

    private class TempMovieRevenuesContainer extends
            ListContainer<AptRevenue> {

        public TempMovieRevenuesContainer(
                final Collection<AptRevenue> collection) {
            super(AptRevenue.class, collection);
        }

        // This is only temporarily overridden until issues with
        // BeanComparator get resolved.
        @Override
        public void sort(final Object[] propertyId, final boolean[] ascending) {
            final boolean sortAscending = ascending[0];
            Collections.sort(getBackingList(), new Comparator<AptRevenue>() {
                @Override
                public int compare(final AptRevenue o1, final AptRevenue o2) {
                    int result = o1.getTimestamp().compareTo(o2.getTimestamp());
                    if (!sortAscending) {
                        result *= -1;
                    }
                    return result;
                }
            });
        }

    }
}
