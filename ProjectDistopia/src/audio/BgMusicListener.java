package audio;

import java.util.Random;

import javax.sound.sampled.LineEvent;

import javafx.scene.media.AudioSpectrumListener;

public class BgMusicListener {

	private int lastPlayed;
	private MediaAudio media;
	private int songs;
	private String type;

	public BgMusicListener(int songs,  String type) {
		// Maybe use action for something later, cause it's awesome
		lastPlayed = 0;
		this.songs = songs;
		this.type = type;
		playNext();
	}

	public void playNext() {
		if(media != null && media.isPlaying())
			media.stop();
		lastPlayed = findSong();
		media = new MediaAudio("/music/" + type + "/music" + lastPlayed);
		
		media.getMediaPlayer().setOnEndOfMedia(() -> playNext());
	}

	public void updateVolume() {
		media.setVolume(1);
	}

	private int findSong() {
		return (lastPlayed + 1) % (songs + 1);
	}

	public void playOrStop() {
		if(media != null && media.isPlaying())
			media.stop();
		else
			playNext();
	}

}
