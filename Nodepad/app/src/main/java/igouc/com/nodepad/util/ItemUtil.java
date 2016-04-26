package igouc.com.nodepad.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import igouc.com.nodepad.Empty.Item;
import igouc.com.nodepad.db.NodepadDatabaseHelper;

/**
 * Created by gouchao on 16-4-26.
 */
public class ItemUtil {
	private static NodepadDatabaseHelper mdbhelper;
	private static SQLiteDatabase sqLiteDatabase;
	private static final String dbname = "gcnodepad";
	private static  final int version = 1;


	public static void init(Context context){
		mdbhelper = new NodepadDatabaseHelper(context,dbname,null,version);
		sqLiteDatabase = mdbhelper.getReadableDatabase();
	}

	public static List<Map<String,Object>> getAllItems(){
		List<Map<String,Object>> listNode = new ArrayList<>();
		String sql = "select * from node " +
						"order by datetime desc , power desc";
		Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
		Map<String,Object> item;
		while(cursor.moveToNext()){
			item  = new HashMap<>();
			item.put("id",cursor.getInt(0));
			item.put("title",cursor.getString(1));
			item.put("content",cursor.getString(2));
			item.put("datetime",cursor.getLong(3));
			item.put("label",cursor.getInt(4));
			item.put("power",cursor.getInt(5));
			listNode.add(item);
		}
		return listNode;
	}
	public static void insertItem(Item item){
		String sql = "insert into node (" +
						"title,content,datetime,label,power" +
					")values('"
					+item.getTitle()+"','"
					+item.getContent()+"','"
					+item.getDatetime()+"','"
					+item.getLabel()+"','"
					+item.getPower()+
					"')";
		sqLiteDatabase.execSQL(sql);
	}

}
