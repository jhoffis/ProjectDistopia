package scene;

import javax.swing.JOptionPane;

import adt.LobbySceneADT;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import startup.Main;
import window.LobbyFrame;

public class MainMenu extends LobbySceneADT {

	private Button createUser;
	private Button hostGame;
	private Button joinGame;
	private Button options;
	private Button exit;


	public MainMenu(String pathname) {
		super(pathname);

		createUser = new Button("Create a user");
		hostGame = new Button("Host");
		joinGame = new Button("Join");
		options = new Button("Options");
		exit = new Button("Exit Game");

		super.numBtn = LobbyFrame.HEIGHT / (5 + 2);

		createUser.setOnAction((ActionEvent e) -> LobbyFrame.setScene("User"));
		hostGame.setOnAction((ActionEvent e) -> LobbyFrame.setScene("HostSetup"));
		joinGame.setOnAction((ActionEvent e) -> LobbyFrame.setScene("JoinSetup"));
		options.setOnAction((ActionEvent e) -> LobbyFrame.setScene("Options"));
		exit.setOnAction((ActionEvent e) -> LobbyFrame.shutdown());

		createUser.setTranslateY(numBtn * 1);
		hostGame.setTranslateY(numBtn * 2);
		joinGame.setTranslateY(numBtn * 3);
		options.setLayoutY(numBtn * 4);
		exit.setLayoutY(numBtn * 5);

		createUser.setTranslateX(mid);
		hostGame.setTranslateX(mid);
		joinGame.setTranslateX(mid);
		options.setLayoutX(mid);
		exit.setLayoutX(mid);

		createUser.setPrefSize(btnWidth, btnHeight);
		hostGame.setPrefSize(btnWidth, btnHeight);
		joinGame.setPrefSize(btnWidth, btnHeight);
		options.setPrefSize(btnWidth, btnHeight);
		exit.setPrefSize(btnWidth, btnHeight);

		add(createUser);
		add(hostGame);
		add(joinGame);
		add(options);
		add(exit);
	}

	
	public Button getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Button createUser) {
		this.createUser = createUser;
	}

	public Button getHostGame() {
		return hostGame;
	}

	public void setHostGame(Button hostGame) {
		this.hostGame = hostGame;
	}

	public Button getJoinGame() {
		return joinGame;
	}

	public void setJoinGame(Button joinGame) {
		this.joinGame = joinGame;
	}

	public Button getOptions() {
		return options;
	}

	public void setOptions(Button options) {
		this.options = options;
	}

	public int getBtnWidth() {
		return btnWidth;
	}

	public void setBtnWidth(int btnWidth) {
		this.btnWidth = btnWidth;
	}

	public int getBtnHeight() {
		return btnHeight;
	}

	public void setBtnHeight(int btnHeight) {
		this.btnHeight = btnHeight;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
