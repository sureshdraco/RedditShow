
package redit.com.redditshow.network.reply.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaEmbed implements Parcelable {
	public String content;
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.content);
	}
	
	public MediaEmbed() {
	}
	
	protected MediaEmbed(Parcel in) {
		this.content = in.readString();
	}
	
	public static final Parcelable.Creator<MediaEmbed> CREATOR = new Parcelable.Creator<MediaEmbed>() {
		@Override
		public MediaEmbed createFromParcel(Parcel source) {
			return new MediaEmbed(source);
		}
		
		@Override
		public MediaEmbed[] newArray(int size) {
			return new MediaEmbed[size];
		}
	};
}
