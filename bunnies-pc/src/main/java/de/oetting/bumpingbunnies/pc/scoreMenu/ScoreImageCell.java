package de.oetting.bumpingbunnies.pc.scoreMenu;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcImagesColoror;

public class ScoreImageCell extends TableCell<ScoreEntry, Integer> {

	private final ImagesColorer coloror;
	private ImageView imageView;
	private ImageWrapper originalImageWrapper;

	public ScoreImageCell() {
		coloror = new PcImagesColoror();
		VBox box = new VBox();
		setGraphic(box);
		InputStream image = new BunnyImagesReader().loadOneImage();
		imageView = new ImageView(new Image(image));
		imageView.setFitHeight(25);
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(imageView);

		InputStream imageInputstream = new BunnyImagesReader().loadOneImage();
		Image fxImage = new Image(imageInputstream, 25, 25, true, true);
		originalImageWrapper = new ImageWrapper(fxImage, "dummy");
	}

	@Override
	protected void updateItem(Integer color, boolean empty) {
		if (color != null) {
			ImageWrapper coloredImage = colorImage(color);
			imageView.setImage((Image) coloredImage.getBitmap());
		} else {
			imageView.setImage(null);
		}
	}

	private ImageWrapper colorImage(Integer color) {
		ImageWrapper coloredImage = coloror.colorImage(originalImageWrapper, color);
		return coloredImage;
	}
}
