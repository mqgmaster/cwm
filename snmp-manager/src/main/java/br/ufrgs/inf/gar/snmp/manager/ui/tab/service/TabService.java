package br.ufrgs.inf.gar.snmp.manager.ui.tab.service;

import br.ufrgs.inf.gar.snmp.manager.ui.tab.generic.ManagerTab;

import com.vaadin.ui.TabSheet;

public class TabService {
	
	private TabSheet tabs;
	
	public TabService(TabSheet tabs) {
		this.tabs = tabs;
	}
	
	/**
	* Remove todas as abas abertas e adiciona a aba passada como parametro.
	*/
	public void openExclusiveTab(ManagerTab<?> tab) {
		tabs.removeAllComponents();
		tabs.addComponent(tab.getLayout());
	}
	
	/**
	* Adiciona a aba ao navegador mantendo as abas atualmente abertas.
	*/
	public void openTab(ManagerTab<?> tab) {
		tabs.addComponent(tab.getLayout());
	}
}
