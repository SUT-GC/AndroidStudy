package igouc.com.nodepad.util;

/**
 * Created by gouchao on 16-4-26.
 */
public class ChangeIntAndLabel {
	private static  final String[] labels = new String[]{
		"默认","生活","学习","工作"
	};
	private final static int COUNT = 3;
	public static String getLabelByInt(int i){
		return labels[i];
	}
	public static int getIntByLabel(String label){
		int result = -1;
		for(int i = 0; i < COUNT; i++){
			if(labels[i].equals(label)){
				result = i;
				break;
			}
		}
		if(result == -1){
			try {
				throw new Exception("对不起，没有找到对应的label码");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
