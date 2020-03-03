package jp.cyrus.vidniceWrapper.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import jp.cyrus.vidniceWrapper.VidniceWrapper;
import jp.cyrus.vidniceWrapper.constants.Configurations;

/**
 * Http client helper.
 *
 * @author cyrus
 */
public class HttpClientHelper {

	/**
	 * Retry interval millis.
	 */
	private static final int RETRY_INTERVAL_MILLI_SEC = 60 * 1000;

	/**
	 * Max retry count.
	 */
	private static final int MAX_RETRY_COUNT = 10;

	/**
	 * Get HTTP response.
	 *
	 * @param vidniceWrapper
	 * @param url
	 * @param referer
	 * @param params
	 * @param valueType
	 * @return
	 */
	public static <T> T postHttpResponse(VidniceWrapper vidniceWrapper, String url, String referer,
			List<NameValuePair> params, Class<T> valueType) {
		try {
			LogHelper.debug("Post : " + url);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("User-Agent", Configurations.HTTP_CLIENT_USER_AGENT);
			httpPost.setHeader("Referer", referer);
			httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
			httpPost.setHeader("Accept", "*/*");
			httpPost.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpPost.setHeader("Accept-Encoding", "gzip");
			httpPost.setHeader("Pragma", "no-cache");
			httpPost.setHeader("Cache-Control", "no-cache");
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			try (CloseableHttpClient client = getHttpClient(vidniceWrapper);
					CloseableHttpResponse response = client.execute(httpPost, vidniceWrapper.httpClientContext)) {
				LogHelper.debug(response);
				LogHelper.debug("code = " + response.getStatusLine().getStatusCode());
				String responseString = EntityUtils.toString(response.getEntity());
				LogHelper.debug("response = " + responseString);
				return JsonHelper.getObjectMapper().readValue(responseString, valueType);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get HTTP response.
	 *
	 * @param vidniceWrapper
	 * @param url
	 * @param referer
	 * @param valueType
	 * @return
	 */
	@Deprecated
	public static <T> T getHttpResponse(VidniceWrapper vidniceWrapper, String url, String referer,
			Class<T> valueType) {
		try {
			LogHelper.debug("Get : " + url);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", Configurations.HTTP_CLIENT_USER_AGENT);
			httpGet.setHeader("Referer", referer);
			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
			httpGet.setHeader("Accept", "*/*");
			httpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpGet.setHeader("Accept-Encoding", "gzip");
			httpGet.setHeader("Pragma", "no-cache");
			httpGet.setHeader("Cache-Control", "no-cache");
			try (CloseableHttpClient client = getHttpClient(vidniceWrapper);
					CloseableHttpResponse response = client.execute(httpGet, vidniceWrapper.httpClientContext)) {
				LogHelper.debug(response);
				LogHelper.debug("code = " + response.getStatusLine().getStatusCode());
				String responseString = EntityUtils.toString(response.getEntity());
				LogHelper.debug("response = " + responseString);
				return JsonHelper.getObjectMapper().readValue(responseString, valueType);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get HTTP response without response.
	 *
	 * @param vidniceWrapper
	 * @param url
	 * @param referer
	 * @param valueType
	 * @return
	 */
	public static void getHttpResponseWithoutResponse(VidniceWrapper vidniceWrapper, String url, String referer) {
		try {
			LogHelper.debug("Get : " + url);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", Configurations.HTTP_CLIENT_USER_AGENT);
			httpGet.setHeader("Referer", referer);
			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
			httpGet.setHeader("Accept", "*/*");
			httpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpGet.setHeader("Accept-Encoding", "gzip");
			httpGet.setHeader("Pragma", "no-cache");
			httpGet.setHeader("Cache-Control", "no-cache");
			try (CloseableHttpClient client = getHttpClient(vidniceWrapper);
					CloseableHttpResponse response = client.execute(httpGet, vidniceWrapper.httpClientContext)) {
				LogHelper.debug(response);
				LogHelper.debug("code = " + response.getStatusLine().getStatusCode());
				String responseString = EntityUtils.toString(response.getEntity());
				LogHelper.debug("response = " + responseString);
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get HttpClient.<br>
	 * https://qiita.com/kgyamaryllis/items/531abacdc83f72239332.
	 *
	 * @param vidniceWrapper
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(VidniceWrapper vidniceWrapper) {
		return HttpClientBuilder.create()
				.setRetryHandler(new DefaultHttpRequestRetryHandler())
				.setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {

					@Override
					public boolean retryRequest(
							final HttpResponse response, final int executionCount, final HttpContext context) {
						int statusCode = response.getStatusLine().getStatusCode();
						return Arrays.asList(HttpStatus.SC_SERVICE_UNAVAILABLE).contains(statusCode)
								&& executionCount < MAX_RETRY_COUNT;
					}

					@Override
					public long getRetryInterval() {
						return RETRY_INTERVAL_MILLI_SEC;
					}
				})
				.setDefaultCookieStore(vidniceWrapper.httpClientContext.getCookieStore())
				.build();
	}
}