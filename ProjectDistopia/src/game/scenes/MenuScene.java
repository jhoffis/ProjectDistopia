package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import adt.Button;
import adt.GameSceneADT;
import elem.Area;
import elem.StdBtn;
import game.handlers.SceneHandler;
import javafx.event.ActionEvent;
import startup.Main;
import window.LobbyFrame;

public class MenuScene implements GameSceneADT {

	private ArrayList<Button> buttons;

	public MenuScene() {
		buttons = new ArrayList<Button>();

		int numBtn = Main.HEIGHT / (4 + 2);

		int b0y = (numBtn * 1);
		int b1y = (numBtn * 2);
		int b2y = (numBtn * 3);
		int b3y = (numBtn * 4);

		int btnWidth = (int) (Main.WIDTH / 2.5f);
		int btnHeight = (int) (Main.HEIGHT / 12.5f);
		int mid = (Main.WIDTH / 2) - (btnWidth / 2);
		Font font = new Font("Georgia", Font.PLAIN, (int) (btnHeight / 1.5f));

		buttons.add(new StdBtn(mid, b0y, btnWidth, btnHeight, Color.BLACK, "Return to game", font,
				() -> SceneHandler.changeScene(0)));
		buttons.add(new StdBtn(mid, b1y, btnWidth, btnHeight, Color.BLACK, "Save game", font,
				() -> SceneHandler.changeScene(4)));
		buttons.add(new StdBtn(mid, b2y, btnWidth, btnHeight, Color.BLACK, "Options", font,
				() -> SceneHandler.changeScene(3)));
		buttons.add(new StdBtn(mid, b3y, btnWidth, btnHeight, Color.BLACK, "Leave", font,
				() -> Main.closeGameFrame()));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(g);
		}
	}

	@Override
	public void tick() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).tick();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void runEvent(int x, int y) {
		System.err.println("RUNNING EVENT");
		for (int i = 0; i < buttons.size(); i++) {

			Area a = buttons.get(i).getArea();
			if (x >= a.getX() && y >= a.getY() && x <= a.getX2() && y <= a.getY2()) {
				buttons.get(i).runEvent();
				return;
			}
		}
	}

}
