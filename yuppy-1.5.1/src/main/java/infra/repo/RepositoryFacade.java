package infra.repo;

import domain.obj.SongList;
import domain.repo.OutRepository;
import lombok.NonNull;

/**
 * repositoryパッケージへのファサードクラス。
 */
public class RepositoryFacade {
	public static OutRepository outRepository(@NonNull SongList songList) {
		return new OutRepositoryImpl(songList);
	}
}
