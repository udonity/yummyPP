package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GlobalRankingURLTest {

	GlobalRankingURL sut;

	@BeforeEach
	void setup() {
		sut = new GlobalRankingURL(1);
	}

	@Test
	void コンストラクタでrankが1の時URLが整形される() throws MalformedURLException {
		// exercise
		URL actual = sut.getURL();
		URL expected = new URL("https://www.scoresaber.com/global/1");
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void pageが1の時setURLで50をセットしてもURLは変わらない() throws MalformedURLException {
		// exercise
		sut.setURL(50);
		URL actual = sut.getURL();
		URL expected = new URL(
				"https://www.scoresaber.com/global/1");
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void pageが1の時setURLで51をセットすると2ページ目になる() throws MalformedURLException {
		// exercise
		sut.setURL(51);
		URL actual = sut.getURL();
		URL expected = new URL(
				"https://www.scoresaber.com/global/2");
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void pageが1の時nextを実行すると2ページ目になる() throws MalformedURLException {
		// exercise
		sut.next();
		URL actual = sut.getURL();
		URL expected = new URL(
				"https://www.scoresaber.com/global/2");
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void cloneを実行すると前のオブジェクトとは等値ではない() throws CloneNotSupportedException {
		// exercise
		GlobalRankingURL clone = sut.clone();
		// verify
		assertThat(sut, not(sameInstance(clone)));
	}

	@Test
	void cloneを実行すると前のオブジェクトと等価() throws CloneNotSupportedException {
		// exercise
		GlobalRankingURL clone = sut.clone();
		// verify
		assertEquals(sut, clone);
	}
}
