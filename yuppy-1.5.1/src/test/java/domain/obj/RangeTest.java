package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeTest {
	
	@Test
	void コンストラクタでminStrが1でmaxStrが2の時minが1になる() {
		// setup
		Range sut = new Range("1.0", "2.0");
		// exercise
		double expected = 1.0;
		double actual = sut.doubleMin();
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでminStrが1でmaxStrが2の時maxが2になる() {
		// setup
		Range sut = new Range("1.0", "2.0");
		// exercise
		double expected = 2.0;
		double actual = sut.doubleMax();
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでminStrが空白付きの文字列ならトリミングされる() {
		// setup
		Range sut = new Range(" 1.0", "2.0");
		// exercise
		double expected = 1.0;
		double actual = sut.doubleMin();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでminStrが空文字ならminがマイナス1になる() {
		// setup
		Range sut = new Range("", "2.0");
		// exercise
		double expected = -1.0;
		double actual = sut.doubleMin();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでmaxStrが空文字ならmaxがマイナス1になる() {
		// setup
		Range sut = new Range("1.0", "");
		// exercise
		double expected = -1.0;
		double actual = sut.doubleMax();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでminStrが空文字の時maxStrが1ならmaxが1になる() {
		// setup
		Range sut = new Range("", "1");
		// exercise
		double expected = 1.0;
		double actual = sut.doubleMax();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでminStrが空文字の時maxStrが0なら例外() {
		// setup
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Range("", "0"));
		// exercise
		String actual = exception.getMessage();
		String expected = "min:-1.0, max:0.0";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでmaxStrが空文字の時minStrが1ならminが1になる() {
		// setup
		Range sut = new Range("1", "");
		// exercise
		double expected = 1.0;
		double actual = sut.doubleMin();
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでmaxStrが空文字の時minStrが0なら例外() {
		// setup
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Range("0", ""));
		// exercise
		String actual = exception.getMessage();
		String expected = "min:0.0, max:-1.0";
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでminStrが2でmaxStrが1の時例外() {
		// setup
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Range("2", "1"));
		// exercise
		String actual = exception.getMessage();
		String expected = "min:2.0, max:1.0";
		// verify
		assertThat(actual, is(expected));
	}

}
