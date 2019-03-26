package game.handlers;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import adt.GameSceneADT;
import adt.GameVisualADT;
import audio.BgMusicListener;
import audio.MediaAudio;
import game.scenes.WorldScene;
import game.scenes.world.Echo;
import javafx.embed.swing.JFXPanel;
import startup.Main;

public class GameHandler extends GameVisualADT implements Runnable {

	private SceneAndMouseHandler sh;
	private BufferStrategy bs;
	private JFrame frame;
	public static boolean RUNNING;

	public GameHandler(JFrame frame) {
		RUNNING = true;
		
		sh = new SceneAndMouseHandler(frame);
		this.frame = frame;

		JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.add(this);

		for (GameSceneADT s : sh.getScenes()) {
			frame.addKeyListener(s);
		}
		this.addMouseListener(sh);
		this.addMouseMotionListener(sh);
		frame.addMouseWheelListener(sh);

		frame.setFocusable(true);
		frame.requestFocus();
		frame.setVisible(true);

		switch (Main.MUSIC_TYPE) {
		case 1:
			Main.BACKGROUNDMUSIC = new BgMusicListener(2, "type1");
			break;
		case 2:
			Main.BACKGROUNDMUSIC = new BgMusicListener(9, "type2");
			break;
		case 3:
			Main.BACKGROUNDMUSIC = new BgMusicListener(1, "type3");
			break;
		case 4:
			Main.BACKGROUNDMUSIC = new BgMusicListener(1, "type4");
			break;
		case 5:
			Main.BACKGROUNDMUSIC = new BgMusicListener(5, "type5");
			break;
		};

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

		while (RUNNING) {
			long now = System.nanoTime();
			deltaTick += (now - lastTime) / ns;
			deltaRender += (now - lastTime) / (ns / 3);
			lastTime = now;
			while (deltaTick >= 1) {
				deltaTick--;
				tick();
				Main.CLIENT.sendAck(Main.USER.getId());
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
//				System.out.println("FPS: " + frames);
//				Echo.println("FPS: " + frames);
				frames = 0;
			}
//			try {
//				Thread.sleep(4);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}

	}

}
