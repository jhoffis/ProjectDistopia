package adt;

import java.awt.Graphics;
import java.io.Serializable;

import elem.Animation;

public abstract class GameObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160720415294991283L;
	protected Animation animation;
	protected int userID;
	
	public GameObject(String animationSource, int amountOfImgs, int userID) {
		animation = new Animation(animationSource, amountOfImgs);
		this.userID = userID;
	}
	
	public abstract void render(Graphics g, int screenX, int screenY, int screenSizeX, int screenSizeY);

	public abstract void tick();
}
