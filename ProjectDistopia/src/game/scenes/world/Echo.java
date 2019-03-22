package game.scenes.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import adt.GameSceneADT;

public class Echo implements GameSceneADT {

	private static LinkedList<String> q;
	private static long timeTillRemove;
	private Font font;
	private Color color;
	private int y;

	public Echo(Font font, int y) {
		this.y = y;
		q = new LinkedList<String>();
		timeTillRemove = -1;
		font = new Font("Georgia", Font.BOLD, 16);
		this.font = font;
		color = new Color(30, 240, 237);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void render(Graphics g) {
		if (!q.isEmpty()) {

			@SuppressWarnings("unchecked")
			LinkedList<String> temp = (LinkedList<String>) q.clone();
			g.setFont(font);
			g.setColor(color);

			for (int i = 0; i < q.size(); i++) {
				String str = temp.poll();
				if (str != null)
					g.drawString(str, 10, (int) (y + (font.getSize() * (i + 1))));
			}
		}
	}

	@Override
	public void tick() {
		if (!q.isEmpty()) {
			if (timeTillRemove < System.currentTimeMillis()) {
				q.remove();
				timeTillRemove = System.currentTimeMillis() + 2000;
			} else if (q.size() > 10) {
				q.remove();
			}
		}
	}

	public static void println(String s) {
		if (q.isEmpty())
			timeTillRemove = System.currentTimeMillis() + 2000;
		q.add(s);
	}

	public static void println(float f) {
		println(String.valueOf(f));
	}

	public static void println(int i) {
		println(String.valueOf(i));
	}

}
