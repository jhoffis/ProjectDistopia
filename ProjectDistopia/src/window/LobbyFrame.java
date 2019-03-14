package window;

import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scene.MainMenu;
import scene.Options;

public class LobbyFrame extends Application {

	public static int WIDTH;
	public static int HEIGHT;
	public static Stage primaryStage;

	private static String TITLE;
	private static HashMap<String, Scene> scenes;
	private static HashMap<String, Pane> panes;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		scenes = new HashMap<String, Scene>();
		panes = new HashMap<String, Pane>();

		Pane mainMenuPane = new Pane();
		Pane optionsPane = new Pane();
		panes.put("MainMenu", mainMenuPane);
		panes.put("Options", optionsPane);

		scenes.put("MainMenu", new MainMenu(mainMenuPane));
		scenes.put("Options", new Options(optionsPane));

		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		setScene("MainMenu");
		primaryStage.show();
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
		add(panes.get(paneName), e);
	}

	public static void setScene(String sceneName) {
		try {
//			primaryStage.hide();
			primaryStage.setScene(scenes.get(sceneName));
			primaryStage.show();

		} catch (Exception e) {
			System.err.println("Failed to change scene");
			e.printStackTrace();
		}
	}

	public void openWindow(String[] args, int width, int height, String title) {
		WIDTH = width;
		HEIGHT = height;
		TITLE = title;
		launch(args);
	}

}
