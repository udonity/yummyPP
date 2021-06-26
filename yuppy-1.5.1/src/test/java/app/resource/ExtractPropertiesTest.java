package app.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.obj.Injection;

class ExtractPropertiesTest {
	ExtractProperties sut = null;
	
	@BeforeEach
	public void initSUT() throws MalformedURLException {
		URL url = new File("src\\test\\resources\\config.properties").toURI().toURL();
		sut = new ExtractProperties(url);
	}
	@Test
	void setPropertyでvalueがnullなら空文字がセットされる() {
		sut.setProperty(Injection.COUNTRY, null);
		String expected = "";
		String actual = sut.getProperty(Injection.COUNTRY);
		assertThat(actual, is(expected));
	}

}
