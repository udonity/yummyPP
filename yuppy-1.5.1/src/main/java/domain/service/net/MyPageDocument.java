package domain.service.net;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.obj.Country;
import domain.service.MyPageExtractor;

/**
 * 自分のページのドキュメントを表すクラス。
 */
class MyPageDocument extends UserPageDocument implements MyPageExtractor{
	/**
	 * マイページのランク情報Elements
	 */
	private Elements rankElements;

	/**
	 * 日本ランキングとグローバルランキングを持つマップ
	 */
	private Map<Country, Integer> countryToRank;

	/**
	 * @param url マイページのURL
	 */
	public MyPageDocument(URL url) {
		// 親クラスで1ページ目の曲情報を取得する。
		super(url);
		this.countryToRank = new HashMap<>();
		Document docu = super.getDocument();
		this.rankElements = docu.select("div div div div div div");

		Objects.requireNonNull(this.rankElements);
	}

	@Override
	public Map<Country, Integer> getRank() {
		return this.countryToRank;
	}

	/**
	 * マイページ上のランキング情報を取得する。
	 */
	@Override
	public void extract() {
		super.extract();
		List<Integer> rankList = this.rankElements.select("ul li a").stream()
										.map(Element::text)
										.flatMap(str -> Stream.of(str.replaceAll("[#,]", "")))
										.map(Integer::valueOf)
										.collect(Collectors.toList());
		Country global = new Country("global");
		this.countryToRank.put(global, rankList.get(0));
		Country jp = new Country("jp");
		this.countryToRank.put(jp, rankList.get(1));

		Objects.requireNonNull(countryToRank);
	}

}
