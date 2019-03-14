package startup;

import handlers.Properties;
import window.LobbyFrame;

public class Main {

	private static String OS;
	public static Properties PROPERTIES;

	public static void main(String[] args) {
		OS = System.getProperty("os.name").toLowerCase();
		PROPERTIES = new Properties();

		LobbyFrame lf = new LobbyFrame();
		lf.openWindow(args, 600, 500, "Battle of Authrohpia");
	}
	
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
}
