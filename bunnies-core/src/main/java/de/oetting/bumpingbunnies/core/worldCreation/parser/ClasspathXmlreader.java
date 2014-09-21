package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.io.InputStream;

public class ClasspathXmlreader implements XmlReader {

	private final InputStream stream;

	public ClasspathXmlreader(InputStream stream) {
		super();
		this.stream = stream;
	}

	@Override
	public InputStream openXmlStream() {
		return stream;
	}

}
