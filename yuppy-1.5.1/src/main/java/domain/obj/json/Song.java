package domain.obj.json;

import java.net.URL;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Song implements Comparable<Song> {

	@JsonProperty("hash")
	private final String hash;

	@JsonProperty("songName")
	private final String songName;

	@JsonIgnore
	private final String difficulty;

	@JsonIgnore
	private final double pp;

	@JsonIgnore
	private double star;

	@JsonIgnore
	private URL songURL;

	@JsonIgnore
	private static final String FORMAT = "%3.2fpp : hash = %s, %-8s, songName = %s";

	@JsonIgnore
	private static final String FORMAT_STAR = "%3.2fpp : star = %.1f, hash : %s, %-8s, songName = %s";

	public Song(@NonNull String hash, @NonNull String songName,
			@NonNull String difficulty, double pp) {
		if (!hash.matches("[0-9a-zA-Z]{1,}")) {
			IllegalArgumentException e = new IllegalArgumentException(
					"hash must be [0-9a-zA-Z]{1,}");
			log.error("Hash:" + hash + ", songName:" + songName, e);
			throw e;
		}
		this.hash = hash;
		if (songName.isEmpty()) {
			IllegalArgumentException e = new IllegalArgumentException(
					"songName should not be empty");
			log.error("Hash:" + hash, e);
			throw e;
		}
		this.songName = songName;
		if (difficulty.isEmpty()) {
			IllegalArgumentException e = new IllegalArgumentException(
					"difficulty should not be empty");
			log.error("Hash:" + hash + ", songName:" + songName, e);
			throw e;
		}
		this.difficulty = difficulty;
		if (pp <= 0.0D) {
			IllegalArgumentException e = new IllegalArgumentException(
					"pp must be greater than 0");
			log.error("Hash:" + hash + ", songName:" + songName + ", pp:" + pp,
					e);
			throw e;
		}
		this.pp = pp;
	}

	@JsonIgnore
	public void setURL(@NonNull URL url) {
		Objects.requireNonNull(url);
		this.songURL = url;
	}

	@JsonIgnore
	public URL getSongURL() {
		return this.songURL;
	}

	@JsonIgnore
	public void setStar(double star) {
		if (star <= 0.0D) {
			IllegalArgumentException e = new IllegalArgumentException(
					"star must be greater than 0");
			log.error("Star:" + star, e);
			throw e;
		}
		this.star = star;
	}

	@JsonIgnore
	public double getPP() {
		return this.pp;
	}

	@JsonIgnore
	public double getStar() {
		return this.star;
	}

	@JsonIgnore
	public String toString() {
		if (this.star == 0.0D) {
			return String.format(FORMAT, this.pp, this.hash, this.difficulty,
					this.songName);
		}
		return String.format(FORMAT_STAR, this.pp, this.star, this.hash,
				this.difficulty, this.songName);
	}

	@JsonIgnore
	public int compareTo(Song o) {
		return Double.compare(this.pp, o.pp);
	}

	@JsonIgnore
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.hash == null) ? 0 : this.hash.hashCode());
		return result;
	}

	@JsonIgnore
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		if (this.hash == null) {
			if (other.hash != null)
				return false;
		} else if (!this.hash.equals(other.hash)) {
			return false;
		}
		return true;
	}
}
