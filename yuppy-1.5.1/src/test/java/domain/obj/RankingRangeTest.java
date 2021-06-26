package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Countryは移譲のみで副作用はない。
 */
class RankingRangeTest {
	Country ctry;

	@BeforeEach
	void countryスタブ() {
		ctry = new Country("global");
	}

	@Test
	void コンストラクタでrangeStrが空文字の時setRangeを行うとランク幅が6になる() {
		// setup
		RankingRange sut = new RankingRange("", ctry);
		sut.setRange(5);
		// exercise
		int actual = sut.intMax() - sut.intMin();
		int expected = 6;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void コンストラクタでrangeStrが1の時setRangeを行うとランク幅が2になる() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		sut.setRange(5);
		// exercise
		int actual = sut.intMax() - sut.intMin();
		int expected = 2;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void setRangeでmyRankが0の時例外() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		Throwable e = assertThrows(IllegalArgumentException.class, () -> sut.setRange(0));
		// exercise
		String actual = e.getMessage();
		String expected = "MyRank must be greater than 0. myRank:0";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void レンジが3でmyRankが1の時intMinが1() {
		// setup
		RankingRange sut = new RankingRange("3", ctry);
		sut.setRange(1);
		// exercise
		int actual = sut.intMin();
		int expected = 1;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void レンジが1でmyRankが3の時intMinが2() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		sut.setRange(3);
		// exercise
		int actual = sut.intMin();
		int expected = 2;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void setRangeの前にgetMyRankを行うと例外() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.getMyRank());
		// exercise
		String actual = e.getMessage();
		String expected = "MyRank not set. MyRank:-1";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void setRangeの前にintMaxを行うと例外() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.intMax());
		// exercise
		String actual = e.getMessage();
		String expected = "MyRank not set. MyRank:-1";
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void setRangeの前にintMinを行うと例外() {
		// setup
		RankingRange sut = new RankingRange("1", ctry);
		Throwable e = assertThrows(IllegalStateException.class, () -> sut.intMin());
		// exercise
		String actual = e.getMessage();
		String expected = "MyRank not set. MyRank:-1";
		// verify
		assertThat(actual, is(expected));
	}

}
