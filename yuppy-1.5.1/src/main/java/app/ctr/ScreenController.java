package app.ctr;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.resource.ResouceFacade;
import domain.obj.Injection;
import domain.repo.Property;
import domain.service.ServiceFacade;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScreenController {
	private Property storeProp;

	@FXML
	private TextField clientURL;
	@FXML
	private TextField myPageSearchRange;
	@FXML
	private TextField pageSearchRange;
	@FXML
	private TextField rankSearchRange;
	@FXML
	private TextField country;
	@FXML
	private TextField starMin;
	@FXML
	private TextField starMax;
	@FXML
	private TextField ppMin;
	@FXML
	private TextField ppMax;
	@FXML
	private Button submit;
	@FXML
	private ImageView loading;

	@FXML
	void initialize() {
		File f = new File("resources\\config.properties");
		URL url = null;
		try {
			if(f.exists()) {
				url = f.toURI().toURL();
			} else {
				f = new File("config.properties");
				url = f.toURI().toURL();
			}
		} catch (MalformedURLException e) {
			log.warn("URI NotFound.", e);
		}
		if(url == null) {
			NullPointerException e = new NullPointerException("URL is null");
			log.error("URL is null.", e);
			throw e;
		}
		this.storeProp = ResouceFacade.getProperty(url);

		this.clientURL.setText(this.storeProp.getProperty(Injection.URL));
		this.myPageSearchRange.setText(this.storeProp.getProperty(Injection.MYPAGE_SERACH_RANGE));
		this.rankSearchRange.setText(this.storeProp.getProperty(Injection.RANK_SEARCH_RANGE));
		this.pageSearchRange.setText(this.storeProp.getProperty(Injection.PAGE_SERACH_RANGE));
		this.country.setText(this.storeProp.getProperty(Injection.COUNTRY));
		this.starMin.setText(this.storeProp.getProperty(Injection.STAR_MIN));
		this.starMax.setText(this.storeProp.getProperty(Injection.STAR_MAX));
		this.ppMin.setText(this.storeProp.getProperty(Injection.PP_MIN));
		this.ppMax.setText(this.storeProp.getProperty(Injection.PP_MAX));

	}

	@FXML
	void submitOK(ActionEvent event) {
		ExecutorService exService = Executors.newCachedThreadPool();

		Task<Boolean> imgTask = new Task<>() {
			@Override
			protected Boolean call() {
				log.info("Loading IMG:Update");
				Image image = new Image(
						getClass().getResourceAsStream("/loading.gif"));
				loading.setImage(image);
				return true;
			}
		};
		exService.execute(imgTask);

		Task<Boolean> tfTask = ServiceFacade.mainTask(this.storeProp);
		tfTask.setOnScheduled(e -> {
			this.storeProp.setProperty(Injection.URL, this.clientURL.getText());
			this.storeProp.setProperty(Injection.COUNTRY, this.country.getText());
			this.storeProp.setProperty(Injection.MYPAGE_SERACH_RANGE, this.myPageSearchRange.getText());
			this.storeProp.setProperty(Injection.PAGE_SERACH_RANGE, this.pageSearchRange.getText());
			this.storeProp.setProperty(Injection.RANK_SEARCH_RANGE, this.rankSearchRange.getText());
			this.storeProp.setProperty(Injection.PP_MIN, this.ppMin.getText());
			this.storeProp.setProperty(Injection.PP_MAX, this.ppMax.getText());
			this.storeProp.setProperty(Injection.STAR_MIN, this.starMin.getText());
			this.storeProp.setProperty(Injection.STAR_MAX, this.starMax.getText());

			this.storeProp.store();

			log.info("Completion of writing to the property file");
		});

		tfTask.setOnSucceeded(e -> {
			// ウインドウを閉じる
			this.submit.getScene().getWindow().hide();

			// 完了画面を表示
			URL compURL = null;
			compURL = Thread.currentThread().getContextClassLoader()
					.getResource("CompletionScreen.fxml");
			if (compURL == null) {
				try {
					compURL = new File("src\\main\\resources\\CompletionScreen.fxml").toURI().toURL();
				} catch (MalformedURLException e2) {
					log.warn("Could not read CompletionScreenFile", e2);
				}
			}
			FXMLLoader loader = new FXMLLoader(compURL);
			Objects.requireNonNull(loader);
			Parent root = null;
			try {
				root = (Parent) loader.load();
			} catch (IOException e3) {
				log.warn("Could not read Parent from FXML file.", e3);
			}
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("完了画面");
			stage.show();
			log.info("Successful completion");
		});
		exService.execute(tfTask);

		exService.shutdown();
	}
}
