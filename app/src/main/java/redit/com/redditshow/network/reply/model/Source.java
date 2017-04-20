
package redit.com.redditshow.network.reply.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source implements Parcelable {

	@SerializedName("url")
	@Expose
	public String url;
	@SerializedName("width")
	@Expose
	public int width;
	@SerializedName("height")
	@Expose
	public int height;
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.url);
		dest.writeInt(this.width);
		dest.writeInt(this.height);
	}
	
	public Source() {
	}
	
	protected Source(Parcel in) {
		this.url = in.readString();
		this.width = in.readInt();
		this.height = in.readInt();
	}
	
	public static final Parcelable.Creator<Source> CREATOR = new Parcelable.Creator<Source>() {
		@Override
		public Source createFromParcel(Parcel source) {
			return new Source(source);
		}
		
		@Override
		public Source[] newArray(int size) {
			return new Source[size];
		}
	};
}
