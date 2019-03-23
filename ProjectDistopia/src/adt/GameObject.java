package adt;

import java.awt.Graphics;

import elem.Animation;

public abstract class GameObject{

	protected Animation animation;
	
	public GameObject(String animationSource, int amountOfImgs) {
		animation = new Animation(animationSource, amountOfImgs);
	}
	
	public abstract void render(Graphics g, int screenX, int screenY, int screenSizeX, int screenSizeY);

	public abstract void tick();
}
