package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;

public class OtherPlayerConfiguration implements Parcelable {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OtherPlayerConfiguration.class);
	private final AbstractOtherPlayersFactory factory;
	private final PlayerProperties otherPlayerState;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		this.otherPlayerState.writeToParcel(dest, flags);
		dest.writeString(this.factory.getClass().getName());
		this.factory.writeToParcel(dest, flags);
	}

	public OtherPlayerConfiguration(AbstractOtherPlayersFactory factory,
			PlayerProperties otherPlayerState) {
		this.factory = factory;
		this.otherPlayerState = otherPlayerState;
	}

	@SuppressWarnings("unchecked")
	public OtherPlayerConfiguration(Parcel in) {
		this.otherPlayerState = new PlayerProperties(in);
		String strClazz = in.readString();
		try {

			Class<? extends AbstractOtherPlayersFactory> clazz = (Class<? extends AbstractOtherPlayersFactory>) Class
					.forName(strClazz, false, getClass().getClassLoader());
			this.factory = clazz.getConstructor(Parcel.class).newInstance(in);
		} catch (Exception e) {
			LOGGER.error("error for %s", strClazz);
			throw new RuntimeException(e);
		}
	}

	public AbstractOtherPlayersFactory getFactory() {
		return this.factory;
	}

	public int getPlayerId() {
		return this.otherPlayerState.getPlayerId();
	}

}
