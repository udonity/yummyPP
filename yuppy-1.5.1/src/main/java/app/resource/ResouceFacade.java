package app.resource;

import java.net.URL;

import domain.repo.Property;

public class ResouceFacade {
	public static Property getProperty(URL url) {
		return new ExtractProperties(url);
	}
}
