
package redit.com.redditshow.network.reply.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Preview implements Parcelable {

	@SerializedName("images")
	@Expose
	public List<Image> images = null;
	@SerializedName("enabled")
	@Expose
	public boolean enabled;
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.images);
		dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
	}
	
	public Preview() {
	}
	
	protected Preview(Parcel in) {
		this.images = in.createTypedArrayList(Image.CREATOR);
		this.enabled = in.readByte() != 0;
	}
	
	public static final Parcelable.Creator<Preview> CREATOR = new Parcelable.Creator<Preview>() {
		@Override
		public Preview createFromParcel(Parcel source) {
			return new Preview(source);
		}
		
		@Override
		public Preview[] newArray(int size) {
			return new Preview[size];
		}
	};
}
