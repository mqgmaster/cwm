package br.ufrgs.inf.gar.cwm.dash.condo;

import java.io.IOException;

import br.ufrgs.inf.gar.condo.domain.Condominium;
import br.ufrgs.inf.gar.condo.domain.UsageValue;
import br.ufrgs.inf.gar.cwm.dash.data.DaoService;
import br.ufrgs.inf.gar.cwm.dash.data.DummyDataGenerator;

@SuppressWarnings("serial")
public class CondoWaterWeekChart extends UsageWeekChart implements RefresherComponent {

	private static final String TITLE_CHART = "Consumo de água / semana";
	private static final String UNIT = "L";

	public CondoWaterWeekChart() {
		super(TITLE_CHART, DummyDataGenerator.chartColors[0]);
	}

	@Override
	protected void setInitialUsage() {
		try {
			initWeek = DaoService.get().getCondoUsages().getTotalWaterUsage().toFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Condominium condo = DaoService.get().getCondoUsages();
			UsageValue waterLimit = condo.getInstantWaterLimit();
			endWeek = condo.getTotalWaterUsage().toFloat();
			
			weekConsum = endWeek - initWeek;
			System.out.println(endWeek + " - " + initWeek);
			if (getSeries().size() > 4) {
				addUsageItemsSeries(new UsageValue(weekConsum), waterLimit, true);
			} else {
				addUsageItemsSeries(new UsageValue(weekConsum), waterLimit, false);
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
