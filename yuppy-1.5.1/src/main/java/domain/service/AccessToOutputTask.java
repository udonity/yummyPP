package domain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.obj.ClientInjection;
import domain.obj.SongList;
import domain.repo.OutRepository;
import domain.repo.Property;
import infra.repo.RepositoryFacade;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;


@Slf4j
class AccessToOutputTask extends Task<Boolean> {
	private Property eString;

	public AccessToOutputTask(Property eString) {
		this.eString = eString;
	}

	@Override
	protected Boolean call() {
		log.info("Get client information: Start.");
		ClientInjection ci = new ClientInjection(eString);
		log.info("Get client information: Finished.");

		log.info("Access to ScoreSaber: Start.");
		AccessFlow accessCtr = new AccessFlow(ci);
		accessCtr.access();
		SongList songs = accessCtr.getSongList();
		log.info("Access to ScoreSaber: Finished.");
		
		log.info("Output to File: Start.");
		OutRepository outRepo = RepositoryFacade.outRepository(songs);
		LocalDateTime ldTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyy_MM_dd_HH_mm_ss");
		String title = "Songs" + formatter.format(ldTime);
		outRepo.storeText(title);
		outRepo.storeJSON(title);
		log.info("Output to File: Finished.");

		return true;
	}
}
