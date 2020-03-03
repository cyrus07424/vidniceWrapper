package jp.cyrus.vidniceWrapper;

import jp.cyrus.vidniceWrapper.models.UserOwnVideos;
import jp.cyrus.vidniceWrapper.models.tikTok.Aweme;

/**
 * Get UserOwnVideos test.
 *
 * @author cyrus
 */
public class GetUserOwnVideosTest {

	/**
	 * User ID.
	 */
	private static final long USER_ID = 68616495085350913L;

	/**
	 * main.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Get UserOwnVideos data
		UserOwnVideos userOwnVideos = new VidniceWrapper().getUserOwnVideos(USER_ID);
		System.out.println(userOwnVideos);

		for (Aweme aweme : userOwnVideos.tiktokdata.awemeList) {
			System.out.println(aweme);
		}
	}
}