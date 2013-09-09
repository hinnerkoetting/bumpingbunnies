package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public class XmlWorldBuilderTemplate implements WorldObjectsBuilder {
	private XmlWorldBuilder worldBuilder;

	public XmlWorldBuilderTemplate(int id) {
		this.worldBuilder = new XmlWorldBuilder(id);
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
	public void build(Context context) {
		this.worldBuilder.build(context);
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		return this.worldBuilder.createSpawnPoints();
	}

	@Override
	public Collection<Water> getAllWaters() {
		return this.worldBuilder.getAllWaters();
	}

}