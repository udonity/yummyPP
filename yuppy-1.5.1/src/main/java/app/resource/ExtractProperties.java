package app.resource;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import domain.obj.Injection;
import domain.repo.Property;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ExtractProperties implements Property {
	private URL url;
	private Properties prop;

	public ExtractProperties(@NonNull URL url) {
		this.url = url;
		this.prop = new Properties();
		try {
			this.prop.load(Files.newBufferedReader(Paths.get(this.url.toURI()),
					StandardCharsets.UTF_8));

		} catch (IOException | URISyntaxException e) {
			log.error("URI is Illegal.", e);
		}
	}

	@Override
	public String getProperty(Injection inje) {
		String result = this.prop.getProperty(inje.toString());
		return result;
	}

	@Override
	public void setProperty(Injection inje, String value) {
		String putVal = value;
		if(value == null) {
			putVal = "";
		}

		this.prop.setProperty(inje.toString(), putVal);
	}

	@Override
	public void store() {
		try(OutputStream os = new FileOutputStream("config.properties");
			BufferedOutputStream bos = new BufferedOutputStream(os)){
			this.prop.store(bos, "config");

		} catch (FileNotFoundException fnException) {
			log.error("File Not Found. URI:" + this.url.toString(), fnException);
		} catch (IOException ioException) {
			log.error("Could not write property file.URI:" + this.url.toString(), ioException);
		}
	}

}
