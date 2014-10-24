package br.ufrgs.inf.gar.snmp.manager.ui.tab.generic;

import java.io.Serializable;

import com.vaadin.ui.CustomComponent;

public interface ManagerTab<T extends CustomComponent> extends Serializable {

	public T getLayout();
}
