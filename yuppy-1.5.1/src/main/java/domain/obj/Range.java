package domain.obj;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 範囲を表すクラス
 * 正の実数のminとmaxを持つ。持たない場合値は-1となる。
 */
@Slf4j
public class Range {
	/**
	 * 空の場合は-1
	 */
	private double min;
	/**
	 * 空の場合は-1
	 */
	private double max;

	/**
	 * @param minStr 前後に空白があればトリミングする。空の場合-1で格納する。
	 * -1以外の0以下の値はIllegalArgumentExceptionをスローする。
	 * @param maxStr 前後に空白があればトリミングする。空の場合	-1で格納する。
	 * -1以外の0以下の値はIllegalArgumentExceptionをスローする。
	 */
	public Range(@NonNull String minStr, @NonNull String maxStr) {
		String trimmedMin = minStr.trim();
		String trimmedMax = maxStr.trim();

		// 空なら-1
		if(trimmedMin.isEmpty() && trimmedMax.isEmpty()) {
			this.min = -1;
			this.max = -1;
			return;
		}

		if(trimmedMin.isEmpty()) {
			this.min = -1;
			// -1以外で0以下なら例外
			this.max = Double.valueOf(trimmedMax);
			if(this.max <= 0) {
				throwIAException();
			}
			return;
		}

		if(trimmedMax.isEmpty()) {
			this.max = -1;
			// -1以外で0以下なら例外
			this.min = Double.valueOf(trimmedMin);
			if(this.min <= 0) {
				throwIAException();
			}
			return;
		}

		this.min = Double.valueOf(minStr);
		this.max = Double.valueOf(maxStr);
		// -1以外
		if(this.min > this.max) {
			throwIAException();
		}
	}

	public double doubleMin( ) {
		return this.min;
	}

	public int intMin() {
		return (int) this.min;
	}

	public double doubleMax() {
		return this.max;
	}

	public int intMax() {
		return (int) this.max;
	}

	void setMin(int min) {
		this.min = Double.valueOf(min);
	}

	void setMax(int max) {
		this.max = Double.valueOf(max);
	}

	private void throwIAException() {
		IllegalArgumentException e = new IllegalArgumentException("min:" + this.min + ", max:" + this.max);
		log.warn("Range is Illegal.", e);
		throw e;
	}
}
