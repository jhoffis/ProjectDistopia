package game.scenes.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import adt.GameSceneADT;
import startup.Main;

public class WorldUI implements GameSceneADT {

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
		g.setColor(Color.blue);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT / 16);
	}

	@Override
	public void tick() {
		
	}



}
