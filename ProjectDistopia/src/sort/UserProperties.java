package sort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

import adt.PropertiesADT;
import elem.User;
import search.BinarySortedFileSearch;

public class UserProperties implements PropertiesADT {

	private File file;
	private List<String> lines;
	private BinarySortedFileSearch userPositionFinder;

	public UserProperties() {
		this(null);
	}

	public UserProperties(File file) {
		this.file = file;
		userPositionFinder = new BinarySortedFileSearch();
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
				}
			}

			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Sorter denne og legg til antall øverst.
	@Override
	public boolean createProperties(String val) {

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = 1;

			if (userPositionFinder.find(lines, val, 1) != -1)
				return false;
			pos = userPositionFinder.findNearestLine(lines, val, 1);

			String line = "NAME=" + new User(val, -1, -1, new Random().nextInt(100000)).toString();

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
		}
		return true;
	}

	public User setUserID(String newUserVal) {
		String[] user = newUserVal.split("#");
		String name = user[0];
		int id = Integer.valueOf(user[1]);
		int host = Integer.valueOf(user[2]);
		int finalid = Integer.valueOf(user[3]);
		return setUserID(name, id, host, finalid);
	}

	public User setUserID(String name, int id, int host, int finalid) {
		User user = null;

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = userPositionFinder.find(lines, name, 1);

			if (pos == -1)
				return null;
			user = new User(name, id, host, finalid);
			lines.set(pos, "NAME=" + user.toString());

			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.err.println("Error IOException trying to edit ID user to properties");
			return null;
		}
		return user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

}
