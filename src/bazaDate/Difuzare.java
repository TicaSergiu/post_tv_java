package bazaDate;

import java.sql.Date;
import java.sql.Time;

public class Difuzare {
	private final String emisiune;
	private final Date zi;
	private final Time oraStart;
	private final Time oraSfarsit;

	public Difuzare(String emisiune, Date zi, Time oraStart, Time oraSfarsit) {
		this.emisiune = emisiune;
		this.zi = zi;
		this.oraStart = oraStart;
		this.oraSfarsit = oraSfarsit;
	}

	public String getEmisiune() {
		return emisiune;
	}

	public Date getZi() {
		return zi;
	}

	public String getOraStart() {
		return oraStart.toString();
	}

	public String getOraSfarsit() {
		return oraSfarsit.toString();
	}

}
