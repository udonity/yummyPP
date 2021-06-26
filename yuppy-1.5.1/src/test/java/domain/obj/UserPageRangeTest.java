package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

class UserPageRangeTest {

	@Test
	void コンストラクタでmaxStrが空文字の時intMaxが3になる() {
		// setup
		UserPageRange sut = new UserPageRange("");
		// exercise
		int actual = sut.intMax();
		int expected = 3;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void intMinは1固定() {
		// setup
		UserPageRange sut = new UserPageRange("3");
		// exercise
		int actual = sut.intMin();
		int expected = 1;
		// verify
		assertThat(actual, is(expected));
	}
	
	@Test
	void コンストラクタでmaxStrが5の時intMaxは5() {
		// setup
		UserPageRange sut = new UserPageRange("5");
		// exercise
		int actual = sut.intMax();
		int expected = 5;
		// verify
		assertThat(actual, is(expected));
	}

}
