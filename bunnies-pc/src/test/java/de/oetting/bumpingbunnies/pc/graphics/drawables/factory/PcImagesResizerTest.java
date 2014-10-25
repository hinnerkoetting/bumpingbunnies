package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.PcResourceProvider;

public class PcImagesResizerTest {

	// Does not work because fx is missing
	@Ignore
	@Test
	public void resize_always_imageSizeIsEqualToTargetsie() {
		ImageWrapper image = new PcResourceProvider(null, null).readBitmap("wiese1");
		ImageWrapper resizedImage = new PcImagesResizer().resize(image, 5, 5);
		assertThat(resizedImage.getBitmap(), allOf(hasProperty("width"), is(5.0), hasProperty("height"), is(5.0)));
	}

}
