
package redit.com.redditshow.network.reply.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resolution {

	@SerializedName("url")
	@Expose
	public String url;
	@SerializedName("width")
	@Expose
	public int width;
	@SerializedName("height")
	@Expose
	public int height;

}
