package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class SelectAction implements MouseAction {

	private final MyCanvas canvas;
	private final World container;
	private final CoordinatesCalculation coordinatesCalculation;

	public SelectAction(MyCanvas canvas, World container, CoordinatesCalculation coordinatesCalculation) {
		super();
		this.canvas = canvas;
		this.container = container;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void newMousePosition(MouseEvent e) {
		Optional<GameObjectWithImage> go = findObject(e);
		if (go.isPresent())
			this.canvas.setSelectedObject(go.get());
		else
			this.canvas.setSelectedObject(null);
		this.canvas.repaint();
	}

	private Optional<GameObjectWithImage> findObject(MouseEvent e) {
		long gameX = this.coordinatesCalculation.getGameCoordinateX((e.getX()));
		long gameY = this.coordinatesCalculation.getGameCoordinateY((e.getY()));
		return findGameObject(gameX, gameY);
	}

	private Optional<GameObjectWithImage> findGameObject(long gameX, long gameY) {
		return this.container.getAllDrawingObjects().stream().filter((object) -> isSelected(object, gameX, gameY)).findFirst();
	}

	private boolean isSelected(GameObjectWithImage go, long gameX, long gameY) {
		return go.minX() < gameX && go.maxX() > gameX && go.minY() < gameY && go.maxY() > gameY;
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(createToFrontItem(event));
		menu.add(createOneUpItem(event));
		menu.add(createOneDownItem(event));
		menu.add(createToBackItem(event));
		menu.show(canvas, event.getX(), event.getY());
	}

	private JMenuItem createOneUpItem(MouseEvent event) {
		JMenuItem item = new JMenuItem("One up");
		item.addActionListener((e) -> itemUp(event));
		return item;
	}

	private JMenuItem createOneDownItem(MouseEvent event) {
		JMenuItem item = new JMenuItem("One down");
		item.addActionListener((e) -> itemDown(event));
		return item;
	}

	private JMenuItem createToBackItem(MouseEvent event) {
		JMenuItem item = new JMenuItem("To back");
		item.addActionListener((e) -> itemToBack(event));
		return item;
	}

	private JMenuItem createToFrontItem(MouseEvent event) {
		JMenuItem item = new JMenuItem("To front");
		item.addActionListener((e) -> itemToFront(event));
		return item;
	}

	private void itemUp(MouseEvent event) {
		move(event, (object, list) -> moveUp(object, list));
		canvas.repaint();
	}

	private void itemDown(MouseEvent event) {
		move(event, (object, list) -> moveDown(object, list));
		canvas.repaint();
	}

	private <S> void moveUp(GameObject object, List<S> list) {
		int index = list.indexOf(object);
		if (index < list.size() - 1) {
			S wall = list.remove(index);
			list.add(index + 1, wall);
		}
	}

	private <S> void moveDown(GameObject object, List<S> list) {
		int index = list.indexOf(object);
		if (index > 0) {
			S wall = list.remove(index);
			list.add(index - 1, wall);
		}
	}

	private void itemToFront(MouseEvent event) {
		move(event, (object, list) -> moveToFront(object, list));
		canvas.repaint();
	}

	private void itemToBack(MouseEvent event) {
		move(event, (object, list) -> moveToBack(object, list));
		canvas.repaint();
	}

	private <S> void moveToBack(GameObject object, List<S> list) {
		list.remove(object);
		list.add(0, (S) object);
	}

	private <S> void moveToFront(GameObject object, List<S> list) {
		list.remove(object);
		list.add(list.size(), (S) object);
	}

	private void move(MouseEvent event, MoveAction action) {
		Optional<GameObjectWithImage> go = findObject(event);
		go.ifPresent((object) -> moveExistingObject(object, action));
	}

	private void moveExistingObject(GameObject go, MoveAction action) {
		moveIfContains(go, container.getAllIcyWalls(), action);
		moveIfContains(go, container.getAllJumper(), action);
		moveIfContains(go, container.getAllWalls(), action);
		moveIfContains(go, container.getAllWaters(), action);
		moveIfContains(go, container.getBackgrounds(), action);
	}

	public <S extends GameObject> void moveIfContains(GameObject object, List<S> list, MoveAction action) {
		if (list.contains(object))
			action.moveObject(object, list);
	}

	public static interface MoveAction {
		void moveObject(GameObject object, List<? extends GameObject> list);
	}
}
