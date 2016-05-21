package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.text.Font;
import rmi.RemoteHelper;

public class MainFrame extends JFrame {
	private JTextArea textArea;
	private JTextArea resultArea;
	private JTextArea argsArea;
	private JLabel userLabel;
	private JLabel newFile;
	private JLabel saveFile;
	private JLabel runFile;
	private JLabel log;
	private JLabel delete;
	private JLabel undo;
	private JLabel redo;
	boolean hasTyped = false;
	boolean argsHasTyped = false;
	private JPanel user;
	private JList fileList;
	private DefaultListModel fileNames;
	private JList fileVersions;
	private DefaultListModel versions;
	private String admin;
	private JFrame frame;
	// private boolean logOut = false;

	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Server");
		frame.setLayout(null);

		user = new JPanel();
		user.setBounds(0, 0, 60, 472);
		user.setBackground(Color.white);
		user.setLayout(new GridLayout(10, 1));

		userLabel = new JLabel("Hello");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setVerticalAlignment(SwingConstants.CENTER);
		newFile = new JLabel("new");
		newFile.addMouseListener(new UserItemMouseAdapter());
		newFile.setHorizontalAlignment(SwingConstants.CENTER);
		newFile.setVerticalAlignment(SwingConstants.CENTER);
		saveFile = new JLabel("save");
		saveFile.addMouseListener(new UserItemMouseAdapter());
		saveFile.setHorizontalAlignment(SwingConstants.CENTER);
		saveFile.setVerticalAlignment(SwingConstants.CENTER);
		runFile = new JLabel("run");
		runFile.addMouseListener(new UserItemMouseAdapter());
		runFile.setHorizontalAlignment(SwingConstants.CENTER);
		runFile.setVerticalAlignment(SwingConstants.CENTER);
		log = new JLabel("log in");
		log.addMouseListener(new UserItemMouseAdapter());
		log.setHorizontalAlignment(SwingConstants.CENTER);
		log.setVerticalAlignment(SwingConstants.CENTER);
		delete = new JLabel("delete");
		delete.addMouseListener(new deleteMouseListener());
		delete.setHorizontalAlignment(SwingConstants.CENTER);
		delete.setVerticalAlignment(SwingConstants.CENTER);
		undo = new JLabel("undo");
		redo = new JLabel("redo");
		undo.setHorizontalAlignment(SwingConstants.CENTER);
		undo.setVerticalAlignment(SwingConstants.CENTER);
		redo.setHorizontalAlignment(SwingConstants.CENTER);
		redo.setVerticalAlignment(SwingConstants.CENTER);
		user.add(userLabel);
		user.add(log);
		user.add(newFile);
		user.add(saveFile);
		user.add(delete);
		user.add(runFile);
		user.add(undo);
		user.add(redo);

		fileNames = new DefaultListModel();

		fileList = new JList(fileNames);

		fileList.setBackground(Color.lightGray);
		fileList.setForeground(Color.WHITE);
		fileList.setFont(this.getFont());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setListSelected();
		fileList.setBounds(62, 0, 86, 300);
		versions = new DefaultListModel();
		fileVersions = new JList(versions);
		fileVersions.setBackground(Color.lightGray);
		fileVersions.setForeground(Color.white);
		fileVersions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileVersions.setBounds(62, 302, 86, 176);
		this.setVersionSelected();
		frame.add(fileVersions);

		ImageIcon frameImage = new ImageIcon("/Users/chengyunfei/Desktop/壁纸/20100819001418.jpeg");
		frame.setIconImage(frameImage.getImage());
		frame.add(user);
		textArea = new JTextArea("Start coding...");
		textArea.setMargin(new Insets(10, 10, 10, 10));

		textArea.setLineWrap(true);
		textArea.setDragEnabled(true);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(150, 0, 598, 300);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Color middleGray1 = new Color(130, 130, 130);
		Color middleGray2 = new Color(150, 150, 150);
		textArea.setBackground(middleGray1);
		textArea.setForeground(Color.lightGray);
		scroll.setOpaque(true);
		frame.add(scroll);

		// boolean hasTyped =false;
		textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				textArea.setForeground(Color.WHITE);
				if (hasTyped) {

				} else {
					textArea.setText("");
					hasTyped = true;
				}
			}
		});
		JPanel twoArgs = new JPanel();

		twoArgs.setLayout(null);
		twoArgs.setOpaque(true);
		twoArgs.setBounds(150, 302, 600, 176);

		argsArea = new JTextArea("args...");
		argsArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				argsArea.setForeground(Color.WHITE);
				if (argsHasTyped) {

				} else {
					argsArea.setText("");
					argsHasTyped = true;
				}
			}
		});
		argsArea.setBackground(Color.gray);
		argsArea.setForeground(Color.white);
		JScrollPane argScr = new JScrollPane(argsArea);
		argScr.setBounds(0, 0, 298, 176);

		argScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		argsArea.setBounds(0, 0, 298, 176);
		// frame.add(argsArea, BorderLayout.SOUTH);
		// 显示结果
		resultArea = new JTextArea();
		resultArea.setText("result");
		resultArea.setEditable(false);
		resultArea.setBounds(300, 0, 298, 176);
		JScrollPane resScr = new JScrollPane(resultArea);
		resScr.setBounds(300, 0, 298, 176);
		resScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// resultArea.setOpaque(true);
		resultArea.setBackground(Color.gray);
		resultArea.setForeground(Color.WHITE);

		twoArgs.add(argScr);
		twoArgs.add(resScr);
		frame.add(twoArgs);
		frame.add(fileList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 480);
		Dimension fraDim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((fraDim.width - 750) / 2, (fraDim.height - 480) / 2);
		frame.setVisible(true);

	}

	public void setFileList(String[] str) {
		fileNames.removeAllElements();
		if (str != null) {
			for (int num = 0; num < str.length; num++) {
				fileNames.addElement(str[num]);
			}
		}
		fileList.repaint();
	}

	/*
	 * public void setLogOut() { logOut = false; }
	 */

	public void setListSelected() {
		fileList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub

				String selected = null;
				// if (logOut == false) {
				if (!fileList.isSelectionEmpty()) {
					selected = fileList.getSelectedValue().toString();
					// System.out.println(selected);
					try {
						textArea.setText(RemoteHelper.getInstance().getIOService().readFile(admin, selected, null));
						setVersions(selected);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
	}

	public void setVersionSelected() {
		fileVersions.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				String selected = null;
				if (!fileVersions.isSelectionEmpty()) {
					selected = fileVersions.getSelectedValue().toString();
					try {
						textArea.setText(RemoteHelper.getInstance().getIOService().readFile(admin,
								fileList.getSelectedValue().toString(), selected));
						// setVersions(selected);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
	}

	public void setVersions(String fileName) {
		try {
			versions.removeAllElements();
			String[] vers = RemoteHelper.getInstance().getIOService().readVersions(admin, fileName);
			for (int index = 0; index < vers.length; index++) {
				versions.addElement(vers[index]);
			}
			repaint();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void log() {
		if (log.getText() == "log in") {
			log.setText("log out");
		} else {
			log.setText("log in");
		}
	}

	public void setUserName(String name) {
		admin = name;
		// System.out.println(admin);
		userLabel.setText("Hi, " + name + " .");
	}

	public String getUserName() {
		// System.out.println(admin);
		return admin;

	}

	public String getTextArea() {
		return textArea.getText();
	}

	class UserItemMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			if (cmd == runFile) {
				resultArea.setText(runMethod());
			} else if (cmd == saveFile) {
				// createSave("Save as...");
				if (admin != null) {
					try {
						String used = null;
						used = RemoteHelper.getInstance().getIOService().readFile(admin,
								fileList.getSelectedValue().toString(), null);
						if ((used == null) && (textArea.getText() == null)) {
							createSaveFailure();
						} else if (textArea.getText().equals(used)) {
							createSaveFailure();
						} else {
							boolean canSave = RemoteHelper.getInstance().getIOService().writeFile(textArea.getText(),
									admin, fileList.getSelectedValue().toString());
							if (canSave) {
								createSaveSucess();
								setVersions(fileList.getSelectedValue().toString());
								repaint();
							}
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					createSaveFailure();
				}
			} else if (cmd == newFile) {
				createNew("New");
			} else if (cmd == log) {
				createLog();
			}
		}

	}

	private void createSaveFailure() {
		SaveStateDia saveFailure = new SaveStateDia(this, "Failure", true);
		saveFailure.setVisible(true);
	}

	private void createSaveSucess() {
		// TODO Auto-generated method stub
		SaveStateDia saveDia = new SaveStateDia(this, "Success", true);
		saveDia.setVisible(true);
	}

	public void createNew(String in) {
		NewFileDialog newFileDia = new NewFileDialog(this, in, true);
		newFileDia.setVisible(true);
	}

	public void setItemSelected(String sel) {
		int index = fileNames.indexOf(sel);
		// System.out.println(index);
		this.fileList.setSelectedValue(sel, true);
	}
	/*
	 * public void setTextArea(String name) { try {
	 * textArea.setText(RemoteHelper.getInstance().getIOService().readFile(
	 * admin, name, null)); } catch (RemoteException e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); } }
	 */

	public void createLog() {
		if (log.getText() == "log in") {
			LoginDialog logDia = new LoginDialog(this, "Log in", true);
			logDia.setVisible(true);
		} else {
			try {
				boolean canLogOut = RemoteHelper.getInstance().getUserService().logout(admin);
				if (canLogOut) {
					// fileNames.removeAllElements();
					// this.fileList = new JList();
					// repaint();
					// logOut = true;
					// this.setListSelected();
					// this.fileVersions.removeAll();
					this.allSetInitial();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void allSetInitial() {
		textArea.setText(null);
		resultArea.setText(null);
		argsArea.setText(null);
		userLabel.setText("Hello");
		this.log();
		// admin = null;
		// this.fileList.addMouseListener(null);
		fileVersions.clearSelection();
		fileList.clearSelection();
		fileNames.removeAllElements();
		versions.removeAllElements();

		// this.repaint();
		admin = null;
		this.repaint();
	}

	/*
	 * class MenuItemActionListener implements ActionListener { /** 子菜单响应事件
	 */
	// @Override
	/*
	 * public void actionPerformed(ActionEvent e) { String cmd =
	 * e.getActionCommand(); if (cmd.equals("Open")) { textArea.setText("Open");
	 * } else if (cmd.equals("Save")) { textArea.setText("Save"); } else if
	 * (cmd.equals("Run")) { resultArea.setText(runMethod()); } } }
	 */

	public String runMethod() {
		String code = textArea.getText();
		String param = argsArea.getText();
		String result = null;
		try {
			result = RemoteHelper.getInstance().getExecuteService().execute(code, param);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	class deleteMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

		}

	}
}
