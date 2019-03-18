package adt;

import java.awt.Canvas;
import java.awt.Graphics;

public abstract class GameVisualADT extends Canvas{

	private static final long serialVersionUID = -5140936653010780058L;
	public abstract void render();
	public abstract void tick();
}
