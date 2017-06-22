package br.ufrgs.inf.gar.cwm.dash.data;

import java.util.Collection;
import java.util.Collections;

import br.ufrgs.inf.gar.cwm.dash.domain.DashboardNotification;
import br.ufrgs.inf.gar.cwm.dash.domain.User;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class DummyDataProvider {

    private final Collection<DashboardNotification> notifications = DummyDataGenerator
            .randomNotifications();

    public DummyDataProvider() {
    }

    public User authenticate(String userName, String password) {
        User user = new User();
        user.setName("Mauricio Q.G.");
        user.setRole("admin");
        String email = "mauricio@cwm.com";
        user.setEmail(email.replaceAll(" ", ""));
        return user;
    }

    public int getUnreadNotificationsCount() {
        Predicate<DashboardNotification> unreadPredicate = new Predicate<DashboardNotification>() {
            @Override
            public boolean apply(DashboardNotification input) {
                return !input.isRead();
            }
        };
        return Collections2.filter(notifications, unreadPredicate).size();
    }

    public Collection<DashboardNotification> getNotifications() {
        for (DashboardNotification notification : notifications) {
            notification.setRead(true);
        }
        return Collections.unmodifiableCollection(notifications);
    }
}
