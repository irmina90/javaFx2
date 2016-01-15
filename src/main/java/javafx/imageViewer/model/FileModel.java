package javafx.imageViewer.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class FileModel {
	private ObjectProperty<File> file = new SimpleObjectProperty<>();
	private ListProperty<File> files = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

	public FileModel()

	{

	}
	/*
	 * REV: metoda powinna nazywac sie 'fileProperty'
	 */
	public final ObjectProperty<File> getProperty() {
		return file;
	}

	public final void setFile(File value) {
		file.set(value);
	}

	public final File getFile() {
		return file.get();
	}

	public final void setFiles(List<File> value) {
		files.setAll(value);
	}

	public final void addOneFile(File value) {
		files.add(value);
	}

	public ListProperty<File> filesProperty() {
		return files;
	}

	/*
	 * REV: chodzi raczej o index a nie id
	 */
	public File getFileById(int id) {
		return files.get(id);
	}

	public int getSizeOfAllFiles() {
		return files.size();
	}

}
