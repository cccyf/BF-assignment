package saveInfo;

import java.util.ArrayList;

public class StackInfo {
	private ArrayList<String> UndoList = new ArrayList<String>();
	private ArrayList<String> RedoList = new ArrayList<String>();

	public boolean canRedo() {
		if (!RedoList.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean canUndo() {
		if (!UndoList.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean canAdd(String str) {

		if (UndoList.size() > 0) {
			if (UndoList.get(UndoList.size() - 1).equals(str)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public void UndoListPush(String str) {
		if (canAdd(str)) {
			UndoList.add(str);
		//	System.out.println(str);
		}
	}

	public void RedoListPush(String str) {
		RedoList.add(str);
		System.out.println(str);
	}

	public String popUndo() {
		String ret = "0";
		if (canUndo()) {
			RedoListPush(UndoList.get(UndoList.size() - 1));
			UndoList.remove(UndoList.size() - 1);
			//System.out.println("pushRedo");
			if (canUndo()) {
				ret = UndoList.get(UndoList.size() - 1);
			}
		}
		return ret;
	}

	public String popRedo() {
		String ret = "0";
		if (canRedo()) {
			ret =  RedoList.get(RedoList.size() - 1);
			UndoList.add(ret);
			RedoList.remove(RedoList.size() - 1);
		}
		System.out.println(ret);
		return ret;
	}

}
