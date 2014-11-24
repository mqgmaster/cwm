package br.ufrgs.inf.gar.cwm.dash;

import java.util.Locale;

import br.ufrgs.inf.gar.cwm.dash.data.DataProvider;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataProvider;
import br.ufrgs.inf.gar.cwm.dash.data.Simulator;
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
	
	public static final String AGENT_ADDRESS = "udp:10.0.0.102/161";

	/**
	* Porta 161 é usada para gets and sets
	* Porta 162 é usada para traps
	*/

    private final DataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);
        
      //  try {
			//SNMPManager.start(AGENT_ADDRESS);
		//} catch (IOException e) {
	//		e.printStackTrace();
	//	}
        Simulator.start();
        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);

        updateContent();

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event but on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        DashboardEventBus.post(new BrowserResizeEvent());
                    }
                });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
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
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((DashboardUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}
