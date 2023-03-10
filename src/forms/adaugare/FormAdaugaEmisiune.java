package forms.adaugare;

import bazaDate.BD;
import forms.AutoCompletion;
import forms.afisare.FormAfiseazaEmisiuni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

public class FormAdaugaEmisiune extends JDialog {
	private final JTextField tNumeEmisiune;
	private final AscultatorButon ab;
	private JComboBox<String> cbTipEmisiune, cbCNPCoordonator, cbNumeStudio;
	private final JButton bAdauga, bAnuleaza;

	public FormAdaugaEmisiune(FormAfiseazaEmisiuni parent, boolean modal) {
		super(parent, modal);
		parent.setVisible(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ab = new AscultatorButon();


		tNumeEmisiune = new JTextField(10);

		bAdauga = new JButton("Adauga");
		bAnuleaza = new JButton("Anuleaza");

		cbTipEmisiune = new JComboBox<>();
		initListe();

		bAdauga.addActionListener(ab);
		bAnuleaza.addActionListener(e -> {
			parent.actualizeazaTabel();
			dispose();
		});


		setLayout(new GridLayout(5, 2, 5, 5));
		add(new JLabel("Nume emisiune:"));
		add(tNumeEmisiune);
		add(new JLabel("CNP coordonator:"));
		add(cbCNPCoordonator);
		add(new JLabel("Tip emisiune:"));
		add(cbTipEmisiune);
		add(new JLabel("Nume studio:"));
		add(cbNumeStudio);
		add(bAnuleaza);
		add(bAdauga);

		pack();
		setVisible(true);

		addWindowListener(new WindowAdapter() {

			public void windowClosed(java.awt.event.WindowEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
	}

	private void initListe() {
		cbCNPCoordonator = new JComboBox<>();
		// Face posibila autocompletarea combobox-ului cu cnp-urile existente in baza de date
		AutoCompletion.enable(cbCNPCoordonator);
		cbCNPCoordonator.addItem("");
		List<String> lista = BD.getInstance().getListaCNP();
		for (var cnp : lista) {
			cbCNPCoordonator.addItem(cnp);
		}

		cbTipEmisiune = new JComboBox<>();
		lista = BD.getInstance().getListaTipEmisiuni();
		for (var tip : lista) {
			cbTipEmisiune.addItem(tip);
		}

		cbNumeStudio = new JComboBox<>();
		lista = BD.getInstance().getListaNumeStudio();
		for (var nume : lista) {
			cbNumeStudio.addItem(nume);
		}
	}

	private void resetFields() {
		tNumeEmisiune.setText("");
		cbCNPCoordonator.setSelectedIndex(0);
		cbTipEmisiune.setSelectedIndex(0);
		cbNumeStudio.setSelectedIndex(0);
	}


	private class AscultatorButon implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String numeEmisiune = tNumeEmisiune.getText();
			String cnpCoordonator = cbCNPCoordonator.getSelectedItem().toString();
			String tipEmisiune = cbTipEmisiune.getSelectedItem().toString();
			String numeStudio = cbNumeStudio.getSelectedItem().toString();

			if (numeEmisiune.isEmpty() || cnpCoordonator.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Trebuie sa alegeti un nume al emisiunii si un coordonator!");
			} else {
				try {
					BD.getInstance().adaugaEmisiune(numeEmisiune, cnpCoordonator, tipEmisiune, numeStudio);
					JOptionPane.showMessageDialog(null, "Emisiune adaugata cu succes!");
					resetFields();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Emisiunea exista deja");
				}
			}

		}

	}

}
