package game.scenes.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import elem.AnimationVal;
import elem.AnimationVis;
import elem.GreatLeader;
import elem.Tile;

public class TileLocalVisual {

	private BufferedImage img;
	private Tile tile;
	private AnimationVis unit;
	private AnimationVal val;
	private boolean fogOfWar;
	private boolean greatLeader;

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
			if(tile.getObject(0).getClass().equals(GreatLeader.class)){
				greatLeader = true;
			} else {
				greatLeader = false;
			}
		} else {
			val = null;
			unit = null;
		}
	}

	public void render(Graphics g, int screenX, int screenY, int tileSize) {
		g.drawImage(img, screenX, screenY, tileSize, tileSize, null);

		if (tile.getFaction() != null) {
			g.setColor(new Color(1f, 0f, 0f, 0.6f));
			g.fillRect(screenX, screenY, tileSize, tileSize);
		}
		if (!fogOfWar) {
			if (unit != null) {
				
				//TODO draw line around selected tile

				if (greatLeader) {
					screenX = (int) (screenX - (tileSize / 2f));
					screenY = (int) (screenY - (tileSize / 1f));
					tileSize = (int) (tileSize * 2f);
				}

				g.drawImage(unit.getFrame(), screenX, screenY, tileSize, tileSize, null);
			}
		} else {
			g.setColor(new Color(0f, 0f, 0f, 0.5f));
			g.fillRect(screenX, screenY, tileSize, tileSize);
		}
	}

}
