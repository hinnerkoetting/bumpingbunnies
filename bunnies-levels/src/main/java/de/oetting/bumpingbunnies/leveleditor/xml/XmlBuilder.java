package de.oetting.bumpingbunnies.leveleditor.xml;

import java.io.InputStream;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.ClasspathImageReader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.ClasspathXmlreader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlConstants;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.PcWorldObjectsParser;

public class XmlBuilder {

	public World parse(InputStream is) {
		XmlReader reader = new ClasspathXmlreader(is);
		return new PcWorldObjectsParser().build(new SwingResourceProvider(new ClasspathImageReader()), reader);
	}

}
