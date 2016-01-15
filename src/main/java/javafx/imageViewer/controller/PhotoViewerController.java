package javafx.imageViewer.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.imageViewer.model.FileModel;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhotoViewerController {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ImageView image;

	@FXML
	private Button prevButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button zoomInButton;

	@FXML
	private Button zoomOutButton;

	@FXML
	private Button slideShowButton;

	private FileModel model;
	private int idFile;
	private boolean slideShowOn = false;
	private static final long DELAY = 1000;
	DoubleProperty zoomProperty = new SimpleDoubleProperty(2);

	@FXML
	private void initialize() {

	}

	public FileModel getModel() {
		return model;
	}

	public void setModelWithSelectedFileId(FileModel model, int idFile) {
		this.idFile = idFile;
		this.model = model;
		try {
			setImage(model.getFileById(idFile));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	@FXML
	public void nextPhotoAction(ActionEvent event) throws MalformedURLException {
		incrementFildId();
		setImage(model.getFileById(idFile));
	}

	@FXML
	public void prevPhotoAction(ActionEvent event) throws MalformedURLException {
		decrementFildId();
		setImage(model.getFileById(idFile));
	}

	@FXML
	public void slideShowAction(ActionEvent event) throws MalformedURLException {
		setSlideShow();
		final Timer timer = new Timer();
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (slideShowOn) {
					try {
						setImage(model.getFileById(idFile));
						incrementFildId();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else {
					timer.cancel();
					timer.purge();
				}
			}
		};
		timer.schedule(task, 0, DELAY);
	}

	private void setSlideShow() {
		if (slideShowOn == true) {
			slideShowButton.setText("Start SlideShow");
			slideShowButton.setStyle("-fx-background-color: green;");
			slideShowOn = false;
		} else {
			slideShowButton.setText("Stop SlideShow");
			slideShowButton.setStyle("-fx-background-color: red;");
			slideShowOn = true;
		}
	}

	private void setImage(File file) throws MalformedURLException {
		Image imageToShow = new Image(file.toURI().toURL().toString());
		image.setImage(imageToShow);
		image.setFitHeight(imageToShow.getHeight());
		image.setFitWidth(imageToShow.getWidth());
	}

	private void incrementFildId() {
		if (this.idFile < model.getSizeOfAllFiles() - 1)
			this.idFile = this.idFile + 1;
		else
			this.idFile = 0;
	}

	private void decrementFildId() {
		if (this.idFile >= 1)
			this.idFile = this.idFile - 1;
		else
			this.idFile = model.getSizeOfAllFiles() - 1;
	}

	@FXML
	public void zoomInAction(ActionEvent event) {
		image.setFitWidth(zoomProperty.get() * image.getImage().getWidth());
		image.setFitHeight(zoomProperty.get() * image.getImage().getWidth());
	}

	@FXML
	public void zoomOutAction(ActionEvent event) {
		image.setFitWidth(scrollPane.getWidth());
		image.setFitHeight(scrollPane.getHeight());
	}

}
