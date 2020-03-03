package jp.cyrus.vidniceWrapper;

import jp.cyrus.vidniceWrapper.models.UserInfo;

/**
 * Get UserInfo test.
 *
 * @author cyrus
 */
public class GetUserInfoTest {

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
		// Get UserInfo data
		UserInfo userInfo = new VidniceWrapper().getUserInfo(USER_ID);
		System.out.println(userInfo);
	}
}