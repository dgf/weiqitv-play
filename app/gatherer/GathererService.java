package gatherer;

import play.Logger;

public class GathererService {

	public static GathererService instance = new GathererService();

	private Gatherer igs = new IgsGatherer();

	private GathererService() {
		Logger.debug("start gatherer service");
	}

	public void startGatherer(String server, int port) {
		Logger.info("start gatherer %s:%s", server, port);
		igs.setServer(server, port);
		igs.connect();
		igs.loginAnonymous();
		igs.start();
	}

	public void restartGatherer(String server, int port) {
		Logger.info("restart gatherer %s:%s", server, port);
		stopGatherer();
		startGatherer(server, port);
	}

	public void stopGatherer() {
		Logger.info("stop gatherer");
		igs.stop();
		igs.logout();
		igs.disconnect();
	}

	public void observe(String number) {
		igs.observce(number);
	}
}
