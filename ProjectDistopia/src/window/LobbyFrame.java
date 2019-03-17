package window;

import java.util.HashMap;

import javax.swing.JOptionPane;

import adt.LobbySceneADT;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scene.Lobby;
import scene.LobbySetup;
import scene.MainMenu;
import scene.Options;
import scene.UserSetup;
import startup.Main;

public class LobbyFrame extends Application {

	public static int WIDTH;
	public static int HEIGHT;
	public static Stage PRIMARY_STAGE;
	public static LobbySceneADT LAST_SCENE;
	public static LobbySceneADT CURRENT_SCENE;
	public static String TITLE;

	private static HashMap<String, LobbySceneADT> SCENES;
	private static HashMap<String, Pane> PANES;

	private String[] pathnames = { "LAST", "UPPER", "MAINMENU", "OPTIONS", "HOSTSETUP", "JOINSETUP", "LOBBY", "USER" };

	@Override
	public void start(Stage primaryStage) {
		PRIMARY_STAGE = primaryStage;
		SCENES = new HashMap<String, LobbySceneADT>();
		PANES = new HashMap<String, Pane>();

		for (int i = 0; i < pathnames.length; i++) {
			PANES.put(pathnames[i], new Pane());
		}
		// FAKE:
		SCENES.put(pathnames[0], new MainMenu(pathnames[0]));
		SCENES.put(pathnames[1], new MainMenu(pathnames[1]));

		SCENES.put(pathnames[2], new MainMenu(pathnames[2]));
		SCENES.put(pathnames[3], new Options(pathnames[3]));
		SCENES.put(pathnames[4], new LobbySetup(pathnames[4], true));
		SCENES.put(pathnames[5], new LobbySetup(pathnames[5], false));
		SCENES.put(pathnames[6], new Lobby(pathnames[6]));
		SCENES.put(pathnames[7], new UserSetup(pathnames[7]));

		setScene("MainMenu");
		PRIMARY_STAGE.setTitle(TITLE);
		PRIMARY_STAGE.setResizable(false);
		PRIMARY_STAGE.setOnHidden(e -> shutdown());
		PRIMARY_STAGE.show();
	}

	public static void shutdown() {
		int val = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
		if (val == 0) {
			System.out.println("Shutdown");
			if (Main.CLIENT != null)
				Main.CLIENT.forceLeave();
			if (Main.SERVER != null)
				Main.SERVER.setRunning(false);
			System.exit(0);
		}
	}

	public static void add(Pane pane, Node e) {

		try {
			pane.getChildren().add(e);
		} catch (Exception ex) {
			System.err.println("Could not ADD element");
			ex.printStackTrace();
		}
	}

	public static void add(String paneName, Node e) {
		add(PANES.get(paneName), e);
	}

	public static void remove(String panename, Node e) {
		try {
			PANES.get(panename).getChildren().remove(e);
		} catch (Exception ex) {
			System.err.println("Could not REMOVE element");
			ex.printStackTrace();
		}
	}
	
	public static void replacePane(String panename) {
		PANES.replace(panename, new Pane());
	}

	public static void setAndReplaceScene(LobbySceneADT scene) {
		SCENES.replace(scene.getPanename(), scene);
		setScene(scene.getPanename());
	}

	public static void setScene(String sceneName) {
		try {
			sceneName = sceneName.toUpperCase();

			if (!SCENES.containsKey(sceneName)) {
				throw new Exception();
			}

			LobbySceneADT scene = null;
			if (sceneName.equals("LAST")) {
				scene = LAST_SCENE;
			} else if (sceneName.equals("UPPER")) {
				if (CURRENT_SCENE.getPanename().equals(("MAINMENU"))) {
					shutdown();
				} else {
					scene = SCENES.get("MAINMENU");
				}
			} else {
				scene = SCENES.get(sceneName);
			}

			LAST_SCENE = CURRENT_SCENE;
			scene.update();
			PRIMARY_STAGE.setScene(scene);
			PRIMARY_STAGE.show();
			CURRENT_SCENE = scene;
		} catch (Exception e) {
			System.err.println("FAILED TO CHANGE SCENE IN LobbyFrame: setScene(\"" + sceneName + "\");");
		}
	}

	public void openWindow(String[] args, int width, int height, String title) {
		WIDTH = width;
		HEIGHT = height;
		TITLE = title;
		launch(args);
	}

	public static Pane getPane(String panename) {
		return PANES.get(panename);
	}

}
