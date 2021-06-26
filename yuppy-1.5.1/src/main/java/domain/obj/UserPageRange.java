package domain.obj;

public class UserPageRange extends Range {

	public UserPageRange(String maxStr) {
		super("1.0", maxStr);
		if(super.doubleMax() == -1.0) {
			super.setMax(3);
			return;
		}
	}

}
