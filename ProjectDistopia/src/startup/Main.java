package startup;

import elem.User;
import network.client.Client;
import network.server.Server;
import sort.SettingsProperties;
import sort.UserProperties;
import window.GameFrame;
import window.LobbyFrame;

public class Main {

	private static GameFrame GAME_FRAME;
	private static String OS;
	public static String NAME;
	public static UserProperties USER_PROPERTIES;
	public static SettingsProperties SETTINGS_PROPERTIES;
	public static Server SERVER; 
	public static Client CLIENT;
	public static User USER;
	
	

	public static void main(String[] args) {
		OS = System.getProperty("os.name").toLowerCase();
		USER_PROPERTIES = new UserProperties();
		SETTINGS_PROPERTIES = new SettingsProperties();
		SERVER = null;
		CLIENT = null;
		USER = null;
		NAME = "Battle of Authradgard";
		
		String dir = "./.battleOfAuthradgard/";
		if (Main.isWindows()) {
			System.out.println("You have Windows");
			dir = "%appdata%/.battleOfAuthradgard/";
		} else if (Main.isUnix()) {
			System.out.println("You have Unix of some sort");
			dir = System.getProperty("user.home") + "/usr/.battleOfAuthradgard/";
		} else {
			System.out.println("I don't know your OS");
		}
		USER_PROPERTIES.initProperties(dir, "users");
		SETTINGS_PROPERTIES.initProperties(dir, "settings");

		LobbyFrame lf = new LobbyFrame();
		lf.openWindow(args, 600, 500, NAME);
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
	
	public static void openGameFrame() {
		
		
		GAME_FRAME = new GameFrame(600, 500, NAME);
		
	}
	
	public static void closeGameFrame() {
		//FIXME close the GAME_FRAME
	}
}
