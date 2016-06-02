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
	private final String ABOUT_USE_IT="" +
			"insert into node(" +
				"title,content,datetime,label,power)" +
				"values(" +
				"'Tm9kZXBhZOS7i+e7jQ==','Tm9kZXBhZCAyLjAg5Yqf6IO95LuL57uNCgrmlrDlip/og73ku4vnu43vvJoKICAgIAogICAgTm9kZXBhZCAyLjAg5paw5re75Yqg5o+S5YWl5Zu+54mH5Yqf6IO9OgogICAgCiAgICDlvZPkuovku7blhoXlrrnovpPlhaXmoYbojrflvpfnhKbngrnml7bvvIzmj5LlhaXlm77niYfmjInpkq7lsIbkvJroh6rliqjmmL7npLrlh7rmnaXkvpvnlKjmiLfkvb/nlKjvvJvlpLHljrvnhKbngrnml7bvvIzmjInpkq7lsIbkvJrpmpDol4/jgIIKICAgIOmmlumhteaYvuekuuaXtu+8jOWwhuS8muaKiuWbvueJh+i9rOaNouaIkOWtl+espuS4siLjgJDlm77niYfjgJEi5Lul5L6b6L6o6K6k44CCCgrlt7Lmj5Dkvpvlip/og73vvJoKICAgIAogICAgTm9kZXBhZCAxLjAgOgoKICAgICAgICBhLua3u+WKoOS6i+S7tgogICAgICAgIGIu5Yig6Zmk5LqL5Lu2CiAgICAgICAgYy7kv67mlLnkuovku7YKICAgICAgICBkLue9rumhtuS6i+S7tgogICAgICAgIGUu5oyJ5qCH562+5YiG57G75LqL5Lu2CgogICAgTm9kZXBhZCAxLjEgOgogICAgICAgIAogICAgICAgIDEu5L+u5pS55bGP5bmV5peL6L2s77yM6aaW6aG16YeN5paw5p6E6YCg77yM5a+86Ie055qE5YiG57G75pi+56S65peg5pWIYnVn44CCCiAgICAgICAgMi7mt7vliqDlr7nmlofmnKzlhoXlrrnov5vooYxCYXNlNjTovaznoIHvvIzpmLLmraLpnZ7ms5XlrZfnrKblr7nmlbDmja7lupPnmoTlvbHlk43jgIIKCiAgICBOb2RlcGFkIDEuMiA6CgogICAgICAgIDEu5re75Yqg55So5oi36Ieq5Li76K6+572u55WM6Z2i6YWN6Imy5pa55qGI77yMMS4y54mI5pys5o+Q5L6b5LqG5LiL6Z2i5Yeg56eN6YWN6Imy5pa55qGI77yaCiAgICAgICAgICAgIDEuMS7ns7vnu5/pu5jorqQKICAgICAgICAgICAgMS4yLuWPpOactOe6uOW8oAogICAgICAgICAgICAxLjMu6buR55m9566A57qmCiAgICAgICAgMi7lj5bmtojov5vlhaXnvJbovpHkuI7mlrDlu7rnlYzpnaLlkI7ovpPlhaXmoYboh6rliqjojrflj5bnhKbngrnvvIzlop7lvLrnlKjmiLfkvZPpqozjgIIK'," +
				"'1462348249295','0','0')";

	public NodepadDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB_SQL);
		db.execSQL(ABOUT_USE_IT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
