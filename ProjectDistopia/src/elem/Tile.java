package elem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import adt.GameObject;
import adt.GameSceneADT;
import adt.Unit;
import game.scenes.WorldScene;
import game.scenes.world.Echo;
import startup.Main;

public class Tile implements GameSceneADT, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 136420646496997209L;
	private int type;
	private int state;
	private int x,y;
	private Color color;
	private Color textColor;
	private ArrayList<GameObject> objects;
	private String unitSelectedText;
	private Font font;
	private String province;
	private int uiY;

	public Tile(int state, Color color, String province, int x, int y) {
		this.state = state;
		this.color = color;
		this.x = x;
		this.y = y;
		unitSelectedText = "No unit selected";
		type = findImg(color);

		objects = new ArrayList<GameObject>();
		font = new Font("Georgia", Font.BOLD, 16);
		this.province = province;
		textColor = new Color(244f / 255f, 240f / 255f, 224f / 255f, 1f);
	}

	public void select() {
		uiY = (int) (Main.HEIGHT - (Main.HEIGHT / 8f));
		Echo.println("I am selected");

		if (objects.get(0).getClass().equals(Unit.class)) {
			unitSelectedText = objects.get(0).toString();
		} else if (objects.get(0).getClass().equals(GreatLeader.class)) {
			unitSelectedText = objects.get(0).toString();
		} else {
			unitSelectedText = "No unit selected";
		}
	}
	
	public void setStats(int state, ArrayList<GameObject> objects) {
		this.state = state;
		this.objects = objects;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO render ui.
		g.setColor(color);
		g.fillRect(10, uiY + 10, 100, 100);
		g.setFont(font);
		g.setColor(textColor);
		drawStrings(g, " -" + province + "- " + "\nStatus: " + state + "\nThings", 0, uiY);

		g.drawString(unitSelectedText, 200, uiY);

	}

	@Override
	public void tick() {
		for (GameObject go : objects) {
			go.tick();
		}
	}

	private void drawStrings(Graphics g, String str, int x, int y) {
		String[] arr = str.split("\n");
		for (int i = 0; i < arr.length; i++) {
			g.drawString(arr[i], x, y + (i * font.getSize()));
//			System.out.println(arr[i]);
		}
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}



	public GameObject getObject(int i) {
		return objects.get(i);
	}

	private int findImg(Color rgb) {
		int res = -1;
		if (color.getRed() == 0) {
			if (color.getBlue() == 255) {
				// elv
				res = 0;
			} else if (color.getGreen() == 255) {
				// Grass
				res = 1;
			} else if (color.getGreen() > 0) {
				// skog
				res = 2;
			} else {
				// dypvann
				res = 3;
			}
		} else {

			switch (color.getGreen()) {
			case 255:
				res = 4;
				break;
			case 148:
				res = 5;
				break;
			case 117:
				res = 6;
				break;
			case 80:
				res = 7;
				break;
			case 65:
				res = 8;
				break;
			case 0:
				res = 9;
				switch (color.getBlue()) {
				// Spawn loc og arbeidsbygning
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				}
				break;
			}

		}

		return res;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getUnitSelectedText() {
		return unitSelectedText;
	}

	public void setUnitSelectedText(String unitSelectedText) {
		this.unitSelectedText = unitSelectedText;
	}

	public int getUiY() {
		return uiY;
	}

	public void setUiY(int uiY) {
		this.uiY = uiY;
	}
}
