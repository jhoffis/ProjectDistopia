package game.handlers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import adt.GameVisualADT;

public class GameHandler extends GameVisualADT implements Runnable {

	private SceneHandler sh;
	private BufferStrategy bs;

	public GameHandler() {
		sh = new SceneHandler();

	}

	@Override
	public void render() {
		// Render the current scene from sh.
		Graphics g = null;
		try {
			bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			g = bs.getDrawGraphics();
			
			sh.getCurrent().render(g);

		} finally {
			if (g != null) {
				g.dispose();
			}
		}
		bs.show();
	}

	@Override
	public void tick() {
		sh.getCurrent().tick();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 20.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				delta--;

				tick();
				frames++;
				render();
			}
			
				
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS RACEEE: " + frames);
				frames = 0;
			}
		}
		
	}

}
