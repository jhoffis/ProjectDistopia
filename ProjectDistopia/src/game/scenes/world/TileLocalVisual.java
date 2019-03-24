package game.scenes.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elem.Tile;

public class TileLocalVisual {

	private BufferedImage img;

	public TileLocalVisual(BufferedImage img) {
		this.img = img;

	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
