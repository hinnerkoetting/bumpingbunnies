package de.oetting.bumpingbunnies.pc.graphics.imageTest;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.PcImagesColoror;

public class GraphicsTesterController implements Initializable {

	@FXML
	private ImageView imageView;
	private Image sourceImage;
	@FXML
	private TextField colorTextfield;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sourceImage = new Image(getClass().getResourceAsStream("/drawable/v1d_down_1.png"));
		imageView.setImage(sourceImage);
	}

	@FXML
	public void onButtonColor() {
		long color = (int) Long.parseLong(colorTextfield.getText(), 16);
		if ((int) color != color) {
			throw new IllegalArgumentException("Number is too big");
		}
		ImageWrapper coloredImage = new PcImagesColoror().colorImage(new ImageWrapper(sourceImage), (int) color);
		imageView.setImage((Image) coloredImage.getBitmap());
	}
}
