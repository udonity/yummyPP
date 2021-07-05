package domain.service.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import domain.obj.RankingRange;
import domain.service.UserURLExtractor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * ランキングリストのドキュメントを扱うクラス。
 * ランク数を格納したリスト・ユーザーページへのURLを格納したリストをそれぞれ取得した後、
 * Mapへと変換する。
 */
@Slf4j
class RankingPageDocument extends ScoreSaberDocument implements UserURLExtractor {
	/**
	 * ランキングリストHTML内でのターゲットとなるElements
	 */
	private Elements rankingElements;
	/**
	 * ランク数を格納するリスト
	 */
	private List<Integer> rankList = null;

	/**
	 * URLを格納するリスト
	 */
	private List<URL> urlList = null;

	/**
	 * @param url ランキングページのURL
	 */
	public RankingPageDocument(URL url) {
		super(url);
		Document docu = super.getDocument();
		this.rankingElements = docu.select("div div div div div div div table tbody tr");

		Objects.requireNonNull(this.rankingElements);
	}

	/**
	 * 指定したランキングレンジ内のURLを取得する。
	 * @return rankingRangeの範囲に無ければ空のmapを返す。
	 */
	@Override
	public Map<Integer, URL> obtainRankToURLMap(@NonNull RankingRange rankingRange) {
		// extractを実行していないなら、実行する必要がある
		if(this.rankList == null || this.urlList == null) {
			extract();
		}

		Map<Integer, URL> rankToURL = new TreeMap<>();
		int min = rankingRange.intMin();
		int max = rankingRange.intMax();
		for (int i = 0; i < this.rankList.size(); i++) {
			int rank = this.rankList.get(i);
			if (rank >= min && rank <= max) {;
				URL url = this.urlList.get(i);
				rankToURL.put(rank, url);
			}
		}
		return rankToURL;
	}

	/**
	 * ランク数のリスト・URLのリストそれぞれを取得する。
	 */
	@Override
	public void extract() {
		this.rankList = this.rankingElements.select("td[class=rank]").stream()
						.map(Element::text)
						.map(RankingPageDocument::formatToRank)
						.collect(Collectors.toList());
		Objects.requireNonNull(this.rankList);

		this.urlList = this.rankingElements.select("td[class=player] a[href]").stream()
						.map(Node::toString)
						.map(RankingPageDocument::formatToURL)
						.collect(Collectors.toList());
		Objects.requireNonNull(this.urlList);

		if(this.rankList.size() != this.urlList.size()) {
			IllegalStateException e
				= new IllegalStateException("Size is difficult. URLListSize:"
											+ this.urlList.size()
											+ ", RankListSize:"
											+ this.rankList.size());
			log.warn("Size is Illegal.", e);
		}
	}

	/**
	 * Streamでのヘルパーメソッド。
	 * 文字列をトリミングし変換したランク数を返す。
	 * @param text 文字列
	 * @return ランク数
	 */
	private static int formatToRank(String text) {
		String substring = text.substring(1);
		String intStr = substring.replaceAll("[#,]", "");
		return Integer.valueOf(intStr);
	}

	/**
	 * Streamでのヘルパーメソッド。
	 * 文字列をトリミングし変換したURLを返す。
	 * @param text 文字列
	 * @return ユーザーページへのURL
	 */
	private static URL formatToURL(String text) {
		int start = text.indexOf("/u/") + 3;
		int end = text.indexOf("\">");
		String urlText = text.substring(start, end);
		URL url = null;
		try {
			url = new URL("https://scoresaber.com/u/" + urlText);
		} catch (MalformedURLException e) {
			log.warn("URL is Illegal. URL:" + urlText, e);
		}
		return url;
	}

}
