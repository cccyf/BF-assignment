package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public boolean login(String username, String password) throws RemoteException {
		File f = new File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + username);
	    if(f.exists()){
	    	File pass = new File(f,"password.txt");
	    	try {
				FileReader  pr = new FileReader(pass);
				BufferedReader br = new BufferedReader(pr);
				String rl = br.readLine();
				br.close();
			//	System.out.println(rl);
			//	System.out.println(password);
			//	System.out.println("a");
				return password.equals(rl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

	@Override
	public boolean register(String name, String pass) throws RemoteException {
		File f = new File("/Users/chengyunfei/百度云同步盘/workspace_v1.0/workspace/BFServer/userInfo/" + name);
		File p = new File(
				f,"password.txt");
		File files = new File(f,"files");
		if (!f.exists()) {
			f.mkdirs();
			files.mkdir();
			try {
				p.createNewFile();
				//files.createNewFile();
				//FileOutputStream pw = new FileOutputStream(p);
				//OutputStreamWriter pWriter = new OutputStreamWriter(pw);
				FileWriter pWriter = new FileWriter(p);
				pWriter.write(pass);
				pWriter.flush();
				pWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		}
		return false;
	}

}
