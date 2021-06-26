package domain.obj;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPageURL {
	/**
	 * https://scoresaber.com/u/"XXX"&page=NUM&sort=1のXXXの部分
	 * ユーザを表す一意な文字列
	 */
	private String hash;

	/**
	 * https://scoresaber.com/u/XXX&page="NUM"&sort=1のNUMの部分
	 */
	private int page;

	private static final String PREFIX = "https://www.scoresaber.com/u/";
	private static final String SUFFIX_PAGE = "&page=";
	private static final String SUFFIX_SORT = "&sort=1";

	public UserPageURL(@NonNull URL url) {
		// ホストチェック
		if (!url.getHost().contains("scoresaber.com") || !url.toString().contains("/u/")) {
			IllegalArgumentException e = new IllegalArgumentException("URL:" + url.toString());
			log.warn("URL is Illegal.");
			throw e;
		}
		this.page = 1; // ページ初期値
		String urlText = url.toString();
		// hashを整形
		int startIndex = urlText.indexOf("/u/") + 3;
		// URLに&page=NUM&sort=1が付く場合と付かない場合があるので場合分け
		if (urlText.endsWith(SUFFIX_SORT)) {
			int endIndex = urlText.indexOf(SUFFIX_PAGE);
			this.hash = urlText.substring(startIndex, endIndex);
		} else {
			this.hash = urlText.substring(startIndex);
		}
	}

	public URL getURL() {
		StringBuilder sb = new StringBuilder(PREFIX);
		URL result = null;
		try {
			result = new URL(sb.append(hash)
								.append(SUFFIX_PAGE)
								.append(String.valueOf(this.page))
								.append(SUFFIX_SORT)
								.toString());
		} catch (MalformedURLException e) {
			log.warn("URL formatting failed.", e);
		}
		return result;
	}

	public void nextPage() {
		this.page++;
	}

	public int getPage() {
		return this.page;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hash, page);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserPageURL)) {
			return false;
		}
		UserPageURL other = (UserPageURL) obj;
		return Objects.equals(hash, other.hash) && page == other.page;
	}



}
