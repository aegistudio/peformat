package net.aegistudio.pe.viewer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Information extends JPanel {
	private static final long serialVersionUID = 1L;

	public Information(String key, String value) {
		JLabel label = new JLabel("<html>" + key + "</html>");
		label.setPreferredSize(new Dimension(100, 30));
		label.setHorizontalAlignment(JLabel.RIGHT);
		super.add(label);
		
		JTextField data = new JTextField(value);
		data.setEditable(false);
		//data.setEnabled(false);
		data.setBackground(Color.WHITE);
		data.setPreferredSize(new Dimension(150, 30));
		super.add(data);
		
		super.setBorder(null);
	}
}
