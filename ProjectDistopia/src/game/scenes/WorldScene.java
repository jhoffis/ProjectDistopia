package game.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

import adt.GameSceneADT;
import elem.Camera;
import game.scenes.world.World;
import game.scenes.world.WorldMouse;
import game.scenes.world.WorldMouseWheel;
import game.scenes.world.WorldUI;
import startup.Main;

public class WorldScene implements GameSceneADT {

	private MouseListener ml;
	private MouseWheelListener mwl;
	private World world;
	private WorldUI ui;
	private Camera cam;
	private int size = 4;
	private int incVecSize;
	private int sizeWH;

	public WorldScene() {
		world = new World(64, 64);
		cam = new Camera((Main.WIDTH / 2), (Main.HEIGHT / 2), 0);
		ml = new WorldMouse(this);
		mwl = new WorldMouseWheel(this);
		ui = new WorldUI();
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		for (int x = 0; x < world.getWidth(); x++) {
			int n = x;
			for (int y = 0; y < world.getHeight(); y++) {
				if (n % 2 == 0)
					g.setColor(Color.green);
				else
					g.setColor(Color.white);

				sizeWH = size + cam.getZ();
				int calcX = (int) (((x * sizeWH) + cam.getX()) - (sizeWH / 2f * world.getWidth()));
				int calcY = (int) (((y * sizeWH) + cam.getY()) - (sizeWH / 2f * world.getWidth()));

				g.fillRect(calcX, calcY, sizeWH, sizeWH);

				n++;
			}
		}
		
		ui.render(g);
	}

	@Override
	public void tick() {
		ui.tick();
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		incVecSize = (2 * cam.getZ() / 10) + 8;
//		System.out.println("X " + cam.getX() + " Y " + cam.getY());

		if (e.getKeyCode() == 37) {
			// Ve
			cam.translateX(incVecSize);
		}
		if (e.getKeyCode() == 38) {
			// opp

			if (e.isControlDown())
				cam.translateZ(incVecSize);
			else
				cam.translateY(incVecSize);
		}
		if (e.getKeyCode() == 39) {
			// Høyre
			cam.translateX(-incVecSize);
		}
		if (e.getKeyCode() == 40) {
			// ned

			if (e.isControlDown())
				cam.translateZ(-incVecSize);
			else
				cam.translateY(-incVecSize);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public MouseListener getMouseListener() {
		return ml;
	}

	@Override
	public MouseWheelListener getMouseWheelListener() {
		return mwl;
	}

	public int getSizeWH() {
		return sizeWH;
	}

	public void setSizeWH(int sizeWH) {
		this.sizeWH = sizeWH;
	}

}
