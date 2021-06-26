package domain.service.net;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

class ScoreSaberDocumentTest {

	@Test
	void コンストラクタでスコアセイバーのサイトなら例外を投げない() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber.com/");
		// verify
		assertDoesNotThrow(() -> new ScoreSaberDocument(url));
	}
	
	@Test
	void コンストラクタでスコアセイバー以外のサイトだと例外() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber/");
		// exercise
		Throwable e = assertThrows(IllegalArgumentException.class, () -> new ScoreSaberDocument(url));
		String actual = e.getMessage();
		String expected = "URL:https://scoresaber/";
		// verify
		assertThat(actual, is(expected));
	}

}
