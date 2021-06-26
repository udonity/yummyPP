package domain.obj;

import java.net.URL;

import lombok.extern.slf4j.Slf4j;

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

	public RankingURL clone( ) throws CloneNotSupportedException {
		RankingURL clone = (RankingURL) super.clone();
		return clone;
	}

	public abstract void next();
	public abstract URL getURL();
	public abstract void setURL(int rank);
}
