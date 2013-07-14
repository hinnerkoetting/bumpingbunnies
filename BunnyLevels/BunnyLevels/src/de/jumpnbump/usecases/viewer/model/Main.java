package de.jumpnbump.usecases.viewer.model;

import java.io.InputStream;

import de.jumpnbump.usecases.viewer.Viewer;
import de.jumpnbump.usecases.viewer.xml.ObjectContainer;
import de.jumpnbump.usecases.viewer.xml.XmlBuilder;

public class Main {

	public static void main(String args[]) throws InterruptedException {
		new Main().parse();
	}

	public void parse() throws InterruptedException {
		InputStream is = getClass().getResourceAsStream("/test_world.xml");
		ObjectContainer parse = new XmlBuilder().parse(is);
		new Viewer().display(parse);
		System.out.print(parse);
	}
}
