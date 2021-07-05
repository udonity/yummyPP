package domain.obj;

/**
 * ユーザーページのページ範囲を表すクラス。
 * 最長値は1で固定される。指定がない場合デフォルトで3ページとなる。
 */
public class UserPageRange extends Range {

	public UserPageRange(String maxStr) {
		// 最長値は1.0で固定
		super("1.0", maxStr);
		if(super.doubleMax() == -1.0) {
			// 指定がないならデフォルト3ページ
			super.setMax(3);
			return;
		}
	}

}
