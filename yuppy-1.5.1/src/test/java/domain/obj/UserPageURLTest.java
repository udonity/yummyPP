package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

class UserPageURLTest {

	@Test
	void コンストラクタでユーザーページのURLは例外をスローしない() throws MalformedURLException {
		// setup
		URL userPage = new URL("https://scoresaber.com/u/xxxxx");
		// verify
		assertDoesNotThrow(() -> new UserPageURL(userPage));
	}
	
	@Test
	void コンストラクタでスコアセイバーホームのURLは例外() throws MalformedURLException {
		// setup
		URL home = new URL("https://scoresaber.com/");
		// exercise
		Throwable e = assertThrows(IllegalArgumentException.class, () -> new UserPageURL(home));
		String actual = e.getMessage();
		String expected = "URL:https://scoresaber.com/";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタ後サフィックスが挿入される() throws MalformedURLException {
		// setup
		UserPageURL sut = new UserPageURL(new URL("https://scoresaber.com/u/xxxxxxxxx"));
		// exercise
		URL actual = sut.getURL();
		URL expected = new URL("https://www.scoresaber.com/u/xxxxxxxxx&page=1&sort=1");
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void nextPageで次のページになる() throws MalformedURLException {
		// setup
		UserPageURL sut = new UserPageURL(new URL("https://scoresaber.com/u/xxxxxxxxx"));
		// exercise
		sut.nextPage();
		URL actual = sut.getURL();
		URL expected = new URL("https://www.scoresaber.com/u/xxxxxxxxx&page=2&sort=1");
		// verify
		assertThat(actual, is(expected));
	}

}
