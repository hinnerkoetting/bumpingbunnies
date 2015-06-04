package de.oetting.bumpingbunnies.pc.game.input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class PcInputDispatcher {

	private final List<ConfigurableKeyboardInputService> inputServices;
	private final Node canvas;
	private final IngameMenu ingameMenu;
	private final World world;
	private final GameStartParameter parameter;

	public PcInputDispatcher(Node canvas, IngameMenu ingameMenu, World world, GameStartParameter parameter) {
		this.world = world;
		this.parameter = parameter;
		this.inputServices = new ArrayList<>();
		this.canvas = canvas;
		this.ingameMenu = ingameMenu;
	}

	public void dispatchOnKeyDown(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE))
			openContextMenu();
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyDown(event.getCode().getName());
	}

	private void openContextMenu() {
		ContextMenu menu = createMenu();
		showRelativeToWindow(menu);
	}

	private ContextMenu createMenu() {
		ContextMenu menu = new ContextMenu();
		if (parameter.getConfiguration().isHost())
			addOptionsForHost(menu);
		menu.getItems().add(createQuitIem());
		return menu;
	}

	private MenuItem createQuitIem() {
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.addEventHandler(Event.ANY, event -> ingameMenu.onQuitGame());
		return quitItem;
	}

	private void addOptionsForHost(ContextMenu menu) {
		menu.getItems().add(createAddAiButton());
		MenuItem separator = new MenuItem("------");
		separator.setDisable(true);
		menu.getItems().add(separator);
		for (Bunny bunny : getAllAis()) {
			MenuItem itemRemove = createRemoveAiButton(bunny);
			menu.getItems().add(itemRemove);
		}
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
		menu.show(canvas, window.getX(), window.getY() + 25);
	}

	public void dispatchOnKeyUp(KeyCode keyCode) {
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyUp(keyCode.getName());
	}

	public void addInputService(ConfigurableKeyboardInputService inputService) {
		inputServices.add(inputService);
	}
}
