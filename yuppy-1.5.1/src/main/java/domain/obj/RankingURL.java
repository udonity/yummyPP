package domain.obj;

import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * ランキングページのURLを表す抽象クラス
 */
@Slf4j
public abstract class RankingURL implements Cloneable {
	protected static final String PREFIX_RANKING = "https://www.scoresaber.com/global/";

	/**
	 * コンストラクタ内でsetURLが実行される。
	 * @param rank クライアントのランク数。rankが1未満のときIllegalArgumentExceptionをスロー。
	 */
	public RankingURL(int rank) {
		if (rank < 1) {
			IllegalArgumentException e = new IllegalArgumentException("Rank must greater than 0.rank:" + rank);
			log.error("RankNumber is Illegal.", e);
			throw e;
		}
		setURL(rank);
	}

	@Override
	public RankingURL clone( ) throws CloneNotSupportedException {
		RankingURL clone = (RankingURL) super.clone();
		return clone;
	}

	/**
	 * 次のページにURLを整形する
	 */
	public abstract void next();
	/**
	 * @return URLを返す
	 */
	public abstract URL getURL();
	/**
	 * セットされたランクによりURLが変わるならURLを更新する
	 * @param rank ユーザーのランク
	 */
	public abstract void setURL(int rank);
}
