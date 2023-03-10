package tabele;

import bazaDate.BD;
import bazaDate.Emisiune;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TabelEmisiuni extends JTable {

	private final String[] numeColoane = {"Emisiune", "Coordonator Sef", "Tip emisiune", "Studio"};
	private boolean esteModificabil;
	private TabelPersonalizat dateTabel;

	public TabelEmisiuni() {
		super();

		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);

		esteModificabil = false;
		vizualizeazaEmisiuni();
	}

	public void modificaTabel(boolean modModificare) {
		esteModificabil = !esteModificabil;
		if (modModificare) {
			modificaEmisiuni();
		} else {
			vizualizeazaEmisiuni();
		}
	}

	private void vizualizeazaEmisiuni() {
		List<Emisiune> listaEmisiuni = BD.getInstance().getListaEmisiuni();
		Object[][] date = new Object[listaEmisiuni.size()][numeColoane.length];
		for (int i = 0; i < listaEmisiuni.size(); i++) {
			Emisiune emisiune = listaEmisiuni.get(i);
			date[i][0] = emisiune.getEmisiune();
			date[i][1] = emisiune.getCoordonatorSef();
			date[i][2] = emisiune.getTipEmisiune();
			date[i][3] = emisiune.getStudio();
		}
		dateTabel = new TabelPersonalizat(date, numeColoane);
		setModel(dateTabel);
	}

	private void modificaEmisiuni() {

		JComboBox<String> coordonatori = new JComboBox<>();
		List<String> listaCoordonatori = BD.getInstance().getListaCNP();
		for (String coordonator : listaCoordonatori) {
			coordonatori.addItem(coordonator);
		}

		JComboBox<String> tipEmisiuni = new JComboBox<>();
		List<String> listaTipEmisiuni = BD.getInstance().getListaTipEmisiuni();
		for (String tipEmisiune : listaTipEmisiuni) {
			tipEmisiuni.addItem(tipEmisiune);
		}

		JComboBox<String> studio = new JComboBox<>();
		List<String> listaStudio = BD.getInstance().getListaNumeStudio();
		for (String studiu : listaStudio) {
			studio.addItem(studiu);
		}

		getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(coordonatori));
		getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(tipEmisiuni));
		getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(studio));
	}

	public void salveazaTabel() {
		BD.getInstance().modificaEmisiuni(dateTabel.getListaModificari());
		modificaTabel(true);
	}

	public void stergeEmisiune() {
		int index = getSelectedRow();
		if (index != -1) {
			String emisiune = (String)getValueAt(index, 0);
			BD.getInstance().stergeEmisiune(emisiune);
			DefaultTableModel model = (DefaultTableModel)getModel();
			model.removeRow(index);
			vizualizeazaEmisiuni();
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return column != 0 && esteModificabil;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (esteModificabil) {
			return JComboBox.class;
		}
		return super.getColumnClass(column);
	}

}
