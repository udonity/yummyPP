package domain.service;

import java.util.Map;

import domain.obj.Country;

public interface MyPageExtractor extends UserSongExtractor{
	public Map<Country, Integer> getRank();
}
