package elem;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimationVis {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8595997650191035372L;
	private BufferedImage[] frames;
	private AnimationVal values;

	public AnimationVis(AnimationVal values) {
		this.values = values;
		frames = new BufferedImage[values.getAmount()];
		for (int i = 0; i < values.getAmount(); i++) {
			try {
				frames[i] = ImageIO
						.read(AnimationVal.class.getResourceAsStream("/pic/ani/" + values.getSource() + (i+1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public BufferedImage getFrame() {
		if (!values.isSet())
			return null;
		return frames[values.getCurrentFrame()];
	}

	
}
