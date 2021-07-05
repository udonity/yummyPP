package domain.service;

import domain.obj.Range;
import domain.obj.SongList;

/**
 * ユーザーページのドキュメントを取得するインターフェイス。
 */
public interface UserSongExtractor extends Extractor{
	/**
	 * @param range ppのレンジ
	 * @return 範囲内の曲リストを返す
	 */
	public SongList obtainSongList(Range range);
}
