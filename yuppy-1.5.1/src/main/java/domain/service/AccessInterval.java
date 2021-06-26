package domain.service;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class AccessInterval {
	private static final long SEC = 1100; // 1.1秒
	private long startTime = -1;
	private final long span;

	AccessInterval() {
		this.span = TimeUnit.NANOSECONDS.convert(SEC, TimeUnit.MILLISECONDS);
	}

	/**
	 * インスタンス化してから1回目のinterval()は間隔をあけない。
	 * 1秒以上待機する事を保障する。(実測値1.1秒前後)
	 */
	void interval() {
		if (this.startTime == -1) {
			this.startTime = System.nanoTime();
			return;
		}
		long elapsedTime = System.nanoTime() - this.startTime;
		if (elapsedTime < this.span) {
			long waitTime = this.span - elapsedTime;
			try {
				Thread.sleep(TimeUnit.MILLISECONDS.convert(waitTime, TimeUnit.NANOSECONDS));
			} catch (InterruptedException e) {
				log.warn("Thread was interrupted.", e);
			}
		}
		this.startTime = System.nanoTime();
	}
}
