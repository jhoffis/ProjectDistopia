package game.scenes.world;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import game.scenes.WorldScene;

public class WorldMouseWheel implements MouseWheelListener {

	WorldScene worldScene;

	public WorldMouseWheel(WorldScene worldScene) {
		this.worldScene = worldScene;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
//		System.out.println("SCROLL");
		int unitsToScroll = e.getUnitsToScroll();
        int direction = unitsToScroll < 0 ? 1 : -1;
		worldScene.getCam().translateZ(direction * worldScene.getSizeWH() / 4);

	}

}
