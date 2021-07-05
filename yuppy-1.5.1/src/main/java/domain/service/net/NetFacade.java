package domain.service.net;

import java.net.URL;

import domain.service.MyPageExtractor;
import domain.service.SongPageExtractor;
import domain.service.UserSongExtractor;
import domain.service.UserURLExtractor;
import lombok.NonNull;

/**
 * netパッケージへのファサードクラス。各インターフェイスを返す。
 */
public class NetFacade {
	/**
	 * マイページ情報。
	 * @param url マイページのURL
	 * @return マイページのExtractor
	 */
	public static MyPageExtractor getMyRankDocument(@NonNull URL url) {
		return new MyPageDocument(url);
	}

	/**
	 * ユーザーページ情報。
	 * @param url ユーザーページのURL
	 * @return ユーザーページのExtractor
	 */
	public static UserSongExtractor getUserSongDocument(@NonNull URL url) {
		return new UserPageDocument(url);
	}

	/**
	 * ランキングリストページの情報。
	 * @param url ランキングリストページのURL
	 * @return ランキングリストページのExtractor
	 */
	public static UserURLExtractor getRankingDocument(@NonNull URL url) {
		return new RankingPageDocument(url);
	}

	/**
	 * 曲詳細ページの情報。
	 * @param url 曲詳細ページのURL
	 * @return 曲詳細ページのExtractor
	 */
	public static SongPageExtractor getSongPageDocument(@NonNull URL url) {
		return new SongPageDocument(url);
	}
}
