package dad.calendario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CalendarioApp extends Application {

	private CalendarioController calendarioController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		calendarioController = new CalendarioController(this);
		calendarioController.bind();
		
		primaryStage.setTitle("Calendario");
		primaryStage.getIcons().add(new Image("/dad/calendario/resources/calendar.png"));
		primaryStage.setScene(new Scene(calendarioController.getView(), 700, 600));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
