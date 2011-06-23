package gatherer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

/**
 * synchronous write and read for initial commands like login
 * 
 * @author danny
 */
class TelnetUtil extends Thread {

	private static final Logger log = Logger.getLogger(TelnetUtil.class);

	private TelnetClient telnet;

	private BufferedReader in;
	private PrintWriter out;

	private String server;

	private int port;

	private boolean shouldRead;

	private List<TelnetOutputListener> listener = new ArrayList<TelnetOutputListener>();

	public TelnetUtil() {
		telnet = new TelnetClient();
	}

	/**
	 * factory method
	 * 
	 * @param server
	 * @param port
	 * @return
	 */
	public static TelnetUtil connect(String server, int port) {
		TelnetUtil util = new TelnetUtil();
		util.setServer(server);
		util.setPort(port);
		log.debug("create telnet connection with " + util);
		util.connect();
		return util;
	}

	public synchronized String readUntil(String pattern) {
		log.debug("read until: " + pattern);
		StringBuffer sb = new StringBuffer();
		char lastChar = pattern.charAt(pattern.length() - 1);
		char ch = readChar();
		while (true) {
			sb.append(ch);
			if (ch == lastChar && sb.toString().endsWith(pattern)) {
				log.debug("readed: " + sb);
				return sb.toString();
			}
			ch = readChar();
		}
	}

	private char readChar() {
		try {
			return (char) in.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void send(String value) {
		log.debug("write: " + value);
		out.println(value);
		out.flush();
	}

	public String getServer() {
		return server;
	}

	public int getPort() {
		return port;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * open telnet connection and assign streams
	 */
	public synchronized void connect() {
		log.debug("check whether already connected with " + this);
		if (telnet.isConnected()) {
			throw new IllegalStateException("already connected");
		}

		try {
			log.info("try to connect with " + this);
			telnet.connect(server, port);
			log.debug("connected with " + this);
		} catch (Exception e) {
			throw new RuntimeException("telnet connection failed with " + this, e);
		}

		in = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
		out = new PrintWriter(telnet.getOutputStream());
	}

	public synchronized boolean isConnected() {
		return telnet.isConnected();
	}

	public synchronized void disconnect() throws IOException {
		telnet.disconnect();
	}

	@Override
	public String toString() {
		return server + ":" + port;
	}

	public void addOutputListener(TelnetOutputListener handler) {
		listener.add(handler);
	}

	public synchronized void setShouldRead(boolean shouldRead) {
		this.shouldRead = shouldRead;
	}

	public synchronized boolean isShouldRead() {
		return shouldRead;
	}

	@Override
	public void run() {
		setShouldRead(true);
		while (isShouldRead()) {
			String line;
			try {
				line = in.readLine();
				log.debug("read line: " + line);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			for (TelnetOutputListener l : listener) {
				if (l.notify(line)) {
					try {
						while (l.notify(in.readLine())) {
							// send next lines to the interested listener
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				}
			}
		}
		log.debug("telnet reading stopped");
	}

}
