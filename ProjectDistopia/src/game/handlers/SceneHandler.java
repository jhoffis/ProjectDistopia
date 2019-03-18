package game.handlers;

import adt.GameSceneADT;
import game.scenes.GovScene;
import game.scenes.MenuScene;
import game.scenes.OptionsScene;
import game.scenes.SaveGameScene;
import game.scenes.WorldScene;

public class SceneHandler {

	private GameSceneADT[] scenes;
	private int currentScene;

	public SceneHandler() {
		scenes = new GameSceneADT[5];
		currentScene = 0;
		scenes[0] = new WorldScene();
		scenes[1] = new GovScene();
		scenes[2] = new MenuScene();
		scenes[3] = new OptionsScene();
		scenes[4] = new SaveGameScene();
	}

	public GameSceneADT getCurrent() {
		return scenes[currentScene];
	}

}
