package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public class XmlWorldParserTemplate implements WorldObjectsParser {
	private XmlWorldParser worldBuilder;

	public XmlWorldParserTemplate(int resourceId) {
		this.worldBuilder = new XmlWorldParser(resourceId);
	}

	@Override
	public Collection<Wall> getAllWalls() {
		return this.worldBuilder.getAllWalls();
	}

	@Override
	public Collection<IcyWall> getAllIcyWalls() {
		return this.worldBuilder.getAllIcyWalls();
	}

	@Override
	public Collection<Jumper> getAllJumpers() {
		return this.worldBuilder.getAllJumpers();
	}

	@Override
	public World build(ResourceProvider provider, XmlReader reader) {
		return this.worldBuilder.build(provider, reader);
	}

	@Override
	public List<SpawnPoint> getAllSpawnPoints() {
		return this.worldBuilder.getAllSpawnPoints();
	}

	@Override
	public Collection<Water> getAllWaters() {
		return this.worldBuilder.getAllWaters();
	}

	@Override
	public int getResourceId() {
		return worldBuilder.getResourceId();
	}

}