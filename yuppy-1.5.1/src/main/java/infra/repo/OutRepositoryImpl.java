package infra.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.obj.SongList;
import domain.obj.json.JSONElement;
import domain.obj.json.Song;
import domain.repo.OutRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * OutRepositoryの実装クラス。
 * ファイルへの出力を担う。
 */
@Slf4j
class OutRepositoryImpl implements OutRepository{
	/**
	 * 出力する曲リスト
	 */
	private SongList songList;

	public OutRepositoryImpl(@NonNull SongList songList) {
		this.songList = songList;
	}

	@Override
	public void storeText(@NonNull String title) {
		log.info("Output to Notepad.");
		File f = new File(title + ".txt");
		// UTF-8で出力
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), "UTF-8")) {
			BufferedWriter bw = new BufferedWriter(osw);
			for (Song song : this.songList.getSongs()) {
				bw.write(song.toString());
				bw.newLine();
				bw.flush();
			}
		} catch (UnsupportedEncodingException e) {
			log.warn("Encoding failure. SongList:" + this.songList.toString(), e);
		} catch (FileNotFoundException e) {
			log.warn("File NotFound. Path:" + f.toURI().toString(), e);
		} catch (IOException e) {
			log.warn("Writing failed. SongList:" + this.songList.toString(), e);
		}
	}

	@Override
	public void storeJSON(@NonNull String title) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			File jsonFile = new File(title + ".json");
			JSONElement element = new JSONElement(title, songList);
			mapper.writeValue(jsonFile, element);
		} catch (IOException e) {
			log.error("Could not output to JSON. FileTitle:" + title + ".json");
		}
	}
}
