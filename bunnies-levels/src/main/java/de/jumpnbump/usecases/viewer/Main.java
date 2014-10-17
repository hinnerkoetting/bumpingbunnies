package de.jumpnbump.usecases.viewer;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.jumpnbump.usecases.viewer.Viewer.Viewer;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws FileNotFoundException {
		try {
			openViewer(args);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Fehler", e);
			JOptionPane.showMessageDialog(null, "Could not open:\n" + e.getMessage());
		}
	}

	private static void openViewer(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			new Viewer().display(args[0]);
		} else {
			new Viewer().display();
		}
	}
}
