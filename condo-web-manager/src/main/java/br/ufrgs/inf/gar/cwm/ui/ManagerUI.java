package br.ufrgs.inf.gar.cwm.ui;


/*
@Push
@Theme("mytheme")
@SuppressWarnings("serial")
public class ManagerUI extends UI {
	
	public static final String AGENT_ADDRESS = "udp:10.0.0.102/161";

	private static final String BROWSER_TITLE = "CWM";
	
	/**
	* Porta 161 é usada para gets and sets
	* Porta 162 é usada para traps
	*/

	//private final ManagerLayout layout = new ManagerLayout();
	//private final TabService tabService = new TabService(layout.getTabs());
	
   /* @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ManagerUI.class, 
    	widgetset = "br.ufrgs.inf.gar.cwm.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    	Page.getCurrent().setTitle(BROWSER_TITLE);
        setContent(layout);
        tabService.openTab(new HomeTab());
        layout.getAptosButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new AptInfoTab());
	        }
	    });
        
        layout.getHomeButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new HomeTab());
	        }
	    });
        
        layout.getCondominiumButton().addClickListener(new Button.ClickListener() {
	        public void buttonClick(ClickEvent event) {
	        	tabService.openExclusiveTab(new CondoInfoTab());
	        	tabService.openTab(new CondoLightTab());
	        }
	    });
        
        try {
        	Simulator.start();
			SNMPManager.start(AGENT_ADDRESS);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
*/