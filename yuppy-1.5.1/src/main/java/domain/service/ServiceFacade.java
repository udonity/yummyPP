package domain.service;

import domain.repo.Property;
import javafx.concurrent.Task;

public class ServiceFacade {
	public static Task<Boolean> mainTask(Property property){
		return new AccessToOutputTask(property);
	}
}
