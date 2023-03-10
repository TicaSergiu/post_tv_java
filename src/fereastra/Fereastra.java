package fereastra;

import forms.adaugare.FormAdaugaDifuzare;
import forms.adaugare.FormAdaugaEmisiune;
import forms.adaugare.FormAdaugaPers;
import forms.afisare.FormAfiseazaDifuzari;
import forms.afisare.FormAfiseazaEmisiuni;

import javax.swing.*;
import java.awt.*;

public class Fereastra extends JFrame {

	public Fereastra(String titlu) {
		super(titlu);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton bAfiseazaEmisiuni = new JButton("Afiseaza emisiuni");
		JButton bAfiseazaDifuzari = new JButton("Afiseaza difuzari");
		JButton bAdaugaDifuzare = new JButton("Adaugati o difuzare");
		JButton bAdaugaPersoana = new JButton("Adaugati o persoana");

		bAfiseazaEmisiuni.addActionListener(e -> new FormAfiseazaEmisiuni(this));
		bAfiseazaDifuzari.addActionListener(e -> new FormAfiseazaDifuzari(this, true));
		bAdaugaDifuzare.addActionListener(e -> new FormAdaugaDifuzare(this, true));
		bAdaugaPersoana.addActionListener(e -> new FormAdaugaPers(this, true));

		setLayout(new GridLayout(4, 1, 5, 5));
		add(bAfiseazaEmisiuni);
		add(bAfiseazaDifuzari);
		add(bAdaugaDifuzare);
		add(bAdaugaPersoana);

		pack();
		setVisible(true);
	}

}
