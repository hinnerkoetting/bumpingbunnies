package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;

public class OtherPlayerConfiguration implements Parcelable {

	private static final MyLog LOGGER = Logger
			.getLogger(OtherPlayerConfiguration.class);
	private final AbstractOtherPlayersFactory factory;
	private final int playerId;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.playerId);
		dest.writeString(this.factory.getClass().getName());
		this.factory.writeToParcel(dest, flags);
	}

	public OtherPlayerConfiguration(AbstractOtherPlayersFactory factory,
			int playerId) {
		this.factory = factory;
		this.playerId = playerId;
	}

	@SuppressWarnings("unchecked")
	public OtherPlayerConfiguration(Parcel in) {
		this.playerId = in.readInt();
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
		return this.playerId;
	}

}
