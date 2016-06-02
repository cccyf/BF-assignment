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

public class NotSaveDialog extends Dialog {
	JLabel notSave;
	JButton yes;
	JButton no;
	MainFrame mf;
	public NotSaveDialog(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		// TODO Auto-generated constructor stub
		mf = owner;
		initAll();
	}
	private void initAll(){
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 300) / 2, (scrSize.height - 300) / 2, 300, 200);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.setLayout(null);
		notSave = new JLabel("You haven't save this file!");
		notSave.setBounds(50, 40, 200, 50);
		yes = new JButton("Save");
		yes.setBounds(50, 120, 75, 50);
		yes.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mf.saveFile(mf.nowSelectedFile);;
				dis();
			}
			
		});
		no = new JButton("Not Save");
		no.setBounds(175, 120, 75, 50);
		no.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dis();
			}

		});
		this.add(yes);
		this.add(no);
		this.add(notSave);
	}
	public void dis() {
		this.dispose();
	}

}
