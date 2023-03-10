package tabele;

import bazaDate.BD;
import bazaDate.Difuzare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TabelDifuzari extends JTable {
	private final String[] numeCol = new String[]{"Emisiune", "Zi", "Ora start", "Ora sfarsit"};

	public TabelDifuzari() {
		super();

		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);

		List<Difuzare> listaDifuzari = BD.getInstance().getListaDifuzari();
		Object[][] date = new Object[listaDifuzari.size()][numeCol.length];
		for (int i = 0; i < listaDifuzari.size(); i++) {
			Difuzare difuzare = listaDifuzari.get(i);
			date[i][0] = difuzare.getEmisiune();
			date[i][1] = difuzare.getZi();
			date[i][2] = difuzare.getOraStart();
			date[i][3] = difuzare.getOraSfarsit();
		}

		setModel(new DefaultTableModel(date, numeCol));
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
