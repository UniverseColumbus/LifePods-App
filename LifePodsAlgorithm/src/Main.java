import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	private File fileToRead = null;
	private String outputDir;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage theStage) throws Exception {
		GridPane mainPane = new GridPane();
		mainPane.setVgap(10);

		// File chooser to select csv file
		FileChooser inputFile = new FileChooser();
		inputFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		// Text field with file absolute path
		TextField filePath = new TextField();
		filePath.setPrefWidth(300);

		// Button to open dialog and select input file
		Button selectFile = new Button("Select file");
		selectFile.setOnAction(e -> {
			fileToRead = inputFile.showOpenDialog(theStage);
			filePath.setText(fileToRead.getAbsolutePath());
		});

		// Input horizontal box
		HBox inputBox = new HBox(filePath, selectFile);
		inputBox.setSpacing(10);

		/*--------------------------------------------------------------------*/

		// Directory chooser to select place for output csv file
		DirectoryChooser outputFile = new DirectoryChooser();

		// Text field with directory absolute path
		TextField directoryPath = new TextField();
		directoryPath.setPrefWidth(300);

		// Button to open dialog and select output directory
		Button selectDir = new Button("Select directory");
		selectDir.setOnAction(e -> {
			File output = outputFile.showDialog(theStage);
			outputDir = output.getAbsolutePath();
			directoryPath.setText(outputDir);
		});

		// Output horizontal box
		HBox outputBox = new HBox(directoryPath, selectDir);
		outputBox.setSpacing(10);

		/*--------------------------------------------------------------------*/

		// Button to run algorithm and generate the pods
		Button generatePods = new Button("Generate pods");
		generatePods.setOnAction(e -> {
			ArrayList<User> users = FileReader.readFile(fileToRead);
			GroupBuilder gb = new GroupBuilder(users);

			HashMap<Integer, LifePod> pods = gb.buildPods();
			ArrayList<LifePod> podList = new ArrayList<>();

			for (Entry<Integer, LifePod> p : pods.entrySet()) {
				System.out.println(p.getKey() + " " + p.getValue().toString());
				podList.add(p.getValue());
			}

			ExcelWriter ew = new ExcelWriter(podList);
			ew.write(outputDir);
		});

		// Biding disable property to text fields
		BooleanBinding booleanBind = filePath.textProperty().isEmpty().or(directoryPath.textProperty().isEmpty());
		generatePods.disableProperty().bind(booleanBind);

		// Button horizontal box
		HBox buttonHBox = new HBox();
		buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonHBox.setSpacing(10);

		buttonHBox.getChildren().add(generatePods);

		/*--------------------------------------------------------------------*/

		Separator separator1 = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL);

		mainPane.add(inputBox, 0, 0);
		mainPane.add(separator1, 0, 1);
		mainPane.add(outputBox, 0, 2);
		mainPane.add(separator2, 0, 3);
		mainPane.add(buttonHBox, 0, 4);

		BorderPane fullPane = new BorderPane(mainPane);
		BorderPane.setMargin(mainPane, new Insets(10, 10, 10, 10));

		Scene scene = new Scene(fullPane);

		theStage.setTitle("LifePods Builder");
		theStage.setScene(scene);
		theStage.show();
	}

}
