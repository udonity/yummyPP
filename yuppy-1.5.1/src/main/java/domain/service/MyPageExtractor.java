package domain.service;

import java.util.Map;

import domain.obj.Country;

/**
 * マイページから情報を取得するインターフェイス
 */
public interface MyPageExtractor extends UserSongExtractor{
	/**
	 * @return 日本ランキング・グローバルランキング数のマップを返す
	 */
	public Map<Country, Integer> getRank();
}
