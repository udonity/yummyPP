package domain.obj;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalRankingURL extends RankingURL {

	private int page;
	private URL url;

	public GlobalRankingURL(int rank) {
		super(rank);
	}

	@Override
	public void next() {
		this.page++;
		StringBuilder sb = new StringBuilder(PREFIX_RANKING);
		try {
			this.url = new URL(sb.append(String.valueOf(this.page)).toString());
		} catch (MalformedURLException e) {
			log.warn("URL is Illegal. URL:" + sb.toString(), e);
		}

	}

	@Override
	public void setURL(int rank) {
		int nextPage = (rank % 50 == 0) ? (rank / 50) : (rank / 50 + 1);
		if (this.page == nextPage) {
			return;
		}
		this.page = nextPage;
		StringBuilder sb = new StringBuilder(PREFIX_RANKING);
		try {
			this.url = new URL(sb.append(String.valueOf(this.page)).toString());
		} catch (MalformedURLException e1) {
			log.warn("URL is Illegal. URL:" + sb.toString(), e1);
		}
		if (!this.url.toString().matches("https://www\\.scoresaber\\.com/global/[0-9]{1,}")) {
			IllegalStateException e
				= new IllegalStateException("URL must be \"https://www.scoresaber.com/global/[0-9]{1,}\". URL:"
												+ this.url.toString());
			log.error("Illegal URL", e);
		}
	}

	@Override
	public URL getURL() {
		return this.url;
	}

	@Override
	public GlobalRankingURL clone() throws CloneNotSupportedException {
		GlobalRankingURL clone = (GlobalRankingURL) super.clone();
		return clone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(page, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GlobalRankingURL other = (GlobalRankingURL) obj;
		return page == other.page && Objects.equals(url, other.url);
	}



}
