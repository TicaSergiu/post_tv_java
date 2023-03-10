package forms.afisare;

import tabele.TabelDifuzari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class FormAfiseazaDifuzari extends JDialog {
	public FormAfiseazaDifuzari(JFrame parent, boolean modal) {
		super(parent, modal);
		parent.setVisible(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		TabelDifuzari tabelDifuzari = new TabelDifuzari();
		JScrollPane scrollPane = new JScrollPane(tabelDifuzari);

		add(new JLabel("Difuzari:"), BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		pack();
		setVisible(true);

		addWindowListener(new WindowAdapter() {

			public void windowClosed(java.awt.event.WindowEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
	}

}
