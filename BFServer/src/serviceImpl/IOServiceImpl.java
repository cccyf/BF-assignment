package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import service.IOService;

public class IOServiceImpl implements IOService {
	private ArrayList<versionInfo> infoList = new ArrayList<versionInfo>();

	@Override
	public boolean newFile(String userId, String fileName) {
		File newFile = new File(
				"/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files");
		// System.out.println(userId);
		File f = new File(newFile, fileName);
		File fHistory = new File(newFile, "history" + fileName);
		if (f.exists()) {
			return false;
		} else {
			try {
				f.createNewFile();
				fHistory.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		// setInfoList(userId,"history"+fileName);
		File newFile = new File(
				"/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files");
		// System.out.println(userId);
		File Hf = new File(newFile, "history" + fileName);
		File f = new File(newFile, fileName);
		// File f= new
		// File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/"
		// + userId + "/files/"+fileName);
		try {
			Date now = new Date();
			DateFormat timeFormat = new SimpleDateFormat("yy-MM-dd,HH:mm:ss");
			// DateFormat timeFormat = new SimpleDateFormat("MMddHHmmss");
			String version = timeFormat.format(now);
			// if (f.exists()) {
			FileWriter Hfw = new FileWriter(Hf, true);
			Hfw.write("No." + version + "/" + file + "e" + "\n");
			// System.out.println(version);
			Hfw.flush();
			Hfw.close();
			FileWriter fw = new FileWriter(f, false);
			fw.write(file + "e");
			fw.flush();
			fw.close();
			// } else {
			// f.createNewFile();
			// FileWriter fw = new FileWriter(f, false);
			// fw.write("No."+version + "/" + file +"e"+ "\n");
			// System.out.println("new");
			// fw.flush();
			// fw.close();
			// }
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void setInfoList(String userId, String fileName) {
		if (!infoList.isEmpty()) {
			infoList.clear();
		}
		FileReader fr;
		File readFile = new File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId
				+ "/files/" + fileName);
		System.out.println(fileName);
		try {
			fr = new FileReader(readFile);
			BufferedReader fb = new BufferedReader(fr);
			String rl = null;
			String ver = null;
			String information = null;
			while (true) {
				if ((rl = fb.readLine()) != null) {
					if ((rl.contains("No."))) {
						if (ver != null) {
							infoList.add(new versionInfo(ver, information));
							ver = null;
							information = null;
						}
						if ((rl.contains("e"))) {

							ver = rl.split("/")[0];
							if (rl.split("/")[1].length() > 1) {
								information = rl.split("/")[1].split("e")[0];
							} else {
								information = null;
							}

						} else {
							ver = rl.split("/")[0];
							information = rl.split("/")[1];
						}
					} else if (rl.contains("e")) {
						information += rl.split("e")[0];
					} else {
						information += rl;
					}
				} else {
					if (ver != null) {
						infoList.add(new versionInfo(ver, information));
						ver = null;
						information = null;
					}
					break;
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// FileInputStream fs = new FileInputStream(readFile);
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String readFile(String userId, String fileName, String version) {
		// TODO Auto-generated method stub
		// String[] ret;
		// String inside = "";
		setInfoList(userId, "history" + fileName);

		if (version != null) {
			// System.out.println(version);
			// System.out.println(infoList.get(0).getVersion());
			for (int index = 0; index < infoList.size(); index++) {
				if (infoList.get(index).getVersion().equals(version)) {
					String in = infoList.get(index).getInfo();
					System.out.println(in);
					File file = new File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/"
							+ userId + "/files/" + fileName);
					try {
						FileWriter fw = new FileWriter(file);
						System.out.println(fileName);
						fw.write(in);
						fw.flush();
						fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}

		File rf = new File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files/"
				+ fileName);
		String result ="";
		String line = null;
		try {
			FileReader fr = new FileReader(rf);
			BufferedReader br = new BufferedReader(fr);
			while (((line = br.readLine()) != null) && (line != "e")) {

				if (!(line).contains("e")) {
					result += line;
				} else {
					result += line.split("e")[0];
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public String[] readFileList(String userId) {
		// TODO Auto-generated method stub
		File newFile = new File(
				"/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files");
		File[] txts = newFile.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if ((pathname.getName().endsWith(".txt")) && (!pathname.getName().startsWith("history"))) {
					return true;
				}
				return false;
			}

		});
		if (txts.length > 0) {
			String[] names = new String[txts.length];
			for (int i = 0; i < txts.length; i++) {
				names[i] = txts[i].getName();
			}
			return names;
		}
		return null;
	}

	public String[] readVersions(String userId, String fileName) throws RemoteException {
		String[] versions;
		setInfoList(userId, "history" + fileName);
		versions = new String[infoList.size()];
		for (int in = 0; in < infoList.size(); in++) {
			versions[in] = infoList.get(in).getVersion();
		}
		return versions;

	}

	public class versionInfo {
		private String version = null;
		private String info = null;

		public versionInfo(String v, String i) {
			version = v;
			info = i;
		}

		public String getVersion() {
			return version;
		}

		public String getInfo() {
			return info;
		}
	}
}
