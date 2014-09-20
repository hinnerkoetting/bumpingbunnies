package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.io.InputStream;

import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import android.content.Context;

public class AndroidXmlReader implements XmlReader {

	private final Context context;
	private final int resourceId;

	public AndroidXmlReader(Context context, int resourceId) {
		super();
		this.context = context;
		this.resourceId = resourceId;
	}

	@Override
	public InputStream openXmlStream() {
		return context.getResources().openRawResource(this.resourceId);
	}

}
