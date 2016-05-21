package ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SaveStateDia extends Dialog {
	MainFrame frame;
	JLabel success;
	JButton confirm;

	public SaveStateDia(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 250) / 2, (scrSize.height - 150) / 2, 250, 150);
		frame = owner;
		if (title == "Success") {
			success = new JLabel("Saved successfully!");
		} else if (title == "Failed!") {
			success = new JLabel("Failed!You Haven't Change The Text");
		} else {
			success = new JLabel("You haven't logged in!");
		}
		success.setBounds(50, 40, 80, 20);
		confirm = new JButton("ok");
		confirm.setBounds(30, 100, 30, 30);
		confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}

		});
		this.add(success);
		this.add(confirm);
		// TODO Auto-generated constructor stub
	}

}
