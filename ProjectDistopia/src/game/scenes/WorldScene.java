package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;

import adt.GameObject;
import adt.GameSceneADT;
import elem.Camera;
import elem.GreatLeader;
import elem.Tile;
import game.scenes.world.Echo;
import game.scenes.world.World;
import game.scenes.world.WorldLocalVisual;
import game.scenes.world.WorldUI;
import network.client.Client;
import startup.Main;

public class WorldScene implements GameSceneADT {

	private World world;
	private WorldLocalVisual visual;
	private WorldUI ui;
	private Camera cam;
	private Client client;
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

		// GET WORLD FROM REGISTRY

		client = Main.CLIENT;
		try {
			world = client.getServerMethods().getAllWorldInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		visual = new WorldLocalVisual(world);
		cam = new Camera((Main.WIDTH / 2), (Main.HEIGHT / 2), 0);
		ui = new WorldUI(Main.USER.getFaction(), font);

		world.getTile(10, 10).getObjects().add(new GreatLeader("aiazom/greatleader", 1, 0));
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

				// legg til som Tiles i stedet og ha en eller annen type tabell som holder
				// referanser til hver sin x,y koordinat : px.
				// Size with height (of camera)
				sizeWH = size + cam.getZ();
				// Midpoint of the map.
				zoom = (sizeWH / 2f * world.getWidth());
				// x, y respecively.
				calcX = (int) (((x * sizeWH) + cam.getX()) - zoom);
				calcY = (int) (((y * sizeWH) + cam.getY()) - zoom);
				
				g.drawImage(visual.getTile(x, y).getImg(), calcX, calcY, sizeWH, sizeWH, null);
				
				for (int i = 0; i < tile.getObjects().size(); i++) {
					tile.getObject(i).render(g, calcX, calcY, sizeWH, sizeWH);
					
				}

				n++;
			}
		}

		ui.render(g);
	}

	@Override
	public void tick() {
		ui.tick();
		for (Tile t : world.getTiles()) {
			t.tick();
		}
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

	public Tile getTileByCoor(int mx, int my) {
		int size = world.getWidth();
		Tile res = null;

		int x = (int) (size - (zoom - (mx - cam.getX())) / sizeWH);
		int y = (int) (size - (zoom - (my - cam.getY())) / sizeWH);
		Echo.println("Mouse Clicked at X: " + x + " - Y: " + y);

		if (x >= 0 && x <= size && y >= 0 && y <= size)
			res = world.getTile(x, y);

		return res;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public WorldUI getUi() {
		return ui;
	}

	public void setUi(WorldUI ui) {
		this.ui = ui;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIncVecSize() {
		return incVecSize;
	}

	public void setIncVecSize(int incVecSize) {
		this.incVecSize = incVecSize;
	}

	public int getMouseClickxCam() {
		return mouseClickxCam;
	}

	public void setMouseClickxCam(int mouseClickxCam) {
		this.mouseClickxCam = mouseClickxCam;
	}

	public int getMouseClickyCam() {
		return mouseClickyCam;
	}

	public void setMouseClickyCam(int mouseClickyCam) {
		this.mouseClickyCam = mouseClickyCam;
	}

	public int getMouseClickx() {
		return mouseClickx;
	}

	public void setMouseClickx(int mouseClickx) {
		this.mouseClickx = mouseClickx;
	}

	public int getMouseClicky() {
		return mouseClicky;
	}

	public void setMouseClicky(int mouseClicky) {
		this.mouseClicky = mouseClicky;
	}

	public int getCalcX() {
		return calcX;
	}

	public void setCalcX(int calcX) {
		this.calcX = calcX;
	}

	public int getCalcY() {
		return calcY;
	}

	public void setCalcY(int calcY) {
		this.calcY = calcY;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public boolean isMouseSelect() {
		return mouseSelect;
	}

	public void setMouseSelect(boolean mouseSelect) {
		this.mouseSelect = mouseSelect;
	}

	public static Font getFont() {
		return font;
	}

	public static void setFont(Font font) {
		WorldScene.font = font;
	}

}
