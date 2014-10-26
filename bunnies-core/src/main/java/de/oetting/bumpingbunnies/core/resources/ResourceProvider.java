package de.oetting.bumpingbunnies.core.resources;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface ResourceProvider {

	ImageWrapper readBitmap(String fileName);

}
