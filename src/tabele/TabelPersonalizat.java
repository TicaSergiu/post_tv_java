package tabele;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelPersonalizat extends DefaultTableModel {
	private Map<Integer, String> listaModificari;

	TabelPersonalizat(Object[][] date, String[] numeColoane) {
		super(date, numeColoane);

		listaModificari = new HashMap<>();
	}

	@Override
	public void fireTableCellUpdated(int row, int column) {
		StringBuilder modificari = new StringBuilder();
		for (int i = 0; i < getColumnCount(); i++) {
			modificari.append((String)getValueAt(row, i));
			modificari.append("_");
		}
		listaModificari.put(row, modificari.toString());
		super.fireTableCellUpdated(row, column);
	}

	public List<String> getListaModificari() {
		return new ArrayList<>(listaModificari.values());
	}

}
