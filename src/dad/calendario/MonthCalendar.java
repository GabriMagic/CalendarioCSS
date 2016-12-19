package dad.calendario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import dad.calendario.utils.DateUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MonthCalendar extends GridPane {

	private static final DateFormat FORMATTER = new SimpleDateFormat("MMMM");
	private static final String[] WEEK_DAYS = { "L", "M", "X", "J", "V", "S", "D" };

	// model
	private IntegerProperty month;
	private IntegerProperty year;

	// view
	private Label monthNameLabel;
	private Label[] weekDaysLabel;
	private Label[] daysLabel;

	public MonthCalendar(int year, int month) {
		super();
		this.month = new SimpleIntegerProperty(this, "month", month);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MonthCalendarView.fxml"));
		loader.setController(this);

		this.month.addListener((observable, oldValue, newValue) -> {
			onModelChanged();
		});
		this.year = new SimpleIntegerProperty(this, "year", year);
		this.year.addListener((observable, oldValue, newValue) -> {
			onModelChanged();
		});

		monthNameLabel = new Label();
		monthNameLabel.getStyleClass().add("month");
		GridPane.setColumnSpan(monthNameLabel, 7);
		GridPane.setHalignment(monthNameLabel, HPos.CENTER);
		GridPane.setValignment(monthNameLabel, VPos.CENTER);
		GridPane.setHgrow(monthNameLabel, Priority.ALWAYS);
		GridPane.setVgrow(monthNameLabel, Priority.ALWAYS);
		add(monthNameLabel, 0, 0);

		weekDaysLabel = new Label[7];
		for (int i = 0; i < weekDaysLabel.length; i++) {
			weekDaysLabel[i] = new Label(WEEK_DAYS[i]);
			GridPane.setHalignment(weekDaysLabel[i], HPos.CENTER);
			GridPane.setValignment(weekDaysLabel[i], VPos.CENTER);
			GridPane.setHgrow(weekDaysLabel[i], Priority.ALWAYS);
			GridPane.setVgrow(weekDaysLabel[i], Priority.ALWAYS);
			add(weekDaysLabel[i], i, 1);
			weekDaysLabel[i].getStyleClass().add("weekday");
			if (i == 6) {
				weekDaysLabel[i].getStyleClass().add("sunday");
			}

		}

		daysLabel = new Label[6 * 7];
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				int pos = row * 7 + col;
				daysLabel[pos] = new Label("" + pos);
				GridPane.setHalignment(daysLabel[pos], HPos.CENTER);
				GridPane.setValignment(daysLabel[pos], VPos.CENTER);
				GridPane.setHgrow(daysLabel[pos], Priority.ALWAYS);
				GridPane.setVgrow(daysLabel[pos], Priority.ALWAYS);
				add(daysLabel[pos], col, row + 1 + 1);
				daysLabel[pos].getStyleClass().add("day");
				if (col == 6) {
					daysLabel[pos].getStyleClass().add("sunday");
				}

			}
		}

	}

	public void onModelChanged() {
		int first = DateUtils.firstDay(year.get(), month.get()) - 1;
		int last = DateUtils.lastDay(year.get(), month.get());
		for (int i = 0; i < first; i++) {
			daysLabel[i].setText("");
		}
		for (int i = first, j = 1; i < first + last; i++, j++) {
			daysLabel[i].setText("" + j);
		}
		for (int i = first + last; i < daysLabel.length; i++) {
			daysLabel[i].setText("");
		}
		Date day = DateUtils.day(year.get(), month.get(), 1);
		monthNameLabel.setText(FORMATTER.format(day));

		int pos = LocalDate.now().getDayOfMonth() + first - 1;
		if (this.month.get() == LocalDate.now().getMonthValue() && this.year.get() == LocalDate.now().getYear()) {
			daysLabel[pos].getStyleClass().add("today");
		} else {
			daysLabel[LocalDate.now().getDayOfMonth() + 2].getStyleClass().removeAll("today");
		}

	}

	public IntegerProperty monthProperty() {
		return this.month;
	}

	public int getMonth() {
		return this.monthProperty().get();
	}

	public void setMonth(final int month) {
		this.monthProperty().set(month);
	}

	public IntegerProperty yearProperty() {
		return this.year;
	}

	public int getYear() {
		return this.yearProperty().get();
	}

	public void setYear(final int year) {
		this.yearProperty().set(year);
	}

}
