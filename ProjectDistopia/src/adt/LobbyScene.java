package adt;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import window.LobbyFrame;

public abstract class LobbyScene extends Scene{

	protected int mid;
	protected int btnWidth;
	protected int btnHeight;
	protected int numBtn;
	
	public LobbyScene(Parent root) {
		this(root, Color.AQUA);
	}
	
	public LobbyScene(Parent root, Paint fill) {
		super(root, LobbyFrame.WIDTH, LobbyFrame.HEIGHT, fill);
		
		btnWidth = (int) (LobbyFrame.WIDTH / 4f);
		btnHeight = (int) (LobbyFrame.HEIGHT / 12.5f);
		
		mid = (LobbyFrame.WIDTH / 2) - (btnWidth / 2);
		
		//Not impl
		numBtn = -1;
	}

}
