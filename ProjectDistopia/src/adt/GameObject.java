package adt;

import java.awt.Graphics;
import java.io.Serializable;

import elem.AnimationVal;

public abstract class GameObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160720415294991283L;
	protected AnimationVal animation;
	protected int userID;

	public GameObject(String animationSource, int amountOfImgs, int userID) {
		animation = new AnimationVal(animationSource, amountOfImgs);
		this.userID = userID;
	}

	public AnimationVal getAnimation() {
		return animation;
	}

	public void setAnimation(AnimationVal animation) {
		this.animation = animation;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public abstract void tick(); 
	
}
