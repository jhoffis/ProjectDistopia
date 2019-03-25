package game.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import adt.GameSceneADT;
import adt.Unit;
import elem.Tile;
import game.scenes.GovScene;
import game.scenes.MenuScene;
import game.scenes.OptionsScene;
import game.scenes.SaveGameScene;
import game.scenes.WorldScene;
import game.scenes.world.Echo;
import startup.Main;

public class SceneAndMouseHandler extends MouseInputAdapter {

	private GameSceneADT[] scenes;
	private static int currentScene;

	public SceneAndMouseHandler(JFrame frame) {
		scenes = new GameSceneADT[5];
		currentScene = 0;
		scenes[0] = new WorldScene(frame);
		scenes[1] = new GovScene();
		scenes[2] = new MenuScene();
		scenes[3] = new OptionsScene();
		scenes[4] = new SaveGameScene();
	}

	public GameSceneADT getCurrent() {
		return scenes[currentScene];
	}

	public static void changeScene(int n) {
		currentScene = n;
	}

	public GameSceneADT[] getScenes() {
		return scenes;
	}

	public void setScenes(GameSceneADT[] scenes) {
		this.scenes = scenes;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TEST FIXME
//		System.out.println("x: " + (e.getX() - mouseClickx) + " y: " + (e.getY() - mouseClicky));

		int x = e.getX();
		int y = e.getY();

		switch (currentScene) {

		// Sjekk om man er over ui
		case (0):
			if (SwingUtilities.isLeftMouseButton(e)) {
				WorldScene s0 = ((WorldScene) scenes[0]);
				if (!s0.getUi().above(s0.getMouseClickx(), s0.getMouseClicky())) {
					s0.getCam().setX(s0.getMouseClickxCam() + (e.getX() - s0.getMouseClickx()));
					s0.getCam().setY(s0.getMouseClickyCam() + (e.getY() - s0.getMouseClicky()));
					s0.setMouseSelect(false);
				}
			}
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println("Move");
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
//		System.out.println("SCROLL");

		switch (currentScene) {

		case (0):
			WorldScene s0 = ((WorldScene) scenes[0]);

			int unitsToScroll = e.getUnitsToScroll();
			int direction = unitsToScroll < 0 ? 1 : -1;

			s0.getCam().translateZ(direction * s0.getSizeWH() / 4);
			int x = direction * ((Main.WIDTH / 2) - e.getX()) / 4;
			int y = direction * ((Main.HEIGHT / 2) - e.getY()) / 4;
			s0.getCam().translateX(x);
			s0.getCam().translateY(y);

			Echo.println("Cam X: " + x + ", Y: " + y + ", Z: " + s0.getCam().getZ());
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		switch (currentScene) {

		case (0):
			WorldScene s0 = ((WorldScene) scenes[0]);
			if (e.getButton() == MouseEvent.BUTTON1) {
				// Left click
				if (!s0.getUi().above(x, y)) {
					Tile tile = s0.getTileByCoor(x, y);
					s0.getUi().setSelectedTile(tile);
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				// Right click
				Tile curr = s0.getUi().getSelectedTile();
				if (!curr.getObjects().isEmpty()) {
					Tile dest = s0.getTileByCoor(x, y);

					if (curr.getPoint().equals(dest.getPoint()) || s0.getUi().above(x,y))
						break;

					// FIXME make and add path to unit and move immidiatley when unit has movement
					// points to spare, but checking if possible to move to next tile. if not then
					// abort movement.
					Unit unit = ((Unit) curr.getObject(0));
					try {
						unit.setMovepoints(1);
						dest = Main.CLIENT.getServerMethods().move(unit, curr, dest);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					curr.getObjects().clear();

					s0.getUi().setSelectedTile(dest);
				}

			}
			break;

		case (2):
			MenuScene s2 = ((MenuScene) scenes[2]);
			s2.runEvent(x, y);
			break;
		case (3):
			OptionsScene s3 = ((OptionsScene) scenes[3]);
			s3.runEvent(x, y);
			break;
		case (4):
			SaveGameScene s4 = ((SaveGameScene) scenes[4]);
			s4.runEvent(x, y);
			break;
		// and 3 and 4

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (currentScene) {

		case (0):
			WorldScene s0 = ((WorldScene) scenes[0]);
			s0.setMouseClickxCam(s0.getCam().getX());
			s0.setMouseClickyCam(s0.getCam().getY());
			s0.setMouseClickx(e.getX());
			s0.setMouseClicky(e.getY());
			s0.setMouseSelect(true);
			break;
		}
//        System.out.println("Mouse Pressed at X: " + mouseClickx + " - Y: " + mouseClickx);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		switch (currentScene) {
		case (0):
			WorldScene s0 = ((WorldScene) scenes[0]);

			if (s0.isMouseSelect()) {
				// Sjekk om man er over ui
				if (s0.getUi().above(x, y)) {
					// check for ui events
					s0.getUi().runEvent(x, y);
				} else {
					// select tile.
				}
			}
			s0.setMouseSelect(false);
			break;
		}
	}
}
