package application;

import javafx.application.Application;
import javafx.stage.Stage;
import viewer.ViewManager;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
						
			ViewManager manager = new ViewManager();
			
			Stage s = manager.getStage();
			s.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
