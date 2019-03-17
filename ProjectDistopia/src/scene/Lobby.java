package scene;

import adt.LobbySceneADT;
import elem.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import network.client.Client;
import network.server.Server;
import startup.Main;
import window.LobbyFrame;

public class Lobby extends LobbySceneADT implements Runnable {

	private Button goBack;
	private Label loading;
	private User user;
	private Server server;
	private Client client;
	private boolean running;

	private Label humans;
	private Label aliens;
	private Text scenetitle;

	public Lobby(String pathname) {
		super(pathname);

		goBack = new Button("Return");

		goBack.setOnAction((ActionEvent e) -> leaveLobby());
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

	public void leaveLobby() {
		running = false;
		client.leave(user.getId());

		if (user.getHost() == 1)
			server.setRunning(false);

		LobbyFrame.setScene("MainMenu");
	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 2.0;
		double ns = 1000000000 / amountOfTicks;
		double deltatick = 0;
		double deltarender = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			deltatick += (now - lastTime) / ns;
			lastTime = now;
			while (deltatick >= 1) {
				deltatick--;
				frames++;

//				Platform.runLater(() -> loading.setText(client.sendStringRequest("U#" + user.getId())));
				client.sendAck(user.getId());
				
				Platform.runLater(() -> humans.setText("Humans:\n" + client.sendStringRequest("U#" + user.getId())));
				
				
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void tryJoin(String ip) {
		System.out.println("Trying to join a server with " + ip);

		Main.CLIENT = new Client(ip);
		client = Main.CLIENT;
		client.setUser(user);

		String newUserVal = client.sendStringRequest("JOIN#" + user.toString());
		if (!newUserVal.equals(user.toString()))
			user = Main.USER_PROPERTIES.setUserID(newUserVal);

		initLook();
		running = true;
		new Thread(this).start();

		System.out.println("Joined and got this in return " + newUserVal);
	}

	private void initLook() {

		rm(loading);
		
		humans = new Label();
		aliens = new Label();

		String txt;
		scenetitle = new Text(client.sendStringRequest("TITLE"));
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		scenetitle.setTranslateX(mid);
		humans.setTranslateX(mid - 80);
		aliens.setTranslateX(mid + 80);
		
		scenetitle.setTranslateY(50);
		humans.setTranslateY(140);
		aliens.setTranslateY(140);
		
		add(scenetitle);
		add(humans);
		add(aliens);
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
