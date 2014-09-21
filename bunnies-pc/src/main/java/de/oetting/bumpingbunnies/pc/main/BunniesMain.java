package de.oetting.bumpingbunnies.pc.main;

import java.util.ArrayList;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.ClasspathXmlreader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;
import de.oetting.bumpingbunnies.pc.graphics.YCoordinateInverterCalculation;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.NoopResourceProvider;
import de.oetting.bumpingbunnies.pc.worldcreation.parser.PcWorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;
import de.oetting.bumpingbunnies.world.WorldProperties;

public class BunniesMain {

	public static void main(String[] args) {
		World world = createWorld();
		WorldProperties worldProperties = new WorldProperties(ModelConstants.STANDARD_WORLD_SIZE, ModelConstants.STANDARD_WORLD_SIZE);
		CoordinatesCalculation coordinatesCalculation = new YCoordinateInverterCalculation(new AbsoluteCoordinatesCalculation(800, 600, worldProperties));
		coordinatesCalculation.updateCanvas(800, 600);
		GameThread gamethread = createGameThread(world, coordinatesCalculation);
		BunniesApplication.setWorld(world);
		BunniesApplication.setCoordinatesCalculation(coordinatesCalculation);
		BunniesApplication app = new BunniesApplication();
		gamethread.start();
		startApplication(app);
	}

	private static World createWorld() {
		XmlReader reader = new ClasspathXmlreader(World.class.getResourceAsStream("/worlds/classic.xml"));
		return new PcWorldObjectsParser().build(new NoopResourceProvider(), reader);
	}

	private static GameThread createGameThread(World world, CoordinatesCalculation coordinatesCalculation) {

		Configuration configuration = createConfiguration();
		return new GameThreadFactory().create(coordinatesCalculation, world, new NoopGameStopper(), configuration,
				new PlayerFactory(1).createPlayer(1, "local", new Opponent("", OpponentType.MY_PLAYER)));

	}

	private static Configuration createConfiguration() {
		LocalSettings localSettings = new LocalSettings(InputConfiguration.DISTRIBUTED_KEYBOARD, 1, true, false);
		GeneralSettings generalSettings = new GeneralSettings(WorldConfiguration.CASTLE, 1, NetworkType.WLAN);
		LocalPlayerSettings localPlayerSettings = new LocalPlayerSettings("local");
		Configuration configuration = new Configuration(localSettings, generalSettings, new ArrayList<>(), localPlayerSettings, true);
		return configuration;
	}

	private static void startApplication(BunniesApplication app) {
		new Thread(app).start();
	}

}
