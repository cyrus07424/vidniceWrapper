package jp.cyrus.vidniceWrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;

import jp.cyrus.vidniceWrapper.constants.Commons;
import jp.cyrus.vidniceWrapper.constants.Configurations;
import jp.cyrus.vidniceWrapper.models.UserOwnVideos;
import jp.cyrus.vidniceWrapper.utils.HttpClientHelper;

/**
 * VidniceWrapper.
 *
 * @author cyrus
 */
public class VidniceWrapper {

	/**
	 * Http client context.
	 */
	public final HttpClientContext httpClientContext;

	/**
	 * Constructor.
	 *
	 * @param phpSessId
	 * @param ses
	 */
	public VidniceWrapper() {
		// disable SNIExtension
		System.setProperty("jsse.enableSNIExtension", "false");

		// Initialize context
		this.httpClientContext = HttpClientContext.create();

		// Initialize cookies
		HttpClientHelper.getHttpResponseWithoutResponse(this, "https://vidnice.com/", Commons.REFERER);
	}

	/**
	 * Get user own videos.
	 *
	 * @param userId
	 * @return
	 */
	public UserOwnVideos getUserOwnVideos(long userId) {
		return getUserOwnVideos(userId, 0);
	}

	/**
	 * Get user own videos.
	 *
	 * @param userId
	 * @param cursor
	 * @return
	 */
	public UserOwnVideos getUserOwnVideos(long userId, long cursor) {
		String url = String.format("https://vidnice.com/APIswitch.php?key=%s", "userownvideos");
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("region", Configurations.REGION));
		params.add(new BasicNameValuePair("user_id", String.valueOf(userId)));
		params.add(new BasicNameValuePair("max_cursor", String.valueOf(cursor)));
		return HttpClientHelper.postHttpResponse(this, url, Commons.REFERER, params, UserOwnVideos.class);
	}
}