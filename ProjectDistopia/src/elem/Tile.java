package elem;

import java.awt.Color;

public class Tile {

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
	
}
