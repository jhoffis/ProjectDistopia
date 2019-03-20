package game.handlers;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import adt.GameVisualADT;
import audio.BgMusicListener;
import audio.MediaAudio;
import javafx.embed.swing.JFXPanel;
import startup.Main;

public class GameHandler extends GameVisualADT implements Runnable {

	private SceneHandler sh;
	private BufferStrategy bs;
	private BgMusicListener bg;

	public GameHandler() {
		sh = new SceneHandler();

		JFXPanel fxPanel = new JFXPanel();
		Main.GAME_FRAME.add(fxPanel);
		Main.GAME_FRAME.add(this);
		Main.GAME_FRAME.addKeyListener(sh.getCurrent());
		Main.GAME_FRAME.addMouseListener(sh.getCurrent().getMouseListener());
		Main.GAME_FRAME.addMouseWheelListener(sh.getCurrent().getMouseWheelListener());
		Main.GAME_FRAME.setFocusable(true);
		Main.GAME_FRAME.requestFocus();
		Main.GAME_FRAME.setVisible(true);

		switch (Main.MUSIC_TYPE) {
		case 1:
			bg = new BgMusicListener(2, "type1");
			break;
		case 2:
			bg = new BgMusicListener(2, "type2");
			break;
		case 3:
			bg = new BgMusicListener(1, "type3");
			break;
		case 4:
			bg = new BgMusicListener(1, "type4");
			break;
		case 5:
			bg = new BgMusicListener(5, "type5");
			break;
		}
		;
		new MediaAudio("/sfx/hover").play();
//		bg.playAndChooseNextRandomly();

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
		Main.GAME_FRAME.requestFocus();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 20.0;
		double ns = 1000000000 / amountOfTicks;
		double deltaTick = 0;
		double deltaRender = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (true) {
			long now = System.nanoTime();
			deltaTick += (now - lastTime) / ns;
			deltaRender += (now - lastTime) / (ns / 3);
			lastTime = now;
			while (deltaTick >= 1) {
				deltaTick--;
				tick();

			}

			while (deltaRender >= 1) {
				deltaRender--;
				try {
					render();
				} catch (IllegalStateException e) {
					System.err.println("EXITING LOOP: " + e.getMessage());
					return;
				}

				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

	}

}
