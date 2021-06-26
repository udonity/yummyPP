package domain.service.net;

import java.net.URL;

import domain.service.MyPageExtractor;
import domain.service.UserURLExtractor;
import domain.service.SongPageExtractor;
import domain.service.UserSongExtractor;
import lombok.NonNull;

public class NetFacade {
	public static MyPageExtractor getMyRankDocument(@NonNull URL url) {
		return new MyPageDocument(url);
	}

	public static UserSongExtractor getUserSongDocument(@NonNull URL url) {
		return new UserPageDocument(url);
	}

	public static UserURLExtractor getRankingDocument(@NonNull URL url) {
		return new RankingPageDocument(url);
	}

	public static SongPageExtractor getSongPageDocument(@NonNull URL url) {
		return new SongPageDocument(url);
	}
}
