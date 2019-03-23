package adt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import elem.Area;
import startup.Main;

public abstract class Button {

	protected Area area;
	protected Color color;
	protected String title;
	protected Font font;
	protected Event event;
	protected int textX;
	protected int textY;
	
	public Button(int x, int y, int width, int height, Event event) {
		area = new Area(x, y, x + width, y + height);
		this.event = event;
	}
	
	public Button(int x, int y, int width, int height, Color color, String title, Font font, Event event) {
		area = new Area(x, y, x + width, y + height);
		this.color = color;
		this.title = title;
		this.font = font;
		this.event = event;
		textX = x + (width / 8);
		textY = y + height - (height / 2) + (font.getSize() / 2);
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	
	public Color getContrastColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}
	
	public void runEvent() {
		event.exec();
		Main.lbtn();
	}
}
