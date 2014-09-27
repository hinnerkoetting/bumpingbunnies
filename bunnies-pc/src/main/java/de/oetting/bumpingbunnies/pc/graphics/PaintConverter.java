package de.oetting.bumpingbunnies.pc.graphics;

import java.awt.Color;

import javafx.scene.paint.Paint;

public class PaintConverter {

	public Paint convert(de.oetting.bumpingbunnies.core.graphics.Paint original) {
		Color awtColor = new Color(original.getColor());
		javafx.scene.paint.Color paint = new javafx.scene.paint.Color(awtColor.getRed() / 256.0, awtColor.getGreen() / 256.0, awtColor.getBlue() / 256.0,
				original.getAlpha() / 256.0);
		return paint;
	}
}
