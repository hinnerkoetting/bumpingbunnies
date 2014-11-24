package de.jumpnbump.usecases.viewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.jumpnbump.usecases.viewer.viewer.Viewer;

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
			new Viewer().display();
		}
	}
}
