package domain.service.net;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import domain.obj.RankingRange;

class RankingPageDocumentTest {

	@Test
	void コンストラクタで正常なグローバルランキングのURLを受け取り例外を投げない() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber.com/global/2");
		// verify
		assertDoesNotThrow(() -> new RankingPageDocument(url));
	}

	@Test
	void コンストラクタで正常な日本ランキングのURLを受け取り例外を投げない() throws MalformedURLException {
		// setup
		URL url = new URL("https://scoresaber.com/global/2&country=jp");
		// verify
		assertDoesNotThrow(() -> new RankingPageDocument(url));
	}

	/**
	 * Elementsをモック化してテストする
	 * @throws Exception
	 */
	@Test
	void extractでJPランキングを読み込んだ後rankListの状態が正常() throws Exception {
		// setup
		RankingPageDocument sut = new RankingPageDocument(new URL("https://scoresaber.com/global/2&country=jp"));
		Document d = Jsoup.parse(new File("src\\test\\resources\\ranking_page_jp_correct.html"), null);
		Elements mockElements = d.body().select("div div div div div div div table tbody tr");
		Field[] fs = sut.getClass().getDeclaredFields();
		Field eField = null;
		Field rlField = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankingElements")) {
				eField = fd;
			}
			if(fd.getName().equals("rankList")) {
				rlField = fd;
			}
		}
		eField.setAccessible(true);
		eField.set(sut, mockElements);
		rlField.setAccessible(true);
		// exercise
		sut.extract();
		@SuppressWarnings("unchecked")
		List<Integer> actual = (List<Integer>) rlField.get(sut);
		List<Integer> expected = new ArrayList<>();
		for(int i = 51; i <= 100; i++) {
			expected.add(i);
		}
		// verify
		assertThat(actual, is(expected));
	}

	/**
	 * Elementsをモック化してテストする
	 * @throws Exception
	 */
	@Test
	void extractでGLOBALランキングを読み込んだ後rankListの状態が正常() throws Exception {
		// setup
		RankingPageDocument sut = new RankingPageDocument(new URL("https://scoresaber.com/global/2&country=jp"));
		Document d = Jsoup.parse(new File("src\\test\\resources\\ranking_page_global_correct.html"), null);
		Elements mockElements = d.body().select("div div div div div div div table tbody tr");
		Field[] fs = sut.getClass().getDeclaredFields();
		Field eField = null;
		Field rlField = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankingElements")) {
				eField = fd;
			}
			if(fd.getName().equals("rankList")) {
				rlField = fd;
			}
		}
		eField.setAccessible(true);
		eField.set(sut, mockElements);
		rlField.setAccessible(true);
		// exercise
		sut.extract();
		@SuppressWarnings("unchecked")
		List<Integer> actual = (List<Integer>) rlField.get(sut);
		List<Integer> expected = new ArrayList<>();
		for(int i = 51; i <= 100; i++) {
			expected.add(i);
		}
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void extractを実行していない状態でobtainRankToURLMapを実行しても例外を投げない() throws Exception {
		// setup
		URL url = new URL("https://scoresaber.com/global/2");
		RankingPageDocument sut = new RankingPageDocument(url);
		RankingRange rangeMock = mock(RankingRange.class);
		when(rangeMock.intMin()).thenReturn(55);
		when(rangeMock.intMax()).thenReturn(60);
		// verify
		assertDoesNotThrow(() -> sut.obtainRankToURLMap(rangeMock));
	}

	/**
	 * RankingRageをモック化してテストする
	 * @throws Exception
	 */
	@Test
	void extractを実行した後obtainRankToURLMapを実行しても例外を投げない() throws Exception {
		// setup
		URL url = new URL("https://scoresaber.com/global/2");
		RankingPageDocument sut = new RankingPageDocument(url);
		RankingRange rangeMock = mock(RankingRange.class);
		when(rangeMock.intMin()).thenReturn(55);
		when(rangeMock.intMax()).thenReturn(60);
		// exercies
		sut.extract();
		// verify
		assertDoesNotThrow(() -> sut.obtainRankToURLMap(rangeMock));
	}

	/**
	 * Elements,RankingRageをモック化してテストする
	 * @throws Exception
	 */
	@Test
	void obtainRankToURLで正常なMapを取得できる() throws Exception{

		// setup

		RankingPageDocument sut = new RankingPageDocument(new URL("https://scoresaber.com/global/2&country=jp"));
		// Elementsのモックをセット
		Document d = Jsoup.parse(new File("src\\test\\resources\\ranking_page_jp_correct.html"), null);
		Elements mockElements = d.body().select("div div div div div div div table tbody tr");
		Field[] fs = sut.getClass().getDeclaredFields();
		Field eField = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankingElements")) {
				eField = fd;
			}
		}
		eField.setAccessible(true);
		eField.set(sut, mockElements);
		// RankingRangeのモックをセット
		RankingRange rangeMock = mock(RankingRange.class);
		when(rangeMock.intMin()).thenReturn(55);
		when(rangeMock.intMax()).thenReturn(60);

		// exercise

		sut.extract();
		Map<Integer, URL> actual = sut.obtainRankToURLMap(rangeMock);
		Map<Integer, URL> expected = new TreeMap<>();
		expected.put(55, new URL("https://scoresaber.com/u/76561198948522366"));
		expected.put(56, new URL("https://scoresaber.com/u/76561198267730787"));
		expected.put(57, new URL("https://scoresaber.com/u/76561198964854544"));
		expected.put(58, new URL("https://scoresaber.com/u/76561198356647464"));
		expected.put(59, new URL("https://scoresaber.com/u/3248335171894947"));
		expected.put(60, new URL("https://scoresaber.com/u/76561199039416835"));

		// verify

		assertThat(actual, is(expected));
	}
	
	@Test
	void obtainRankToURLでRankingRangeの範囲で該当がないと空のMapを返す() throws Exception{

		// setup

		RankingPageDocument sut = new RankingPageDocument(new URL("https://scoresaber.com/global/2&country=jp"));
		// Elementsのモックをセット
		Document d = Jsoup.parse(new File("src\\test\\resources\\ranking_page_jp_correct.html"), null);
		Elements mockElements = d.body().select("div div div div div div div table tbody tr");
		Field[] fs = sut.getClass().getDeclaredFields();
		Field eField = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankingElements")) {
				eField = fd;
			}
		}
		eField.setAccessible(true);
		eField.set(sut, mockElements);
		// RankingRangeのモックをセット
		RankingRange rangeMock = mock(RankingRange.class);
		when(rangeMock.intMin()).thenReturn(150);
		when(rangeMock.intMax()).thenReturn(155);

		// exercise

		sut.extract();
		Map<Integer, URL> actual = sut.obtainRankToURLMap(rangeMock);

		// verify

		assertTrue(actual.isEmpty());
	}

}
