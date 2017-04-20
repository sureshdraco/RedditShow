
package redit.com.redditshow.network.reply.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Variants implements Parcelable {
	public Image gif;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.gif, flags);
	}

	public Variants() {
	}

	protected Variants(Parcel in) {
		this.gif = in.readParcelable(Image.class.getClassLoader());
	}

	public static final Creator<Variants> CREATOR = new Creator<Variants>() {
		@Override
		public Variants createFromParcel(Parcel source) {
			return new Variants(source);
		}

		@Override
		public Variants[] newArray(int size) {
			return new Variants[size];
		}
	};
}
