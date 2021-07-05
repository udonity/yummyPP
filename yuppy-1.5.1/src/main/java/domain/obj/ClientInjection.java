package domain.obj;

import java.net.MalformedURLException;
import java.net.URL;

import domain.repo.Property;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * クライアントが入力した情報を持つクラス。
 * domainパッケージ内で扱うクラスへ加工する責務を持つ。
 */
@Slf4j
public class ClientInjection {

	/**
	 * グローバルランキングか日本ランキングかを指定するための国情報。
	 * 空ならばglobal
	 */
	private final Country country;

	/**
	 * ランキングページで上下何位分読み込むかのレンジ。
	 * 空なら5
	 */
	private final RankingRange rankingRange;

	/**
	 * 自分以外のユーザーページで何ページ分読み込むか
	 */
	private final Range anotherUserPageRange;

	/**
	 * 自分のユーザーページで何ページ分読み込むか
	 */
	private final Range myPageRange;

	/**
	 * 自分のページのURL
	 */
	private URL clientURL;

	/**
	 * ★のレンジ
	 */
	private final Range star;

	/**
	 * PPのレンジ
	 */
	private final Range pp;

	/**
	 * @param eString 検索時に指定したいプロパティ
	 */
	public ClientInjection(@NonNull Property eString) {
		this.country = new Country(eString.getProperty(Injection.COUNTRY));
		this.rankingRange = new RankingRange(eString.getProperty(Injection.RANK_SEARCH_RANGE), this.country);
		this.anotherUserPageRange = new UserPageRange(eString.getProperty(Injection.PAGE_SERACH_RANGE));
		this.myPageRange = new UserPageRange(eString.getProperty(Injection.MYPAGE_SERACH_RANGE));
		try {
			this.clientURL = new URL(eString.getProperty(Injection.URL).trim());
		} catch (MalformedURLException e) {
			log.error("URL is Illegal.URL:" + this.clientURL, e);
			System.exit(-1);
		}
		this.star = new Range(eString.getProperty(Injection.STAR_MIN), eString.getProperty(Injection.STAR_MAX));
		this.pp = new Range(eString.getProperty(Injection.PP_MIN), eString.getProperty(Injection.PP_MAX));
	}

	public final Country country() {
		return this.country;
	}

	public final Range anotherUserPage() {
		return this.anotherUserPageRange;
	}

	public final Range myPageRange() {
		return this.myPageRange;
	}

	public final URL clientURL() {
		return this.clientURL;
	}

	public final RankingRange ranking() {
		return this.rankingRange;
	}

	public final Range starRange() {
		return this.star;
	}

	public final Range ppRange() {
		return this.pp;
	}
}