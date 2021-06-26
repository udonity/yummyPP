package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

class CountryTest {

	@Test
	void コンストラクタでcountryStrが空文字の時TypeはGLOBAL() {
		// setup
		Country sut = new Country("");
		// exercise
		Country.Type actual = sut.getType();
		Country.Type expected = Country.Type.GLOBAL;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでcountryStrがglobalの時TypeはGLOBAL() {
		// setup
		Country sut = new Country("global");
		// exercise
		Country.Type actual = sut.getType();
		Country.Type expected = Country.Type.GLOBAL;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでcountryStrがjpの時TypeはJP() {
		// setup
		Country sut = new Country("jp");
		// exercise
		Country.Type actual = sut.getType();
		Country.Type expected = Country.Type.JP;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでcountryStrがxxxの時TypeはGLOBAL() {
		// setup
		Country sut = new Country("xxx");
		// exercise
		Country.Type actual = sut.getType();
		Country.Type expected = Country.Type.GLOBAL;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでcountryStrが空白文字の時TypeはGLOBAL() {
		// setup
		Country sut = new Country(" ");
		// exercise
		Country.Type actual = sut.getType();
		Country.Type expected = Country.Type.GLOBAL;
		// verify
		assertThat(actual, is(expected));
	}

}
