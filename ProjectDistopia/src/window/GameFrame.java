package window;

import javax.swing.JFrame;

import game.handlers.GameHandler;

public class GameFrame extends JFrame{

	public GameFrame(int width, int height, String title, GameHandler gh) {
		
		setBounds(0, 0, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(title);
		setResizable(false);
		add(gh);
		setVisible(true);
		
	}
}
