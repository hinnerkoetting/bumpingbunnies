package de.oetting.bumpingbunnies.leveleditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.oetting.bumpingbunnies.leveleditor.viewer.Viewer;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws FileNotFoundException {
		try {
			addFilesToRuntime();
			openViewer(args);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Fehler", e);
			JOptionPane.showMessageDialog(null, "Could not open:\n" + e.getMessage());
		}
	}

	private static void addFilesToRuntime() throws Exception {
		addPath("files");
	}

	public static void addPath(String s) throws Exception {
		File f = new File(s);
		URI u = f.toURI();
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> urlClass = URLClassLoader.class;
		Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(urlClassLoader, new Object[] { u.toURL() });
	}

	private static void openViewer(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			new Viewer().display(args[0]);
		} else {
			String lastFile = loadLastFile();
			if (lastFile != null)
				new Viewer().display(lastFile);
			else {
				new Viewer().display();
			}
		}
	}

	private static String loadLastFile() {
		try {
			Properties properties = new Properties();
			File config = new File("config.properties");
			if (config.exists()) {
				properties.load(new FileInputStream(config));
				return properties.getProperty("file");
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
