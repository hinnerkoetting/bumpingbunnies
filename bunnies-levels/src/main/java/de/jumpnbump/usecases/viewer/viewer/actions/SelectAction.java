package de.jumpnbump.usecases.viewer.viewer.actions;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.jumpnbump.usecases.viewer.viewer.PropertyEditorDialog;
import de.jumpnbump.usecases.viewer.viewer.editingMode.SelectionModeProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class SelectAction implements MouseAction {

	private final SelectionModeProvider provider;
	private final CanvasObjectsFinder objectsFinder;

	public SelectAction(SelectionModeProvider provider, CanvasObjectsFinder objectsFinder) {
		this.provider = provider;
		this.objectsFinder = objectsFinder;
	}

	@Override
	public void newMousePosition(MouseEvent e) {
		Optional<? extends GameObjectWithImage> go = objectsFinder.findClickedObject(e);
		provider.setSelectedObject(go);
	}

	private Optional<? extends GameObjectWithImage> findObject(MouseEvent e) {
		return provider.getCurrentSelectedObject();
	}

	@Override
	public void rightMouseClick(MouseEvent event) {
		if (provider.getCurrentSelectedObject().isPresent()) {
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
		Optional<? extends GameObjectWithImage> go = findObject(event);
		if (go.isPresent()) {
			PropertyEditorDialog dialog = new PropertyEditorDialog(provider.getFrame(), go.get());
			dialog.show();
			provider.refreshAll();
		}
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
		Optional<? extends GameObjectWithImage> go = findObject(event);
		go.ifPresent((object) -> moveExistingObject(object, action));
		provider.repaintCanvas();
		provider.refreshTables();
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
