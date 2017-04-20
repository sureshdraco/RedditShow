
package redit.com.redditshow.network.reply.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child implements Parcelable {

	@SerializedName("kind")
	@Expose
	public String kind;
	@SerializedName("data")
	@Expose
	public Data_ data;

	public Data_ getData() {
		return data;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.kind);
		dest.writeParcelable(this.data, flags);
	}
	
	public Child() {
	}
	
	protected Child(Parcel in) {
		this.kind = in.readString();
		this.data = in.readParcelable(Data_.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Child> CREATOR = new Parcelable.Creator<Child>() {
		@Override
		public Child createFromParcel(Parcel source) {
			return new Child(source);
		}
		
		@Override
		public Child[] newArray(int size) {
			return new Child[size];
		}
	};
}
