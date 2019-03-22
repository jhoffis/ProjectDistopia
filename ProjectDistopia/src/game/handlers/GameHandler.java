package game.handlers;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import adt.GameVisualADT;
import audio.BgMusicListener;
import audio.MediaAudio;
import game.scenes.WorldScene;
import game.scenes.world.Echo;
import javafx.embed.swing.JFXPanel;
import startup.Main;

public class GameHandler extends GameVisualADT implements Runnable {

	private SceneHandler sh;
	private BufferStrategy bs;
	private BgMusicListener bg;
	private JFrame frame;

	public GameHandler(JFrame frame) {
		sh = new SceneHandler(frame);
		this.frame = frame;
		
		JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.add(this);

//		MouseListener ml = new WorldMouse((WorldScene) sh.getCurrent());

		frame.addKeyListener(sh.getCurrent());
		this.addMouseListener((WorldScene) sh.getCurrent());
		this.addMouseMotionListener((WorldScene) sh.getCurrent());
		frame.addMouseWheelListener((WorldScene) sh.getCurrent());
		
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setVisible(true);
		
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

		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void tick() {
		sh.getCurrent().tick();
		frame.requestFocus();
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
				Echo.println("FPS: " + frames);
				frames = 0;
			}
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
