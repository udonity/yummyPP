package domain.obj;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
/**
 * Rankingの範囲を表すクラス。
 * setRangeを行っていない状態でgetterを用いるとIllegalStateExceptionをスローする。
 */
@Slf4j
public class RankingRange extends Range {
	private int range;
	/**
	 * 初期値-1でillegalな状態を表す。
	 */
	private int myRank = -1;
	private Country country;

	/**
	 * minとmaxの初期値を-1でセットする。
	 * @param rangeStr ランキングページで検索するランキング幅
	 * @param country 国別ランキングかglobalランキングかの指定
	 */
	public RankingRange(@NonNull String rangeStr, @NonNull Country country) {
		// minとmaxを-1にする
		super("","");
		this.country = country;
		String trimmedRangeStr = rangeStr.trim();
		if(trimmedRangeStr.isEmpty()) {
			this.range = 3;
			return;
		}
		this.range = Integer.valueOf(trimmedRangeStr);
	}

	@Override
	public int intMin() {
		checkMyRank();

		return super.intMin();
	}

	@Override
	public int intMax() {
		checkMyRank();

		return super.intMax();
	}

	/**
	 * ゲッターを用いる前に必ずセットする必要がある
	 * @param myRank
	 * @throws IllegalArgumentException myRank < 1の場合
	 */
	public void setRange(int myRank) {
		if(myRank < 1) {
			IllegalArgumentException e
				= new IllegalArgumentException("MyRank must be greater than 0. myRank:" + myRank);
			log.warn("MyRank is Illegal.", e);
			throw e;
		}
		this.myRank = myRank;
		// 最小値が1より小さいなら最小値を1固定
		int min = this.myRank - this.range;
		min = min < 1 ? 1 : min;
		super.setMin(min);
		int max = this.myRank + this.range;
		super.setMax(max);
	}

	public int getMyRank() {
		checkMyRank();

		return this.myRank;
	}

	public Country getCountry() {
		return this.country;
	}

	private void checkMyRank() {
		if(this.myRank == -1.0) {
			IllegalStateException e = new IllegalStateException("MyRank not set. MyRank:" + this.myRank);
			log.warn("MyRank is Illegal.", e);
			throw e;
		}
	}

}
