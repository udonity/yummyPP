package domain.service;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

import domain.obj.ClientInjection;
import domain.obj.RankingRange;
import domain.obj.SongList;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class AccessFlow {
	private ClientInjection ci;
	private SongList songList;

	AccessFlow(@NonNull ClientInjection ci) {
		this.ci = ci;
	}

	void access() {
		AccessInterval interval = new AccessInterval();

		log.info("\tClientPagePhase");
		ClientPagePhase cpPhase = new ClientPagePhase(interval, this.ci.clientURL());
		cpPhase.accessMyPage(this.ci.myPageRange(), this.ci.ppRange());
		SongList mySongs = cpPhase.getClientSongs();
		if(mySongs.isEmpty()) {
			log.warn("MySong is Empty.");
		}

		log.info("\tRankingListPhase");
		AccessInterval rlInterval = cpPhase.getInterval();
		RankingListPhase rlPhase = new RankingListPhase(rlInterval);
		RankingRange rRange = ci.ranking();
		int rank = cpPhase.getRank(ci.country());
		rRange.setRange(rank);
		rlPhase.accessRankingList(rRange);
		Map<Integer, URL> rankToURL = rlPhase.getRankToURLMap();
		if(rankToURL.isEmpty()) {
			log.warn("RankToURLMap is Empty.");
		}


		log.info("\tAnotherUserPhase");
		AccessInterval auInterval = rlPhase.getInterval();
		AnotherUserPhase auPhase = new AnotherUserPhase(auInterval, rankToURL);
		auPhase.accessUserPage(this.ci.anotherUserPage(), this.ci.ppRange());
		SongList anotherSongs = auPhase.getSongList();
		if(anotherSongs.isEmpty()) {
			log.info("AnotherUserSongList is Empty.");
		}

		log.info("\tDuplicateProcess");
		anotherSongs.removeAll(mySongs.getSongs());
		anotherSongs.removeDuplicate();

		log.info("\tStarPhase");
		AccessInterval sInterval = auPhase.getInterval();
		StarPhase sPhase = new StarPhase(sInterval, anotherSongs);
		sPhase.accessSongPage(this.ci.starRange());
		this.songList = sPhase.getSongList();
		this.songList.sort();
		if(this.songList.isEmpty()) {
			log.info("SongList is Empty.");
		}


	}

	SongList getSongList() {
		Objects.requireNonNull(this.songList);
		return this.songList;
	}
}
