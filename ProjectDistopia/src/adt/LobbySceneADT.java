package adt;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import window.LobbyFrame;

public abstract class LobbySceneADT extends Scene {

	protected int mid;
	protected int btnWidth;
	protected int btnHeight;
	protected int numBtn;
	protected String panename;

	public LobbySceneADT(String panename) {
		super(LobbyFrame.getPane(panename), LobbyFrame.WIDTH, LobbyFrame.HEIGHT);

		btnWidth = (int) (LobbyFrame.WIDTH / 4f);
		btnHeight = (int) (LobbyFrame.HEIGHT / 12.5f);

		setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().equals(KeyCode.ESCAPE)) {
				LobbyFrame.setScene("UPPER");
			}
		});

		mid = (LobbyFrame.WIDTH / 2) - (btnWidth / 2);
		this.panename = panename;
		// Not impl
		numBtn = -1;
	}

	protected void add(Node e) {
		LobbyFrame.add(panename, e);
	}
	
	protected void rm(Node e) {
		LobbyFrame.remove(panename, e);
	}

	public abstract void update();
	
	public String getPanename() {
		return panename;
	}

}
