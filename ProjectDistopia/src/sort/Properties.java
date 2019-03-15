package sort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import search.BinarySortedFileSearch;

public class Properties {

	private File file;
	private List<String> lines;
	private BinarySortedFileSearch userPositionFinder;

	public Properties() {
		this(null);
	}

	public Properties(File file) {
		this.file = file;
		userPositionFinder = new BinarySortedFileSearch();
	}

	public void initProperties(String dir) {
		new File(dir).mkdirs();
		file = new File(dir + "users.properties");
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
	public boolean createProperties(String name) {

		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			int pos = 1;

			if (userPositionFinder.find(lines, name) != -1)
				return false;
			pos = userPositionFinder.findNearestLine(lines, name);

			if (pos == lines.size()) {
				lines.add("NAME=" + name);
			} else {
				lines.add(pos, "NAME=" + name);
			}
			
			lines.set(0, String.valueOf(Integer.valueOf(lines.get(0)) + 1));
			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Error IOException with appending new user to properties");
			return false;
		}
		return true;
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
