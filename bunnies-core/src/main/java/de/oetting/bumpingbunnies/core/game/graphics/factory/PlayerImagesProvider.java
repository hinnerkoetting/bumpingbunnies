package de.oetting.bumpingbunnies.core.game.graphics.factory;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public interface PlayerImagesProvider {

	List<ImageWrapper> loadAllRunningImages(int width, int heigth);

	List<ImageWrapper> loadAllFallingImages(int width, int heigth);

	List<ImageWrapper> loadAllJumpingImages(int width, int heigth);

	List<ImageWrapper> loadAllSittingImages(int width, int heigth);

	List<ImageWrapper> loadAllJumpingUpImages(int width, int heigth);
}
