package adt;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public interface GameSceneADT extends KeyListener{

	public void render(Graphics g);

	public void tick();

}
