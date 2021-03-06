package de.oetting.bumpingbunnies.model.game.world;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Rect;

public class Segment<S extends GameObject> {

	private final Rect rectangle;
	private final List<S> objectsInThisSegment;

	public Segment(Rect rectangle) {
		this.rectangle = rectangle;
		objectsInThisSegment = new ArrayList<S>();
	}

	public void addObjects(List<? extends S> objects) {
		for (S object : objects) {
			if (matchesWidthPartially(object) && matchesHeightPartially(object))
				objectsInThisSegment.add(object);
		}
	}

	private boolean matchesWidthPartially(GameObject object) {
		return object.maxX() >= rectangle.getMinX() && object.minX() <= rectangle.getMaxX();
	}

	private boolean matchesHeightPartially(GameObject object) {
		return object.maxY() >= rectangle.getMinY() && object.minY() <= rectangle.getMaxY();
	}

	public List<S> getObjectsInSegment() {
		return objectsInThisSegment;
	}

	public long getMinX() {
		return rectangle.getMinX();
	}

	public long getMaxX() {
		return rectangle.getMaxX();
	}

	public long getMinY() {
		return rectangle.getMinY();
	}

	public long getMaxY() {
		return rectangle.getMaxY();
	}
	
	

}
