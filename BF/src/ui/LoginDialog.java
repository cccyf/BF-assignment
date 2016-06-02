package ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import rmi.RemoteHelper;

public class LoginDialog extends Dialog {
	JLabel userL;
	JLabel passwordL;
	JButton loginB;
	JTextArea userT;
	JPasswordField passwordP;
	JButton registerB;
	JLabel out;
	MainFrame jf;
	String nameInput;

	public LoginDialog(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		jf = owner;
		initDia();

	}

	public void initDia() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((scrSize.width - 300) / 2, (scrSize.height - 300) / 2, 300, 200);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}

		});
		this.setLayout(null);
		userL = new JLabel("User name : ");
		userL.setBounds(30, 35, 80, 40);
		// userL.setOpaque(false);
		userT = new JTextArea(5, 5);
		userT.setBounds(140, 35, 120, 40);

		passwordL = new JLabel("Password : ");
		passwordL.setOpaque(false);
		passwordL.setBounds(30, 95, 80, 40);
		passwordP = new JPasswordField();
		passwordP.setBounds(140, 95, 120, 40);
		passwordP.setEchoChar('*');

		loginB = new JButton("Log In");
		loginB.setBounds(30, 155, 105, 40);
		loginB.addActionListener(new ButtonActionListener());
		registerB = new JButton("Register");
		registerB.setBounds(165, 155, 105, 40);
		registerB.addActionListener(new ButtonActionListener());

		this.add(userL);
		this.add(userT);
		this.add(passwordL);
		this.add(passwordP);
		this.add(loginB);
		this.add(registerB);

	}

	public void outDia(String output) {
		// this.dispose();
		this.removeAll();
		this.repaint();
		out = new JLabel(output);// ,Label.CENTER);
		out.setHorizontalAlignment(SwingConstants.CENTER);
		out.setVerticalAlignment(SwingConstants.TOP);
		out.setBounds(50, 60, 200, 60);
		// out.setText(output);
		// out.setDefaultLocale(Label.CENTER);
		JButton back = new JButton("back");
		back.setBounds(110, 110, 80, 60);
		back.addActionListener(new backActionListener());
		this.add(out);
		this.add(back);
		this.repaint();
	}

	/*
	 * public void rem() { this.removeAll(); repaint(); }
	 */

	/*
	 * public void rep() { repaint(); }
	 */

	class backActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String result = out.getText();
			if ((result == "Wrong!") || (result == "Already existed!")) {
				removeAll();
				initDia();
				repaint();
			} else {
				// jf.setUserName("Hi , "+nameInput+"");
				dispose();
			}

		}

	}

	class ButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			String out = "Wrong!";
			if (cmd == "Log In") {
				nameInput = userT.getText();
				char[] password = passwordP.getPassword();
				String pass = new String(password);
				try {
					boolean canLogIn = RemoteHelper.getInstance().getUserService().login(nameInput, pass);
					if (canLogIn) {
						out = "Success!";
						jf.setUserName(nameInput);
						jf.setListSelected();
						jf.setVersionSelected();
						// jf.setLogOut();
						setList();

					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			} else if (cmd == "Register") {
				nameInput = userT.getText();
				char[] password = passwordP.getPassword();
				String pass = new String(password);
				// System.out.println(pass);
				try {
					boolean canRegister = RemoteHelper.getInstance().getUserService().register(nameInput, pass);
					if (canRegister) {
						out = "Welcome!";
						jf.setUserName(nameInput);
						jf.setListSelected();
						jf.log();
					} else {
						out = "Already existed!";
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
			outDia(out);

		}

	}

	public void setList() {
		try {
			jf.setFileList(RemoteHelper.getInstance().getIOService().readFileList(jf.getUserName()));
			jf.log();
			// System.out.println(RemoteHelper.getInstance().getIOService().readFileList(jf.getUserName()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
