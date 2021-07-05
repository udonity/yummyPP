package domain.repo;

/**
 * ファイルへ出力する責務を持つインターフェース。
 */
public interface OutRepository {
	/**
	 * テキストファイルへ出力する。
	 * @param title タイトル
	 */
	public void storeText(String title);

	/**
	 * JSONファイルへ出力する。
	 * @param title タイトル
	 */
	public void storeJSON(String title);
}
