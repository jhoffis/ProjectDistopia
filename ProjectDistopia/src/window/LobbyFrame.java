package window;

import java.util.HashMap;

import adt.LobbySceneADT;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lobby.scene.Lobby;
import lobby.scene.LobbySetup;
import lobby.scene.MainMenu;
import lobby.scene.Options;
import lobby.scene.UserSetup;
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
			Pane pane = new Pane();
			pane.setBackground(
					new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			PANES.put(pathnames[i], pane);
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
		PRIMARY_STAGE.setOnCloseRequest(e -> {
			e.consume();

			// execute own shutdown procedure
			shutdown();
		});

		PRIMARY_STAGE.show();
	}

	public static void forceShutdownLobby() {
		PRIMARY_STAGE.setOnCloseRequest(null);
		PRIMARY_STAGE.close();
	}

	public static synchronized void shutdown() {
		Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to exit now?", ButtonType.YES,
				ButtonType.NO);
		alert.setTitle("Beware!");
		if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
			// you may need to close other windows or replace this with Platform.exit();
			System.out.println("Shutdown");
			if (Main.CLIENT != null) {
				if(Main.USER != null)
					Main.CLIENT.leave(Main.USER.getId());
				Main.CLIENT = null;
			}
			if (Main.SERVER != null) {
				Main.SERVER.setRunning(false);
				Main.SERVER = null;
			}
			Platform.runLater(() -> PRIMARY_STAGE.close());
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
