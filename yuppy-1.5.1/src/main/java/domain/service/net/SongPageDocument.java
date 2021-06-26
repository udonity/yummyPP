package domain.service.net;

import java.net.URL;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import domain.service.SongPageExtractor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SongPageDocument extends ScoreSaberDocument implements SongPageExtractor{
	/**
	 *  読み込みしていない状態は-1
	 */
	private double star = -1;
	private Element element;


	public SongPageDocument(URL url) {
		super(url);
		Document docu = super.getDocument();
		this.element = docu.head();

		Objects.requireNonNull(element);
	}


	/**
	 * extractを実行していないなら、内部で実行される。
	 */
	@Override
	public double getStar() {
		if(star == -1) {
			extract();
			if(star != -1) {
				return this.star;
			}
			
			IllegalStateException e
				= new IllegalStateException("Failed to write star. Element:\n" + this.element.toString());
			log.warn("Star is Illegal.", e);
			throw e;
		}
		return this.star;
	}

	@Override
	public void extract() {
		String text = this.element.getElementsByAttributeValue("property", "og:description").toString();
		int start = text.indexOf("Stars:") + 6;
		int end = text.indexOf("★");
		String substring = text.substring(start, end).trim();
		this.star = Double.valueOf(substring);
	}

}
