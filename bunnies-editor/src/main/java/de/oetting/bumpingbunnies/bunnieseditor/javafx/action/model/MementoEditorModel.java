package de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Circle;
import de.oetting.bumpingbunnies.bunnieseditor.javafx.action.model.shapes.Shape;

public class MementoEditorModel implements EditorModel {

	private final Deque<ModelState> statesStack = new LinkedList<ModelState>();

	public MementoEditorModel() {
		add(new ModelState());
	}

	private void add(ModelState modelState) {
		statesStack.addLast(modelState);
	}

	public void setCurrentlyEditedObject(Optional<Shape> currentlyEditedObject) {
		pushStack();
		getCurrentState().setCurrentlyEditedObject(currentlyEditedObject);
	}

	private void pushStack() {
		ModelState newState = new ModelState(getCurrentState());
		statesStack.addLast(newState);
	}

	public Optional<Shape> getCurrentlyEditedObject() {
		return getCurrentState().getCurrentlyEditedObject();
	}

	private ModelState getCurrentState() {
		return statesStack.getLast();
	}
	
	@Override
	public List<Circle> getAllCircles() {
		return getCurrentState().getAllCircles();
	}

	public void addCircle(Circle circle) {
		pushStack();
		getCurrentState().addCircle(circle);
	}
}
