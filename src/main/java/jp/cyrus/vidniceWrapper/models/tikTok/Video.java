
package jp.cyrus.vidniceWrapper.models.tikTok;

import java.util.List;

/**
 * Video.
 *
 * @author cyrus
 */
public class Video {

	public Url playAddr;

	public Url originCover;

	public String ratio;

	public Url downloadAddr;

	public Boolean hasWatermark;

	public Url cover;

	public Long height;

	public List<BitRate> bitRate;

	public Url dynamicCover;

	public Boolean hasDownloadSuffixLogoAddr;

	public Long isH265;

	public Long width;

	public Long duration;

	public Url downloadSuffixLogoAddr;
}