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

	void observce(String id);

	void setServer(String server, int port);

	void start();

	void stop();

	void unObservce(String id);

}
