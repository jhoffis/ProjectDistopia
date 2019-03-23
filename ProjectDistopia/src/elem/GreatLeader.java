package elem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import adt.Unit;
import game.scenes.world.Echo;

public class GreatLeader extends Unit {

	private BufferedImage frame;
	
	public GreatLeader(String animationSource, int amountOfImgs) {
		super(animationSource, amountOfImgs);
		animation.setAnimation(0, amountOfImgs);
	}

	@Override
	public void render(Graphics g, int screenX, int screenY, int screenSizeX, int screenSizeY) {
		try {
			g.drawImage(frame, screenX, screenY, screenSizeX, screenSizeY, null);
		} catch(Exception e) {
			Echo.println(e.getMessage());
		}
	}

	@Override
	public void tick() {
		frame = animation.getFrame();
	}


}
