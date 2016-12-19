package dad.calendario;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class CalendarioController {

	private CalendarioModel calendario;

	@FXML
	private Button siguienteButton;

	@FXML
	private Label anisoLabel;

	@FXML
	private GridPane mesesPanel;

	@FXML
	private BorderPane view;

	@FXML
	private Button anteriorButton;

	private ObservableList<MonthCalendar> listaMeses;

	public CalendarioController(CalendarioApp app) {

		calendario = new CalendarioModel();
		listaMeses = FXCollections.observableArrayList();

		FXMLloads();

		anteriorButton.getStyleClass().add("changeButton");
		siguienteButton.getStyleClass().add("changeButton");

		anisoLabel.getStyleClass().add("year");

		calendario.setAnio(LocalDate.now().getYear());

		int mes = 1;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				MonthCalendar monthCalendar = new MonthCalendar(calendario.getAnio(), mes);
				mesesPanel.add(monthCalendar, j, i);
				listaMeses.add(monthCalendar);
				monthCalendar.onModelChanged();
				mes++;
			}
		}

	}

	public void bind() {

		for (int i = 0; i < listaMeses.size(); i++) {
			listaMeses.get(i).yearProperty().bind(calendario.anioProperty());
		}

		anisoLabel.textProperty().bind(calendario.anioProperty().asString());
	}

	@FXML
	void onAnterior(ActionEvent event) {
		calendario.setAnio(calendario.getAnio() - 1);
	}

	@FXML
	void onSiguiente(ActionEvent event) {
		calendario.setAnio(calendario.getAnio() + 1);
	}

	private void FXMLloads() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarioView.fxml"));
			loader.setController(this);
			view = loader.load();
			view.getStylesheets().add(getClass().getResource("calendario.css").toExternalForm());
			view.getStyleClass().add("root");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public BorderPane getView() {
		return view;
	}

}
