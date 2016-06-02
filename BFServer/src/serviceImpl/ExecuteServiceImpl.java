//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	int point = 0;
	char[] saveInfo = new char[100];
	char[] inputs;
	boolean[] isWhoseTurn;
	boolean isSecond = false;
	int NumOfInput = 0;
	int saveNum;
	int[] savePosition;
	boolean hasPosition;

	public void initInfo(String param) {
		for (int m = 0; m < 100; m++) {
			saveInfo[m] = 0;
		}
		point = 0;
		if (param != "") {
			inputs = new char[param.length()];
			isWhoseTurn = new boolean[param.length()];
			NumOfInput = param.length();
			for (int i = 0; i < param.length(); i++) {
				inputs[i] = param.charAt(i);
				isWhoseTurn[i] = false;
			}
		}
	}

	public int judgeTurn() {
		int turn;
		for (turn = 0; turn < NumOfInput; turn++) {
			if (!isWhoseTurn[turn]) {
				isWhoseTurn[turn] = true;
				break;
			}
		}
		return turn;
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		String ret ="";
		initInfo(param);
		saveNum = 0;
		int t = 0;
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == '[') {
				saveNum++;
			}
		}
		savePosition = new int[saveNum];
		for (int i = 0; i < saveNum; i++) {
			savePosition[i] = -1;
		}
		for (int position = 0; position < code.length(); position++) {
			switch (code.charAt(position)) {
			case ',':
				int turn = judgeTurn();
				System.out.println(turn);
				saveInfo[point] = inputs[turn];
				continue;
			case '>':
				point++;
				continue;
			case '<':
				point--;
				continue;
			case '.':
				//System.out.print(saveInfo[point]);
				ret+=saveInfo[point];
				continue;
			case '+':
				saveInfo[point]++;
				continue;
			case '-':
				saveInfo[point]--;
				continue;
			case '[':
				for (t = 0; t < saveNum; t++) {
					if (savePosition[t] == -1) {
						savePosition[t] = position;
						break;
					}
				}
				continue;
			case ']':
				for (t = 0; t < saveNum; t++) {
					if (savePosition[t] == -1) {
						break;
					}
				}
				if (saveInfo[point] != 0) {
					position = savePosition[t - 1] - 1;
				}
				savePosition[t - 1] = -1;
				continue;
			}
		}
		return ret;
	}

/*	public static void main(String[] args) {
		ExecuteServiceImpl a = new ExecuteServiceImpl();
		try {
			String b = a.execute(
					"++++++++++[>+++++++>++++++++++>+++>+<<<<-] >++.>+.+++++++..+++.>++.<<+++++++++++++++. >.+++.------.--------.>+.>. ",
					"");
			System.out.print(b);
			// System.out.print("\n");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
