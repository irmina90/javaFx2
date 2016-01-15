package javafx.imageViewer.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.imageViewer.App;
import javafx.imageViewer.model.FileModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhotoChooserController {

	@FXML
	private Label photoSelected;

	@FXML
	private TableView<File> fileTable;

	@FXML
	private TableColumn<File, String> fileColumn;

	private FileModel model = new FileModel();

	private App mainApp;

	public PhotoChooserController() {
	}

	@FXML
	private void initialize() {
		initializeFileTable();
		fileTable.itemsProperty().bind(model.filesProperty());
		fileTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (fileTable.getSelectionModel().getSelectedItem() != null) {
				showFileDialog(fileTable.getSelectionModel().getSelectedIndex());
			}
		});
	}

	private void initializeFileTable() {
		fileColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
	}

	@FXML
	public void searchPhotoButtonAction(ActionEvent event) throws MalformedURLException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select image files");
		fileChooser.setInitialDirectory(new File("C:/Users/wzietek/Pictures"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
				"Image Files *.bmp, *.png, *.jpg, *.gif, *.bmp", "*.png", "*.jpg", "*.gif"));
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());

		if (selectedFiles != null) {
			model.setFiles(selectedFiles);
			fileTable.getSortOrder().clear();
		} else {
			photoSelected.setText("Image files selection cancelled.");
		}
	}

	private void showFileDialog(int idFileToShow) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/javafx/photoViewer/view/photo-view.fxml"));
			loader.setResources(ResourceBundle.getBundle("javafx/photoViewer/bundle/base"));

			Parent root = (Parent) loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/javafx/photoViewer/css/standard.css").toExternalForm());

			Stage photosStage = new Stage();
			photosStage.setTitle("Photo Viewer");
			photosStage.setScene(scene);
			photosStage.initModality(Modality.WINDOW_MODAL);
			photosStage.initOwner(mainApp.getPrimaryStage());

			PhotoViewerController photoViewerCtrl = loader.getController();
			photoViewerCtrl.setModelWithSelectedFileId(model, idFileToShow);
			photosStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public App getMainApp() {
		return mainApp;
	}

	public void setMainApp(App mainApp) {
		this.mainApp = mainApp;
	}

}
