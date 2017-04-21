
package redit.com.redditshow.network.reply.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

	@SerializedName("source")
	@Expose
	public Source source;
	@SerializedName("variants")
	@Expose
	public Variants variants;
	@SerializedName("id")
	@Expose
	public String id;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.source, flags);
		dest.writeParcelable(this.variants, flags);
		dest.writeString(this.id);
	}

	public Image() {
	}

	protected Image(Parcel in) {
		this.source = in.readParcelable(Source.class.getClassLoader());
		this.variants = in.readParcelable(Variants.class.getClassLoader());
		this.id = in.readString();
	}

	public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
		@Override
		public Image createFromParcel(Parcel source) {
			return new Image(source);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};
}
