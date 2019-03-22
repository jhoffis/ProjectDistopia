package game.scenes;

import java.awt.Color;
import java.awt.Font;
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
import game.scenes.world.Echo;
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
	private boolean mouseSelect;
	public static Font font;

	public WorldScene(JFrame frame) {
		font = new Font("Georgia", Font.BOLD, 16);
		world = new World(64, 64, size);
		cam = new Camera((Main.WIDTH / 2), (Main.HEIGHT / 2), 0);
		ui = new WorldUI(Main.USER.getFaction(), font);
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		for (int x = 0; x < world.getWidth(); x++) {
			int n = x;
			for (int y = 0; y < world.getHeight(); y++) {

				Tile tile = world.getTile(x, y);

				g.setColor(tile.getColor());

				// FIXME legg til som Tiles i stedet og ha en eller annen type tabell som holder
				// referanser til hver sin x,y koordinat : px.
				// Size with height (of camera)
				sizeWH = size + cam.getZ();
				// Midpoint of the map.
				zoom = (sizeWH / 2f * world.getWidth());
				// x, y respecively.
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

		int x = e.getX();
		int y = e.getY();

		// Sjekk om man er over ui
		if (!ui.above(mouseClickx, mouseClicky)) {
			getCam().setX(mouseClickxCam + (e.getX() - mouseClickx));
			getCam().setY(mouseClickyCam + (e.getY() - mouseClicky));
			mouseSelect = false;
		}
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
		getCam().translateX(((Main.WIDTH / 2) - e.getX()) / 6);
		getCam().translateY(((Main.HEIGHT / 2) - e.getY()) / 6);

		Echo.println(zoom);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		Tile tile = getTileByCoor(x, y);
		ui.setSelectedTile(tile);
		tile.select();
	}

	private Tile getTileByCoor(int mx, int my) {
		int size = world.getWidth();
		int x = (int) (size - (zoom - (mx - cam.getX())) / sizeWH);
		int y = (int) (size -(zoom - (my - cam.getY())) / sizeWH);
		Echo.println("Mouse Clicked at X: " + x + " - Y: " + y);
		return world.getTile(x, y);
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
		mouseClickxCam = cam.getX();
		mouseClickyCam = cam.getY();
		mouseClickx = e.getX();
		mouseClicky = e.getY();
		mouseSelect = true;
//        System.out.println("Mouse Pressed at X: " + mouseClickx + " - Y: " + mouseClickx);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (mouseSelect) {
			// Sjekk om man er over ui
			if (ui.above(x, y)) {
				// check for ui events
				ui.runEvent(x, y);
			} else {
				// select tile.
			}
		}
		mouseSelect = false;
	}

}
