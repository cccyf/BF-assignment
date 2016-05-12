package serviceImpl;

import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public boolean login(String username, String password) throws RemoteException {
		return true;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
