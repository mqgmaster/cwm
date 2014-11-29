package br.ufrgs.inf.gar.cwm.dash.data;

import java.util.Collection;
import java.util.Date;

import br.ufrgs.inf.gar.cwm.dash.domain.DashboardNotification;
import br.ufrgs.inf.gar.cwm.dash.domain.Apt;
import br.ufrgs.inf.gar.cwm.dash.domain.AptRevenue;
import br.ufrgs.inf.gar.cwm.dash.domain.Transaction;
import br.ufrgs.inf.gar.cwm.dash.domain.User;

/**
 * QuickTickets Dashboard backend API.
 */
public interface DataProvider {
    /**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */
    Collection<Transaction> getRecentTransactions(int count);

    /**
     * @param id
     *            Movie identifier.
     * @return A Collection of daily revenues for the movie.
     */
    Collection<AptRevenue> getDailyRevenuesByMovie(long id);

    /**
     * @return Total revenues for each listed movie.
     */
    Collection<AptRevenue> getTotalMovieRevenues();

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password);

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<DashboardNotification> getNotifications();

    /**
     * @return The total summed up revenue of sold movie tickets
     */
    double getTotalSum();

    /**
     * @return A Collection of movies.
     */
    Collection<Apt> getMovies();

    /**
     * @param movieId
     *            Movie's identifier
     * @return A Movie instance for the given id.
     */
    Apt getMovie(long movieId);

    /**
     * @param startDate
     * @param endDate
     * @return A Collection of Transactions between the given start and end
     *         dates.
     */
    Collection<Transaction> getTransactionsBetween(Date startDate, Date endDate);
}
