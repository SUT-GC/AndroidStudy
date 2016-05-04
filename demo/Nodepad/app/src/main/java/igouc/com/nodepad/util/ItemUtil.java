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

	//查询出所有的Item
	public static List<Map<String,Object>> getAllItems(){
		List<Map<String,Object>> listNode = new ArrayList<>();
		String sql = "select * from node " +
						"order by power desc , datetime desc";
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

	//向数据库中插入Item
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

	//根据id删除Item
	public static void deleteItemById(int id){
		String sql = "delete from node where _id = '"+id+"'";
		sqLiteDatabase.execSQL(sql);
	}

	//根据Id查询Item
	public static Item selectItemById(int id){
		Item item = new Item();
		String sql = "select * from node where _id = '"+id+"'";
		Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

		while (cursor.moveToNext()){
			item.setId(cursor.getInt(0));
			item.setTitle(cursor.getString(1));
			item.setContent(cursor.getString(2));
			item.setDatetime(cursor.getLong(3));
			item.setLabel(cursor.getInt(4));
			item.setPower(cursor.getInt(5));
		}

		return item;
	}


	//根据id更新Item
	public static void updateItemById(int id, Item item){
		String sql = "update node set " +
						" title = '"+item.getTitle()+"'," +
						" content = '"+item.getContent()+"'," +
						" label = '"+item.getLabel()+"'" +
					 " where _id = '"+id+"'";
		sqLiteDatabase.execSQL(sql);
	}

	//更新Item中的power
	public static void setPowerById(int id, int power){
		String sql = "update node set " +
						"power = '"+power+"'" +
					 "where _id = '"+id+"'";
		sqLiteDatabase.execSQL(sql);
	}

	//根据label查询出相关的Items
	public static List<Map<String,Object>> getItemsByLabel(int label){
		List<Map<String,Object>> listNode = new ArrayList<>();
		String sql = "select * from node " +
						"where label = '"+label+"'" +
						"order by power desc , datetime desc";
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
}
