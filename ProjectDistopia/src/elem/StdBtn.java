package elem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import adt.Button;
import adt.Event;

public class StdBtn extends Button {

	public StdBtn(int x, int y, int width, int height, Color color, String title, Font font, Event event) {
		super(x, y, width, height, color, title, font, event);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(area.getX(), area.getY(), area.getX2() - area.getX(), area.getY2() - area.getY());
		g.setColor(getContrastColor(color));
		g.setFont(font);
		g.drawString(title, textX, textY);
	}

	@Override
	public void tick() {

	}

}
