package domain.obj;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.obj.json.Song;

class SongListTest {
	SongList sut = null;

	@BeforeEach
	void 毎回コンストラクタをnew() {
		sut = new SongList();
	}

	@Test
	void 追加したSongとゲッターから得たSongが等価() {
		sut.add(new Song("A1B2C3D4E5F6G", "Name", "Hard", 111.1));
		Song expected = new Song("A1B2C3D4E5F6G", "Name", "Hard", 111.1);
		Song actual = sut.getSongs().get(0);

		assertThat(actual, is(expected));
	}

	@Test
	void addAllで追加した内容とaddで追加した内容が等価() {
		// setup
		SongList addList = new SongList();
		addList.add(new Song("AAA", "Name1", "Hard1", 111.1));
		addList.add(new Song("BBB", "Name2", "Hard2", 111.2));
		addList.add(new Song("CCC", "Name3", "Hard3", 111.3));
		List<Song> expectedList = addList.getSongs();
		// exercise
		sut.addAll(addList);
		// verify
		List<Song> actuals = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			assertThat(actuals.get(i), is(expectedList.get(i)));
		}
	}

	@Test
	void addで追加したSongをremoveAllで空になる() {
		// setup
		List<Song> removeList = new ArrayList<>();
		removeList.add(new Song("AAA", "Name1", "Hard1", 111.1));
		removeList.add(new Song("BBB", "Name2", "Hard2", 111.2));
		removeList.add(new Song("CCC", "Name3", "Hard3", 111.3));
		sut.add(new Song("AAA", "Name1", "Hard1", 111.1));
		sut.add(new Song("BBB", "Name2", "Hard2", 111.2));
		sut.add(new Song("CCC", "Name3", "Hard3", 111.3));
		// exercise
		sut.removeAll(removeList);
		List<Song> actual = sut.getSongs();
		// verify
		assertTrue(actual.isEmpty());
	}

	@Test
	void filterPPでminとmaxがマイナス1ならフィルターしない() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		expectedList.add(new Song("AAA", "Name1", "Hard1", 111.1));
		expectedList.add(new Song("BBB", "Name2", "Hard2", 111.2));
		expectedList.add(new Song("CCC", "Name3", "Hard3", 111.3));
		sut.add(new Song("AAA", "Name1", "Hard1", 111.1));
		sut.add(new Song("BBB", "Name2", "Hard2", 111.2));
		sut.add(new Song("CCC", "Name3", "Hard3", 111.3));
		// exercise
		sut.filterPP(-1, -1);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterPPでminのみマイナス1ならmaxフィルターが機能する() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		expectedList.add(new Song("AAA", "Name1", "Hard1", 100.0));
		expectedList.add(new Song("BBB", "Name2", "Hard2", 200.0)); // max200.0は含む
		sut.add(new Song("AAA", "Name1", "Hard1", 100.0));
		sut.add(new Song("BBB", "Name2", "Hard2", 200.0));
		sut.add(new Song("CCC", "Name3", "Hard3", 300.0));
		// exercise
		sut.filterPP(-1, 200);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterPPでmaxのみマイナス1ならminフィルターが機能する() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		expectedList.add(new Song("BBB", "Name2", "Hard2", 200.0));
		expectedList.add(new Song("CCC", "Name3", "Hard3", 300.0));
		sut.add(new Song("AAA", "Name1", "Hard1", 100.0));
		sut.add(new Song("BBB", "Name2", "Hard2", 200.0));
		sut.add(new Song("CCC", "Name3", "Hard3", 300.0));
		// exercise
		sut.filterPP(200, -1);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterPPでminとmaxの指定でフィルターできる() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		expectedList.add(new Song("BBB", "Name2", "Hard2", 111.2));
		expectedList.add(new Song("CCC", "Name3", "Hard3", 111.3));
		sut.add(new Song("AAA", "Name1", "Hard1", 111.1));
		sut.add(new Song("BBB", "Name2", "Hard2", 111.2));
		sut.add(new Song("CCC", "Name3", "Hard3", 111.3));
		sut.add(new Song("DDD", "Name4", "Hard4", 111.4));
		// exercise
		sut.filterPP(111.2, 111.3); // 111.2と111.3を含む
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterStarでminとmaxがマイナス1ならフィルターしない() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		Song song1 = new Song("AAA", "Name1", "Hard1", 111.1);
		song1.setStar(1);
		Song song2 = new Song("BBB", "Name2", "Hard2", 111.2);
		song2.setStar(2);
		Song song3 = new Song("CCC", "Name3", "Hard3", 111.3);
		song3.setStar(3);
		expectedList.add(song1);
		expectedList.add(song2);
		expectedList.add(song3);
		sut.add(song1);
		sut.add(song2);
		sut.add(song3);
		// exercise
		sut.filterStar(-1, -1);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterStarでminのみマイナス1ならmaxフィルターが機能する() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		Song song1 = new Song("AAA", "Name1", "Hard1", 111.1);
		song1.setStar(1);
		Song song2 = new Song("BBB", "Name2", "Hard2", 111.2);
		song2.setStar(2);
		Song song3 = new Song("CCC", "Name3", "Hard3", 111.3);
		song3.setStar(3);
		expectedList.add(song1);
		expectedList.add(song2);
		sut.add(song1);
		sut.add(song2);
		sut.add(song3);
		// exercise
		sut.filterStar(-1, 2);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

	@Test
	void filterStarでmaxのみマイナス1ならminフィルターが機能する() {
		// setup
		List<Song> expectedList = new ArrayList<>();
		Song song1 = new Song("AAA", "Name1", "Hard1", 111.1);
		song1.setStar(1);
		Song song2 = new Song("BBB", "Name2", "Hard2", 111.2);
		song2.setStar(2);
		Song song3 = new Song("CCC", "Name3", "Hard3", 111.3);
		song3.setStar(3);
		expectedList.add(song2);
		expectedList.add(song3);
		sut.add(song1);
		sut.add(song2);
		sut.add(song3);
		// exercise
		sut.filterStar(2, -1);
		// verify
		List<Song> actualList = sut.getSongs();
		for (int i = 0; i < expectedList.size(); i++) {
			Song actual = actualList.get(i);
			Song expected = expectedList.get(i);
			assertThat(actual, is(expected));
		}
	}

}
