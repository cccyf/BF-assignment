package ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SaveStateDia extends Dialog {
	MainFrame frame;
	JLabel success;
	JButton confirm;

	public SaveStateDia(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 300) / 2, (scrSize.height - 300) / 2, 300, 200);
		frame = owner;
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.setLayout(null);
		if (title == "Success!") {
			success = new JLabel("Saved successfully!");
		} else if (title == "Failed!") {
			success = new JLabel("You Haven't Change The Text!");
		} else {
			success = new JLabel("You haven't logged in!");
		}
		success.setBounds(40, 40, 220, 50);
		success.setHorizontalAlignment(SwingConstants.CENTER);
		success.setVerticalAlignment(SwingConstants.CENTER);
		confirm = new JButton("ok");
		confirm.setBounds(100, 120, 100, 50);
		confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.add(success);
		this.add(confirm);
		// TODO Auto-generated constructor stub
	}

	public void dis() {
		this.dispose();
	}
}
