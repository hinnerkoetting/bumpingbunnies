import java.io.FileNotFoundException;

import de.jumpnbump.usecases.viewer.Viewer.Viewer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			new Viewer().display(args[0]);
		} else {
			new Viewer().display();
		}
	}
}
