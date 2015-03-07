package application;
	
import gui.MazeGUIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import solver.Zombie;
import solver.ZombieRecursiveImpl;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Zombie solver = new ZombieRecursiveImpl();

			MazeGUIPane c = new MazeGUIPane(solver);
			Scene scene = new Scene(c);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
