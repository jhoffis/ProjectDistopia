package elem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import adt.GameObject;
import adt.GameSceneADT;
import game.scenes.WorldScene;
import game.scenes.world.Echo;
import startup.Main;

public class Tile implements GameSceneADT {

	private int type;
	private int state;
	private Color color;
	private Color textColor;
	private ArrayList<GameObject> objects;
	private Font font;
	private String province;
	private int y;

	public Tile(int type, int state, Color color, String province) {
		this.type = type;
		this.state = state;
		this.color = color;
		objects = new ArrayList<GameObject>();
		font = WorldScene.font;
		this.province = province;
		y = (int) (Main.HEIGHT - (Main.HEIGHT / 8f));
		textColor = new Color(244f / 255f, 240f / 255f, 224f / 255f, 1f);
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

	public void select() {
		// TODO Auto-generated method stub
		Echo.println("I am selected");
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
		g.fillRect(10, y + 10, 100, 100);
		g.setFont(font);
		g.setColor(textColor);
		drawStrings(g, " -" + province + "- " + "\nStatus: " + state + "\nThings", 0, y);
		

	}

	@Override
	public void tick() {
		for(GameObject go : objects) {
			go.tick();
		}
	}

	private void drawStrings(Graphics g, String str, int x, int y) {
		String[] arr = str.split("\n");
		for (int i = 0; i < arr.length; i++) {
			g.drawString(arr[i], x, y + (i * font.getSize()));
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

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public GameObject getObject(int i) {
		return objects.get(i);
	}

}
