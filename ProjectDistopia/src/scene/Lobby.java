package scene;

import adt.LobbyScene;
import elem.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import window.LobbyFrame;

public class Lobby extends LobbyScene {

	private Button goBack;
	private Label loading;
	private User user;

	public Lobby(String pathname) {
		super(pathname);

		goBack = new Button("Return");

		goBack.setOnAction((ActionEvent e) -> LobbyFrame.setScene("MainMenu"));
		String str = "Loading...";
		loading = new Label(str);
		loading.setTranslateX(mid);
		loading.setTranslateY(mid);

		add(loading);
		add(goBack);
	}

	@Override
	public void update() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void tryJoin(String ip) {
		System.out.println("Trying to join a server with " + ip);
	}

}
