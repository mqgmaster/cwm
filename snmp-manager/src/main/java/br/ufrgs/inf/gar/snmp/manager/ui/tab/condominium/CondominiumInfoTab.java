package br.ufrgs.inf.gar.snmp.manager.ui.tab.condominium;

import br.ufrgs.inf.gar.snmp.manager.ui.tab.generic.ManagerTab;

@SuppressWarnings("serial")
public class CondominiumInfoTab implements ManagerTab<CondominiumInfoLayout>{

	private CondominiumInfoLayout layout = new CondominiumInfoLayout();
	
	public CondominiumInfoTab() {
		
	}
	
	@Override
	public CondominiumInfoLayout getLayout() {
		return layout;
	}

}
