package redit.com.redditshow.network.reply;

public class ReplyBase {
	public static final int NETWORK_ERROR = 8001;
	public static final int DISASTER_ERROR = 503;
	public static final int NOT_ACCEPTABLE = 406;
	public static final int SERVER_ERROR = 8002;
	public transient int responseCode;

	public ReplyBase(int responseCode) {
		this.responseCode = responseCode;
	}

	public ReplyBase() {
	}

	public boolean isOk() {
		return responseCode >= 200 && responseCode < 300;
	}
}