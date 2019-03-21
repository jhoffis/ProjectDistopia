package game.scenes.world;

import elem.Tile;

public class World {

	private int width;
	private int height;
	private Tile[] tiles;
	private int size;
	
	
	public World(int width, int height, int size) {
		this.width = width;
		this.height = height;
		this.size = size;
		
		tiles = new Tile[width * height];
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(0, 100);
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
