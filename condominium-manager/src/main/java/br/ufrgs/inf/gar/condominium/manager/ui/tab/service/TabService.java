package br.ufrgs.inf.gar.condominium.manager.ui.tab.service;

import java.io.Serializable;

import br.ufrgs.inf.gar.condominium.manager.ui.tab.generic.AbstractTab;

import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class TabService implements Serializable {
	
	private TabSheet tabs;
	
	public TabService(TabSheet tabs) {
		this.tabs = tabs;
	}
	
	/**
	* Remove todas as abas abertas e adiciona a aba passada como parametro.
	*/
	public void openExclusiveTab(AbstractTab<?> tab) {
		tabs.removeAllComponents();
		tabs.addComponent(tab.getLayout());
	}
	
	/**
	* Adiciona a aba ao navegador mantendo as abas atualmente abertas.
	*/
	public void openTab(AbstractTab<?> tab) {
		tabs.addComponent(tab.getLayout());
	}
}
