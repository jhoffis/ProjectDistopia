package world;

import elem.Tile;

public class World {

	private int width;
	private int height;
	private Tile[] tiles;
	
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = generateWorld();
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x + width * y];
	}
	
	private Tile[] generateWorld() {
		Tile[] world = new Tile[width * height];
		
		for(Tile tile : world) {
			tile = new Tile(0, 100);
		}
		return world;
	}
}
