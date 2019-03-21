package game.scenes.world;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import game.scenes.WorldScene;

public class WorldMouseMove extends MouseInputAdapter {

	WorldScene worldScene;

	public WorldMouseMove(JFrame frame) {
		frame.addMouseMotionListener(this);
	}
	
	public WorldMouseMove(WorldScene worldScene) {
		this.worldScene = worldScene;
//		Main.GAME_FRAME
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TEST FIXME
		System.out.println("Dragging");
//		worldScene.getCam().setX(e.getXOnScreen());
//		worldScene.getCam().setY(e.getYOnScreen());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Move");
	}
}
