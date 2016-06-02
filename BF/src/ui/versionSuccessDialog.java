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

public class versionSuccessDialog extends Dialog {
	JLabel successLabel;
	JButton yes;

	public versionSuccessDialog(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		initDia();
		// TODO Auto-generated constructor stub
	}

	public void dis() {
		this.dispose();
	}

	public void initDia() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 300) / 2, (scrSize.height - 300) / 2, 300, 200);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.setLayout(null);
		successLabel = new JLabel("Change version successfully!");
		successLabel.setBounds(50, 40, 200, 50);
		yes = new JButton("ok");
		yes.setBounds(100, 120, 100, 50);
		yes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.add(yes);
		this.add(successLabel);
	}
}
