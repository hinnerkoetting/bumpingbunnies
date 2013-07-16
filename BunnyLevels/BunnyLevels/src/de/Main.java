package de;

import java.io.FileNotFoundException;

import de.jumpnbump.usecases.viewer.Viewer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		new Viewer().display(args[0]);
	}
}
