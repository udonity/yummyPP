package domain.service;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import domain.obj.Range;
import domain.obj.SongList;
import domain.obj.json.Song;
import domain.service.net.NetFacade;
import lombok.NonNull;

class StarPhase extends Phase {
	private SongList songList;

	StarPhase(AccessInterval interval, @NonNull SongList songList) {
		super(interval);
		this.songList = songList;
	}

	void accessSongPage(Range starRange) {
		double starMin = starRange.doubleMin();
		double starMax = starRange.doubleMax();
		if ( starMin== -1 && starMax == -1) {
			return;
		}
		Set<Song> removeSet = new HashSet<>();
		for (Song song : this.songList.getSongs()) {
			URL url = song.getSongURL();
			interval();

			SongPageExtractor starDocu = NetFacade.getSongPageDocument(url);
			starDocu.extract();
			double star = starDocu.getStar();
			if (starMin != -1 && star < starMin) {
				removeSet.add(song);
			}
			if (starMax != -1 && star > starMax) {
				removeSet.add(song);
			}
			song.setStar(star);
		}
		this.songList.removeAll(removeSet);
	}

	SongList getSongList() {
		return this.songList;
	}

}
