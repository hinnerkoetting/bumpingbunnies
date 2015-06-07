package de.oetting.bumpingbunnies.usecases.resultScreen.model;

import de.oetting.bumpingbunnies.IntegerComparator;
import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class ResultPlayerEntry implements Parcelable, Comparable<ResultPlayerEntry> {

	private final String playerName;
	private final int playerScore;
	private final int playerColor;

	public ResultPlayerEntry(String playerName, int playerScore, int playerColor) {
		this.playerName = playerName;
		this.playerScore = playerScore;
		this.playerColor = playerColor;
	}

	public ResultPlayerEntry(Parcel in) {
		this.playerName = in.readString();
		this.playerScore = in.readInt();
		this.playerColor = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.playerName);
		dest.writeInt(this.playerScore);
		dest.writeInt(this.playerColor);
	}


	public String getPlayerName() {
		return this.playerName;
	}

	public int getPlayerScore() {
		return this.playerScore;
	}

	public int getPlayerColor() {
		return this.playerColor;
	}

	@Override
	public int compareTo(ResultPlayerEntry o) {
		return IntegerComparator.compareInt(o.playerScore, playerScore);
	}

}
