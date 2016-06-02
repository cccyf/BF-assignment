package ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import rmi.RemoteHelper;

public class NewFileDialog extends Dialog {
	JLabel nameLabel;
	JLabel txtLabel;
	JTextArea nameArea;
	JButton confirmBt;
	MainFrame frame;

	public NewFileDialog(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		frame = owner;
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 250) / 2, (scrSize.height - 150) / 2, 250, 150);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}

		});
		// init();
		this.setLayout(null);
		nameLabel = new JLabel("File name : ");
		nameLabel.setBounds(30, 50, 95, 30);
		// nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// nameLabel.setVerticalAlignment(SwingConstants.TOP);
		nameArea = new JTextArea(3, 1);
		nameArea.setBounds(130, 50, 70, 30);
		txtLabel = new JLabel(".txt");
		txtLabel.setBounds(205, 50, 30, 30);
		confirmBt = new JButton("yes");
		confirmBt.setBounds(75, 100, 100, 30);
		confirmBt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				disposeDia();

			}

		});
		addInit();
		// TODO Auto-generated constructor stub
	}

	public void disposeDia() {
		if (frame.getUserName() != null) {
			String name = nameArea.getText() + txtLabel.getText();
			String userId = frame.getUserName();
			//String file = frame.getTextArea();
			try {
				boolean canWrite = RemoteHelper.getInstance().getIOService().newFile(userId, name);
		//		System.out.println(userId);
				if (canWrite) {
					setList();
					//frame.setTextArea(name);
					this.dispose();
				} else {
					this.removeAll();
					wrongInput("This name has been used!");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// this.dispose();
		} else {
			this.removeAll();
			wrongInput("you haven't logged in!");
		}
	}

	public void addInit() {
		this.add(nameLabel);
		this.add(nameArea);
		this.add(txtLabel);
		this.add(confirmBt);
	}

	public void wrongInput(String info) {
		JLabel wrong = new JLabel(info);
		 wrong.setHorizontalAlignment(SwingConstants.CENTER);
		 wrong.setVerticalAlignment(SwingConstants.TOP);
		wrong.setBounds(25, 60, 200, 30);
		this.add(wrong);
		JButton back = new JButton("back");
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeAll();
				addInit();
				repaint();
			}

		});
		back.setBounds(50, 100, 150, 30);
		this.add(back);
		repaint();
	}
	public void setList() {
		try {
			//frame.addStack();
			frame.setFileList(RemoteHelper.getInstance().getIOService().readFileList(frame.getUserName()));
			frame.setItemSelected(new String(nameArea.getText())+".txt");
		//	System.out.println(nameArea.getText());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
