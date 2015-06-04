package de.oetting.bumpingbunnies.worldcreator.load;

import java.io.InputStream;

public class ClasspathXmlreader implements XmlReader {

	private final InputStream stream;

	public ClasspathXmlreader(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public InputStream openXmlStream() {
		return stream;
	}

}
