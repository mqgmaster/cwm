package br.ufrgs.inf.gar.cwm.dash;

import java.io.IOException;
import java.util.Locale;

import br.ufrgs.inf.gar.cwm.dash.data.DaoService;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataProvider;
import br.ufrgs.inf.gar.cwm.dash.domain.User;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.BrowserResizeEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.CloseOpenWindowsEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.UserLoggedOutEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEvent.UserLoginRequestedEvent;
import br.ufrgs.inf.gar.cwm.dash.event.DashboardEventBus;
import br.ufrgs.inf.gar.cwm.dash.ui.LoginView;
import br.ufrgs.inf.gar.cwm.dash.ui.MainView;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@Push
@Theme("dashboard")
@Widgetset("br.ufrgs.inf.gar.cwm.CondoWebManagerWidgetSet")
@Title("Condo WebManager")
@SuppressWarnings("serial")
public final class DashboardUI extends UI {
	
    private final DummyDataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US); //Necessario devido a valores float
        try {
			DaoService.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);

        updateContent();

        Page.getCurrent().addBrowserWindowResizeListener(
            new BrowserWindowResizeListener() {
                @Override
                public void browserWindowResized(
                        final BrowserWindowResizeEvent event) {
                    DashboardEventBus.post(new BrowserResizeEvent());
                }
            });
    }

    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null && "admin".equals(user.getRole())) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = getDataProvider().authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    public static DummyDataProvider getDataProvider() {
        return ((DashboardUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}
