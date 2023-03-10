package forms.afisare;

import forms.adaugare.FormAdaugaEmisiune;
import tabele.TabelEmisiuni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class FormAfiseazaEmisiuni extends JFrame {
	private boolean modModificare = true;
	private JButton bStergeEmisiune, bAdauga, bSalveaza;
	TabelEmisiuni tabelEmisiuni;

	public FormAfiseazaEmisiuni(JFrame parent) {
		super("Emisiuni");
		parent.setVisible(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tabelEmisiuni = new TabelEmisiuni();
		JScrollPane scrollPane = new JScrollPane(tabelEmisiuni);
		JButton bModificaTabel = new JButton("Modifica tabel");
		bSalveaza = new JButton("Salveaza tabel");
		bAdauga = new JButton("Adauga emisiune");
		bStergeEmisiune = new JButton("Sterge emisiune");

		toggleVizibilitate();

		bModificaTabel.addActionListener(e -> {
			toggleVizibilitate();
			actualizeazaTabel();
		});

		bSalveaza.addActionListener(e -> {
			tabelEmisiuni.salveazaTabel();
			JOptionPane.showMessageDialog(this, "Tabelul a fost salvat!");
			toggleVizibilitate();
		});

		bAdauga.addActionListener(e -> new FormAdaugaEmisiune(this, true));

		bStergeEmisiune.addActionListener(e -> tabelEmisiuni.stergeEmisiune());

		JPanel pButoane = new JPanel(new GridLayout(4, 1, 5, 5));
		pButoane.add(bModificaTabel);
		pButoane.add(bAdauga);
		pButoane.add(bStergeEmisiune);
		pButoane.add(bSalveaza);

		add(new JLabel("Emisiuni:"), BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(pButoane, BorderLayout.EAST);


		pack();
		setVisible(true);

		addWindowListener(new WindowAdapter() {

			public void windowClosed(java.awt.event.WindowEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
	}

	public void actualizeazaTabel() {
		tabelEmisiuni.modificaTabel(modModificare);
	}


	private void toggleVizibilitate() {
		modModificare = !modModificare;
		bSalveaza.setVisible(modModificare);
		bStergeEmisiune.setVisible(modModificare);
		bAdauga.setVisible(modModificare);
	}

}
