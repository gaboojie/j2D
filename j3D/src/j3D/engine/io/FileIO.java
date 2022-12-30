package j3D.engine.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {

	public static String getContentsOfPath(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			System.out.println("ERROR Reading File from path: " + path);
			e.printStackTrace();
		}
		return null;
	}
	
}
