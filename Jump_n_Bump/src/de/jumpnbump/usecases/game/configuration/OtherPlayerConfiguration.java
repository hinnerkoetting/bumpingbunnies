package de.jumpnbump.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;

public class OtherPlayerConfiguration implements Parcelable {

	private static final MyLog LOGGER = Logger
			.getLogger(OtherPlayerConfiguration.class);
	private final AbstractOtherPlayersFactory factory;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.factory.getClass().getName());
		this.factory.writeToParcel(dest, flags);
	}

	public OtherPlayerConfiguration(AbstractOtherPlayersFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public OtherPlayerConfiguration(Parcel in) {
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

}
