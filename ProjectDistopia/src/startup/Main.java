package startup;

import java.util.ArrayList;

import audio.BgMusicListener;
import audio.MediaAudio;
import elem.User;
import game.handlers.GameHandler;
import javafx.application.Platform;
import network.client.Client;
import network.server.workinggears.Server;
import sort.SettingsProperties;
import sort.UserProperties;
import window.GameFrame;
import window.LobbyFrame;

public class Main {

	private static GameFrame GAME_FRAME;
	public static String OS;
	public static String NAME;
	public static UserProperties USER_PROPERTIES;
	public static SettingsProperties SETTINGS_PROPERTIES;
	public static Server SERVER;
	public static Client CLIENT;
	public static User USER;
	public static int WIDTH;
	public static int HEIGHT;
	public static int MUSIC_TYPE;
	public static ArrayList<MediaAudio> SOUNDS;

	public static void main(String[] args) {
		OS = System.getProperty("os.name").toLowerCase();
		USER_PROPERTIES = new UserProperties();
		SETTINGS_PROPERTIES = new SettingsProperties();
		SERVER = null;
		CLIENT = null;
		USER = null;
		NAME = "Battle of Authradgard";
		WIDTH = 600;
		HEIGHT = 500;
		SOUNDS = new ArrayList<MediaAudio>();
		String dir = "./.battleOfAuthradgard/";
		if (Main.isWindows()) {
			System.out.println("You have Windows");
			dir = System.getenv("APPDATA") + "/.battleOfAuthradgard/";
		} else if (Main.isUnix()) {
			System.out.println("You have Unix of some sort");
			dir = System.getProperty("user.home") + "/usr/.battleOfAuthradgard/";
		} else {
			System.out.println("I don't know your OS");
		}
		USER_PROPERTIES.initProperties(dir, "users");
		SETTINGS_PROPERTIES.initProperties(dir, "settings");

		/*
		 * DEBUG
		 */
		boolean testing = false;

		if (testing) {
			USER = new User("JOnathan Test", 69, 1, 69);
			USER.setFaction("Gazellia");
			MUSIC_TYPE = 0;
			openGameFrame();

		} else {
			LobbyFrame lf = new LobbyFrame();
			lf.openWindow(args, WIDTH, HEIGHT, NAME);
		}
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static void openGameFrame() {

		GAME_FRAME = new GameFrame(WIDTH, HEIGHT, NAME);
		GameHandler gm = new GameHandler(GAME_FRAME);
		new Thread(gm).start();
	}

	public static void closeGameFrame() {
		GAME_FRAME.close();
	}

	public static void updateVolume() {
		for(int i = 0; i < SOUNDS.size(); i++) {
			SOUNDS.get(i).setVolume();
		}		
	}
	
	public static void lbtn() {
		SOUNDS.add(1, new MediaAudio("/sfx/btn"));
	}
}
