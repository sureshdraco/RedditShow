
package redit.com.redditshow.network.reply.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Data_ implements Parcelable {

	@SerializedName("subreddit")
	@Expose
	public String subreddit;
	@SerializedName("preview")
	@Expose
	public Preview preview;
	@SerializedName("id")
	@Expose
	public String id;
	@SerializedName("score")
	@Expose
	public int score;
	@SerializedName("author")
	@Expose
	public String author;
	@SerializedName("saved")
	@Expose
	public boolean saved;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("subreddit_name_prefixed")
	@Expose
	public String subredditNamePrefixed;
	@SerializedName("over_18")
	@Expose
	public boolean over18;
	@SerializedName("domain")
	@Expose
	public String domain;
	@SerializedName("hidden")
	@Expose
	public boolean hidden;
	@SerializedName("thumbnail")
	@Expose
	public String thumbnail;
	@SerializedName("subreddit_id")
	@Expose
	public String subredditId;
	@SerializedName("downs")
	@Expose
	public int downs;
	@SerializedName("brand_safe")
	@Expose
	public boolean brandSafe;
	@SerializedName("archived")
	@Expose
	public boolean archived;
	@SerializedName("post_hint")
	@Expose
	public String postHint;
	@SerializedName("is_self")
	@Expose
	public boolean isSelf;
	@SerializedName("hide_score")
	@Expose
	public boolean hideScore;
	@SerializedName("spoiler")
	@Expose
	public boolean spoiler;
	@SerializedName("permalink")
	@Expose
	public String permalink;
	@SerializedName("locked")
	@Expose
	public boolean locked;
	@SerializedName("stickied")
	@Expose
	public boolean stickied;
	@SerializedName("created")
	@Expose
	public int created;
	@SerializedName("url")
	@Expose
	public String url;
	@SerializedName("quarantine")
	@Expose
	public boolean quarantine;
	@SerializedName("title")
	@Expose
	public String title;
	@SerializedName("created_utc")
	@Expose
	public int createdUtc;
	@SerializedName("num_comments")
	@Expose
	public int numComments;
	@SerializedName("visited")
	@Expose
	public boolean visited;
	@SerializedName("subreddit_type")
	@Expose
	public String subredditType;
	@SerializedName("ups")
	@Expose
	public int ups;

	public String getTitle() {
		return title;
	}

	public Data_() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.subreddit);
		dest.writeParcelable(this.preview, flags);
		dest.writeString(this.id);
		dest.writeInt(this.score);
		dest.writeString(this.author);
		dest.writeByte(this.saved ? (byte) 1 : (byte) 0);
		dest.writeString(this.name);
		dest.writeString(this.subredditNamePrefixed);
		dest.writeByte(this.over18 ? (byte) 1 : (byte) 0);
		dest.writeString(this.domain);
		dest.writeByte(this.hidden ? (byte) 1 : (byte) 0);
		dest.writeString(this.thumbnail);
		dest.writeString(this.subredditId);
		dest.writeInt(this.downs);
		dest.writeByte(this.brandSafe ? (byte) 1 : (byte) 0);
		dest.writeByte(this.archived ? (byte) 1 : (byte) 0);
		dest.writeString(this.postHint);
		dest.writeByte(this.isSelf ? (byte) 1 : (byte) 0);
		dest.writeByte(this.hideScore ? (byte) 1 : (byte) 0);
		dest.writeByte(this.spoiler ? (byte) 1 : (byte) 0);
		dest.writeString(this.permalink);
		dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
		dest.writeByte(this.stickied ? (byte) 1 : (byte) 0);
		dest.writeInt(this.created);
		dest.writeString(this.url);
		dest.writeByte(this.quarantine ? (byte) 1 : (byte) 0);
		dest.writeString(this.title);
		dest.writeInt(this.createdUtc);
		dest.writeInt(this.numComments);
		dest.writeByte(this.visited ? (byte) 1 : (byte) 0);
		dest.writeString(this.subredditType);
		dest.writeInt(this.ups);
	}

	protected Data_(Parcel in) {
		this.subreddit = in.readString();
		this.preview = in.readParcelable(Preview.class.getClassLoader());
		this.id = in.readString();
		this.score = in.readInt();
		this.author = in.readString();
		this.saved = in.readByte() != 0;
		this.name = in.readString();
		this.subredditNamePrefixed = in.readString();
		this.over18 = in.readByte() != 0;
		this.domain = in.readString();
		this.hidden = in.readByte() != 0;
		this.thumbnail = in.readString();
		this.subredditId = in.readString();
		this.downs = in.readInt();
		this.brandSafe = in.readByte() != 0;
		this.archived = in.readByte() != 0;
		this.postHint = in.readString();
		this.isSelf = in.readByte() != 0;
		this.hideScore = in.readByte() != 0;
		this.spoiler = in.readByte() != 0;
		this.permalink = in.readString();
		this.locked = in.readByte() != 0;
		this.stickied = in.readByte() != 0;
		this.created = in.readInt();
		this.url = in.readString();
		this.quarantine = in.readByte() != 0;
		this.title = in.readString();
		this.createdUtc = in.readInt();
		this.numComments = in.readInt();
		this.visited = in.readByte() != 0;
		this.subredditType = in.readString();
		this.ups = in.readInt();
	}

	public static final Creator<Data_> CREATOR = new Creator<Data_>() {
		@Override
		public Data_ createFromParcel(Parcel source) {
			return new Data_(source);
		}

		@Override
		public Data_[] newArray(int size) {
			return new Data_[size];
		}
	};
}
