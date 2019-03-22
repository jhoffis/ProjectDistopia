package game.scenes.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import adt.Button;
import adt.GameSceneADT;
import elem.Area;
import elem.StdBtn;
import startup.Main;

public class WorldUI implements GameSceneADT {

	private ArrayList<Area> occupiedAreas;
	private ArrayList<Button> buttons;
	private BufferedImage profilePic;
	private Echo debug;
	private int profilePicSize;
	private int profilePicX;
	private int profilePicY;

	public WorldUI(String fac, Font font) {
		occupiedAreas = new ArrayList<Area>();
		// Top bar
		occupiedAreas.add(0, new Area(0, 0, Main.WIDTH, Main.HEIGHT / 24));
		// Bottom bar
		occupiedAreas.add(1, new Area(0, (int) (Main.HEIGHT - (Main.HEIGHT / 6f)), Main.WIDTH, Main.HEIGHT));

		// Profile picture
		try {
			System.out.println("/pic/fac/" + fac + "IsSel.png");
			profilePic = ImageIO.read(World.class.getResourceAsStream("/pic/fac/" + fac + "IsSel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		profilePicSize = (int) (Main.HEIGHT / 8f);
		int picBuffer = (int) (((Main.HEIGHT / 6f) - (Main.HEIGHT / 8f)) / 2f);
		profilePicX = Main.WIDTH - profilePicSize - picBuffer;
		profilePicY = Main.HEIGHT - profilePicSize - picBuffer;

		debug = new Echo(font, occupiedAreas.get(0).getY2() + font.getSize());
		
		
		// Final buttons like menu and shit
		buttons = new ArrayList<Button>();
		int topBtnHeight = Main.HEIGHT / 32;
		int topBtnWidth = 100;
		int topBtnBuffer = (int) (((Main.HEIGHT / 24f) - (Main.HEIGHT / 32f)) / 2f);
		buttons.add(new StdBtn(Main.WIDTH - topBtnBuffer - topBtnWidth, topBtnBuffer, 100, topBtnHeight,
				new Color(13, 0, 80), "Menu", new Font("Georgia", Font.PLAIN, (int) (topBtnHeight / 1.5f)),
				() -> System.exit(0)));
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void render(Graphics g) {
		debug.render(g);
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));

		for (int i = 0; i < occupiedAreas.size(); i++) {
			Area a = occupiedAreas.get(i);
			g.fillRect(a.getX(), a.getY(), a.getX2(), a.getY2());
		}

		g.drawImage(profilePic, profilePicX, profilePicY, profilePicSize, profilePicSize, null);
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(g);
		}
		
	}

	@Override
	public void tick() {
		debug.tick();
	}

	public boolean above(int x, int y) {
		for (int i = 0; i < occupiedAreas.size(); i++) {
			Area a = occupiedAreas.get(i);
			if (x >= a.getX() && y >= a.getY() && x <= a.getX2() && y <= a.getY2()) {
				return true;
			}
		}
		return false;
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

	public ArrayList<Area> getOccupiedAreas() {
		return occupiedAreas;
	}

	public void setOccupiedAreas(ArrayList<Area> occupiedAreas) {
		this.occupiedAreas = occupiedAreas;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}

}
