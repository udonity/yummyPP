package domain.obj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.obj.json.Song;
import lombok.NonNull;

public class SongList {
	private List<Song> songs = new ArrayList<>();

	public void add(@NonNull Song song) {
		this.songs.add(song);
	}

	public void addAll(@NonNull SongList songList) {
		this.songs.addAll(songList.getSongs());
	}

	public void removeAll(@NonNull Collection<Song> collection) {
		this.songs.removeAll(collection);
	}

	public void filterPP(double min, double max) {
		Set<Song> removeList = new HashSet<>();
		for (Song s : this.songs) {
			if (min != -1 && s.getPP() < min)
				removeList.add(s);
			if (max != -1 && s.getPP() > max)
				removeList.add(s);
		}
		this.songs.removeAll(removeList);
	}

	public void filterStar(double min, double max) {
		Set<Song> removeList = new HashSet<>();
		for (Song s : this.songs) {
			if (min != -1 && s.getStar() < min)
				removeList.add(s);
			if (max != -1 && s.getStar() > max)
				removeList.add(s);
		}
		this.songs.removeAll(removeList);
	}

	public void sort() {
		this.songs.sort(Song::compareTo);
	}

	public void removeDuplicate() {
		Set<Song> checker = new HashSet<>(this.songs);
		this.songs = new ArrayList<>(checker);
	}

	public List<Song> getSongs() {
		return this.songs;
	}

	public boolean isEmpty() {
		return this.songs.isEmpty();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Song song : this.songs) {
			sb.append(song.toString()).append("\n");
		}
		return sb.toString();
	}
}
