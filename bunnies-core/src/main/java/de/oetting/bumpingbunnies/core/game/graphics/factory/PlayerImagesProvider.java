package de.oetting.bumpingbunnies.core.game.graphics.factory;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public interface PlayerImagesProvider {

	List<BunnyImage> loadAllRunningImages(int width, int heigth);

	List<BunnyImage> loadAllFallingImages(int width, int heigth);

	List<BunnyImage> loadAllJumpingImages(int width, int heigth);

	List<BunnyImage> loadAllSittingImages(int width, int heigth);

	List<BunnyImage> loadAllJumpingUpImages(int width, int heigth);
	
	ImageWrapper loadOneImage(int width, int heigth);
	
	ImageWrapper readImageForBunny(Bunny bunny);
}
