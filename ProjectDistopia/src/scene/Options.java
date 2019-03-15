package scene;

import adt.LobbyScene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import window.LobbyFrame;

public class Options extends LobbyScene {

	private Button goBack;
	private CheckBox fullscreen;
	private CheckBox allmusic;
	private CheckBox nomusic;
	private CheckBox nosfx;

	public Options(String pathname) {
		super(pathname);

		goBack = new Button("Return");
		fullscreen = new CheckBox("Fullscreen");
		allmusic = new CheckBox("Use all music");
		nomusic = new CheckBox("No music");
		nosfx = new CheckBox("No soundeffects");
		
		Text scenetitle = new Text("Options");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		

		goBack.setOnAction((ActionEvent e) -> LobbyFrame.setScene("MainMenu"));
		
		scenetitle.setTranslateX(mid);
		fullscreen.setTranslateX(100);
		allmusic.setTranslateX(100);
		nomusic.setTranslateX(100);
		nosfx.setTranslateX(100);
		
		scenetitle.setTranslateY(50);
		fullscreen.setTranslateY(100);
		allmusic.setTranslateY(140);
		nomusic.setTranslateY(160);
		nosfx.setTranslateY(180);
		
		add(goBack);
		add(scenetitle);
		add(fullscreen);
		add(allmusic);
		add(nomusic);
		add(nosfx);
	}

	@Override
	public void update() {
		
	}

}
