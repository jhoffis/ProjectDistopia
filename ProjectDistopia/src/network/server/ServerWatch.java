package network.server;

import javafx.application.Platform;
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
	
	public ServerWatch(ServerInfo info) {
		this.info = info;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 2.0;
		double ns = 1000000000 / amountOfTicks;
		double deltatick = 0;
		double deltarender = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (Main.SERVER != null) {
			long now = System.nanoTime();
			deltatick += (now - lastTime) / ns;
			lastTime = now;
			while (deltatick >= 1) {
				deltatick--;
				frames++;

				info.checkConnections();

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("CHECKING CONNECTIONS: " + frames);
				frames = 0;
			}
		}
		
		
	}

}
