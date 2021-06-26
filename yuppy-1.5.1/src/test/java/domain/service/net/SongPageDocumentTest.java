package domain.service.net;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

class SongPageDocumentTest {

	@Test
	void コンストラクタで正常なURLの場合例外を投げない() throws Exception {
		// setup
		
		URL url = new URL("https://scoresaber.com/leaderboard/280301");
		
		// verify
		
		assertDoesNotThrow(() -> new SongPageDocument(url));
	}
	
	@Test
	void 正常な状態でextractを実行しても例外を投げない() throws Exception {
		// setup
		
		URL url = new URL("https://scoresaber.com/leaderboard/280301");
		SongPageDocument sut = new SongPageDocument(url);

		// verify
		
		assertDoesNotThrow(() -> sut.extract());
	}

	/**
	 * 固定の結果を得るためにElementをモック化する。
	 * @throws Exception
	 */
	@Test
	void 正常にstarを取得できる() throws Exception {
		// setup
		
		URL url = new URL("https://scoresaber.com/leaderboard/280301");
		SongPageDocument sut = new SongPageDocument(url);
		// Elementのモック化
		Document d = Jsoup.parse(new File("src\\test\\resources\\song_correct.html"), null);
		Element mockElement = d.head();
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("element")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElement);
		
		// exercise
		
		sut.extract();
		double actual = sut.getStar();
		double expected = 14;

		// verify
		
		assertThat(actual, is(expected));
	}
	
	@Test
	void 正常な状態でextractを実行せずにgetStarを行ってもstarを取得できる() throws Exception {
		// setup
		
		URL url = new URL("https://scoresaber.com/leaderboard/280301");
		SongPageDocument sut = new SongPageDocument(url);
		// Elementのモック化
		Document d = Jsoup.parse(new File("src\\test\\resources\\song_correct.html"), null);
		Element mockElement = d.head();
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("element")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElement);
		
		// exercise
		
		double actual = sut.getStar();
		double expected = 14;

		// verify
		
		assertThat(actual, is(expected));
	}
	
	@Test
	void starを正常に取得できなかった時getStarで例外() throws Exception{
		// setup
		
		// extractで何も行わないように設定
		URL url = new URL("https://scoresaber.com/leaderboard/280301");
		SongPageDocument sutSpy = spy(new SongPageDocument(url));
		doNothing().when(sutSpy).extract();
		// Elementのモック化
		Document d = Jsoup.parse(new File("src\\test\\resources\\song_correct.html"), null);
		Element mockElement = d.head();
		Field[] fs = SongPageDocument.class.getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("element")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sutSpy, mockElement);
		
		// exercise
		
		sutSpy.extract();
		Throwable e = assertThrows(IllegalStateException.class, () -> sutSpy.getStar());
		String actual = e.getMessage();
		String expected = "Failed to write star. Element:\n" + mockElement.toString();

		// verify
		
		assertThat(actual, is(expected));
	}

}
