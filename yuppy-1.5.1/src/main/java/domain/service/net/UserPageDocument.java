package domain.service.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import domain.obj.Range;
import domain.obj.SongList;
import domain.obj.json.Song;
import domain.service.UserSongExtractor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザーページ上のドキュメントを取得するクラス。
 * 難易度・ハッシュ・タイトル・PP・曲詳細ページへのURLそれぞれの情報を一つずつ取得し、
 * 各リストへ格納。その後それぞれのリストからSongListへ整形する。
 */
@Slf4j
class UserPageDocument extends ScoreSaberDocument implements UserSongExtractor{
	/**
	 * 難易度（Hard,Expertなど)
	 */
	private List<String> difficultyList;

	/**
	 * 曲の一意な文字列を表すハッシュ
	 */
	private List<String> hashList;

	/**
	 * 曲のタイトル
	 */
	private List<String> nameList;

	/**
	 * PP
	 */
	private List<Double> ppList;

	/**
	 * 曲詳細ページへのURL
	 */
	private List<URL> songURLs;

	/**
	 * HTML内の曲の情報部分
	 */
	private Elements songElements;

	/**
	 * ユーザーページで1ページ内には必ず8曲分の情報があるので、それぞれのリストサイズは8となる。
	 */
	private static final int size = 8;

	/**
	 * @param url ユーザーページへのURL
	 */
	public UserPageDocument(URL url) {
		super(url);
		// ユーザページかチェック
		if(!url.toString().contains("/u/")) {
			IllegalArgumentException e = new IllegalArgumentException("URL:" + url.toString());
			log.error("URL is Illegal.", e);
			throw e;
		}
		this.songElements = super.getDocument().body().select("body div div div div div table tbody tr th");
	}

	/**
	 * 難易度・ハッシュ・タイトル・PP・URLそれぞれの情報をDocumentより取得する。
	 */
	@Override
	public void extract() {
		this.difficultyList = songElements.select("div div span[style]").stream()
								.map(Element::text)
								.collect(Collectors.toList());

		this.hashList = songElements.select("div div img").stream()
						.map(UserPageDocument::elementToHashText)
						.collect(Collectors.toList());

		this.nameList = songElements.select("div div a").stream()
						.map(Element::text)
						.collect(Collectors.toList());

		this.ppList = songElements.select("span[class=scoreTop ppValue]").stream()
						.map(Element::text)
						.map(Double::valueOf)
						.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

		this.songURLs = songElements.select("th div div a").stream()
						.map(Node::toString)
						.map(UserPageDocument::textToURL)
						.collect(Collectors.toList());

		// 長さチェック
		if(this.difficultyList.size() != size
			|| this.hashList.size() != size
			|| this.nameList.size() != size
			|| this.ppList.size() != size
			|| this.songURLs.size() != size) {
				IllegalStateException e = new IllegalStateException("ListSize is difficult. difficultyListSize:"
				 							+ this.difficultyList.size() + ", "
				 							+ "hashListSize:" + this.hashList.size() + ", "
				 							+ "nameListSize:" + this.nameList.size() + ", "
				 							+ "ppListSize:" + this.ppList.size() + ", "
				 							+ "songURLsSize:" + this.songURLs.size());
				log.warn("Size is difficult.",e);
				throw e;
		}
	}
	/**
	 * 指定されたPPレンジ内の曲を収集し、SongListとして返す。
	 */
	@Override
	public SongList obtainSongList(@NonNull Range ppRange) {
		double ppMin = ppRange.doubleMin();
		double ppMax = ppRange.doubleMax();
		SongList songs = new SongList();

		int reply = this.hashList.size();
		for (int i = 0; i < reply; i++) {
			double pp = this.ppList.get(i);
			if (ppMin != -1 && pp < ppMin) {
				continue;
			}
			if (ppMax != -1 && pp > ppMax) {
				continue;
			}
			String difficulty = this.difficultyList.get(i);
			String hash = this.hashList.get(i);
			String songName = this.nameList.get(i);
			URL url = this.songURLs.get(i);
			Song s = new Song(hash, songName, difficulty, pp);
			s.setURL(url);
			songs.add(s);
		}
		Objects.requireNonNull(songs);
		return songs;
	}

	/**
	 * Streamのヘルパーメソッド。
	 * Elementをテキストへ変換しトリミングした状態で返す。
	 * @param e Element
	 * @return 文字列
	 */
	private static String elementToHashText(Element e) {
		 return e.toString().substring(32, 72);
	}

	/**
	 * Streamのヘルパーメソッド。
	 * 文字列をトリミングし、URLへと変換したものを返す。
	 * @param text 文字列
	 * @return URL
	 */
	private static URL textToURL(String text) {
		 int startIndex = text.indexOf("\"") + 1;
		 int endIndex = text.indexOf(">") - 1;
		 URL result = null;
		 try {
			 result = new URL("https://scoresaber.com" + text.substring(startIndex, endIndex));
		 } catch(MalformedURLException e) {
			 log.warn("URL:" + text.substring(startIndex, endIndex), e);
		 }
		 return result;
	}


}
