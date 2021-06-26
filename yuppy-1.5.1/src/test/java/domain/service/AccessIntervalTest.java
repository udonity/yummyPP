package domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class AccessIntervalTest {

	@Test
	void コンストラクタで例外を投げない() {
		// verify
		
		assertDoesNotThrow(() -> new AccessInterval());
	}
	
	@Test
	void インスタンス化して1回目にintervalを呼び出すと1秒以上待機しない() {
		// setup
		
		AccessInterval sut = new AccessInterval();
		
		// exercise
		
		long start = System.nanoTime();
		sut.interval();
		long end = System.nanoTime();
		long actual = end - start;
		long bound = TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
		
		// verify
		
		assertTrue(actual < bound);
	}
	
	@Test
	void intervalを2回目に呼び出すと1秒以上待機する() {
		// setup
		
		AccessInterval sut = new AccessInterval();
		
		// exercise
		
		sut.interval();
		long start = System.nanoTime();
		sut.interval();
		long end = System.nanoTime();
		long actual = end - start;
		long bound = TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
		
		// verify
		
		assertTrue(actual >= bound);
	}
	
	@Test
	void intervalを3回目に呼び出すと1秒以上待機する() {
		// setup
		
		AccessInterval sut = new AccessInterval();
		
		// exercise
		
		sut.interval();
		sut.interval();
		long start = System.nanoTime();
		sut.interval();
		long end = System.nanoTime();
		long actual = end - start;
		long bound = TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
		
		// verify
		
		assertTrue(actual >= bound);
	}

}
