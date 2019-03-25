package game.scenes.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import elem.AnimationVal;
import elem.AnimationVis;
import elem.Tile;

public class TileLocalVisual {

	private BufferedImage img;
	private Tile tile;
	private AnimationVis unit;
	private AnimationVal val;
	private boolean fogOfWar;

	public TileLocalVisual(BufferedImage img, Tile tile) {
		this.img = img;
		this.tile = tile;
		update(true);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void update(boolean fogOfWar) {
		
		this.fogOfWar = fogOfWar;
		
		if (tile.getObjects().size() > 0) {
			val = tile.getObject(0).getAnimation();
			unit = new AnimationVis(val);
		} else {
			val = null;
			unit = null;
		}
	}

	public void render(Graphics g, int screenX, int screenY, int screenSizeX, int screenSizeY) {
		g.drawImage(img, screenX, screenY, screenSizeX, screenSizeY, null);

		if (tile.getFaction() != null) {
			g.setColor(new Color(1f, 0f, 0f, 0.6f));
			g.fillRect(screenX, screenY, screenSizeX, screenSizeY);
		}
		if (!fogOfWar) {
			if (unit != null)
				g.drawImage(unit.getFrame(), screenX, screenY, screenSizeX, screenSizeY, null);
		} else {
			g.setColor(new Color(0f, 0f, 0f, 0.5f));
			g.fillRect(screenX, screenY, screenSizeX, screenSizeY);
		}
	}

}
