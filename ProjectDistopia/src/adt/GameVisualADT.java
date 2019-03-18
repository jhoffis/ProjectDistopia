package adt;

import java.awt.Canvas;
import java.awt.Graphics;

public abstract class GameVisualADT extends Canvas{

	public abstract void render(Graphics g);
	public abstract void tick();
}
