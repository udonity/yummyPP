package domain.service;

import lombok.NonNull;

class Phase {
	private AccessInterval interval;

	Phase(@NonNull AccessInterval interval) {
		this.interval = interval;
	}

	void interval() {
		this.interval.interval();
	}

	AccessInterval getInterval() {
		return this.interval;
	}
}
