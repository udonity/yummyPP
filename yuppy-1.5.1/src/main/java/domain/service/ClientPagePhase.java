package domain.service;

import java.net.URL;
import java.util.Map;

import domain.obj.Country;
import domain.obj.Range;
import domain.obj.SongList;
import domain.obj.UserPageURL;
import domain.service.net.NetFacade;
import lombok.NonNull;

class ClientPagePhase extends Phase {

	private URL clientURL;

	private SongList clientSongs;

	private Map<Country, Integer> countryToRank;

	ClientPagePhase(AccessInterval interval, @NonNull URL clientURL) {
		super(interval);
		this.clientURL = clientURL;
	}

	void accessMyPage(Range myPageRange, Range ppRange) {
		MyPageExtractor rankDocu = NetFacade.getMyRankDocument(this.clientURL);
		rankDocu.extract();
		this.countryToRank = rankDocu.getRank();
		this.clientSongs = rankDocu.obtainSongList(ppRange);

		if (myPageRange.doubleMax() == 1) {
			return;
		}

		// 1ページ目を読み込み
		UserPageURL sendPage = new UserPageURL(this.clientURL);
		// 2ページ目からwhileループ
		sendPage.nextPage();
		while (sendPage.getPage() <= myPageRange.intMax()) {
			interval();
			UserSongExtractor nextSongDocu = NetFacade.getUserSongDocument(sendPage.getURL());
			nextSongDocu.extract();
			SongList nextSongs = nextSongDocu.obtainSongList(ppRange);
			// 空でbreak
			if (nextSongs.isEmpty()) {
				break;
			}
			this.clientSongs.addAll(nextSongs);
			// URLを次のページへ更新
			sendPage.nextPage();
		}
	}

	/**
	 * @return 該当がない場合空のSongListが返る
	 */
	SongList getClientSongs() {
		return this.clientSongs;
	}

	int getRank(Country country) {
		return this.countryToRank.get(country);
	}

}
