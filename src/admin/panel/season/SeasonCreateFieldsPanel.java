package admin.panel.season;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class SeasonCreateFieldsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblWeeks;
	private JLabel lblContestants;
	private JLabel lblTribe1;
	private JLabel lblTribe2;

	private JSpinner spnWeek;
	private JSpinner spnContestant;

	private JTextField txtTribe1;
	private JTextField txtTribe2;

	private JButton btnCreate;

	private GridBagLayout gbFields;
	private GridBagConstraints gbFieldsConst;

	public SeasonCreateFieldsPanel(JLabel _lblWeeks, JSpinner _spnWeek,
			JLabel _lblContestants, JSpinner _spnContestant, JLabel _lblTribe1,
			JTextField _txtTribe1, JLabel _lblTribe2, JTextField _txtTribe2,
			JButton _btnCreate) {
		super();

		lblWeeks = _lblWeeks;
		lblContestants = _lblContestants;
		lblTribe1 = _lblTribe1;
		lblTribe2 = _lblTribe2;

		spnWeek = _spnWeek;
		spnContestant = _spnContestant;

		txtTribe1 = _txtTribe1;
		txtTribe2 = _txtTribe2;

		btnCreate = _btnCreate;

		gbFields = new GridBagLayout();
		gbFieldsConst = new GridBagConstraints();

		setLayout(gbFields);
		setupGridBag(gbFields, gbFieldsConst);
	}

	private void setupGridBag(GridBagLayout gbl, GridBagConstraints gbc) {

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 80, 0, 0);
		add(lblWeeks, gbc);

		gbc.gridx = 1;
		// gbc.insets = new Insets(0, 0, 0, 40);
		add(spnWeek, gbc);

		gbc.gridx = 2;
		add(lblContestants, gbc);

		gbc.gridx = 3;
		gbc.insets = new Insets(0, 0, 0, 80);
		add(spnContestant, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 80, 0, 0);
		add(lblTribe1, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 0, 0, 80);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(txtTribe1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 80, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		add(lblTribe2, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 0, 0, 80);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(txtTribe2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(0, 95, 0, 80);
		add(btnCreate, gbc);
	}
}
