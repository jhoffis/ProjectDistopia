package handlers;

import java.io.File;
import java.io.IOException;

public class Properties {

	private File file;
	
	public Properties() {
		file = null;
	}
	
	

	public boolean appendToProperties() {
		return false;
	}

	public boolean initProperties(String path) {
		file = new File(path);
		return file.isFile();
	}

	public boolean createProperties() {
		if (file != null) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			addAllPropertiesTypesInFile();
			return true;
		}
		return false;
	}
	
	private void addAllPropertiesTypesInFile() {
		
	}
}
