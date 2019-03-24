package game.scenes.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elem.Tile;

public class WorldLocalVisual {
	private TileLocalVisual[] tiles;
	private BufferedImage[] imgs;
	private World world;

	public WorldLocalVisual(World world) {
		this.world = world;
		Tile[] temp = world.getTiles();
		tiles = new TileLocalVisual[temp.length];
		
		imgs = new BufferedImage[10];

		for(int i = 0; i < imgs.length; i++) {
			try {
				switch (i) {
				case 0:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/river.png"));
					break;
				case 1:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/grass.png"));
					break;
				case 2:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/forest.png"));
					break;
				case 3:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/water.png"));
					break;
				case 4:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/desert.png"));
					break;
				case 5:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/deserthill.png"));
					break;
				case 6:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/hill.png"));
					break;
				case 7:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/mountain.png"));
					break;
				case 8:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/highmountain.png"));
					break;
				case 9:
					imgs[i] = ImageIO.read(Tile.class.getResourceAsStream("/pic/world/highmountain.png"));
					break;

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < temp.length; i++) {
			tiles[i] = new TileLocalVisual(imgs[temp[i].getType()]);
		}
	}

	public TileLocalVisual getTile(int x, int y) {
		return tiles[x + world.getWidth() * y];
	}
}
