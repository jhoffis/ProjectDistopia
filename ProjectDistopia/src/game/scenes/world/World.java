package game.scenes.world;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import elem.Tile;

public class World implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694723104357277257L;
	private int width;
	private int height;
	private int turn;
	private Tile[] tiles;

	public World(int width, int height, int turn) {
		this.width = width;
		this.height = height;
		this.turn = turn;

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
//			System.out.println("Red Color value = " + red);
//			System.out.println("Green Color value = " + green);
//			System.out.println("Blue Color value = " + blue);

			tiles[i] = new Tile(100, new Color(red, green, blue), "SOMETILEFIXME", i % width, i / width);
		}
	}
	
	public void nextTurn() {
		turn++;
	}

	public Tile getTile(int x, int y) {
//		System.out.println("x " + x + " y " + y);
		return tiles[x + width * y];
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

	public void setTile(Tile tile, int x, int y) {
		tiles[x + width * y] = tile;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
}
