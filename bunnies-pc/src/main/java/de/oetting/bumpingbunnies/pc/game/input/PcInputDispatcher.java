package de.oetting.bumpingbunnies.pc.game.input;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;

public class PcInputDispatcher {

	private final List<ConfigurableKeyboardInputService> inputServices;
	private final Node canvas;
	private final IngameMenu ingameMenu;

	public PcInputDispatcher(Node canvas, IngameMenu ingameMenu) {
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
		ContextMenu menu = new ContextMenu();
		MenuItem itemAdd = new MenuItem("+AI");
		itemAdd.addEventHandler(javafx.event.Event.ANY, event -> addAi());
		MenuItem itemRemove = new MenuItem("Remove AI");
		menu.getItems().add(itemAdd);
		menu.getItems().add(itemRemove);
		showRelativeToWindow(menu);
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
