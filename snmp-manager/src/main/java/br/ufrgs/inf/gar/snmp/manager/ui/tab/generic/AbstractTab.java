package br.ufrgs.inf.gar.snmp.manager.ui.tab.generic;

import java.io.Serializable;

import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public abstract class AbstractTab<T extends CustomComponent> implements Serializable {
	
	protected T layout;
	
	public AbstractTab(Class<T> clazz) {
		try {
			layout = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public T getLayout() {
		return layout;
	}
}
