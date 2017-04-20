
package redit.com.redditshow.network.reply;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import redit.com.redditshow.network.reply.model.Data;

public class Listing extends ReplyBase {

	@SerializedName("kind")
	@Expose
	public String kind;
	@SerializedName("data")
	@Expose
	public Data data;

}
