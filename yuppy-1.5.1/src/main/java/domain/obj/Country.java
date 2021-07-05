package domain.obj;

import lombok.NonNull;

/**
 * 国を表すクラス
 */
public class Country {
	private Type country;

	/**
	 * @param countryStr 空の場合はglobalとなる。
	 */
	public Country(@NonNull String countryStr) {
		if(countryStr.isEmpty() || countryStr.equals("global")) {
			this.country = Type.GLOBAL;
		} else if(countryStr.equals("jp")) {
			this.country = Type.JP;
		} else {
			this.country = Type.GLOBAL; // デフォルトでグローバル
		}
	}

	public Type getType() {
		return this.country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Country other = (Country) obj;
		if (country != other.country) {
			return false;
		}
		return true;
	}

	/**
	 * 国のタイプ
	 */
	public enum Type {
		JP,
		GLOBAL
	}
}
