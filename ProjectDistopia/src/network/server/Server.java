package network.server;

import java.io.IOException;
import java.net.ServerSocket;

import elem.ConnectionConfig;

/**
 * Use both regular sockets, ack and rmi. Make it simple as you can practice
 * more advanced stuff other places.
 * 
 * 
 * @author jhoffis
 *
 */

public class Server {

	private ServerWatch watch;
	private Thread watchThread;
	private int serverport;
	private boolean running;
	private ServerSocket socketserver;
	private ServerInfo info;
	private TCPServer server;
	private String title;

	public Server(String title) {
		this.title = title;
		serverport = ConnectionConfig.PORT.valueAsInteger();
		info = new ServerInfo();
		info.setTitle(title);
		System.out.println("TCP server starting at port " + serverport);
		
		try {
			socketserver = new ServerSocket(serverport);
			server = new TCPServer(socketserver, info);

		} catch (Exception e) {
			System.out.println("TCP server: " + e.getMessage());
			e.printStackTrace();
		}
		
		watch = new ServerWatch(info);
		watchThread = new Thread(watch);
		watchThread.start();
	}
	/*
	 * Getters and setters
	 */
	public boolean isRunning() {
		return running;
	}
	
	public void stopWatch() {
		watch.endTheWatch();
	}

	public void setRunning(boolean running) {

		this.running = running;

		if (running == false) {
			try {
				socketserver.close();
				watch.endTheWatch();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void checkLostConnection() {
		//Go through users and check last time they connected;
	}
}
