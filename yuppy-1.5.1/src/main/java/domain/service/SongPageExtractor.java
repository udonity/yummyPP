package domain.service;

/**
 * 曲情報詳細ページより情報を取得するインターフェイス。
 */
public interface SongPageExtractor extends Extractor{
	/**
	 * @return ★の値を返す。
	 */
	public double getStar();
}
