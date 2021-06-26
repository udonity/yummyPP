package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;

class RankingURLTest {

	@Test
	void コンストラクタでrankが0の時例外() {
		// exercise
		// メソッドの実装内容は関係ない
		Throwable e
			= assertThrows(IllegalArgumentException.class,
							() -> new RankingURL(0) {
								@Override
								public void next() {
									// not used
								}

								@Override
								public URL getURL() {
									// not used
									return null;
								}

								@Override
								public void setURL(int rank) {
									// not used
								}
							});
		String actual = e.getMessage();
		String expected = "Rank must greater than 0.rank:0";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでrankが1の時例外が発生しない() {
		// verify
		// 実装内容は関係ない
		assertDoesNotThrow(() -> new RankingURL(1) {
									@Override
									public void next() {
										// not used
									}

									@Override
									public URL getURL() {
										// not used
										return null;
									}

									@Override
									public void setURL(int rank) {
										// not used
									}
								});
	}

}
