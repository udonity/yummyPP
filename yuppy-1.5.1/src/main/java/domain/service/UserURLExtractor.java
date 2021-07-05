package domain.service;

import java.net.URL;
import java.util.Map;

import domain.obj.RankingRange;

/**
 * ユーザーのURLを取得するインターフェイス。
 */
public interface UserURLExtractor extends Extractor{
	/**
	 * @param rankingRange 順位を指定する。
	 * @return ランク数とユーザーページURLのマップを返す
	 */
	public Map<Integer, URL> obtainRankToURLMap(RankingRange rankingRange);
}
