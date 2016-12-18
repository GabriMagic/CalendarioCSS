package dad.calendario;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CalendarioModel {

	private IntegerProperty anio;

	public CalendarioModel() {
		anio = new SimpleIntegerProperty(this, "anio", 0);
	}

	public final IntegerProperty anioProperty() {
		return this.anio;
	}

	public final int getAnio() {
		return this.anioProperty().get();
	}

	public final void setAnio(final int anio) {
		this.anioProperty().set(anio);
	}

}
