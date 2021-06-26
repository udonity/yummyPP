package domain.service.net;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import domain.obj.Country;

class MyPageDocumentTest {

	@Test
	void コンストラクタで正常なURLを受け取り例外を投げない() throws Exception {
		// setup
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		// verify
		assertDoesNotThrow(() -> new MyPageDocument(url));
	}
	
	@Test
	void extract後正常なJPランク数を取得できる() throws Exception {
		// setup
		// モックを使って検証（インスタンス化した後Elementsを差し替える）
		Document docu = Jsoup.parse(new File("src\\test\\resources\\my_page_correct.html"), null);
		Elements mockElements = docu.body().select("div div div div div div");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		MyPageDocument sut = new MyPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		sut.extract();
		Map<Country, Integer> deliverables = sut.getRank();
		int actual = deliverables.get(new Country("jp"));
		int expected = 664;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void extract後正常なGLOBALランク数を取得できる() throws Exception {
		// setup
		// モックを使って検証（インスタンス化した後Elementsを差し替える）
		Document docu = Jsoup.parse(new File("src\\test\\resources\\my_page_correct.html"), null);
		Elements mockElements = docu.body().select("div div div div div div");
		URL url = new URL("https://scoresaber.com/u/76561198151452196");
		MyPageDocument sut = new MyPageDocument(url);
		Field[] fs = sut.getClass().getDeclaredFields();
		Field f = null;
		for(Field fd : fs) {
			if(fd.getName().equals("rankElements")) {
				f = fd;
			}
		}
		f.setAccessible(true);
		f.set(sut, mockElements);
		// exercise
		sut.extract();
		Map<Country, Integer> deliverables = sut.getRank();
		int actual = deliverables.get(new Country("global"));
		int expected = 10372;
		// verify
		assertThat(actual, is(expected));
	}

}
