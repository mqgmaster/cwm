package br.ufrgs.inf.gar.cwm.dash.ui;

import java.util.Iterator;

import br.ufrgs.inf.gar.cwm.dash.component.SparklineChart;
import br.ufrgs.inf.gar.cwm.dash.condo.CondoConsumptionChart;
import br.ufrgs.inf.gar.cwm.dash.condo.EmployeeTable;
import br.ufrgs.inf.gar.cwm.dash.condo.GarageTable;
import br.ufrgs.inf.gar.cwm.dash.condo.LampTable;
import br.ufrgs.inf.gar.cwm.dash.condo.WaterSparklineChart;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ApartmentsView extends Panel implements View {

    private static final String HEADER = "Apartmentos";
    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private HorizontalLayout header;

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

        root.addComponent(buildSparklines());

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

    private Component buildSparklines() {
        CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");
        Responsive.makeResponsive(sparks);

        WaterSparklineChart s = new WaterSparklineChart();
        sparks.addComponent(s);

        SparklineChart s2 = new SparklineChart("Consumo de Luz / Mês", "kWh", "",
                DummyDataGenerator.chartColors[2], 12, 3000, 4000);
        sparks.addComponent(s2);

        s2 = new SparklineChart("Problemas Reportados / Mês", "", "",
                DummyDataGenerator.chartColors[3], 12, 0, 30);
        sparks.addComponent(s2);

        s2 = new SparklineChart("Balanço Financeiro", "", "R$",
                DummyDataGenerator.chartColors[5], 12, 5000, 10000);
        sparks.addComponent(s2);

        return sparks;
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

        dashboardPanels.addComponent(buildCondoLightChart());
        dashboardPanels.addComponent(buildGarageTable());
        dashboardPanels.addComponent(buildEmployeeTable());
        dashboardPanels.addComponent(buildLampTable());

        return dashboardPanels;
    }

    private Component buildLampTable() {
        Component c = createContentWrapper(new LampTable());
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildGarageTable() {
    	Component c = createContentWrapper(new GarageTable());
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildEmployeeTable() {
    	Component c = createContentWrapper(new EmployeeTable());
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildCondoLightChart() {
    	CondoConsumptionChart chart = new CondoConsumptionChart();
    	chart.setSizeFull();
        return createContentWrapper(chart);
    }
    
    private Component buildCondoInfo() {
    	VerticalLayout details = new VerticalLayout();
    	details.setCaption("Detalhes");
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(new TextField("d Name"));
        details.addComponent(new TextField("First sdf"));
        details.addComponent(new TextField("sdf dfsgad"));
        details.addComponent(new TextField("sdf Name"));
        details.addComponent(new TextField("sf Name"));
        details.addComponent(new TextField("sdf adfg"));
        details.addComponent(new TextField("sdf fgh"));
        details.addComponent(new TextField("sdf sfgh"));
        details.addComponent(new TextField("dfg Name"));
        details.addComponent(new TextField("sdfgh Name"));
        details.setHeight("800px");
        Component panel = createContentWrapper(details);
        return panel;
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
        root.addItem("Configurar", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show(":)");
            }
        });

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
