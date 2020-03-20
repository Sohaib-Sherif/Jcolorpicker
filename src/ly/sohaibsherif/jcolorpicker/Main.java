package ly.sohaibsherif.jcolorpicker;
	
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Jcolorpicker.fxml"));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setAlwaysOnTop(true);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setScene(scene);
			primaryStage.setX(primaryScreenBounds.getMaxX()-root.getPrefWidth());//same pref height & width of my scene.
			primaryStage.setY(primaryScreenBounds.getMaxY()-root.getPrefHeight());// fixed them with root
			// TODO: add Icon.
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
