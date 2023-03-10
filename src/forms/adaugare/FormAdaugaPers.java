package forms.adaugare;


import bazaDate.BD;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.text.ParseException;

public class FormAdaugaPers extends JDialog {
	private JTextField tNume, tPrenume, tMeserie, tDomiciliu;
	private JFormattedTextField tCNP;
	private JButton bAdauga, bAnuleaza;
	private final AscultatorButoane ab;

	public FormAdaugaPers(JFrame parent, boolean modal) {
		super(parent, modal);
		parent.setVisible(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		ab = new AscultatorButoane();

		tNume = new JTextField(15);
		tPrenume = new JTextField(15);
		try {
			MaskFormatter formatter = new MaskFormatter("#############");
			tCNP = new JFormattedTextField(formatter);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		tMeserie = new JTextField(15);
		tDomiciliu = new JTextField(15);
		bAdauga = new JButton("Adauga");
		bAnuleaza = new JButton("Anuleaza");

		bAdauga.addActionListener(ab);
		bAnuleaza.addActionListener(ab);

		setLayout(new GridLayout(6, 2, 5, 5));
		add(new JLabel("Nume"));
		add(tNume);
		add(new JLabel("Prenume"));
		add(tPrenume);
		add(new JLabel("CNP"));
		add(tCNP);
		add(new JLabel("Meserie"));
		add(tMeserie);
		add(new JLabel("Domiciliu"));
		add(tDomiciliu);
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

	private void resetValori() {
		tNume.setText("");
		tPrenume.setText("");
		tCNP.setText("");
		tMeserie.setText("");
		tDomiciliu.setText("");
	}


	private class AscultatorButoane implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bAdauga) {
				String nume = tNume.getText();
				String prenume = tPrenume.getText();
				String cnp = tCNP.getText();
				String meserie = tMeserie.getText();
				String domiciliu = tDomiciliu.getText();
				if (nume.equals("") || prenume.equals("") || cnp.equals("") || meserie.equals("") ||
				    domiciliu.equals("")) {
					JOptionPane.showMessageDialog(null, "Toate campurile sunt obligatorii!");
				} else {
					try {
						BD.getInstance().adaugaPersoana(nume, prenume, cnp, meserie, domiciliu);
						JOptionPane.showMessageDialog(FormAdaugaPers.this, "Persoana a fost adaugata cu succes!");
						resetValori();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
				return;
			}

			if (e.getSource() == bAnuleaza) {
				dispose();
			}
		}

	}

}
