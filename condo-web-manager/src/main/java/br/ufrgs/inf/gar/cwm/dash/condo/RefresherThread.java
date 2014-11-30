package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class RefresherThread implements Serializable {
	
	private final List<RefresherComponent> list = new ArrayList<>();
	
	public RefresherThread(final int interval, final int initialPause) {
		final Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(initialPause);
					while (true) {
						synchronized (list) {
							for (RefresherComponent task : list) {
								if (task.isAttached()) {
									task.getUI().access(task);
								}
							}
						}
						System.out.println("refresher thread");
						Thread.sleep(interval);
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
