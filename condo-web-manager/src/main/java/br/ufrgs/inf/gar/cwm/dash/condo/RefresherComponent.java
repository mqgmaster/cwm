package br.ufrgs.inf.gar.cwm.dash.condo;

import com.vaadin.ui.Component;

public interface RefresherComponent extends Runnable, Component {
	
	public String getComponentId();
	
	public static boolean equalsAnother(RefresherComponent component, Object another) {
		if (another instanceof RefresherComponent) {
			if (component.getComponentId() != null) {
				return component.getComponentId().equals(((RefresherComponent) another).getComponentId());
			}
		}
		return false;
	}
}
