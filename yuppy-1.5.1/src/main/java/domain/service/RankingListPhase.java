package domain.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import domain.obj.Country;
import domain.obj.GlobalRankingURL;
import domain.obj.JPRankingURL;
import domain.obj.RankingRange;
import domain.obj.RankingURL;
import domain.service.net.NetFacade;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RankingListPhase extends Phase {
	private Map<Integer, URL> rankToURL = new TreeMap<>();

	RankingListPhase(AccessInterval interval) {
		super(interval);
	}

	void accessRankingList(@NonNull RankingRange rankingRange) {
		int myRank = rankingRange.getMyRank();
		int minRank = rankingRange.intMin();
		int maxRank = rankingRange.intMax();

		// URLリストを作る
		List<RankingURL> urlList = new ArrayList<>();
		RankingURL rankingURL;
		if (rankingRange.getCountry().getType() == Country.Type.GLOBAL) {
			rankingURL = new GlobalRankingURL(minRank);
		} else {
			rankingURL = new JPRankingURL(minRank);
		}

		// 初めのURLをセット
		urlList.add(rankingURL);

		// 2つ目以降のURLをセット
		RankingURL currentURL = null; // ページが異なった場合に更新するためのURL
		try {
			currentURL = rankingURL.clone();
		} catch (CloneNotSupportedException e) {
			log.warn("Clone failed.", e);
		}
		Objects.requireNonNull(currentURL);
		RankingURL nextURL = null; // 次のURL
		try {
			nextURL = rankingURL.clone();
		} catch (CloneNotSupportedException e) {
			log.warn("Clone failed.", e);
		}
		Objects.requireNonNull(nextURL);

		int secondRank = minRank + 1; // 2番目のランクが初期値
		for (int rank = secondRank; rank <= maxRank; rank++) {
			// 自身のランクならスキップ
			if(rank == myRank) {
				continue;
			}

			// 次のランクのURLをセット
			nextURL.setURL(rank);

			// URLが変わらないならスキップ
			if (nextURL.getURL().equals(currentURL.getURL())) {
				continue;
			}

			// URLを更新して追加
			currentURL = nextURL;
			urlList.add(currentURL);
		}

		for (RankingURL url : urlList) {
			interval();
			UserURLExtractor rankingDocu = NetFacade.getRankingDocument(url.getURL());
			rankingDocu.extract();
			Map<Integer, URL> uRLMap = rankingDocu.obtainRankToURLMap(rankingRange);
			this.rankToURL.putAll(uRLMap);
		}
	}

	Map<Integer, URL> getRankToURLMap() {
		return this.rankToURL;
	}

}
