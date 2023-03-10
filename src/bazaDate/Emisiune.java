package bazaDate;

public class Emisiune {
	private final String nume;
	private final String coordonatorSef;
	private final String tipEmisiune;
	private final String studio;

	public Emisiune(String nume, String coordonatorSef, String tipEmisiune, String studio) {
		this.nume = nume;
		this.coordonatorSef = coordonatorSef;
		this.tipEmisiune = tipEmisiune;
		this.studio = studio;
	}

	public String getCoordonatorSef() {
		return coordonatorSef;
	}

	public String getTipEmisiune() {
		return tipEmisiune;
	}

	public String getStudio() {
		return studio;
	}

	public String getEmisiune() {
		return nume;
	}

}
