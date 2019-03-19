package game.scenes;

import java.awt.Color;
import java.awt.Graphics;

import adt.GameSceneADT;
import elem.Camera;
import game.scenes.world.World;
import startup.Main;

public class WorldScene implements GameSceneADT{

	private World world;
	private Camera cam;
	
	public WorldScene() {
		world = new World(16, 16);
		cam = new Camera();
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
	}

	@Override
	public void tick() {
		
	}

}
