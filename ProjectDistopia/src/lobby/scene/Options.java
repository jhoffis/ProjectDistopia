package lobby.scene;

import adt.LobbySceneADT;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import startup.Main;
import window.LobbyFrame;

public class Options extends LobbySceneADT {

	private Button goBack;
	private CheckBox fullscreen;
	private CheckBox allmusic;
	private CheckBox nomusic;
	private CheckBox nosfx;
	private Slider slider;
	private Label sliderLabel;

	public Options(String pathname) {
		super(pathname);
		
		String sliderText = "Volumemixer: ";
		long sliderValue = Main.SETTINGS_PROPERTIES.getVolumeFromFile();
		slider = new Slider();
		sliderLabel = new Label(sliderText + sliderValue);
		goBack = new Button("Return");
		fullscreen = new CheckBox("Fullscreen");
		allmusic = new CheckBox("Use all music");
		nomusic = new CheckBox("No music");
		nosfx = new CheckBox("No soundeffects");
		
		fullscreen.setSelected(Main.SETTINGS_PROPERTIES.getFullscreen());
		allmusic.setSelected(Main.SETTINGS_PROPERTIES.getAllMusic());
		nomusic.setSelected(Main.SETTINGS_PROPERTIES.getNoMusic());
		nosfx.setSelected(Main.SETTINGS_PROPERTIES.getNoSFX());
		
		Text scenetitle = new Text("Options");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(sliderValue);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);

		goBack.setOnAction((ActionEvent e) -> LobbyFrame.setScene("MainMenu"));
		fullscreen.setOnAction((ActionEvent e) -> Main.SETTINGS_PROPERTIES.setFullscreen(fullscreen.isSelected()));
		allmusic.setOnAction((ActionEvent e) -> Main.SETTINGS_PROPERTIES.setAllMusic(allmusic.isSelected()));
		nomusic.setOnAction((ActionEvent e) -> Main.SETTINGS_PROPERTIES.setNoMusic(nomusic.isSelected()));
		nosfx.setOnAction((ActionEvent e) -> Main.SETTINGS_PROPERTIES.setNoSFX(nosfx.isSelected()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
	                Number oldVal, Number newVal) {
				long l = Math.round((double) newVal);
				sliderLabel.setText(sliderText + l);
				Main.SETTINGS_PROPERTIES.setVolume(l);
			}
		});
		
		sliderLabel.setTranslateX(mid + 63);
		slider.setTranslateX(mid + 60);
		scenetitle.setTranslateX(mid);
		fullscreen.setTranslateX(100);
		allmusic.setTranslateX(100);
		nomusic.setTranslateX(100);
		nosfx.setTranslateX(100);
		
		scenetitle.setTranslateY(50);
		fullscreen.setTranslateY(100);
		allmusic.setTranslateY(140);
		sliderLabel.setTranslateY(140);
		slider.setTranslateY(160);
		nomusic.setTranslateY(160);
		nosfx.setTranslateY(180);
		
		add(goBack);
		add(scenetitle);
		add(fullscreen);
		add(allmusic);
		add(nomusic);
		add(nosfx);
		add(slider);
		add(sliderLabel);
	}

	@Override
	public void update() {
		
	}

}
