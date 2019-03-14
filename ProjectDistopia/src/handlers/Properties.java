package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Properties {

	private File file;
	private Scanner scanner;
	
	public Properties() {
		this(null, null);
	}

	public Properties(File file, Scanner scanner) {
		this.file = file;
		this.scanner = scanner;
	}
	
	public boolean appendToProperties() {
		return false;
	}

	public boolean initProperties(String dir, String e) {
		System.out.println(new File(dir).mkdir());
		file = new File(dir + e);
		return file.isFile();
	}

	public boolean createProperties() {
		if (file != null) {
			try {
				file.createNewFile();
				scanner = new Scanner(file);
				addAllPropertiesTypesInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
	
	
	/**
	 * name
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	private void addAllPropertiesTypesInFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(file, "UTF-8");
		pw.write("NAME=;");
	}
}
