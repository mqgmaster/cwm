package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.UsageValue;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;

@SuppressWarnings("serial")
public class CondoElectricWeekChart extends UsageWeekChart implements RefresherComponent {

	private static final String TITLE_CHART = "Consumo de luz / semana";
	private static final String UNIT = "kWh";

	public CondoElectricWeekChart() {
		super(TITLE_CHART, DummyDataGenerator.chartColors[2]);
	}

	@Override
	protected void setInitialUsage() {
		try {
			initWeek = DaoService.get().getCondoUsages().getTotalElectricUsage().toFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Condominium condo = DaoService.get().getCondoUsages();
			UsageValue electricLimit = condo.getInstantElectricLimit();
			endWeek = condo.getTotalElectricUsage().toFloat();
			
			weekConsum = endWeek - initWeek;
			System.out.println(endWeek + " - " + initWeek);
			if (getSeries().size() > 4) {
				addUsageItemsSeries(new UsageValue(weekConsum), electricLimit, true);
			} else {
				addUsageItemsSeries(new UsageValue(weekConsum), electricLimit, false);
			}
			setCurrentUsage(new UsageValue(weekConsum));
			
			initWeek = endWeek;
			endWeek = -2f;
			
			updateMaxMinUsage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getComponentId() {
		return TITLE_CHART;
	}

	@Override
	protected String getUsageUnit() {
		return UNIT;
	}
}
