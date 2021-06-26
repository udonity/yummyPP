package domain.service;

import java.net.URL;
import java.util.Map;

import domain.obj.Range;
import domain.obj.SongList;
import domain.service.net.NetFacade;
import lombok.NonNull;

class AnotherUserPhase extends Phase {
	private Map<Integer, URL> rankToURL;
	private SongList songList = new SongList();
	public AnotherUserPhase(AccessInterval interval, @NonNull Map<Integer, URL> rankToURL) {
		super(interval);
		this.rankToURL = rankToURL;
	}

	void accessUserPage(Range pageRange, Range ppRange) {
		for(int rank : this.rankToURL.keySet()) {
			URL url = rankToURL.get(rank);
			for(int i = 1; i <= pageRange.intMax(); i++) {
				interval();

				UserSongExtractor userDocu = NetFacade.getUserSongDocument(url);
				userDocu.extract();
				SongList sList = userDocu.obtainSongList(ppRange);
				if (sList.isEmpty()) {
					break;
				}
				this.songList.addAll(sList);
			}
		}
	}

	SongList getSongList() {
		return this.songList;
	}
}
