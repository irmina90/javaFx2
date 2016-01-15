package javafx.imageViewer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.imageViewer.controller.PhotoChooserController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	private Stage primaryStage;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setPrimaryStage(primaryStage);
		String langCode = getParameters().getNamed().get("--lang");
		if (langCode != null && !langCode.isEmpty()) {
			Locale.setDefault(Locale.forLanguageTag(langCode));
		}

		primaryStage.setTitle("Photo Viewer 1.0 Beta");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/javafx/photoViewer/view/photo-chooser.fxml"));
		loader.setResources(ResourceBundle.getBundle("javafx/photoViewer/bundle/base"));

		Parent root = (Parent) loader.load();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/javafx/photoViewer/css/standard.css").toExternalForm());
		PhotoChooserController photoChooserCtrl = loader.getController();
		photoChooserCtrl.setMainApp(this);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
