package startup;

import javax.swing.JOptionPane;

import sort.Properties;
import window.LobbyFrame;

public class Main {

	private static String OS;
	public static Properties PROPERTIES;

	public static void main(String[] args) {
		OS = System.getProperty("os.name").toLowerCase();
		PROPERTIES = new Properties();
		
		String dir = "./.battleOfAuthrohpia/";
		if (Main.isWindows()) {
			System.out.println("You have Windows");
			dir = "%appdata%/.battleOfAuthrohpia/";
		} else if (Main.isUnix()) {
			System.out.println("You have Unix of some sort");
			dir = System.getProperty("user.home") + "/usr/.battleOfAuthrohpia/";
		} else {
			System.out.println("I don't know your OS");
		}
		PROPERTIES.initProperties(dir);

		LobbyFrame lf = new LobbyFrame();
		lf.openWindow(args, 600, 500, "Battle of Authrohpia");
	}
	
	public static void exit() {
		int val = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
		if(val == 0) {
			System.exit(0);
		}
	}
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
}
