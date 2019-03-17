package adt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public interface PropertiesADT {

	public void initProperties(String dir, String filename);
	// Sorter denne og legg til antall øverst.
	public boolean createProperties(String val);
	
}
