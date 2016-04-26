package igouc.com.nodepad.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gouchao on 16-4-26.
 */
public class ChangeDateAndLong {

	private static DateFormat dateFormat;

	public static String getFromatDateString(long l){
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(l);
		return dateFormat.format(date);
	}
}
