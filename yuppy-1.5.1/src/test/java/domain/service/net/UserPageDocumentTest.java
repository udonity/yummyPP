package domain.service.net;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

/**
 * TODO コンストラクタの長さチェックの例外テストを実装
 * TODO Jsoupを使ってHTMLを形成してURLをスタブにする。あるいは、HTMLファイル化してテスト用に作る。
 *
 */
class UserPageDocumentTest {
	File abcentDifficulty = new File("src\\test\\resources\\user_page_nothing_difficulty.html");
	File abcentNameDifficultyURL = new File("src\\test\\resources\\user_page_nothing_nameDifficultyURL.html");
	File abcentPP = new File("src\\test\\resources\\user_page_nothing_pp.html");
	File abcentNameURL = new File("src\\test\\resources\\user_page_nothing_nameURL.html");
	File abcentHashNameURL = new File("src\\test\\resources\\user_page_nothing_hashNameURL.html");

	@Test
	void コンストラクタでユーザページのURLで正常にインスタンス化できる() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		// verify
		assertDoesNotThrow(() -> new UserPageDocument(url));
	}

	@Test
	void コンストラクタでURLがユーザーページ以外だと例外() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber.com/leaderboard/322826");
		// exercise
		Throwable e = assertThrows(IllegalArgumentException.class, () -> new UserPageDocument(url));
		String actual = e.getMessage();
		String expected = "URL:https://scoresaber.com/leaderboard/322826";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void difficultyが一つ欠けていると例外() throws Exception {
		// setup
		Document d = Jsoup.parse(abcentDifficulty, null);
		Elements mockElements = d.body().select("body div div div div div table tbody tr th");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		UserPageDocument sut = new UserPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("songElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.extract());
		String actual = e.getMessage();
		String expected = "ListSize is difficult. difficultyListSize:7, hashListSize:8, nameListSize:8, ppListSize:8, "
							+ "songURLsSize:8";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void nameとdifficultyとURLが欠けていると例外() throws Exception {
		// setup
		Document d = Jsoup.parse(abcentNameDifficultyURL, null);
		Elements mockElements = d.body().select("body div div div div div table tbody tr th");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		UserPageDocument sut = new UserPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("songElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.extract());
		String actual = e.getMessage();
		String expected = "ListSize is difficult. difficultyListSize:7, hashListSize:8, nameListSize:7, ppListSize:8, "
							+ "songURLsSize:7";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void hashとnameとURLが欠けていると例外() throws Exception{
		// setup
		Document d = Jsoup.parse(abcentHashNameURL, null);
		Elements mockElements = d.body().select("body div div div div div table tbody tr th");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		UserPageDocument sut = new UserPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("songElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.extract());
		String actual = e.getMessage();
		String expected = "ListSize is difficult. difficultyListSize:8, hashListSize:7, nameListSize:7, ppListSize:8, "
							+ "songURLsSize:7";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void ppが欠けていると例外() throws Exception{
		// setup
		Document d = Jsoup.parse(abcentPP, null);
		Elements mockElements = d.body().select("body div div div div div table tbody tr th");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		UserPageDocument sut = new UserPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("songElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.extract());
		String actual = e.getMessage();
		String expected = "ListSize is difficult. difficultyListSize:8, hashListSize:8, nameListSize:8, ppListSize:7, "
							+ "songURLsSize:8";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void nameとURLが欠けていると例外() throws Exception {
		// setup
		Document d = Jsoup.parse(abcentNameURL, null);
		Elements mockElements = d.body().select("body div div div div div table tbody tr th");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		UserPageDocument sut = new UserPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("songElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.extract());
		String actual = e.getMessage();
		String expected = "ListSize is difficult. difficultyListSize:8, hashListSize:8, nameListSize:7, ppListSize:8, "
							+ "songURLsSize:7";
		// verify
		assertThat(actual, is(expected));
	}
}
