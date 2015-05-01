package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.jumpnbump.usecases.viewer.viewer.PropertyEditorDialog;
import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class SelectAction implements MouseAction {

	private final SelectionModeProvider provider;
	private final CanvasObjectsFinder objectsFinder;
	private final CoordinatesCalculation coordinatesCalculation;
	private int startX = -1;
	private int startY = -1;

	public SelectAction(SelectionModeProvider provider, CanvasObjectsFinder objectsFinder,
			CoordinatesCalculation coordinatesCalculation) {
		this.provider = provider;
		this.objectsFinder = objectsFinder;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		if (firstClick()) {
			startX = e.getX();
			startY = e.getY();
		} else {
			List<GameObjectWithImage> allSelectedObjects = objectsFinder
					.findAllSelectedObjects(createSelectionRectangle(e));
			if (e.isControlDown()) {
				provider.addSelectedObjects(allSelectedObjects);
			} else {
				provider.setSelectedObjects(allSelectedObjects);
			}
		}
	}

	private SelectionRectangle createSelectionRectangle(MouseEvent e) {
		int minX = startX < e.getX() ? startX : e.getX();
		int maxX = startX > e.getX() ? startX : e.getX();
		int minY = startY > e.getY() ? startY : e.getY();
		int maxY = startY < e.getY() ? startY : e.getY();
		return new SelectionRectangle(coordinatesCalculation.getGameCoordinateX(minX),
				coordinatesCalculation.getGameCoordinateY(minY), coordinatesCalculation.getGameCoordinateX(maxX),
				coordinatesCalculation.getGameCoordinateY(maxY));
	}

	@Override
	public void onMousePressedFirst(MouseEvent event) {
		Optional<? extends GameObjectWithImage> go = objectsFinder.findClickedObject(
				coordinatesCalculation.getGameCoordinateX(event.getX()),
				coordinatesCalculation.getGameCoordinateY(event.getY()));
		if (event.isControlDown()) {
			provider.addSelectedObject(go);
		} else {
			provider.setSelectedObject(go);
		}
	}

	private boolean firstClick() {
		return startX == -1 && startY == -1;
	}

	private List<? extends GameObjectWithImage> findObjects(MouseEvent e) {
		return provider.getCurrentSelectedObjects();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
		if (!provider.getCurrentSelectedObjects().isEmpty()) {
			JPopupMenu menu = new JPopupMenu();
			menu.add(createToFrontItem(event));
			menu.add(createOneUpItem(event));
			menu.add(createOneDownItem(event));
			menu.add(createToBackItem(event));
			menu.addSeparator();
			menu.add(createPropertyItem(event));
			menu.show(provider.getCanvas(), event.getX(), event.getY());
		}
	}

	private JMenuItem createPropertyItem(MouseEvent event) {
		JMenuItem item = new JMenuItem("Properties");
		item.addActionListener((actionVent) -> showProperties(event));
		return item;
	}

	private void showProperties(MouseEvent event) {
		provider.storeCurrentState();
		List<? extends GameObjectWithImage> objects = findObjects(event);
		for (GameObjectWithImage go : objects) {
			PropertyEditorDialog dialog = new PropertyEditorDialog(provider.getFrame(), go);
			dialog.show();
		}
		provider.refreshView();
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
	}

	private void itemDown(MouseEvent event) {
		move(event, (object, list) -> moveDown(object, list));
	}

	private <S extends GameObjectWithImage> void moveUp(GameObjectWithImage object, List<S> list) {
		int index = list.indexOf(object);
		if (index < list.size() - 1) {
			S wall = list.remove(index);
			list.add(index + 1, wall);
			switchIndex(object, list, index);
			applyZIndexByListOrder(list);
		}
	}

	private <S extends GameObjectWithImage> void moveDown(GameObjectWithImage object, List<S> list) {
		int index = list.indexOf(object);
		if (index > 0) {
			S wall = list.remove(index);
			list.add(index - 1, wall);
			switchIndex(object, list, index);
			applyZIndexByListOrder(list);
		}
	}

	private <S extends GameObjectWithImage> void switchIndex(GameObjectWithImage object, List<S> list, int index) {
		int tempIndex = list.get(index).getzIndex();
		list.get(index).setzIndex(object.getzIndex());
		object.setzIndex(tempIndex);
	}

	private void itemToFront(MouseEvent event) {
		move(event, (object, list) -> moveToFront(object, list));
	}

	private void itemToBack(MouseEvent event) {
		move(event, (object, list) -> moveToBack(object, list));
	}

	private <S extends GameObjectWithImage> void moveToBack(GameObjectWithImage object, List<S> list) {
		if (list.indexOf(object) != 0) {
			list.remove(object);
			list.add(0, (S) object);
			provider.getAllDrawingObjects().stream().forEach((element) -> element.setzIndex(element.getzIndex() + 1));
			object.setzIndex(0);
			applyZIndexByListOrder(list);
		}
	}

	private <S> void moveToFront(GameObjectWithImage object, List<S> list) {
		if (list.indexOf(object) != list.size() - 1) {
			list.remove(object);
			list.add(list.size(), (S) object);
			provider.getAllDrawingObjects().stream().forEach((element) -> element.setzIndex(element.getzIndex() - 1));
			object.setzIndex(provider.getAllDrawingObjects().size() - 1);
			applyZIndexByListOrder(list);
		}
	}

	private void applyZIndexByListOrder(List list) {
		for (int i = 0; i < list.size(); i++) {
			GameObjectWithImage object = (GameObjectWithImage) list.get(i);
			object.setzIndex(i);
		}
	}

	private void move(MouseEvent event, MoveAction action) {
		provider.storeCurrentState();
		List<? extends GameObjectWithImage> objects = findObjects(event);
		objects.forEach(go -> moveExistingObject(go, action));
		provider.refreshView();
	}

	private void moveExistingObject(GameObjectWithImage go, MoveAction action) {
		World container = provider.getWorld();
		moveIfContains(go, container.getAllDrawingObjects(), action);
	}

	public <S extends GameObjectWithImage> void moveIfContains(GameObjectWithImage object, List<S> list,
			MoveAction action) {
		if (list.contains(object))
			action.moveObject(object, list);
	}

	public interface MoveAction {
		void moveObject(GameObjectWithImage object, List<? extends GameObjectWithImage> list);
	}

}
