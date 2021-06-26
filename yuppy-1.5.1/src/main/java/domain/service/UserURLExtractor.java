package domain.service;

import java.net.URL;
import java.util.Map;

import domain.obj.RankingRange;

public interface UserURLExtractor extends Extractor{
	public Map<Integer, URL> obtainRankToURLMap(RankingRange rankingRange);
}
