package domain.service.net;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
@Slf4j
class ScoreSaberDocument {
	private Document document;

	public ScoreSaberDocument(@NonNull URL url) {
		// サイトチェック
		if(!url.getHost().contains("scoresaber.com")) {
			IllegalArgumentException e = new IllegalArgumentException("URL:" + url.toString());
			log.error("URL is Illegal.", e);
			throw e;
		}
		try {
			this.document = Jsoup.connect(url.toString()).get();
		} catch (IOException e) {
			log.warn("Failed to load URL. URL:" + url.toString(),e);
		}
		Objects.requireNonNull(this.document);
	}

	public Document getDocument() {
		return document;
	}

}
