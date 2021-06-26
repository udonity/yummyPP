package domain.obj;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 *@NotThreadSafe StringBuilderを用いている。
 */
@Slf4j
public class JPRankingURL extends RankingURL {

	private static final String SUFFIX_JP = "&country=jp";

	/**
	 * https://scoresaber.com/global/"X"?country=jp
	 * のXの部分
	 */
	private int page;

	private URL url;

	/**
	 * @param rank JPのランク数
	 */
	public JPRankingURL(int rank) {
		super(rank);
	}

	@Override
	public void next() {
		this.page++;
		StringBuilder sb = new StringBuilder(PREFIX_RANKING);
		String urlStr = sb.append(String.valueOf(this.page))
							.append(SUFFIX_JP)
							.toString();
		try {
			this.url = new URL(urlStr);
		} catch (MalformedURLException e) {
			log.warn("URL is Illegal.URL:" + sb.toString(), e);
		}
	}

	@Override
	public void setURL(int rank) {
		int nextPage = rank % 50 == 0 ? rank / 50 : rank / 50 + 1; // 50で割り切れない時だけページ数+1
		// ページが変わらないならリターン
		if (nextPage == this.page) {
			return;
		}
		this.page = nextPage;
		StringBuilder sb = new StringBuilder(PREFIX_RANKING);
		String urlStr = sb.append(String.valueOf(this.page))
							.append(SUFFIX_JP)
							.toString();
		try {
			this.url = new URL(urlStr);
		} catch (MalformedURLException e1) {
			log.warn("URL is Illegal.URL:" + sb.toString(), e1);
		}
		if (!this.url.toString().matches("https://www\\.scoresaber\\.com/global/.*country=jp")) {
			IllegalStateException e
				= new IllegalStateException("URL must be \"https://www.scoresaber.com/global/.*country=jp\". "
												+ "URL:" + this.url);
			log.error("Illegal URL", e);
			throw e;
		}

	}
	@Override
	public URL getURL() {
		return this.url;
	}

	@Override
	public JPRankingURL clone() throws CloneNotSupportedException {
		JPRankingURL clone = (JPRankingURL) super.clone();
		return clone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(page, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JPRankingURL other = (JPRankingURL) obj;
		return page == other.page && Objects.equals(url, other.url);
	}



}
