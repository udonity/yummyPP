package domain.obj.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.obj.SongList;

/**
 * JSONで出力するための集合体クラス
 */
public class JSONElement {
	@JsonProperty("playListTitle")
	private String title;

	@JsonProperty("songs")
	private List<Song> songList;

	public JSONElement() {
	}

	public JSONElement(String title, SongList songList) {
		this.title = title;
		this.songList = songList.getSongs();
	}

	@JsonIgnore
	public String getTitle() {
		return this.title;
	}

	@JsonIgnore
	public List<Song> getSongList() {
		return this.songList;
	}
}
