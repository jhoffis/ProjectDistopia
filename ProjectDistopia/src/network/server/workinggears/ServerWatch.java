package network.server.workinggears;

import javafx.application.Platform;
import network.server.info.ServerInfo;
import startup.Main;
import window.LobbyFrame;
/**
 * 
 * Watches the users for lost connections.
 * 
 * @author jhoffis
 *
 */
public class ServerWatch implements Runnable{

	private ServerInfo info;
	private boolean running;
	
	public ServerWatch(ServerInfo info) {
		this.info = info;
	}
	
	@Override
	public void run() {
		running = true;
		long lastTime = System.nanoTime();
		double deltatick = 0;

		while (Main.SERVER != null && running) {
			long now = System.nanoTime();
			deltatick += (now - lastTime);
			lastTime = now;
			while (deltatick >= 1) {
				deltatick--;
				info.checkConnections();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void endTheWatch() {
		running = false;
	}

}
