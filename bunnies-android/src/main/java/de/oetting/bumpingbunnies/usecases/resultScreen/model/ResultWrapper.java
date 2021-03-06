package de.oetting.bumpingbunnies.usecases.resultScreen.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ResultWrapper implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(ResultWrapper.class);
	public static final Parcelable.Creator<ResultWrapper> CREATOR = new Parcelable.Creator<ResultWrapper>() {
		@Override
		public ResultWrapper createFromParcel(Parcel source) {
			try {
				return new ResultWrapper(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading result entry %s", e,
						source.toString());

				throw e;
			}
		}

		@Override
		public ResultWrapper[] newArray(int size) {
			return new ResultWrapper[size];
		}
	};

	private final List<ResultPlayerEntry> results;

	public ResultWrapper(List<ResultPlayerEntry> results) {
		super();
		this.results = results;
	}

	public ResultWrapper(Parcel in) {
		int number = in.readInt();
		this.results = new ArrayList<ResultPlayerEntry>(number);
		for (int i = 0; i < number; i++) {
			ResultPlayerEntry entry = new ResultPlayerEntry(in);
			this.results.add(entry);
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.results.size());
		for (int i = 0; i < this.results.size(); i++) {
			this.results.get(i).writeToParcel(dest, flags);
		}
	}

	public List<ResultPlayerEntry> getResults() {
		return this.results;
	}

}
