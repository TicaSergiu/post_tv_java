package bazaDate;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BD {
	private final String locatieBD = "db/PostTv.accdb";
	private final Connection con;
	private static BD instance = null;

	private BD() throws SQLException {
		// Conectarea la baza de date
		con = DriverManager.getConnection("jdbc:ucanaccess://" + locatieBD);
	}

	public static BD getInstance() {
		if (instance == null) {
			try {
				instance = new BD();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return instance;
	}

	public void adaugaPersoana(String nume, String prenume, String cnp, String meserie, String domiciliu) throws Exception {
		Statement stmt = instance.con.createStatement();
		int idLocalitate = getIdLocalitate(domiciliu);
		int idMeserie = getIdMeserie(meserie);

		try {
			stmt.executeUpdate(
					"INSERT INTO persoana (nume, prenume, cnp, id_meserie, id_domiciliu) VALUES ('" + nume + "', '" +
					prenume + "', '" + cnp + "', " + idMeserie + ", " + idLocalitate + ")");
		} catch (SQLException e) {
			throw new Exception("Exista deja o persoana cu acest CNP!");
		}
	}

	public void adaugaEmisiune(String nume, String cnpCoordonator, String tipEmisiune, String numeStudio) throws Exception {
		int idCoordonator = getIdPersoana(cnpCoordonator);
		int idTipEmisiune = getIdTipEmisiune(tipEmisiune);
		int idNumeStudio = getIdStudio(numeStudio);

		try {
			Statement stmt = instance.con.createStatement();
			stmt.executeUpdate(
					"INSERT INTO emisiune (nume_emisiune, coordonator_sef, id_tipemis, id_studio) VALUES ('" + nume +
					"', " + idCoordonator + ", " + idTipEmisiune + ", " + idNumeStudio + ")");
		} catch (SQLException e) {
			throw new Exception("Exista deja o emisiune cu acest nume!");
		}
	}

	public void adaugaDifuzare(String emisiune, Date zi, Timestamp oraStart, Timestamp oraSfarsit) throws Exception {
		int idEmisiune = getIdEmisiune(emisiune);
		try {
			Statement stmt = instance.con.createStatement();
			stmt.executeUpdate(
					"INSERT INTO difuzare (id_emisiune, zi, ora_inceput, ora_sfarsit) VALUES ('" + idEmisiune + "', '" +
					zi + "', '" + oraStart + "', '" + oraSfarsit + "')");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private int getIdEmisiune(String emisiune) {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id_emisiune FROM emisiune WHERE nume_emisiune = '" + emisiune + "'");
			if (rs.next()) {
				return rs.getInt("id_emisiune");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		throw new IllegalArgumentException("Alegeti o emisiune din lista");
	}

	private int getIdPersoana(String cnp) throws SQLException {
		Statement stmt = instance.con.createStatement();
		ResultSet persoana = stmt.executeQuery("SELECT id_persoana FROM persoana WHERE cnp = '" + cnp + "'");
		persoana.next();
		return persoana.getInt("id_persoana");
	}

	private int getIdTipEmisiune(String tipEmisiune) throws SQLException {
		Statement stmt = instance.con.createStatement();
		ResultSet emisiune = stmt.executeQuery(
				"SELECT id_tipemis FROM tip_emisiune WHERE tip_emisiune = '" + tipEmisiune + "'");
		emisiune.next();
		return emisiune.getInt("id_tipemis");
	}

	private int getIdStudio(String numeStudio) throws SQLException {
		Statement stmt = instance.con.createStatement();
		ResultSet studio = stmt.executeQuery("SELECT id_studio FROM studio WHERE nume_studio = '" + numeStudio + "'");
		studio.next();
		return studio.getInt("id_studio");
	}


	private int getIdLocalitate(String domiciliu) throws SQLException {
		Statement stmt = instance.con.createStatement();
		ResultSet localitate = stmt.executeQuery(
				"SELECT id_localitate FROM localitate WHERE nume_localitate = '" + domiciliu + "'");

		// Daca nu exista localitatea, o adaugam
		if (!localitate.next()) {
			int alegere = JOptionPane.showConfirmDialog(null, "Localitatea nu exista. Doriti sa o adaugati?",
			                                            "Adaugare localitate", JOptionPane.YES_NO_OPTION);
			if (alegere == JOptionPane.YES_OPTION) {
				System.out.println(
						stmt.executeUpdate("INSERT INTO localitate (nume_localitate) VALUES ('" + domiciliu + "')"));
				localitate = stmt.executeQuery(
						"SELECT id_localitate FROM localitate WHERE nume_localitate = '" + domiciliu + "'");
				localitate.next();
			}
		}
		return localitate.getInt("id_localitate");
	}

	private int getIdMeserie(String meserie) throws SQLException {
		Statement stmt = instance.con.createStatement();
		ResultSet job = stmt.executeQuery("SELECT id_meserie FROM meserie WHERE nume_meserie = '" + meserie + "'");

		// Daca nu exista meseria, o adaugam
		if (!job.next()) {
			int alegere = JOptionPane.showConfirmDialog(null, "Meseria nu exista. Doriti sa o adaugati?",
			                                            "Adaugare meserie", JOptionPane.YES_NO_OPTION);
			if (alegere == JOptionPane.YES_OPTION) {
				stmt.executeUpdate("INSERT INTO meserie (nume_meserie) VALUES ('" + meserie + "')");
				job = stmt.executeQuery("SELECT id_meserie FROM meserie WHERE nume_meserie = '" + meserie + "'");
				job.next();
			}
		}
		return job.getInt("id_meserie");
	}

	public List<String> getListaCNP() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet cnp = stmt.executeQuery("SELECT cnp FROM persoana");
			List<String> listaCNP = new ArrayList<>();
			while (cnp.next()) {
				listaCNP.add(cnp.getString("cnp"));
			}
			return listaCNP;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<String> getListaTipEmisiuni() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet tipEmisiune = stmt.executeQuery("SELECT tip_emisiune FROM tip_emisiune");
			List<String> listaTipEmisiune = new ArrayList<>();
			while (tipEmisiune.next()) {
				listaTipEmisiune.add(tipEmisiune.getString("tip_emisiune"));
			}
			return listaTipEmisiune;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<String> getListaNumeStudio() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet numeStudio = stmt.executeQuery("SELECT nume_studio FROM studio");
			List<String> listaNumeStudio = new ArrayList<>();
			while (numeStudio.next()) {
				listaNumeStudio.add(numeStudio.getString("nume_studio"));
			}
			return listaNumeStudio;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Emisiune> getListaEmisiuni() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet emisiune = stmt.executeQuery(
					"SELECT nume_emisiune AS Nume_emisiune, p.nume&' '&p.prenume AS Coordonator, t.tip_emisiune" +
					" AS Tip_emisiune, s.nume_studio AS Studio\n" +
					"FROM ((emisiune AS e INNER JOIN persoana AS p ON e.coordonator_sef=p.id_persoana) INNER JOIN tip_emisiune AS t ON e.id_tipemis=t.id_tipemis) INNER JOIN studio AS s ON e.id_studio=s.id_studio");
			List<Emisiune> listaEmisiuni = new ArrayList<>();
			while (emisiune.next()) {
				listaEmisiuni.add(new Emisiune(emisiune.getString("Nume_emisiune").replace("_", " "),
				                               emisiune.getString("Coordonator"),
				                               emisiune.getString("Tip_emisiune").replace("_", " "),
				                               emisiune.getString("Studio")));
			}
			return listaEmisiuni;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Difuzare> getListaDifuzari() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet difuzare = stmt.executeQuery(
					"SELECT nume_emisiune AS Nume_emisiune, Zi, ora_inceput AS Ora_inceput, ora_sfarsit AS " +
					"Ora_sfarsit FROM difuzare AS d INNER JOIN emisiune AS e ON d.id_emisiune=e.id_emisiune");
			List<Difuzare> listaDifuzari = new ArrayList<>();
			while (difuzare.next()) {
				listaDifuzari.add(
						new Difuzare(difuzare.getString("Nume_emisiune").replace("_", " "), difuzare.getDate("Zi"),
						             difuzare.getTime("Ora_inceput"), difuzare.getTime("Ora_sfarsit")));
			}
			return listaDifuzari;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<String> getNumeEmisiuni() {
		try {
			Statement stmt = instance.con.createStatement();
			ResultSet numeEmisiune = stmt.executeQuery("SELECT nume_emisiune FROM emisiune");
			List<String> listaNumeEmisiune = new ArrayList<>();
			while (numeEmisiune.next()) {
				listaNumeEmisiune.add(numeEmisiune.getString("nume_emisiune"));
			}
			return listaNumeEmisiune;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void modificaEmisiuni(List<String> emisiuni) {
		emisiuni.forEach(emisiune -> {
			try {
				String[] detaliiEmisiune = emisiune.split("_");
				PreparedStatement stmt = instance.con.prepareStatement(
						"UPDATE emisiune SET coordonator_sef=?, id_tipemis=?, id_studio=? WHERE nume_emisiune=?");
				if (detaliiEmisiune[1].matches("[0-9]+")) {
					stmt.setInt(1, getIdPersoana(detaliiEmisiune[1]));
				} else {
					Statement stmt2 = instance.con.createStatement();
					ResultSet cnpPers = stmt2.executeQuery(
							"SELECT coordonator_sef FROM emisiune WHERE nume_emisiune='" + detaliiEmisiune[0] + "'");
					cnpPers.next();
					stmt.setInt(1, cnpPers.getInt("coordonator_sef"));
				}
				stmt.setInt(2, getIdTipEmisiune(detaliiEmisiune[2]));
				stmt.setInt(3, getIdStudio(detaliiEmisiune[3]));
				stmt.setString(4, detaliiEmisiune[0]);
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void stergeEmisiune(String emisiune) {
		try {
			Statement stmt = instance.con.createStatement();
			stmt.executeUpdate(
					"DELETE FROM difuzare WHERE id_emisiune = (SELECT id_emisiune FROM emisiune WHERE nume_emisiune = '" +
					emisiune + "')");
			stmt.executeUpdate("DELETE FROM emisiune WHERE nume_emisiune='" + emisiune + "'");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
