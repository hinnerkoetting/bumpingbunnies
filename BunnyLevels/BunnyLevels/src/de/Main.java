package de;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.jumpnbump.usecases.viewer.Viewer;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;
import de.jumpnbump.usecases.viewer.xml.XmlBuilder;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File(args[0]);
		InputStream is = new FileInputStream(f);
		ObjectContainer parse = new XmlBuilder().parse(is);
		new Viewer().display(parse);
	}
}
