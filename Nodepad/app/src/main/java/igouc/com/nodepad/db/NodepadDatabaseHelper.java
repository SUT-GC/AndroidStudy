package igouc.com.nodepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gouchao on 16-4-26.
 */
public class NodepadDatabaseHelper extends SQLiteOpenHelper {


	private final String CREATE_DB_SQL="" +
			"create table node(" +
				"_id integer primary key autoincrement," +
				"title varchar(20) not null," +
				"content varchar(100)," +
				"datetime integer not null," +
				"label int not null," +
				"power int not null" +
			")";

	public NodepadDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
