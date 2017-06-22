package br.ufrgs.inf.gar.cwm.dash.ui;

import java.util.Iterator;

import br.ufrgs.inf.gar.cwm.dash.apt.ApartmentTable;
import br.ufrgs.inf.gar.cwm.dash.apt.AptElectricUsageChart;
import br.ufrgs.inf.gar.cwm.dash.apt.AptLimitPanel;
import br.ufrgs.inf.gar.cwm.dash.apt.AptWaterUsageChart;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherComponent;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherThread;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.CloseOpenWindowsEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEventBus;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ApartmentsView extends Panel implements View {

    private static final String HEADER = "Apartamentos";
    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private HorizontalLayout header;
    private final RefresherThread refresher = new RefresherThread(8000, 8000);

    public ApartmentsView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        DashboardEventBus.register(this);

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);

        root.addComponent(buildHeader());

        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                DashboardEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    private Component buildHeader() {
        header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        titleLabel = new Label(HEADER);
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        return header;
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        dashboardPanels.addComponent(buildAptElectricUsageChart());
        dashboardPanels.addComponent(buildApartmentTable());
        dashboardPanels.addComponent(buildAptWaterUsageChart());
        dashboardPanels.addComponent(buildLimitPanel());

        return dashboardPanels;
    }

    private Component buildLimitPanel() {
    	 Component c = createContentWrapper(new AptLimitPanel());
         c.addStyleName("dashboard-panel-slot-table");
         return c;
	}

	private Component buildApartmentTable() {
    	ApartmentTable table = new ApartmentTable();
    	refresher.subscribe(table);
        Component c = createContentWrapper(table);
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildAptElectricUsageChart() {
    	AptElectricUsageChart chart = new AptElectricUsageChart();
    	refresher.subscribe(chart);
    	chart.setSizeFull();
        return createContentWrapper(chart);
    }
    
    private Component buildAptWaterUsageChart() {
    	AptWaterUsageChart chart = new AptWaterUsageChart();
    	refresher.subscribe(chart);
    	chart.setSizeFull();
        return createContentWrapper(chart);
    }
    
    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");
        MenuItem root = tools.addItem("", FontAwesome.COG, null);
        if (content instanceof RefresherComponent) {
        	RefresherComponent component = (RefresherComponent) content;
        	MenuItem stopRefresher = root.addItem("Pausar", FontAwesome.PAUSE, new Command() {
                @Override
                public void menuSelected(final MenuItem selectedItem) {
                	refresher.unsubscribe(component);
            		root.getChildren().get(0).setVisible(false);
            		root.getChildren().get(1).setVisible(true);
                }
            });
    		MenuItem startRefresher = root.addItem("Continuar", FontAwesome.PLAY, new Command() {
                @Override
                public void menuSelected(final MenuItem selectedItem) {
                	refresher.subscribe(component);
            		root.getChildren().get(0).setVisible(true);
            		root.getChildren().get(1).setVisible(false);
                }
            });
        	if (refresher.isSubscribed(component)) {
        		stopRefresher.setVisible(true);
        		startRefresher.setVisible(false);
        	} else {
        		stopRefresher.setVisible(false);
        		startRefresher.setVisible(true);
        		
        	}
        	root.addSeparator();
        	root.addItem("Atualizar uma vez", FontAwesome.REFRESH, new Command() {
                @Override
                public void menuSelected(final MenuItem selectedItem) {
            		component.run();
                }
            });
        }

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }

    private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
        header.setVisible(true);
    }
}
