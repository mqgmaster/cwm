package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class RefresherThread implements Serializable {
	
	private final List<RefresherComponent> list = new ArrayList<>();
	public static final int INTERVAL = 2000;
	public static final int INITIAL_PAUSE = 2000;
	
	public RefresherThread() {
		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(INITIAL_PAUSE);
					while (true) {
						synchronized (list) {
							for (RefresherComponent task : list) {
								task.getUI().access(task);
							}
						}
						System.out.println("refresher thread");
						Thread.sleep(INTERVAL);
					}
				} catch (InterruptedException e) {
				} catch (com.vaadin.ui.UIDetachedException e) {
				} catch (Exception e) {
					Logger.getLogger(getClass().getName())
							.log(Level.WARNING,
									"Unexpected exception while running scheduled update",
									e);
				}
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Thread stopped");
			};
		};
		thread.start();
	}
	
	public void unsubscribe(RefresherComponent component) {
		synchronized (list) {
			list.remove(component);
		}
	}
	
	public void subscribe(RefresherComponent component) {
		synchronized (list) {
			list.add(component);
		}
	}
	
	public boolean isSubscribed(RefresherComponent component) {
		boolean isSubscribed = false;
		synchronized (list) {
			for (RefresherComponent comp : list) {
				if(comp.getComponentId().equals(component.getComponentId())) {
					isSubscribed = true;
				};
			}
		}
		return isSubscribed;
	}
}
