package de.oetting.bumpingbunnies.android.xml.parsing;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import android.content.Context;
import de.oetting.bumpingbunnies.worldcreator.load.WorldZipReader;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;

public class AndroidXmlReader implements XmlReader {

	private final Context context;
	private final int resourceId;

	public AndroidXmlReader(Context context, int resourceId) {
		this.context = context;
		this.resourceId = resourceId;
	}

	@Override
	public InputStream openXmlStream() {
		InputStream inputStream = context.getResources().openRawResource(this.resourceId);
		return new WorldZipReader().findWorldXml(new ZipInputStream(inputStream));
	}

}
