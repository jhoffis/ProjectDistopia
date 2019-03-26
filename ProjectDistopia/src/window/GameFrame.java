package window;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.handlers.GameHandler;
import javafx.application.Platform;
import startup.Main;

public class GameFrame extends JFrame {

	int width, height;
	GraphicsDevice[] devices;
	DisplayMode oldDisplayMode;

	public GameFrame(int width, int height, String title) {

		this.width = width;
		this.height = height;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = ge.getScreenDevices();
		oldDisplayMode = devices[0].getDisplayMode();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setLocationRelativeTo(null);
		setTitle(title);
		setResizable(false);

		if (Main.SETTINGS_PROPERTIES.getFullscreen()) {
			fullscreen();
		} else {
			windowed();
		}

	}

	public void windowed() {
		 dispose();
         setUndecorated(false);
         setVisible(true);
         devices[0].setFullScreenWindow(null);
	}

	private void mainScreenTurnOff() {
		devices[0].setFullScreenWindow(null);
//		devices[0].setDisplayMode(oldDisplayMode);
		// Fix window size here or whatever etc etc.
	}

	public void fullscreen() {
		setVisible(false);
		oldDisplayMode = devices[0].getDisplayMode();
		boolean result = devices[0].isFullScreenSupported();

		if (result) {
			dispose();
			setUndecorated(true);
			setResizable(true);

			addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
					setAlwaysOnTop(true);
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					setAlwaysOnTop(false);
				}
			});

			pack();

			devices[0].setFullScreenWindow(this);
		} else {
			setPreferredSize(getGraphicsConfiguration().getBounds().getSize());

			pack();

			setResizable(true);

			setExtendedState(Frame.MAXIMIZED_BOTH);
			boolean successful = getExtendedState() == Frame.MAXIMIZED_BOTH;

			setVisible(true);

			if (!successful)
				setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}

	public void close() {
		if (JOptionPane.showConfirmDialog(null, "Sure you want to exit?") == 0) {

			GameHandler.RUNNING = false;
			if (Main.CLIENT != null) {
				Main.CLIENT.leave(Main.USER.getId());
				Main.CLIENT = null;
			}
			if (Main.SERVER != null) {
				Main.SERVER.setRunning(false);
				Main.SERVER = null;
			}
			if (Main.BACKGROUNDMUSIC != null) {
				Main.BACKGROUNDMUSIC.playOrStop();
				Main.BACKGROUNDMUSIC = null;
			}
			Platform.runLater(() -> LobbyFrame.setScene("MAINMENU"));
			dispose();
		}
	}

}
