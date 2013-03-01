package gatherer;

/**
 * gather games and save those in storage
 */
public interface WeiqiGameGatherer {

    void connect();

    void disconnect();

    boolean isConnected();

    boolean isLoggedIn();

    void loginAnonymous();

    void login(String user, String password);

    void logout();

    void observe(String id);

    void setServer(String server, int port);

    void start();

    void stop();

    void unObserve(String id);

    void toggle(IgsOption option);

}
