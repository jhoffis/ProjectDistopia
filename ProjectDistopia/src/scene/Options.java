package scene;

import adt.LobbyScene;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import window.LobbyFrame;

public class Options extends LobbyScene {

	private Button goBack;

	public Options(Parent root) {
		super(root);

		goBack = new Button("Return");

		goBack.setOnAction((ActionEvent e) -> LobbyFrame.setScene("MainMenu"));
		
		LobbyFrame.add("Options", goBack);
	}

}
