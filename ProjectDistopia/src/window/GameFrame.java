package window;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	public GameFrame(int width, int height, String title) {
		
		setBounds(0, 0, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(title);
		setResizable(false);
		setVisible(true);
	}
}
