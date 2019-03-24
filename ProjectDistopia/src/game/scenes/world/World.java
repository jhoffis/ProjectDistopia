package game.scenes.world;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elem.Tile;
import network.server.info.WorldInfo;
import startup.Main;

public class World {
	private WorldInfo worldInfo;
	private int width;
	private int height;
	private Tile[] tiles;
	private int size;

	public World(int size) {
		worldInfo = new WorldInfo(Main.CLIENT.sendStringRequest("WORLDINFO"));
		this.width = worldInfo.getSize();
		this.height = worldInfo.getSize();
		this.size = size;

		BufferedImage image = null;
		try {
			image = ImageIO.read(World.class.getResourceAsStream("/pic/world/world.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Getting pixel color by position x and y
		

		tiles = new Tile[width * height];
		for (int i = 0; i < tiles.length; i++) {
			
			
			int clr = image.getRGB(i % width, i / width);
			int red = (clr & 0x00ff0000) >> 16;
			int green = (clr & 0x0000ff00) >> 8;
			int blue = clr & 0x000000ff;
			System.out.println("Red Color value = " + red);
			System.out.println("Green Color value = " + green);
			System.out.println("Blue Color value = " + blue);

			tiles[i] = new Tile(0, 100, new Color(red, green, blue), "SOMETILEFIXME");
		}
	}

	public Tile getTile(int x, int y) {
//		System.out.println("x " + x + " y " + y);
		return tiles[x + width * y];
	}

	public void setTileSize(int size) {
		this.size = size;
	}

	public int getTileSize() {
		return size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}
}
