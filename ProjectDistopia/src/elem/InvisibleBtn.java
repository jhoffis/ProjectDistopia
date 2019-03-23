package elem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import adt.Button;
import adt.Event;

public class InvisibleBtn extends Button {

	public InvisibleBtn(int x, int y, int width, int height, Event event) {
		super(x, y, width, height, event);
	}

	@Override
	public void render(Graphics g) {
	}
	@Override
	public void tick() {
	}
}
