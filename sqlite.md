##SQLite 数据库

> Android提供SQLiteDataBase 代表一个数据库（数据库文件）

**SQLiteDataBase提供如下方法打开一个文件对应的数据库：**

* static SQLiteDatabase openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags)
* static SQLiteDatabase openOrCreateDatabase(File file, SQLiteDatabase.CursorFactory factory)
* static SQLiteDatabase openOrCreateDatabase(String path, SQLiteDatabase.CursorFactory factory)

**获取SQLiteDatabase之后，调用下面方法操作数据库：**

* void execSQL(String sql , Onject[] bindArgs):执行带占位符的SQL语句
* void ececSQL(String sql) :执行sql语句
* Cursor rawQuery(String sql, String[] selectionArgs) :执行带占位符的SQL查询
* beginTransaction() :开启事物
* endTransaction() :关闭事物
* long insert(String table, String nullColumnHack, ContentValues values) :向指定表中插入数据
    - table：表
    - nullColumnHack：强行插入null值的数据列名
    - values：要插入的值
        + ContentValues是一个key-values对 values.put("key","value"): key代表列名
* int update(String table, ContentValues values, String whereClause, String[] whereArgs)
    - table 表
    - values 代表更新的数据
    - whereClause 满足这个参数的语句将会被更新
    - whereArgs 用户向whereClause的占位符中传参数
    - `int result = db.update("table",values,"_id>?",new Integer[]{2})`
* int delete(String table, String whereClause, String whereArgs) :删除符合whereCause的记录
* Cursor query(boolean distinct, String table, String[] columns, String whereClause, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
    - distinct 是否去掉重复的记录
    - table 表
    - columns 要查询出的列名
    - whereClause where条件
    - selectionArgs whereClause的占位符
    - groupBy 控制分组的语句（group by 后面的句子）
    - having 用户分组过滤的部分
    - orderBy 排序后面的部分
    - limit limit后面的部分
    - `Cursor cursor = db.query("table", new String[]{"_id", "name"}, "name like ?", "new String[]{"_gc%"}, bull, null, "_id desc","5,10")`

**获得Cursor之后进行数据读取操作**    
        sqLiteOpenHelper = new My

* boolean move(int offset) :讲指针向上或者向下移动行数
    - offset > 0 向下移动
    - offset < 0 向上移动
* boolean moveToFirst()
* boolean moveToLast()
* boolean moveToNext()
* boolean moveToPrevious()
* boolean moveToPosition(int position) :将指针移动到指定行，如果移动成功，返回true，否则返回false
* getXxx(int colume) 读取该行指定列的数据    

Cursor 封装成 SimpleCursorAdapter，这个adapter将会作为ListView的种子种入
`SimpleAdapter adapter = new SimpleCursorAdapter(Context, Resource, cursor, new String[]{列}, new int[]{R.id.xxx},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)`

> 如果要将Cursor包装成SimpleAdapter，必须以`_id`作为主键

**SQLiteOpenHelper 类**     

实际项目中很少使用SQLiteDatabase    
通常是集成SQLiteOpenHelper开发子类，并且通过子类的getReadableDatabase方法与getWritableDatabase方法打开数据库    
创建SQLiteOpenHelper的子类，通常重写 onCreate(SQLiteDatabase db) 与 onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)方法    
可以使用的方法如下:    

* sychronized SQLiteDatabase getReadableDatabase() :以读写的方式打开数据库对应的SQLiteDatabase
* sychronized SQLiteDatabase getWriteableDatabase() :以写的方式打开
* sychronized void close()    






