package de.oetting.bumpingbunnies.pc.game.input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PcInputDispatcher {

	private final List<ConfigurableKeyboardInputService> inputServices;
	private final Node canvas;
	private final IngameMenu ingameMenu;
	private final World world;

	public PcInputDispatcher(Node canvas, IngameMenu ingameMenu, World world) {
		this.world = world;
		this.inputServices = new ArrayList<>();
		this.canvas = canvas;
		this.ingameMenu = ingameMenu;
	}

	public void dispatchOnKeyDown(KeyCode keyCode) {
		if (keyCode.equals(KeyCode.ENTER)) {
			openContextMenu();
		}
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyDown(keyCode.getName());
	}

	private void openContextMenu() {
		ContextMenu menu = createMenu();
		showRelativeToWindow(menu);
	}

	private ContextMenu createMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createAddAiButton());
		menu.getItems().add(new MenuItem("------"));
		for (Bunny bunny : getAllAis()) {
			MenuItem itemRemove = createRemoveAiButton(bunny);
			menu.getItems().add(itemRemove);
		}
		return menu;
	}

	private MenuItem createRemoveAiButton(Bunny bunny) {
		MenuItem itemRemove = new MenuItem("Remove " + bunny.getName());
		itemRemove.addEventHandler(Event.ANY, event -> ingameMenu.removePlayer(bunny));
		return itemRemove;
	}

	private MenuItem createAddAiButton() {
		MenuItem itemAdd = new MenuItem("+AI");
		itemAdd.addEventHandler(javafx.event.Event.ANY, event -> addAi());
		return itemAdd;
	}

	private List<Bunny> getAllAis() {
		return world.getAllConnectedBunnies().stream().filter(bunny -> bunny.getOpponent().isAi())
				.collect(Collectors.toList());
	}

	private void addAi() {
		ingameMenu.onAddAiOption();
	}

	private void showRelativeToWindow(ContextMenu menu) {
		Window window = canvas.getScene().getWindow();
		menu.show(canvas, window.getX(), window.getY());
	}

	public void dispatchOnKeyUp(KeyCode keyCode) {
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyUp(keyCode.getName());
	}

	public void addInputService(ConfigurableKeyboardInputService inputService) {
		inputServices.add(inputService);
	}
}
