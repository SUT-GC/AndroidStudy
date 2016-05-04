package igouc.com.nodepad.Empty;

import igouc.com.nodepad.R;

/**
 * Created by gouchao on 16-4-29.
 */
public class ColorSchema {
	public static boolean checkStateKeyLegal(int key){
		return key >= 0 && key <= 2 ? true : false;
	}
	public static class Default{
		public static int getContentBackground(){
			return R.color.white;
		}
		public static int getItemBackground(){
			return R.color.pageColor;
		}
		public static int getAddBackground(){
			return R.color.contentback;
		}
		public static int getAddFrame(){
			return R.color.white;
		}
	}
	public static class Paper{
		public static int getContentBackground(){
			return R.color.white;
		}
		public static int getItemBackground(){
			return R.color.paper;
		}
		public static int getAddBackground(){
			return R.color.paper;
		}
		public static int getAddFrame(){
			return R.color.white;
		}
	}
	public static class Simple{
		public static int getContentBackground(){
			return R.color.white;
		}
		public static int getItemBackground(){
			return R.color.white;
		}
		public static int getAddBackground(){
			return R.color.white;
		}
		public static int getAddFrame(){
			return R.color.white;
		}
	}
}
