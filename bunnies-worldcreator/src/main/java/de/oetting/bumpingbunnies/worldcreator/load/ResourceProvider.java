package de.oetting.bumpingbunnies.worldcreator.load;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface ResourceProvider {

	ImageWrapper readBitmap(String fileName);

}
