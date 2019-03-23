package lobby.scene;

import java.util.HashMap;
import java.util.List;

import adt.LobbySceneADT;
import audio.MediaAudio;
import elem.ConnectionConfig;
import elem.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import network.server.Server;
import startup.Main;
import window.LobbyFrame;

public class LobbySetup extends LobbySceneADT {

	private boolean host;
	private Button goBack;
	private Button request;
	private TextField txtInput;
	private Label txtLabel;
	private Label usrLabel;
	private ComboBox<String> usrs;
	private Button userSetup;
	private EventHandler<MouseEvent> usrCreate;
	private HashMap<String, String> usrsComplete;

	public LobbySetup(String pathname, boolean host) {
		super(pathname);

		this.host = host;

		Text scenetitle;
		String txt;
		if (host) {
			scenetitle = new Text("Host a lobby");
			txt = "Name of game. 1-15 chars";
		} else {
			scenetitle = new Text("Join a lobby");
			txt = "IP. 1-15 chars";
		}
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		goBack = new Button("Return");
		request = new Button("Request");

		txtLabel = new Label(txt);
		txtInput = new TextField();
		usrLabel = new Label("Choose your user:");
		usrs = new ComboBox<String>();
		userSetup = new Button("Create new user");

		usrCreate = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// code used for retrieving x,y values
				LobbyFrame.setScene("User");
				Main.lbtn();
			}
		};
		goBack.setOnAction((ActionEvent e) -> {
			LobbyFrame.setScene("MainMenu");
			Main.lbtn();
		});
		userSetup.setOnMouseClicked((MouseEvent e) -> {
			LobbyFrame.setScene("User");
			Main.lbtn();
		});
		userSetup.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().equals(KeyCode.ENTER))
				LobbyFrame.setScene("User");
		});
		request.setOnMouseClicked((MouseEvent e) -> {
			if (host)
				hostServer();
			else
				joinServer();
			Main.lbtn();
		});

		update();

		request.setTranslateX(mid);
		txtInput.setTranslateX(mid);
		txtLabel.setTranslateX(mid);
		scenetitle.setTranslateX(mid);
		usrLabel.setTranslateX(mid);
		usrs.setLayoutX(mid);
		userSetup.setTranslateX(mid + 80);

		request.setTranslateY(240);
		txtInput.setTranslateY(150);
		txtLabel.setTranslateY(130);
		scenetitle.setTranslateY(50);
		usrLabel.setTranslateY(180);
		usrs.setTranslateY(200);
		userSetup.setTranslateY(240);

		add(goBack);
		add(request);
		add(txtLabel);
		add(txtInput);
		add(scenetitle);
		add(usrLabel);
		add(usrs);
		add(userSetup);
	}

	@Override
	public void update() {

		usrs.getSelectionModel().clearSelection();
		usrs.getItems().clear();
		List<String> lines = Main.USER_PROPERTIES.getLines();

		if (Integer.valueOf(lines.get(0)) < 1) {
			usrs.setPromptText("No users");
			usrs.setTooltip(new Tooltip("Click here to create a new user"));
			usrs.addEventHandler(MouseEvent.MOUSE_PRESSED, usrCreate);
		} else {
			usrs.setPromptText("Users available");
			usrs.removeEventHandler(MouseEvent.MOUSE_PRESSED, usrCreate);
			usrs.getItems().addAll(cleanLines(lines));
		}
	}

	private String[] cleanLines(List<String> lines) {

		String[] arr = new String[lines.size() - 1];
		usrsComplete = new HashMap<String, String>();

		for (int i = 0; i < arr.length; i++) {
			arr[i] = lines.get(i + 1).split("=")[1].split("#")[0];
			usrsComplete.put(arr[i], lines.get(i + 1).split("=")[1]);
		}
		return arr;
	}

	private void hostServer() {

		if (!(txtInput.getText().matches("^[a-zA-Z0-9æøåÆØÅ. ]+$")) || txtInput.getText().length() < 1
				|| txtInput.getText().length() > 15 || usrs.getValue() == null) {
			return;
		}

		Main.SERVER = new Server(txtInput.getText());
		LobbyFrame.replacePane("LOBBY");
		Lobby lobby = new Lobby("LOBBY");
		lobby.setServer(Main.SERVER);
		joinServer(ConnectionConfig.SERVER.valueAsString(), lobby);
	}

	private void joinServer() {
		if (!(txtInput.getText().matches("^[a-zA-Z0-9æøåÆØÅ. ]+$")) || txtInput.getText().length() < 1
				|| txtInput.getText().length() > 15) {
			return;
		}
		LobbyFrame.replacePane("LOBBY");
		Lobby lobby = new Lobby("LOBBY");
		joinServer(txtInput.getText(), lobby);
	}

	private void joinServer(String ip, Lobby lobby) {

		if (usrs.getValue() == null) {
			return;
		}

		String newUserVal = usrsComplete.get(usrs.getValue());
		User user = new User(newUserVal);
		user.setHost(host ? 1 : 0);
		lobby.setUser(user);
		lobby.tryJoin(ip);

		LobbyFrame.setAndReplaceScene(lobby);
		System.out.println(((Lobby) LobbyFrame.CURRENT_SCENE).getUser().getName());

	}

}
