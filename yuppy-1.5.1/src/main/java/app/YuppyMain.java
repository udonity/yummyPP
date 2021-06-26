package app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import app.ctr.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YuppyMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		URL url = Thread.currentThread().getContextClassLoader().getResource("InjectionScreen.fxml");
		if(url == null) {
			try {
				url = new File("src\\main\\resources\\InjectionScreen.fxml").toURI().toURL();
			} catch (MalformedURLException e) {
				log.error("FileNotFound. URL:", e);
			}
		}
		Objects.requireNonNull(url);
		FXMLLoader loader = new FXMLLoader(url);
		Objects.requireNonNull(loader);
		ScreenController controller = new ScreenController();
		Objects.requireNonNull(controller);
		loader.setController(controller);
		loader.setRoot(new ScrollPane());

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			log.error("Parent loading failed", e);
		}
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
