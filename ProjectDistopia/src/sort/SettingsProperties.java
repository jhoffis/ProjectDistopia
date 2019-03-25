package sort;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import adt.PropertiesADT;
import search.BinarySortedFileSearch;
import startup.Main;

public class SettingsProperties implements PropertiesADT {

	
	private File file;
	private List<String> lines;
	private BinarySortedFileSearch userPositionFinder;
	private HashMap<String, Boolean> boolSettings;
	private HashMap<String, Long> longSettings;
	

	public SettingsProperties() {
		this(null);
	}

	public SettingsProperties(File file) {
		this.file = file;
		userPositionFinder = new BinarySortedFileSearch();
		boolSettings = new HashMap<String, Boolean>();
		longSettings = new HashMap<String, Long>();
	}
	
	@Override
	public void initProperties(String dir, String filename) {
		new File(dir).mkdirs();
		file = new File(dir + filename + ".properties");
		try {

			if (!file.isFile()) {
				if (file.createNewFile()) {
					PrintWriter pw = new PrintWriter(file);
					pw.print(0);
					pw.flush();
					pw.close();
					createProperties("ALLMUSIC=0");
					createProperties("FULLSCREEN=0");
					createProperties("NOMUSIC=0");
					createProperties("NOSFX=0");
					createProperties("VOLUME=50");
				}
			}

			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

			getFullscreenFromFile();
			getAllMusicFromFile();
			getNoMusicFromFile();
			getNoSFXFromFile();
			getVolumeFromFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createProperties(String val) {

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

			int pos = 1;

			if (userPositionFinder.find(lines, val, 1) != -1) {
				System.err.println("SETTING ALREADY ADDED!");
				throw new Exception();
			}
			pos = userPositionFinder.findNearestLine(lines, val, 1);

			String line = val;

			if (pos == lines.size()) {
				lines.add(line);
			} else {
				lines.add(pos, line);
			}

			
			lines.set(0, String.valueOf(Integer.valueOf(lines.get(0)) + 1));

			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Error IOException with appending new user to properties");
			return false;
		} catch (Exception e) {
			System.err.println("SETTING ALREADY ADDED!");
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean getFullscreen() {
		return boolSettings.get("FULLSCREEN");
	}
	
	public boolean getAllMusic() {
		return boolSettings.get("ALLMUSIC");
	}
	
	public boolean getNoMusic() {
		return boolSettings.get("NOMUSIC");
	}
	
	public boolean getNoSFX() {
		return boolSettings.get("NOSFX");
	}
	
	public long getVolume() {
		return longSettings.get("VOLUME");
	}
	
	public boolean getFullscreenFromFile() {
		return findBoolVal("FULLSCREEN");
	}

	public boolean getAllMusicFromFile() {
		return findBoolVal("ALLMUSIC");
	}
	
	public boolean getNoMusicFromFile() {
		return findBoolVal("NOMUSIC");
	}
	
	public boolean getNoSFXFromFile() {
		return findBoolVal("NOSFX");
	}
	
	public long getVolumeFromFile() {
		return findLongVal("VOLUME");
	}
	

	public boolean setFullscreen(boolean newVal) {
		return setNewBoolVal("FULLSCREEN", newVal);
	}
	
	public boolean setAllMusic(boolean newVal) {
		return setNewBoolVal("ALLMUSIC", newVal);
	}
	
	public boolean setNoMusic(boolean newVal) {
		return setNewBoolVal("NOMUSIC", newVal);
	}
	
	public boolean setNoSFX(boolean newVal) {
		return setNewBoolVal("NOSFX", newVal);
	}
	
	public void setVolume(long newVal) {
		setNewLongVal("VOLUME", newVal);
	}

	private void setNewLongVal(String type, long newVal) {
		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = userPositionFinder.find(lines, type, 0);

			if (pos == -1)
				throw new IOException();
			
			lines.set(pos, type + "=" + newVal);
			longSettings.put(type, newVal);
			
			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.err.println("Error IOException trying to edit ID user to properties");
			e.printStackTrace();
		}
	
	}

	private boolean setNewBoolVal(String type, boolean newVal) {

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = userPositionFinder.find(lines, type, 0);

			if (pos == -1)
				return false;
			
			lines.set(pos, type + "=" + (newVal ? 1 : 0));
			boolSettings.put(type, newVal);
			
			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.err.println("Error IOException trying to edit ID user to properties");
			return false;
		}
		return true;
	}
	private long findLongVal(String type) {
		long val = 0;
		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = userPositionFinder.find(lines, type, 0);

			if (pos == -1)
				throw new IOException();
			
			val = Integer.valueOf(lines.get(pos).split("=")[1]);
			
			longSettings.put(type, val);
			
		} catch (IOException e) {
			System.err.println("Error IOException trying to edit ID user to properties");
			e.printStackTrace();
		}
		
		return val;
	}
	
	private boolean findBoolVal(String type) {
		int val = 0;
		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = userPositionFinder.find(lines, type, 0);

			if (pos == -1)
				throw new IOException();
			
			val = Integer.valueOf(lines.get(pos).split("=")[1]);
			
			boolSettings.put(type, val == 1);
			
		} catch (IOException e) {
			System.err.println("Error IOException trying to edit ID user to properties");
			e.printStackTrace();
		}
		
		return val == 1;
	}

	public int getWindowedHeight() {
		if(getFullscreen()) {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			return dim.height;
		}
		return 500;
	}

	public int getWindowedWidth() {
		if(getFullscreen()) {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			return dim.width;
		}
		return 600;
	}
	

}
