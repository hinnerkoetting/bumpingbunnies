package de.oetting.bumpingbunnies.worldcreatorPc.load;

import java.io.InputStream;

import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathImageReader;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathXmlreader;
import de.oetting.bumpingbunnies.worldcreator.load.SwingResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;

public class XmlBuilder {

	public World parse(InputStream is) {
		XmlReader reader = new ClasspathXmlreader(is);
		return new PcWorldObjectsParser().build(new SwingResourceProvider(new ClasspathImageReader()), reader);
	}

}
