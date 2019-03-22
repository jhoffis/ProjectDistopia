package elem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import adt.GameSceneADT;
import game.scenes.world.Echo;

public class Tile implements GameSceneADT{

	private int type;
	private int state;
	private Color color;

	
	public Tile( int type, int state, Color color) {
		this.type = type;
		this.state = state;
		this.color = color;
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
		//TODO render ui.
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
	}


	@Override
	public void tick() {
		
	}
	
}
