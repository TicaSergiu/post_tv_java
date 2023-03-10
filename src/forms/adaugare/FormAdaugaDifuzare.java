package forms.adaugare;

import bazaDate.BD;
import forms.AutoCompletion;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class FormAdaugaDifuzare extends JDialog {
	private JComboBox<String> comboBoxEmisiune;
	private JTextField tZi, tOraStart, tOraSfarsit;
	private final JButton bAdauga, bAnuleaza;

	public FormAdaugaDifuzare(JFrame parent, boolean modal) {
		super(parent, modal);
		parent.setVisible(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		AscultatorButoane ab = new AscultatorButoane();

		//Initializeaza input-urile pt emisiune, zi, ora start si sfrasit
		initFields();

		bAdauga = new JButton("Adauga");
		bAnuleaza = new JButton("Anuleaza");
		bAdauga.addActionListener(ab);
		bAnuleaza.addActionListener(e -> dispose());


		JLabel lEmisiune = new JLabel("Emisiune:");
		JLabel lZi = new JLabel("Zi:");
		JLabel lOraStart = new JLabel("Ora start:");
		JLabel lOraSfarsit = new JLabel("Ora sfarsit:");

		setLayout(new GridLayout(5, 2, 5, 5));
		add(lEmisiune);
		add(comboBoxEmisiune);
		add(lZi);
		add(tZi);
		add(lOraStart);
		add(tOraStart);
		add(lOraSfarsit);
		add(tOraSfarsit);
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

	private void initFields() {
		comboBoxEmisiune = new JComboBox<>();
		AutoCompletion.enable(comboBoxEmisiune);
		comboBoxEmisiune.addItem("");
		List<String> numeEmisiuni = BD.getInstance().getNumeEmisiuni();
		for (String numeEmisiune : numeEmisiuni) {
			comboBoxEmisiune.addItem(numeEmisiune);
		}

		try {
			// 01-Jan-22
			MaskFormatter formatterZi = new MaskFormatter("20##-##-##");
			formatterZi.setPlaceholderCharacter('_');
			tZi = new JFormattedTextField(formatterZi);

			MaskFormatter formatterOraStart = new MaskFormatter("##:##:##");
			formatterOraStart.setPlaceholderCharacter('_');
			tOraStart = new JFormattedTextField(formatterOraStart);

			MaskFormatter formatterOraSfarsit = new MaskFormatter("##:##:##");
			formatterOraSfarsit.setPlaceholderCharacter('_');
			tOraSfarsit = new JFormattedTextField(formatterOraSfarsit);
		} catch (ParseException ignored) {
		}
	}

	private void resetFields() {
		comboBoxEmisiune.setSelectedIndex(0);
		tZi.setText("");
		tOraStart.setText("");
		tOraSfarsit.setText("");
	}

	private class AscultatorButoane implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String emisiune = comboBoxEmisiune.getSelectedItem().toString();
			String zi = Date.valueOf(tZi.getText()).toString();
			Date zii = Date.valueOf(tZi.getText());
			Timestamp oraStart = Timestamp.valueOf(zi + " " + tOraStart.getText());
			Timestamp oraSfarsit = Timestamp.valueOf(zi + " " + tOraSfarsit.getText());
			try {
				BD.getInstance().adaugaDifuzare(emisiune, zii, oraStart, oraSfarsit);
				JOptionPane.showMessageDialog(FormAdaugaDifuzare.this, "Difuzarea a fost adaugata cu succes");
				resetFields();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(FormAdaugaDifuzare.this, ex.getMessage());
			}
		}

	}

}
