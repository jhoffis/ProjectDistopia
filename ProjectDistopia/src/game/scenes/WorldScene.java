package game.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import javax.swing.JFrame;

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
	private HashMap<Point, Tile> myLand;
	private WorldUI ui;
	private Camera cam;
	private Client client;
	private int size = 4;
	private int incVecSize;
	private float sizeWH;
	private int mouseClickxCam = 0;
	private int mouseClickyCam = 0;
	private int mouseClickx = 0;
	private int mouseClicky = 0;
	private int calcX;
	private int calcY;
	private int userID;
	private int turn;
	private boolean mouseSelect;
	private int renderTilesFromX;
	private int renderTilesFromY;
	private int renderTilesToX;
	private int renderTilesToY;
	private int camX;
	private int camY;
	private double zoomX;
	private double zoomY;
	private double raycastMiddleOfScreenX;
	private double raycastMiddleOfScreenY;
	public static Font font;

	public WorldScene(JFrame frame) {
		font = new Font("Georgia", Font.BOLD, 16);

		// GET WORLD FROM REGISTRY
		turn = -1;
		client = Main.CLIENT;
		userID = Main.USER.getId();
		try {
			world = client.getServerMethods().getAllWorldInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		visual = new WorldLocalVisual(world);
		cam = new Camera((Main.WIDTH / 2), (Main.HEIGHT / 2), 0);
		ui = new WorldUI(Main.USER.getFaction(), font);
		myLand = new HashMap<Point, Tile>();
		
		
		raycastMiddleOfScreenX = 47f;
		raycastMiddleOfScreenY = 47f;
		
		
		try {
			GreatLeader aifrohm = new GreatLeader("aiazom/greatleader", 1, Main.USER.getFaction(), "Aifrohm");
			// FIXME
			myLand.put(new Point(10, 10), world.getTile(10, 10));
			world.getTile(10, 10).setFaction(Main.USER.getFaction());
			client.getServerMethods().createUnit(aifrohm, world.getTile(10, 10));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {

		BufferedImage bufferedImage = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = (Graphics2D) g;
		// create the offscreen buffer and associated Graphics
		// clear the exposed area
		// Size with height (of camera)
		sizeWH = size + cam.getZ();
		// Midpoint of the map.
		zoomX = (getSizeWH() * raycastMiddleOfScreenX);
		zoomY = (getSizeWH() * raycastMiddleOfScreenY);
		
		// No updates from camera while rendering
		camX = cam.getX();
		camY = cam.getY();
		// do normal redraw
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);

		renderTilesFromX = getClosestTileXByCoor(0);
		renderTilesFromY = getClosestTileYByCoor(0);
		renderTilesToX = getClosestTileXByCoor(Main.WIDTH) + 1;
		renderTilesToY = getClosestTileYByCoor(Main.HEIGHT) + 1;

		for (int x = renderTilesFromX; x < renderTilesToX; x++) {
			for (int y = renderTilesFromY; y < renderTilesToY; y++) {

				Tile tile = world.getTile(x, y);

				// legg til som Tiles i stedet og ha en eller annen type tabell som holder
				// referanser til hver sin x,y koordinat : px.

				// x, y respecively. zoom plasserer kartet i midten...
				calcX = (int) ((x * sizeWH) + camX - zoomX);
				calcY = (int) ((y * sizeWH) + camY - zoomY);

				visual.getTile(x, y).render(g2d, calcX, calcY, (int) sizeWH);

			}
		}
		
		g2d.setColor(Color.white);
		g2d.drawString("Turn: " + turn, 100, 100);

		ui.render(g);
	}

	@Override
	public void tick() {
		ui.tick();

		try {
			updateTiles(client.getServerMethods().getTileUpdates(userID));
			int newTurn = client.getServerMethods().getTurn();
			if (newTurn != turn) {
				turn = newTurn;
				newTurn();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		for (Tile t : world.getTiles()) {
			t.tick();
		}
	}

	private void newTurn() {
		for (Entry<Point, Tile> entry : myLand.entrySet()) {
			Echo.println("Size " + entry.getValue().getObjects().size());
			if (entry.getValue().getObjects().size() > 0)
				entry.getValue().getObject(0).resetExpendablePoints();
		}

		ui.nextTurn(world);
	}

	private void updateTiles(Stack<Tile> tileUpdates) {

		while (!tileUpdates.isEmpty()) {
			Tile tile = tileUpdates.pop();
			int x = tile.getX();
			int y = tile.getY();
			world.getTile(x, y).setStats(tile.getState(), tile.getObjects(), tile.getFaction());
			Point xy = new Point(x, y);
			if (tile.getFaction().equals(Main.USER.getFaction())) {
				// MINE!!!

				if (!myLand.containsKey(xy)) {
					myLand.put(xy, world.getTile(x, y));
				}
				// Gå igjennom og fjern fog of war hvor det trengs.
//				visual.getTile(x, y).update(visible(x, y));
				for (int xa = -1; xa < 2; xa++) {
					for (int yb = -1; yb < 2; yb++) {
						visual.getTile(x + xa, y + yb).update(false);
					}
				}
			} else if (myLand.containsKey(xy)) {
				// NOT mine.... ;(
				myLand.remove(xy);

				// Gå igjennom og legg til fog of war hvor det trengs. FIXME
//				visual.getTile(x, y).update(visible(x, y));
				visual.getTile(x, y).update(true);
			} else {
//				visual.getTile(x, y).update(true);

				visual.getTile(x, y).update(true);
			}

		}

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

	public Tile getTileByCoor(int mx, int my) {
		int size = world.getWidth();
		Tile res = null;

		int x = getTileXByCoor(mx);
		int y = getTileYByCoor(my);
		Echo.println("Mouse Clicked at X: " + x + " - Y: " + y);

		if (x >= 0 && x <= size && y >= 0 && y <= size)
			res = world.getTile(x, y);

		return res;
	}

	public int getTileXByCoor(int mx) {
		return (int) ((mx - camX + zoomX) / sizeWH);
	}

	public int getTileYByCoor(int my) {
		return (int) ((my - camY + zoomY) / sizeWH);
	}

	public int getClosestTileYByCoor(int my) {
		int res = getTileYByCoor(my);
		if (my <= 0 && res < 0) {
			res = 0;
		} else if (my >= Main.HEIGHT && res >= world.getHeight()) {
			res = world.getHeight() - 1;
		}
		return res;
	}

	public int getClosestTileXByCoor(int mx) {
		int res = getTileXByCoor(mx);
		if (mx <= 0 && res < 0) {
			res = 0;
		} else if (mx >= Main.WIDTH && res >= world.getWidth()) {
			res = world.getWidth() - 1;
		}
		return res;
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
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
		return (int) sizeWH;
	}

	public void setSizeWH(int sizeWH) {
		this.sizeWH = sizeWH;
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


	public void setZoom(float zoom) {
		this.zoomX = zoom;
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

	public double getRaycastMiddleOfScreenX() {
		return raycastMiddleOfScreenX;
	}

	public void setRaycastMiddleOfScreenX(double xr) {
		this.raycastMiddleOfScreenX = xr;
	}

	public double getRaycastMiddleOfScreenY() {
		return raycastMiddleOfScreenY;
	}

	public void setRaycastMiddleOfScreenY(double yr) {
		this.raycastMiddleOfScreenY = yr;
	}

	public double getZoomX() {
		return zoomX;
	}

	public void setZoomX(double zoomX) {
		this.zoomX = zoomX;
	}

	public double getZoomY() {
		return zoomY;
	}

	public void setZoomY(double zoomY) {
		this.zoomY = zoomY;
	}

}
