package br.ufrgs.inf.gar.cwm.dash.ui;

import java.util.Collection;
import java.util.Iterator;

import br.ufrgs.inf.gar.cwm.dash.DashboardUI;
import br.ufrgs.inf.gar.cwm.dash.component.SparklineChart;
import br.ufrgs.inf.gar.cwm.dash.condo.CondoUsageChart;
import br.ufrgs.inf.gar.cwm.dash.condo.EmployeeTable;
import br.ufrgs.inf.gar.cwm.dash.condo.GarageTable;
import br.ufrgs.inf.gar.cwm.dash.condo.LampTable;
import br.ufrgs.inf.gar.cwm.dash.condo.LimitPanel;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherComponent;
import br.ufrgs.inf.gar.cwm.dash.condo.RefresherThread;
import br.ufrgs.inf.gar.cwm.dash.condo.WaterSparklineChart;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;
import br.ufrgs.inf.gar.cwm.dash.domain.DashboardNotification;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.CloseOpenWindowsEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.NotificationsCountUpdatedEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEventBus;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class CondoView extends Panel implements View {

    private static final String HEADER = "Condomínio";
	private static final String NOTIFICATIONS = "Notificações";
	private static final String TITLE_ID = "dashboard-title";
    private final RefresherThread refresher = new RefresherThread();

    private Label titleLabel;
    private NotificationsButton notificationsButton;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private Window notificationsWindow;
    private HorizontalLayout header;

    public CondoView() {
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

        notificationsButton = buildNotificationsButton();
        HorizontalLayout tools = new HorizontalLayout(notificationsButton);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    private NotificationsButton buildNotificationsButton() {
        NotificationsButton result = new NotificationsButton();
        result.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                openNotificationsPopup(event);
            }
        });
        return result;
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        dashboardPanels.addComponent(buildCondoLightChart());
        dashboardPanels.addComponent(buildGarageTable());
        dashboardPanels.addComponent(buildEmployeeTable());
        dashboardPanels.addComponent(buildLampTable());
        dashboardPanels.addComponent(buildLimitPanel());

        return dashboardPanels;
    }
    
    private Component buildLimitPanel() {
    	LimitPanel panel = new LimitPanel();
    	refresher.subscribe(panel);
        Component c = createContentWrapper(panel);
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }

    private Component buildLampTable() {
    	LampTable table = new LampTable();
    	refresher.subscribe(table);
        Component c = createContentWrapper(table);
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildGarageTable() {
    	GarageTable table = new GarageTable();
    	refresher.subscribe(table);
    	Component c = createContentWrapper(table);
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildEmployeeTable() {
    	EmployeeTable table = new EmployeeTable();
    	refresher.subscribe(table);
    	Component c = createContentWrapper(table);
        c.addStyleName("dashboard-panel-slot-table");
        return c;
    }
    
    private Component buildCondoLightChart() {
    	CondoUsageChart chart = new CondoUsageChart();
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

    private void openNotificationsPopup(final ClickEvent event) {
        VerticalLayout notificationsLayout = new VerticalLayout();
        notificationsLayout.setMargin(true);
        notificationsLayout.setSpacing(true);

        Label title = new Label(NOTIFICATIONS);
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        notificationsLayout.addComponent(title);

        Collection<DashboardNotification> notifications = DashboardUI
                .getDataProvider().getNotifications();
        DashboardEventBus.post(new NotificationsCountUpdatedEvent());

        for (DashboardNotification notification : notifications) {
            VerticalLayout notificationLayout = new VerticalLayout();
            notificationLayout.addStyleName("notification-item");

            Label titleLabel = new Label(notification.getFirstName() + " "
                    + notification.getLastName() + " "
                    + notification.getAction());
            titleLabel.addStyleName("notification-title");

            Label timeLabel = new Label(notification.getPrettyTime());
            timeLabel.addStyleName("notification-time");

            Label contentLabel = new Label(notification.getContent());
            contentLabel.addStyleName("notification-content");

            notificationLayout.addComponents(titleLabel, timeLabel,
                    contentLabel);
            notificationsLayout.addComponent(notificationLayout);
        }

        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth("100%");
        notificationsLayout.addComponent(footer);

        if (notificationsWindow == null) {
            notificationsWindow = new Window();
            notificationsWindow.setWidth(300.0f, Unit.PIXELS);
            notificationsWindow.addStyleName("notifications");
            notificationsWindow.setClosable(false);
            notificationsWindow.setResizable(false);
            notificationsWindow.setDraggable(false);
            notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
            notificationsWindow.setContent(notificationsLayout);
        }

        if (!notificationsWindow.isAttached()) {
            notificationsWindow.setPositionY(event.getClientY()
                    - event.getRelativeY() + 40);
            getUI().addWindow(notificationsWindow);
            notificationsWindow.focus();
        } else {
            notificationsWindow.close();
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        notificationsButton.updateNotificationsCount(null);
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

    public static final class NotificationsButton extends Button {
        private static final String STYLE_UNREAD = "unread";
        public static final String ID = "dashboard-notifications";

        public NotificationsButton() {
            setIcon(FontAwesome.BELL);
            setId(ID);
            addStyleName("notifications");
            addStyleName(ValoTheme.BUTTON_ICON_ONLY);
            DashboardEventBus.register(this);
        }

        @Subscribe
        public void updateNotificationsCount(
                final NotificationsCountUpdatedEvent event) {
            setUnreadCount(DashboardUI.getDataProvider()
                    .getUnreadNotificationsCount());
        }

        public void setUnreadCount(final int count) {
            setCaption(String.valueOf(count));

            String description = NOTIFICATIONS;
            if (count > 0) {
                addStyleName(STYLE_UNREAD);
                description += " (" + count + " não lida)";
            } else {
                removeStyleName(STYLE_UNREAD);
            }
            setDescription(description);
        }
    }

}
