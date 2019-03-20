package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import startup.Main;

public class GameFrame extends JFrame {

	public GameFrame(int width, int height, String title) {

		setBounds(0, 0, width, height);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Sure you want to exit?") == 0) {
					if (Main.CLIENT != null) {
						Main.CLIENT.leave(Main.USER.getId());
						Main.CLIENT = null;
					}
					if (Main.SERVER != null) {
						Main.SERVER.setRunning(false);
						Main.SERVER = null;
					}
					Platform.runLater(() -> LobbyFrame.PRIMARY_STAGE.close());
					dispose();
					System.exit(0);
				}
			}
		});
		setLocationRelativeTo(null);
		setTitle(title);
		setResizable(false);
		
		if (Main.SETTINGS_PROPERTIES.getFullscreen()) {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			Main.WIDTH = dim.width;
			Main.HEIGHT = dim.height;
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
	}

}
