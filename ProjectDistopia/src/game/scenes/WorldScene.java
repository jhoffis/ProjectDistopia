package game.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import adt.GameSceneADT;
import elem.Camera;
import elem.Tile;
import game.scenes.world.World;
import game.scenes.world.WorldUI;
import startup.Main;

public class WorldScene extends MouseInputAdapter implements GameSceneADT {

	private World world;
	private WorldUI ui;
	private Camera cam;
	private int size = 4;
	private int incVecSize;
	private int sizeWH;
	private int mouseClickxCam = 0;
	private int mouseClickyCam = 0;
	private int mouseClickx = 0;
	private int mouseClicky = 0;
	private int calcX;
	private int calcY;
	private float zoom;

	public WorldScene(JFrame frame) {
		world = new World(64, 64, size);
		cam = new Camera((Main.WIDTH / 2), (Main.HEIGHT / 2), 0);
		ui = new WorldUI();

	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		for (int x = 0; x < world.getWidth(); x++) {
			int n = x;
			for (int y = 0; y < world.getHeight(); y++) {
				
				Tile tile = world.getTile(x, y);
				
				if (n % 2 == 0)
					g.setColor(tile.getColor());
				else
					g.setColor(Color.white);

				// FIXME legg til som Tiles i stedet og ha en eller annen type tabell som holder
				// referanser til hver sin x,y koordinat : px.
				sizeWH = size + cam.getZ();
				zoom = (sizeWH / 2f * world.getWidth());
				calcX = (int) (((x * sizeWH) + cam.getX()) - zoom);
				calcY = (int) (((y * sizeWH) + cam.getY()) - zoom);
				
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

	public int getSizeWH() {
		return sizeWH;
	}

	public void setSizeWH(int sizeWH) {
		this.sizeWH = sizeWH;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TEST FIXME
//		System.out.println("x: " + (e.getX() - mouseClickx) + " y: " + (e.getY() - mouseClicky));
		getCam().setX(mouseClickxCam + (e.getX() - mouseClickx));
		getCam().setY(mouseClickyCam + (e.getY() - mouseClicky));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println("Move");
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
//		System.out.println("SCROLL");
		int unitsToScroll = e.getUnitsToScroll();
		int direction = unitsToScroll < 0 ? 1 : -1;
		getCam().translateZ(direction * getSizeWH() / 4);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Entered frame at X: " + x + " - Y: " + y);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Exited frame at X: " + x + " - Y: " + y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClickxCam = cam.getX();
		mouseClickyCam = cam.getY();
		mouseClickx = e.getX();
		mouseClicky = e.getY();
//        System.out.println("Mouse Pressed at X: " + mouseClickx + " - Y: " + mouseClickx);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Released at X: " + x + " - Y: " + y);
	}

}
