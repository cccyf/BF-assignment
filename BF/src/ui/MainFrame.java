package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
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
	private JLabel login;
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
   
	public MainFrame() {
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		// frame.setLayout(new BorderLayout());
		frame.setLayout(null);
		/*
		 * JMenuBar menuBar = new JMenuBar(); JMenu fileMenu = new
		 * JMenu("File"); menuBar.add(fileMenu); JMenuItem newMenuItem = new
		 * JMenuItem("New"); fileMenu.add(newMenuItem); JMenuItem openMenuItem =
		 * new JMenuItem("Open"); fileMenu.add(openMenuItem); JMenuItem
		 * saveMenuItem = new JMenuItem("Save"); fileMenu.add(saveMenuItem);
		 * JMenuItem runMenuItem = new JMenuItem("Run");
		 * fileMenu.add(runMenuItem); frame.setJMenuBar(menuBar);
		 * 
		 * newMenuItem.addActionListener(new MenuItemActionListener());
		 * openMenuItem.addActionListener(new MenuItemActionListener());
		 * saveMenuItem.addActionListener(new SaveActionListener());
		 * runMenuItem.addActionListener(new MenuItemActionListener());
		 */

		user = new JPanel();
		user.setBounds(0, 0, 60, 472);
		//Color lightBlue = new Color(240,255,255);
		//Color lightPink = new Color(255,235,225);
		user.setBackground(Color.white);
		user.setLayout(new GridLayout(10, 1));
		
		userLabel = new JLabel("Hello");// userLabel.addMouseListener(new
										// UserItemMouseAdapter());
		newFile = new JLabel("new");
		newFile.addMouseListener(new UserItemMouseAdapter());
		saveFile = new JLabel("save");
		saveFile.addMouseListener(new SaveMouseListener());
		runFile = new JLabel("run");
		runFile.addMouseListener(new UserItemMouseAdapter());
		login = new JLabel("log in");
		login.addMouseListener(new UserItemMouseAdapter());
		delete = new JLabel("delete");
		delete.addMouseListener(new deleteMouseListener());
		undo = new JLabel("undo");
		redo = new JLabel("redo");
		user.add(userLabel);
		user.add(login);
		user.add(newFile);
		user.add(saveFile);
		user.add(delete);
		user.add(runFile);
		user.add(undo);
		user.add(redo);


		fileNames = new DefaultListModel();
		//fileNames.addElement(newFile);
		fileNames.addElement("a");
		fileNames.addElement("b");
		fileNames.addElement("c");
		fileList = new JList(fileNames);
		fileList.setBackground(Color.lightGray);
		fileList.setForeground(Color.WHITE);
		fileList.setFont(this.getFont());

		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		fileList.setBounds(62, 0, 86, 300);
		versions = new DefaultListModel();
		versions.addElement("a");
		versions.addElement("b");
		fileVersions = new JList(versions);
		fileVersions.setBackground(Color.lightGray);
		fileVersions.setForeground(Color.white);
		fileVersions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileVersions.setBounds(62, 302, 86, 176);
		frame.add(fileVersions);
		// frame.setUndecorated(true);
		// frame.setOpacity(0.8f);
		ImageIcon frameImage = new ImageIcon("/Users/chengyunfei/Desktop/壁纸/20100819001418.jpeg");
		frame.setIconImage(frameImage.getImage());
		frame.add(user);
		textArea = new JTextArea("Start coding...");
		textArea.setMargin(new Insets(10, 10, 10, 10));
		// textArea.setBounds(150,0,500,300);
		textArea.setLineWrap(true);
		textArea.setDragEnabled(true);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(150, 0, 598, 300);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Color middleGray = new Color(96, 96, 96);
		textArea.setBackground(middleGray);
		textArea.setForeground(Color.lightGray);
		scroll.setOpaque(true);
		frame.add(scroll);

		// boolean hasTyped =false;
		textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				textArea.setForeground(Color.WHITE);
				if (hasTyped) {
					// textArea.setText(textArea.getText()+e.getKeyChar());
				} else {
					textArea.setText("");
					hasTyped = true;
				}
			}
		});
		JPanel twoArgs = new JPanel();
		// twoArgs.setLayout(new GridLayout(1, 2));
		twoArgs.setLayout(null);
		twoArgs.setOpaque(true);
		twoArgs.setBounds(150, 302, 600, 176);

		argsArea = new JTextArea("args...");
		argsArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				argsArea.setForeground(Color.WHITE);
				if (argsHasTyped) {
					// textArea.setText(textArea.getText()+e.getKeyChar());
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
		// argScr.setBackground();
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
		frame.setLocation(300, 100);
		frame.setVisible(true);
		/*
		 * System.out.println(scroll.getSize());
		 * System.out.println(twoArgs.getSize());
		 * System.out.println(argsArea.getSize());
		 */
	}

	class UserItemMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object cmd = e.getSource();
			if (cmd == runFile) {
				resultArea.setText(runMethod());
			} else if (cmd == saveFile) {

			} else if (cmd == newFile) {
			}else if(cmd==login){
				//String inputValue = JOptionPane.showInputDialog("Please input a value"); 
			
			}
		}


	}

/*	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		// @Override
		/*public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				textArea.setText("Open");
			} else if (cmd.equals("Save")) {
				textArea.setText("Save");
			} else if (cmd.equals("Run")) {
				resultArea.setText(runMethod());
			}
		}
	}*/

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

	class SaveMouseListener extends MouseAdapter{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			String code = textArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, admin,"code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}

	class deleteMouseListener extends MouseAdapter{

		public void mouseClicked(MouseEvent e) {
			
		}

	}
}
