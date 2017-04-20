
package redit.com.redditshow.network.reply.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

	@SerializedName("children")
	@Expose
	public List<Child> children = null;
}
