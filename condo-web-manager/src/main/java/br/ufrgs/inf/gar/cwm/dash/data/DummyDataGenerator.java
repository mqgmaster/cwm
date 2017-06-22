package br.ufrgs.inf.gar.cwm.dash.data;

import java.util.Arrays;
import java.util.Collection;

import br.ufrgs.inf.gar.cwm.dash.domain.DashboardNotification;

import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;

public abstract class DummyDataGenerator {

    static Collection<DashboardNotification> randomNotifications() {
        DashboardNotification n1 = new DashboardNotification();
        n1.setId(1);
        n1.setFirstName("Maria");
        n1.setLastName("Paula");
        n1.setAction("registrou um problema");
        n1.setPrettyTime("25 minutos atrás");
        n1.setContent("Vazamento no apartamento 205!");

        return Arrays.asList(n1);
    }

    public static int[] randomSparklineValues(int howMany, int min, int max) {
        int[] values = new int[howMany];

        for (int i = 0; i < howMany; i++) {
            values[i] = (int) (min + (Math.random() * (max - min)));
        }

        return values;
    }

    public static Color[] chartColors = new Color[] {
            new SolidColor("#3090F0"), new SolidColor("#18DDBB"),
            new SolidColor("#98DF58"), new SolidColor("#F9DD51"),
            new SolidColor("#F09042"), new SolidColor("#EC6464") };
}