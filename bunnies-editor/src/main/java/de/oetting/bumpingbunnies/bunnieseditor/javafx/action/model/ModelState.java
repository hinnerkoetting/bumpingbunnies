package de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Circle;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Shape;

public class ModelState implements EditorModel {

	private Optional<Shape> currentlyEditedObject;
	private final List<Circle> circles;

	public ModelState(ModelState currentState) {
		currentlyEditedObject =  currentState.currentlyEditedObject.isPresent() ? Optional.of(currentState.currentlyEditedObject.get().clone()) : Optional.empty();
		circles = new ArrayList<Circle>(currentState.circles);
	}

	public ModelState() {
		circles = new ArrayList<>();
		currentlyEditedObject = Optional.empty();
	}

	@Override
	public Optional<Shape> getCurrentlyEditedObject() {
		return currentlyEditedObject;
	}

	@Override
	public void setCurrentlyEditedObject(Optional<Shape> currentlyEditedObject) {
		this.currentlyEditedObject = currentlyEditedObject;
	}

	@Override
	public List<Circle> getAllCircles() {
		return Collections.unmodifiableList(circles);
	}

	public void addCircle(Circle circle) {
		circles.add(circle);
	}

}
