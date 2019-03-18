package lobby.scene;

import javax.swing.JOptionPane;

import adt.LobbySceneADT;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import startup.Main;
import window.LobbyFrame;

public class UserSetup extends LobbySceneADT {

	private Button mainMenu;
	private Label usrLabel;
	private TextField newUsr;
	private Label errLabel;
	private Button append;
	private Button appendAndRet;

	public UserSetup(String pathname) {
		super(pathname);

		Text scenetitle = new Text("Create a user");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		mainMenu = new Button("Main Menu");
		usrLabel = new Label("Username:");
		newUsr = new TextField();
		errLabel = new Label();
		append = new Button("Append");
		appendAndRet = new Button("Append and return");

		mainMenu.setOnAction((ActionEvent e) -> LobbyFrame.setScene("MainMenu"));
		append.setOnAction((ActionEvent e) -> addUser());
		appendAndRet.setOnAction((ActionEvent e) -> {
			if (addUser())
				LobbyFrame.setScene("Last");
		});
		newUsr.setOnKeyPressed((KeyEvent e) -> checkNoErrorsInUsername());

		scenetitle.setTranslateX(mid);
		usrLabel.setTranslateX(mid);
		newUsr.setTranslateX(mid);
		errLabel.setTranslateX(mid);
		append.setTranslateX(mid);
		appendAndRet.setTranslateX(mid + 80);

		scenetitle.setTranslateY(50);
		usrLabel.setTranslateY(130);
		newUsr.setTranslateY(150);
		errLabel.setTranslateY(180);
		append.setTranslateY(240);
		appendAndRet.setTranslateY(240);

		add(mainMenu);
		add(scenetitle);
		add(usrLabel);
		add(newUsr);
		add(errLabel);
		add(append);
		add(appendAndRet);
	}

	private boolean checkNoErrorsInUsername() {
		String err = "";
		if (newUsr.getText().equals("")) {
			errLabel.setText(err);
			return false;
		}

		boolean res = true;

		if (!(newUsr.getText().matches("^[a-zA-Z0-9æøåÆØÅ]+$"))) {
			err += "Only use alphanumeric chars!\n";
			res = false;
		}
		if (newUsr.getText().length() < 2) {
			err += "String too short! Min 2 chars!";
			res = false;
		} else if (newUsr.getText().length() > 12) {
			err += "String too long! Max 12 chars!";
			res = false;
		}

		errLabel.setText(err);
		return res;
	}

	private boolean addUser() {

		if (!checkNoErrorsInUsername())
			return false;

		if (!Main.USER_PROPERTIES.createProperties(newUsr.getText())) {
			// already exists
			errLabel.setText("You already have a user named " + newUsr.getText() + "!");
			return false;
		} else {
			errLabel.setText("Succesfully appended " + newUsr.getText() + " to list!");
			return true;
		}
	}

	@Override
	public void update() {
		errLabel.setText("");
		newUsr.setText("");
	}

}
