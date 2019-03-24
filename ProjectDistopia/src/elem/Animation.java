package elem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Animation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8595997650191035372L;
	private BufferedImage[] frames;
	private int currentFrame;
	private int from, to;
	private int amount;
	private boolean set;

	public Animation(String animationSource, int amountOfImgs) {
		amount = amountOfImgs;
		frames = new BufferedImage[amountOfImgs];
		for (int i = 0; i < amountOfImgs; i++) {
			try {
				frames[i] = ImageIO
						.read(Animation.class.getResourceAsStream("/pic/ani/" + animationSource + (i+1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

	public BufferedImage getFrame() {
		if (!set)
			return null;
		
		BufferedImage res = frames[currentFrame];
		currentFrame++;
		if (currentFrame == to) 
			currentFrame = from;
		return res;
	}

}
