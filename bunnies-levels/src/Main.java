import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import de.jumpnbump.usecases.viewer.Viewer.Viewer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			openViewer(args);
		} catch (Exception e) {
			e.printStackTrace();
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
