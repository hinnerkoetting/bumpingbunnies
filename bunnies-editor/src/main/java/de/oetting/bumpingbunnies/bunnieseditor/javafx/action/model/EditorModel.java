package de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model;

import java.util.List;
import java.util.Optional;

import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Circle;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Shape;

public interface EditorModel {

	void setCurrentlyEditedObject(Optional<Shape> currentlyEditedObject);

	Optional<Shape> getCurrentlyEditedObject();

	List<Circle> getAllCircles();

}
