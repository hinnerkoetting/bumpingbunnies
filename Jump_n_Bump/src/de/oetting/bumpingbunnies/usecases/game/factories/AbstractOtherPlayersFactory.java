package de.oetting.bumpingbunnies.usecases.game.factories;

import android.annotation.SuppressLint;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public abstract class AbstractOtherPlayersFactory implements Parcelable {

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	protected AbstractOtherPlayersFactory() {
	}

}
