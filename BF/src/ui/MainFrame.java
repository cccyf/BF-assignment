package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.font.*;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.GrayFilter;
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
import saveInfo.StackInfo;

public class MainFrame extends JFrame {
	private JTextArea textArea;
	private JTextArea resultArea;
	private JTextArea argsArea;
	private JLabel photo;
	private JLabel userLabel;
	private JLabel newFile;
	private JLabel saveFile;
	private JLabel runFile;
	private JLabel log;
	private JLabel delete;
	private JLabel undo;
	private JLabel redo;
	// boolean hasTyped = false;
	// boolean argsHasTyped = false;
	private JPanel user;
	private JList fileList;
	private DefaultListModel fileNames;
	private JList fileVersions;
	private DefaultListModel versions;
	private String admin;
	private JFrame frame;
	boolean hasChosen = false;
	Color userColor = new Color(205, 255, 243);
	Color userIn = new Color(190, 240, 228);
	Color userPress = new Color(180, 230, 218);
	private Timer timer;
	private StackInfo stack;
	private LinkedList<StackInfo> stacks = new LinkedList<StackInfo>();
	private boolean hasCanceled = false;
	private boolean hasUndo = false;
	private boolean hasSaved = true;
	public String nowSelectedFile = "";

	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Server");
		frame.setLayout(null);
		// Color userColor = new Color(205, 255, 243);
		// Color userIn = new Color(200,250,238);
		user = new JPanel();

		user.setBounds(0, 0, 60, 472);
		user.setBackground(userColor);
		user.setLayout(new GridLayout(8, 1));
		// photo = new JLabel();
		userLabel = new JLabel("userID");
		userLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		// userLabel.setBackground(Color.CYAN);
		// userLabel.setOpaque(true);
		// userLabel.
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setVerticalAlignment(SwingConstants.CENTER);
		newFile = new JLabel("new");
		newFile.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		newFile.addMouseListener(new UserItemMouseAdapter());
		newFile.setHorizontalAlignment(SwingConstants.CENTER);
		newFile.setVerticalAlignment(SwingConstants.CENTER);
		saveFile = new JLabel("save");
		saveFile.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		saveFile.addMouseListener(new UserItemMouseAdapter());
		saveFile.setHorizontalAlignment(SwingConstants.CENTER);
		saveFile.setVerticalAlignment(SwingConstants.CENTER);
		runFile = new JLabel("run");
		runFile.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		runFile.addMouseListener(new UserItemMouseAdapter());
		runFile.setHorizontalAlignment(SwingConstants.CENTER);
		runFile.setVerticalAlignment(SwingConstants.CENTER);
		log = new JLabel("log in");
		log.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		log.addMouseListener(new UserItemMouseAdapter());
		log.setHorizontalAlignment(SwingConstants.CENTER);
		log.setVerticalAlignment(SwingConstants.CENTER);
		delete = new JLabel("delete");
		delete.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		delete.addMouseListener(new deleteMouseListener());
		delete.setHorizontalAlignment(SwingConstants.CENTER);
		delete.setVerticalAlignment(SwingConstants.CENTER);
		undo = new JLabel("undo");
		undo.addMouseListener(new undoListener());
		undo.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		redo = new JLabel("redo");
		redo.addMouseListener(new redoListener());
		redo.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
		undo.setHorizontalAlignment(SwingConstants.CENTER);
		undo.setVerticalAlignment(SwingConstants.CENTER);
		redo.setHorizontalAlignment(SwingConstants.CENTER);
		redo.setVerticalAlignment(SwingConstants.CENTER);
		user.add(userLabel);
		user.add(log);
		user.add(newFile);
		user.add(saveFile);
		// user.add(delete);
		user.add(runFile);
		user.add(undo);
		user.add(redo);
		Color listColor = new Color(166, 253, 232);
		fileNames = new DefaultListModel();

		fileList = new JList(fileNames);

		fileList.setBackground(listColor);
		fileList.setForeground(Color.darkGray);
		fileList.setFont(this.getFont());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setListSelected();
		fileList.setBounds(62, 0, 186, 300);
		versions = new DefaultListModel();
		fileVersions = new JList(versions);
		fileVersions.setBackground(listColor);
		fileVersions.setForeground(Color.darkGray);
		fileVersions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileVersions.setBounds(62, 302, 186, 176);
		this.setVersionSelected();
		frame.add(fileVersions);

		final ImageIcon frameImage = new ImageIcon("/Users/chengyunfei/Desktop/code.jpg");
		// frame.setIconImage(frameImage.getImage());
		frame.add(user);
		textArea = new JTextArea() {
			Image image = frameImage.getImage();
			// Image grayImage = GrayFilter.createDisabledImage(image);//
			// 这里要用到import
			// javax.swing.GrayFilter;
			{
				setOpaque(false);
			} // instance initializer

			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};
		textArea.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if (hasCanceled) {
					timerInitial();
				}
				hasUndo = false;
				hasSaved = false;
				// System.out.println(hasSaved);
			}

		});
		// textArea
		textArea.setMargin(new Insets(10, 10, 10, 10));
		java.awt.Font textFont = new java.awt.Font("Monaco", 1, 15);
		textArea.setFont(textFont);
		textArea.setLineWrap(true);
		textArea.setDragEnabled(true);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(250, 0, 478, 300);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// Color listColor1 = new Color(206, 202, 232);
		Color middleGray2 = new Color(150, 150, 150);
		// textArea.setBackground(Color.pink);
		textArea.setForeground(Color.darkGray);
		scroll.setOpaque(true);
		frame.add(scroll);
		// textArea.setName("text");
		// boolean hasTyped =false;
		/*
		 * textArea.addKeyListener(new KeyAdapter() { public void
		 * keyPressed(KeyEvent e) { textArea.setForeground(Color.WHITE); if
		 * (hasTyped) {
		 * 
		 * } else { textArea.setText(""); hasTyped = true; } } });
		 */
		JPanel twoArgs = new JPanel();

		twoArgs.setLayout(null);
		twoArgs.setOpaque(true);
		twoArgs.setBounds(250, 302, 600, 176);

		argsArea = new JTextArea() {
			final ImageIcon par = new ImageIcon("/Users/chengyunfei/Desktop/parameter.jpg");
			Image image = par.getImage();
			// Image grayImage = GrayFilter.createDisabledImage(image);//
			// 这里要用到import
			// javax.swing.GrayFilter;
			{
				setOpaque(false);
			} // instance initializer

			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};
		/*
		 * argsArea.addKeyListener(new KeyAdapter() { public void
		 * keyPressed(KeyEvent e) { argsArea.setForeground(Color.WHITE); if
		 * (argsHasTyped) {
		 * 
		 * } else { argsArea.setText(""); argsHasTyped = true; } } });
		 */
		// argsArea.setBackground(Color.lightGray);
		argsArea.setForeground(Color.darkGray);
		argsArea.setFont(textFont);
		JScrollPane argScr = new JScrollPane(argsArea);
		argScr.setBounds(0, 0, 238, 176);

		argScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		argsArea.setBounds(0, 0, 238, 176);
		// frame.add(argsArea, BorderLayout.SOUTH);
		// 显示结果
		resultArea = new JTextArea() {
			final ImageIcon res = new ImageIcon("/Users/chengyunfei/Desktop/result.jpg");
			Image image = res.getImage();
			// Image grayImage = GrayFilter.createDisabledImage(image);//
			// 这里要用到import
			// javax.swing.GrayFilter;
			{
				setOpaque(false);
			} // instance initializer

			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};
		// resultArea.setText();
		resultArea.setEditable(false);
		resultArea.setFont(textFont);
		resultArea.setBounds(240, 0, 238, 176);
		JScrollPane resScr = new JScrollPane(resultArea);
		resScr.setBounds(240, 0, 238, 176);
		resScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// resultArea.setOpaque(true);
		// resultArea.setBackground(Color.lightGray);
		resultArea.setForeground(Color.darkGray);

		twoArgs.add(argScr);
		twoArgs.add(resScr);
		frame.add(twoArgs);
		frame.add(fileList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(730, 480);
		Dimension fraDim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((fraDim.width - 730) / 2, (fraDim.height - 480) / 2);
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
					if (!hasSaved) {
						if (shouldSave(nowSelectedFile)) {
							newNSD();
							hasSaved = true;
						}
					}
					// System.out.println(selected);
					try {
						textArea.setText(RemoteHelper.getInstance().getIOService().readFile(admin, selected, null));
						setVersions(selected);
						// System.out.println(fileList.getSelectedIndex());
						stack = stacks.get(fileList.getSelectedIndex());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					nowSelectedFile = selected;
				}
			}

		});
	}

	public void newNSD() {
		NotSaveDialog nsd = new NotSaveDialog(this, "Haven't Save File", true);
		nsd.setVisible(true);
	}

	private int first = 0;

	public void newVSD() {
		first++;
		// if (first % 2 == 1) {
		versionSuccessDialog vsd = new versionSuccessDialog(this, "Change Version Successful", true);
		vsd.setVisible(true);
		// }
	}

	public void setVersionSelected() {
		fileVersions.addMouseListener(new MouseAdapter() {

			/*
			 * public void valueChanged(ListSelectionEvent e) { // TODO
			 * Auto-generated method stub String selected = null;
			 * //System.out.println(hasChosen); // hasChosen = true; if
			 * (hasChosen) { if (!fileVersions.isSelectionEmpty()) { selected =
			 * fileVersions.getSelectedValue().toString(); try {
			 * textArea.setText(RemoteHelper.getInstance().getIOService().
			 * readFile(admin, fileList.getSelectedValue().toString(),
			 * selected)); if (fileVersions.getModel().getSize() != 1) {
			 * newVSD(); System.out.println("new"); hasChosen = false; } } catch
			 * (RemoteException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); } // newVSD(); } } else { hasChosen = true;
			 * } // newVSD(); }
			 */

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (hasChosen) {
					if (!fileVersions.isSelectionEmpty()) {
						JList a = (JList) (e.getSource());
						String selected = a.getSelectedValue().toString();
						try {
							textArea.setText(RemoteHelper.getInstance().getIOService().readFile(admin,
									fileList.getSelectedValue().toString(), selected));
							if (fileVersions.getModel().getSize() != 1) {
								newVSD();
								System.out.println("new");
								hasChosen = false;
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// System.out.println(b);
					}
				}else{
					hasChosen = true;
				}
			}

		});
	}

	public void setVersions(String fileName) {
		try {
			versions.removeAllElements();
			String[] vers = RemoteHelper.getInstance().getIOService().readVersions(admin, fileName);
			if (vers != null) {
				for (int index = 0; index < vers.length; index++) {
					versions.addElement(vers[index]);
				}
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
				// saveFile();

				if ((admin != null) && (!fileNames.isEmpty())) {
					try {
						String used = null;
						used = RemoteHelper.getInstance().getIOService().readFile(admin,
								fileList.getSelectedValue().toString(), null);
						if ((used == null) && (textArea.getText() == null)) {
							createSaveState("Wrong!");
						} else if (textArea.getText().toString().equals(used)) {
							createSaveState("Failed!");
						} else {
							boolean canSave = RemoteHelper.getInstance().getIOService().writeFile(
									textArea.getText().toString(), admin, fileList.getSelectedValue().toString());
							System.out.println(textArea.getText().toString());
							System.out.println(textArea.getText());
							if (canSave) {
								createSaveState("Success!");
								setVersions(fileList.getSelectedValue().toString());
								repaint();
								hasSaved = true;
							}
						}
					} catch (RemoteException e1) { // TODO Auto-generated catch
													// block
						e1.printStackTrace();
					}
				} else {
					createSaveState("Wrong");
				}

			} else if (cmd == newFile) {
				createNew("New");
			} else if (cmd == log) {
				createLog();
			}
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userPress);
			label.setOpaque(true);
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userIn);
			label.setOpaque(true);
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}

	}

	/*
	 * public void saveFile() { if ((admin != null) && (!fileNames.isEmpty())) {
	 * try { String used = null; used =
	 * RemoteHelper.getInstance().getIOService().readFile(admin,
	 * fileList.getSelectedValue().toString(), null); if ((used == null) &&
	 * (textArea.getText() == null)) { createSaveState("Wrong!"); } else if
	 * (textArea.getText().toString().equals(used)) {
	 * createSaveState("Failed!"); } else { boolean canSave =
	 * RemoteHelper.getInstance().getIOService().writeFile(textArea.getText().
	 * toString(), admin, fileList.getSelectedValue().toString());
	 * System.out.println(textArea.getText().toString());
	 * System.out.println(textArea.getText()); if (canSave) {
	 * createSaveState("Success!");
	 * setVersions(fileList.getSelectedValue().toString()); repaint(); hasSaved
	 * = true; } } } catch (RemoteException e1) { // TODO Auto-generated catch
	 * block e1.printStackTrace(); } } else { createSaveState("Wrong"); } }
	 */

	public boolean shouldSave(String name) {
		try {
			String used = null;
			used = RemoteHelper.getInstance().getIOService().readFile(admin, name, null);
			if (textArea.getText().toString().equals(used)) {
				return false;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void saveFile(String name) {

		try {
			boolean canSave = RemoteHelper.getInstance().getIOService().writeFile(textArea.getText().toString(), admin,
					name);
			// System.out.println(textArea.getText().toString());
			// System.out.println(textArea.getText());
			if (canSave) {
				createSaveState("Success!");
				setVersions(fileList.getSelectedValue().toString());
				repaint();
				hasSaved = true;
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	class undoListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			timer.cancel();
			hasCanceled = true;
			hasUndo = true;
			String contents = stack.popUndo();
			// System.out.println(contents);
			if (contents != "0") {
				// hasUndo = true;
				textArea.setText(contents);
			}
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userPress);
			label.setOpaque(true);
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userIn);
			label.setOpaque(true);
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}
	}

	class redoListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (hasUndo) {
				timer.cancel();
				hasCanceled = true;
				String contents = stack.popRedo();
				if (contents != "0") {
					textArea.setText(contents);
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userPress);
			label.setOpaque(true);
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userIn);
			label.setOpaque(true);
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			JLabel label = (JLabel) cmd;
			label.setBackground(userColor);
			label.setOpaque(false);
		}
	}

	private void createSaveState(String str) {
		SaveStateDia saveFailure = new SaveStateDia(this, str, true);
		saveFailure.setVisible(true);
	}

	public void createNew(String in) {
		NewFileDialog newFileDia = new NewFileDialog(this, in, true);
		newFileDia.setVisible(true);
	}

	public void setItemSelected(String sel) {
		int index = fileNames.indexOf(sel);
		// System.out.println(index);
		addStack(index);
		this.fileList.setSelectedValue(sel, true);
	}

	public void createLog() {
		if (log.getText() == "log in") {
			LoginDialog logDia = new LoginDialog(this, "Log in", true);
			logDia.setVisible(true);
		} else {
			try {
				boolean canLogOut = RemoteHelper.getInstance().getUserService().logout(admin);
				if (canLogOut) {
					this.allSetInitial();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void allSetInitial() {
		/*textArea.setText(null);
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
		hasSaved = true;
		hasCanceled = false;
		hasUndo = false;
		hasChosen = false;
		stack = null;
		stacks = new LinkedList<StackInfo>();
		timer.cancel();
		this.repaint();*/
        new MainFrame();
        this.dispose();
	}

	public void createFileStacks() {
		for (int i = 0; i < fileNames.size(); i++) {
			stacks.add(new StackInfo());
		}
		// System.out.println(stacks.size());
	}

	public void addStack(int i) {
		stacks.add(i, new StackInfo());
		// System.out.println(fileNames.size()+" "+i+" "+stacks.size());

	}

	public void timerInitial() {
		timer = new Timer();
		// timer.schedule(task, delay);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (stack != null) {
					stack.UndoListPush(getTextArea());
				}
			}

		};
		timer.schedule(task, 0, 600);

	}

	public String runMethod() {
		String code = textArea.getText();
		String param = argsArea.getText() + "\n";
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
