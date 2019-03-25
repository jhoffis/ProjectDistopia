package adt;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

import elem.AnimationVal;

public abstract class GameObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160720415294991283L;
	protected AnimationVal animation;
	protected String faction;

	public GameObject(String animationSource, int amountOfImgs, String faction) {
		animation = new AnimationVal(animationSource, amountOfImgs);
		this.faction = faction;
	}
	
	public AnimationVal getAnimation() {
		return animation;
	}

	public void setAnimation(AnimationVal animation) {
		this.animation = animation;
	}

	public abstract void tick();

	public abstract void resetExpendablePoints();

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	
	
}
