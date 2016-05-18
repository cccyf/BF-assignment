package serviceImpl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import service.IOService;

public class IOServiceImpl implements IOService {

	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File newFile = new File(
				"/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files");
		// System.out.println(userId);
		File f = new File(newFile, fileName);
		try {
			if (f.exists()) {
				return false;
			} else {
				f.createNewFile();
				FileWriter fw = new FileWriter(f, false);
				fw.write(file);
				fw.flush();
				fw.close();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		File readFile = new File(
				"/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + userId + "/files/"+fileName);
		// System.out.println(userId);
	//	File f = new File(newFile, fileName);
		try {
			FileReader fr = new FileReader(readFile);
		//	String inside = fr
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
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
				if (pathname.getName().endsWith(".txt")) {
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

}
