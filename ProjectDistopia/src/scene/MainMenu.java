package scene;

import javax.swing.JOptionPane;

import adt.LobbyScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import startup.Main;
import window.LobbyFrame;

public class MainMenu extends LobbyScene{

	private Button createUser;
	private Button hostGame;
	private Button joinGame;
	private Button options;
	private Button exit;
	
	
	public MainMenu(Parent root) {
		this(root, Color.AQUA);
	}
	
	public MainMenu(Parent root, Paint fill) {
		super(root, fill);
		
        createUser = new Button("Create a user");
        hostGame = new Button("Host");
        joinGame = new Button("Join");
        options = new Button("Options");
        exit = new Button("Exit Game");
        
        super.numBtn = LobbyFrame.HEIGHT / (5 + 2); 
        
        
        createUser.setOnAction((ActionEvent e) -> {
        	String name = null;
        	
        	do {
        		name = JOptionPane.showInputDialog("What is your username?\n"
        				+ "2-12 chars, only alphanumeric");
        		if(name == null) {
        			return;
        		}
        		else if(!(name.matches("^[a-zA-Z0-9æøåÆØÅ]+$") && name.length() >= 2 && name.length() <= 12)) {
        			name = null;
        		}
        	} while(name == null);
        	
        	System.out.println("Name recieved is " + name);
        	String path = null;
        	
        	if(Main.isWindows()) {
        		System.out.println("You have Windows");
        		path = "%appdata%/.battleOfAuthrohpia/" + name + ".properties";
        		
        	} else if (Main.isUnix()) {
        		System.out.println("You have Unix of some sort");
        		path = "$HOME/.battleOfAuthrohpia/" + name + ".properties";
        	} else {
        		System.out.println("I don't recognize your OS");
        		System.exit(-1);
        	}
        	
        	boolean file = Main.PROPERTIES.initProperties(path);
    		if(file == false) {
    			//Finish creating.
    			System.out.println("Does not exists");
    			
    		} else {
    			//already exists
    			System.out.println("Already exists");
    		}
    	
        	
        });
        options.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {

            	LobbyFrame.setScene("Options");
            }
        });
        exit.setOnAction((ActionEvent e) -> System.exit(0));
        
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
        
        LobbyFrame.add("MainMenu", createUser);
        LobbyFrame.add("MainMenu", hostGame);
        LobbyFrame.add("MainMenu", joinGame);
        LobbyFrame.add("MainMenu", options);
        LobbyFrame.add("MainMenu", exit);
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

}
