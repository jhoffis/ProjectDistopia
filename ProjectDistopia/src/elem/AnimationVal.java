package elem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class AnimationVal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8595997650191035372L;
	private int currentFrame;
	private int from, to;
	private int amount;
	private boolean set;
	private String source;

	public AnimationVal(String animationSource, int amountOfImgs) {
		amount = amountOfImgs;
		source = animationSource;

	}

	public void setAnimation(int from, int to) {
		if (from >= 0 && to <= amount) {
			this.from = from;
			this.to = to;
			this.currentFrame = from;
			set = true;
		} else {
			set = false;
		}
	}

	public void updateFrame() {
		currentFrame++;
		if (currentFrame == to)
			currentFrame = from;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


}
