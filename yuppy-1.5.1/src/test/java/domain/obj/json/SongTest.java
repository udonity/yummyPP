package domain.obj.json;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SongTest {

	@Test
	void hashがスラッシュから始まるとコンストラクタで例外() {
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					new Song("/ABC124", "name", "hard", 200);
				});
		// verify
		assertThat("hash must be [0-9a-zA-Z]{1,}", is(exception.getMessage()));
	}

	@Test
	void hashから空の場合コンストラクタで例外() {
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					new Song("", "name", "hard", 200);
				});
		// verify
		assertThat("hash must be [0-9a-zA-Z]{1,}", is(exception.getMessage()));
	}

	@Test
	void songNameが空の場合コンストラクタで例外() {
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					new Song("ABC1234", "", "hard", 200);
				});
		// verify
		assertThat("songName should not be empty", is(exception.getMessage()));
	}

	@Test
	void difficultyが空の場合コンストラクタで例外() {
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					new Song("ABC1234", "name", "", 200);
				});
		// verify
		assertThat("difficulty should not be empty",
				is(exception.getMessage()));
	}

	@Test
	void ppが0の場合コンストラクタで例外() {
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					new Song("ABC1234", "name", "hard", 0);
				});
		// verify
		assertThat("pp must be greater than 0", is(exception.getMessage()));
	}

	@Test
	void starが0の場合setStarで例外() {
		// setup
		Song sut = new Song("ABC1234", "name", "hard", 100);
		// exercise
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					sut.setStar(0);
				});
		// verify
		assertThat("star must be greater than 0", is(exception.getMessage()));
	}

	@Test
	void Starがない状態でhashのみが等しければハッシュ値は等しい() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH", "name2", "hard2", 200);
		// exercise
		int actual = sut.hashCode();
		int expected = sut2.hashCode();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void Starこみの状態でhashのみが等しければハッシュ値は等しい() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		sut.setStar(1);
		Song sut2 = new Song("HASH", "name2", "hard2", 200);
		sut2.setStar(2);
		// exercise
		int actual = sut.hashCode();
		int expected = sut2.hashCode();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void Starがない状態でハッシュのみが異なっているとハッシュ値は異なる() {
		//setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH2", "name", "hard", 100);
		// exercise
		int actual = sut.hashCode();
		int expected = sut2.hashCode();
		// verify
		assertThat(actual, not(expected));
	}

	@Test
	void Starがある状態でハッシュのみが異なっているとハッシュ値は異なる() {
		//setup
		Song sut = new Song("HASH", "name", "hard", 100);
		sut.setStar(1);
		Song sut2 = new Song("HASH2", "name", "hard", 100);
		sut2.setStar(1);
		// exercise
		int actual = sut.hashCode();
		int expected = sut2.hashCode();
		// verify
		assertThat(actual, not(expected));
	}

	@Test
	void pp100とpp200でcompareToを使うとマイナス1が返ってくる() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH", "name2", "hard2", 200);
		// exercise
		int actual = sut.compareTo(sut2);
		int expected = -1;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void pp100とpp100でcompareToを使うと0が返ってくる() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH", "name2", "hard2", 100);
		// exercise
		int actual = sut.compareTo(sut2);
		int expected = 0;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void pp200とpp100でcompareToを使うと1が返ってくる() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 200);
		Song sut2 = new Song("HASH", "name2", "hard2", 100);
		// exercise
		int actual = sut.compareTo(sut2);
		int expected = 1;
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	void Starが無い状態でハッシュ以外が異なっていてもオブジェクトは等価になる() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH", "name2", "hard2", 200);
		// exercise
		boolean expectedTrue = sut.equals(sut2);
		// verify
		assertTrue(expectedTrue);
	}

	@Test
	void Starこみの状態でハッシュ以外が異なっていてもオブジェクトは等価になる() {
		// setup
		Song sut = new Song("HASH", "name", "hard", 100);
		sut.setStar(1);
		Song sut2 = new Song("HASH", "name2", "hard2", 200);
		sut2.setStar(2);
		// exercise
		boolean expectedTrue = sut.equals(sut2);
		// verify
		assertTrue(expectedTrue);
	}

	@Test
	void Starが無い状態でハッシュのみが異なっているとオブジェクトは等価ではない() {
		//setup
		Song sut = new Song("HASH", "name", "hard", 100);
		Song sut2 = new Song("HASH2", "name", "hard", 100);
		// exercise
		boolean expectedFalse = sut.equals(sut2);
		assertFalse(expectedFalse);
	}

	@Test
	void Starこみの状態でハッシュのみが異なっているとオブジェクトは等価ではない() {
		//setup
		Song sut = new Song("HASH", "name", "hard", 100);
		sut.setStar(1);
		Song sut2 = new Song("HASH2", "name", "hard", 100);
		sut2.setStar(1);
		// exercise
		boolean expectedFalse = sut.equals(sut2);
		assertFalse(expectedFalse);

	}

}
