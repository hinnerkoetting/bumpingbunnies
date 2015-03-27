package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ClasspathZipreader implements XmlReader {

	private final InputStream stream;

	public ClasspathZipreader(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public InputStream openXmlStream() {
		return new WorldZipReader().findWorldXml(new ZipInputStream(stream));
	}

}
