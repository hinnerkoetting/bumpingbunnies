package de.oetting.bumpingbunnies.pc.music;

import static org.junit.Assert.fail;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactoryTest;

public class PcMusicPlayerFactoryTest extends BunniesMusicPlayerFactoryTest implements ThreadErrorCallback {

	@Override
	protected BunniesMusicPlayerFactory createFactory() {
		return new PcMusicPlayerFactory(this);
	}

	@Override
	public void onThreadError() {
		fail();
	}

}
