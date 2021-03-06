package de.oetting.bumpingbunnies.model.game.world;

import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class WorldProperties {

	private final long width;
	private final long height;

	public WorldProperties() {
		this.width = ModelConstants.STANDARD_WORLD_SIZE;
		this.height = ModelConstants.STANDARD_WORLD_SIZE;
	}

	public WorldProperties(long width, long height) {
		super();
		this.width = width;
		this.height = height;
	}

	public long getWorldWidth() {
		return this.width;
	}

	public long getWorldHeight() {
		return this.height;
	}
}
