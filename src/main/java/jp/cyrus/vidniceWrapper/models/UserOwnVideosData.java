
package jp.cyrus.vidniceWrapper.models;

import java.util.List;

import jp.cyrus.vidniceWrapper.models.tikTok.Aweme;
import jp.cyrus.vidniceWrapper.models.tikTok.Extra;
import jp.cyrus.vidniceWrapper.models.tikTok.LogPb;

/**
 * UserOwnVideosData.
 *
 * @author cyrus
 */
public class UserOwnVideosData {

	public List<Aweme> awemeList;

	public Extra extra;

	public Long hasMore;

	public LogPb logPb;

	public Long maxCursor;

	public Long minCursor;

	public Long statusCode;
}