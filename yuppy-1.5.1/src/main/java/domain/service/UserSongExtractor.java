package domain.service;

import domain.obj.Range;
import domain.obj.SongList;

public interface UserSongExtractor extends Extractor{
	public SongList obtainSongList(Range range);
}
