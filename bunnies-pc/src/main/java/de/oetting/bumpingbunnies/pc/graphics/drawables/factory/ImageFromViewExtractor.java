package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class ImageFromViewExtractor {

	public ImageWrapper extractToWrapper(ImageView imageView) {
		return new ImageWrapper(extract(imageView), "");
	}

	public Image extract(ImageView imageView) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		return imageView.snapshot(params, null);
	}
	
	public Image takeSnapshot(Canvas fxCanvas) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		WritableImage writableImage = new WritableImage((int)fxCanvas.getWidth(),
				(int)fxCanvas.getHeight());
		fxCanvas.snapshot(params, writableImage);
		return writableImage;
	}
}
