package lobby.scene;

import adt.LobbySceneADT;
import audio.MediaAudio;
import elem.BackStoryStrings;
import elem.HoveringTooltip;
import elem.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import network.client.Client;
import network.server.workinggears.Server;
import startup.Main;
import window.LobbyFrame;

public class Lobby extends LobbySceneADT implements Runnable {

	private Button goBack;
	private Label loading;
	private User user;
	private Server server;
	private Client client;
	private boolean running;

	private Label players;
	private Text scenetitle;
	private String availFac = "";
	private Button ready;
	private Button options;
	private Button start;
	private Image[] facImgs;
	private String[] facNames = { "Aiazom", "Gazellia", "Jotnatium", "Republic of Wessland", "Empire of Anglia",
			"Theilron Hills" };
	private boolean[] facChosen;
	private ImageView[] facPics;
	private Color txtColor = new Color(244.0 / 255.0, 240.0 / 255.0, 224.0 / 255.0, 1.0);
	private Color bckColor = new Color(44.0 / 255.0, 44.0 / 255.0, 44.0 / 255.0, 1.0);
	private VBox humanBox;
	private VBox alienBox;
	private MediaAudio lobbybtn;
	private BackStoryStrings backstory;

	public Lobby(String pathname) {
		super(pathname);

		goBack = new Button("Return");
		facChosen = new boolean[facNames.length];
		backstory = new BackStoryStrings();
		goBack.setOnAction((ActionEvent e) -> leaveLobby());
		String str = "Loading...";
		loading = new Label(str);
		loading.setTranslateX(mid);
		loading.setTranslateY(mid);

		facImgs = new Image[12];

		int n = 0;
		for (int i = 0; i < facNames.length; i++) {
			facImgs[n] = new Image("pic/fac/" + facNames[i] + "NotSel.png");
			n++;
			facImgs[n] = new Image("pic/fac/" + facNames[i] + "IsSel.png");
			n++;
		}

		add(loading);
		add(goBack);
	}

	@Override
	public void update() {

	}

	public void leaveLobby() {
		Main.lbtn();
		running = false;

		client.leave(user.getId());

		if (user.getHost() == 1) {
			server.stopWatch();
			server.setRunning(false);
		}

		LobbyFrame.setScene("MainMenu");

	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 5.0;
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

				client.sendAck(user.getId());

				if (client == null) {
					running = false;
					break;
				}

				// START GAME AND RUN LOOP SOMEWHERE ELSE
				if (Integer.valueOf(client.sendStringRequest("STARTED")) == 1) {
					System.err.println("--STARTED--");
					Main.USER = user;
					if (user.getFaction().equals("Aiazom")) {
						Main.MUSIC_TYPE = 5;
					} else if (user.getFaction().equals("Gazellia") || user.getFaction().equals("Empire of Anglia")) {
						Main.MUSIC_TYPE = 3;
					} else if (user.getFaction().equals("Republic of Wessland")) {
						Main.MUSIC_TYPE = 4;
					} else if (user.getFaction().equals("Jotnatium")) {
						Main.MUSIC_TYPE = 1;
					} else if (user.getFaction().equals("Theilron Hills")) {
						Main.MUSIC_TYPE = 2;
					} else {

					}
					Platform.runLater(() -> Main.openGameFrame());
//					Platform.runLater(() -> LobbyFrame.forceShutdownLobby());
					running = false;
					for (int i = 0; i < Main.SOUNDS.size(); i++) {
						int n = i;
						Platform.runLater(() -> Main.SOUNDS.get(n).stop());
					}
					Platform.runLater(() -> rm(goBack));
					Platform.runLater(() -> rm(ready));
					Platform.runLater(() -> rm(start));
					Platform.runLater(() -> rm(humanBox));
					Platform.runLater(() -> rm(alienBox));
					Platform.runLater(() -> rm(options));
					Platform.runLater(() -> scenetitle.setText("Don't mind this ok"));

					break;
				}

				Platform.runLater(() -> {

					String utxt = "Players:\n";
					String[] uinput = client.sendStringRequest("U").split("#");

					for (int i = 0; i < uinput.length; i++) {
						switch (i % 4) {
						case 0:
							utxt += uinput[i] + ", ";
							break;
						case 1:
							if (uinput[i].equals("")) {
								utxt += "No faction, ";
							} else {
								utxt += uinput[i] + ", ";
								user.setFaction(uinput[i]);
							}
							break;
						case 2:
							if (uinput[i].equals("1")) {
								utxt += "Host, ";
							}
							break;
						case 3:
							if (uinput[i].equals("1")) {
								utxt += "Ready";
							} else {
								utxt += "Not ready";
							}

							utxt += "\n";
						}
					}

					players.setText(utxt);
				});

				String availableFactionTemp = client.sendStringRequest("AVLFAC");
				if (!availFac.equals(availableFactionTemp)) {
					availFac = availableFactionTemp;
					String[] listFac = availFac.split("#");

					for (int i = 0; i < listFac.length; i++) {
						boolean bool = Integer.valueOf(listFac[i]) == 1;
						if (bool != facChosen[i]) {
							facPics[i].setImage(facImgs[i * 2 + Integer.valueOf(listFac[i])]);
							facChosen[i] = bool;
						}
					}
				}

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void join(Client client, User user) {

		this.client = client;
		this.user = user;

		initLook();
		running = true;
		new Thread(this).start();

	}

	private void initLook() {

		rm(loading);

		players = new Label();
		ready = new Button("Ready");
		options = new Button("Options");
		facPics = new ImageView[6];
		humanBox = new VBox();
		alienBox = new VBox();

		pics(0, facPics.length / 2, alienBox);
		pics(facPics.length / 2, facPics.length, humanBox);

		if (user.getHost() == 1) {
			start = new Button("Start Game!");
			start.setOnAction((ActionEvent e) -> {
				client.sendStringRequest("START#" + user.getId());
				Main.lbtn();
			});
			start.setTranslateX(mid + 120);
			start.setTranslateY(300);
			add(start);
		}

		ready.setOnAction((ActionEvent e) -> {
			client.sendStringRequest("READY#" + user.getId());
			Main.lbtn();
		});

		options.setOnAction((ActionEvent e) -> {
			LobbyFrame.setScene("OPTIONS");
			Main.lbtn();
		});
		scenetitle = new Text(client.sendStringRequest("TITLE").toUpperCase());
		scenetitle.setFont(Font.font("Georgia", FontWeight.NORMAL, 24));
		scenetitle.setFill(txtColor);
		players.setTextFill(txtColor);
		LobbyFrame.getPane(panename)
				.setBackground(new Background(new BackgroundFill(bckColor, CornerRadii.EMPTY, Insets.EMPTY)));

		alienBox.setTranslateX(Main.WIDTH - 144);
		humanBox.setTranslateX(16);
		scenetitle.setTranslateX(mid);
		players.setTranslateX(mid - 60);
		ready.setTranslateX(mid + 61);
		options.setTranslateX(mid - 3);
		goBack.setTranslateX(mid - 60);

		int boxHeight = -10;
		alienBox.setTranslateY(boxHeight);
		humanBox.setTranslateY(boxHeight);
		scenetitle.setTranslateY(50);
		players.setTranslateY(140);
		goBack.setTranslateY(300);
		options.setTranslateY(300);
		ready.setTranslateY(300);

//		alienBox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//		humanBox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		add(alienBox);
		add(humanBox);
		add(ready);
		add(scenetitle);
		add(players);
		add(options);
	}

	private void pics(int a, int length, VBox box) {

		for (int i = a; i < length; i++) {
			int clicked = i;
			facPics[i] = new ImageView();
			facPics[i].setImage(facImgs[i * 2]);

			Label label = new Label("\n" + facNames[i]);
			label.setTextFill(txtColor);
			box.getChildren().add(label);

			box.getChildren().add(facPics[i]);
			facPics[i].setOnMouseEntered((MouseEvent e) -> {
				new MediaAudio("/sfx/hover").play();
				/* Get big */});
			facPics[i].setOnMouseClicked((MouseEvent e) -> {/* Get smaller */
				changeFac(clicked);
			});


			HoveringTooltip ht = new HoveringTooltip(100);
			ht.setMaxWidth(200);
			ht.setWrapText(true);
			ht.setText(backstory.getStory(i));
			ht.addHoveringTarget(facPics[i]);
			Tooltip.install(facPics[i], ht);
			

		}

	}

	private void changeFac(int clicked) {
		if (!facChosen[clicked]) {
			client.sendStringRequest("CHSFAC#" + facNames[clicked] + "#" + user.getId());
			lobbybtn = new MediaAudio("/sfx/" + facNames[clicked] + "/lobbybtn");
		}
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
