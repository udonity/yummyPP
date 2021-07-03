package domain.obj;

public enum Injection {
	URL,
	/**
	 * ランキングリストページで自分のランクから見て、上下何位分の情報を取得するかの範囲
	 */
	RANK_SEARCH_RANGE,
	/**
	 * マイページで曲を検索するページ数
	 */
	MYPAGE_SERACH_RANGE,
	/**
	 * 自分以外のユーザーページで曲情報を何ページ分読み込むかの範囲
	 */
	PAGE_SERACH_RANGE,
	/**
	 * JPかGLOAL
	 */
	COUNTRY,
	/**
	 * 取得したい曲の★の最小値
	 */
	STAR_MIN,
	/**
	 * 取得したい曲の★の最大値
	 */
	STAR_MAX,
	/**
	 * 取得したい曲のPPの最小値
	 */
	PP_MIN,
	/**
	 * 取得したい曲のPPの最大値
	 */
	PP_MAX;
}