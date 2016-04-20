##SharedPreferences 与 File IO介绍

###SharedPreferences

保存的数据是Key-value 数据
SharedPreferences**接口**主要是读取Preferences数据，提供了如下方法来访问数据：    
* boolean contains(String key) 
* abstract Map<String, ?> getAll() ：获取全部key-value对
* getXxx(String key, xxx defValue) ：获取只等key的value，如果key不存在，返回defValue
    - Xxx: boolean, int, float, long, String

> SharedPreferences 没有提供写入数据的能力，通过`.edit()` 来获取对应的Editor对象，Editor提供了下面方法写入数据：         
> 
>   * SharedPreferences.Editor clear() :清空SharedPreferences里的数据
>   * SharedPreferences.Editor putXxx(String key, xxx value)
>   * SharedPreferences.Editor remove(String key)
>   * boolean commit(): 当Editor编辑完成后，调用该方法提交修改


**SharedPreferences本身是一个接口，无法创建SharedPreferences的实例，只能通过context.getSharedPreferences(String name, int mode)来获取实例**
*mode参数值如下：*    

* Context.MODE_PRIVATE: 只能被本程序读写
* Context.MODE_WORLD_READABLE: 能被其他程序读
* Context.MODE_WORLD_WRITEABKE: 能被其他程序写      
          
`测试代码`

    public class SharedPreferencesTest extends Activity {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        Button bur,buw;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sharedpreferences_main_activity);

            preferences = getSharedPreferences("gouc", Context.MODE_PRIVATE);
            editor = preferences.edit();
            bur = (Button) findViewById(R.id.read);
            buw = (Button) findViewById(R.id.write);

            bur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = "";
                    String time = preferences.getString("time",null);
                    Integer num = preferences.getInt("num",0);
                    System.out.println("time = "+time+", num = "+num);

                    if(time == null || num == 0){
                        result = "您还未写入数据";
                    }else{
                        result = "time = "+time+", num = "+num;
                    }
                    Toast.makeText(SharedPreferencesTest.this,result,Toast.LENGTH_LONG).show();
                }
            });

            buw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    editor.putString("time",format.format(new Date()));
                    editor.putInt("num", (int) (Math.random() * 100));
                    //提交编辑区的缓冲
                    editor.commit();
                }
            });
        }
    }

> 如果要读取别的程序的SharedPreferences，需要获得其程序的Context
> `otherContext = CreatePackageContext("other程序的包, Context.CONTEXT_IGNORE_SECURITY);`      

---

###File IO

Context提供了如下两个方法打开程序数据文件的IO流

* FileInputStream openFileInput(String name);
* FileOutputStream openFileOutput(String name, int mode);
    - MODE_PRIVATE :只能当前程序读写
    - MODE_APPEND :以追加的方式打开
    - MODE_WORLD_READABLE :其他程序可读
    - MODE_WORLD_WRITEABLE :其他程序可写

Context提供如下方法访问数据文件:    

* getDir(String name, int mode) :获取或创建name对应的子目录
* File getFilesDir() :获取该程序数据文件的绝对路径
* String[] fileList() :返回该程序数据文件夹下的全部文件
* deleteFile(String) :删除该程序数据文件夹下的制定文件       

`测试代码`

    package android.com.myapplication;

    import android.app.Activity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.PrintStream;

    /**
     - Created by gouchao on 16-4-20.
     */
    public class FileTest extends Activity {
        Button write,read;
        EditText input;
        TextView show;
        FileInputStream inputStreams;
        FileOutputStream outputStreams;
        String FILE_NAME = "igouc";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.filetest_main_activity);

            input = (EditText) findViewById(R.id.file_edittext);
            show = (TextView) findViewById(R.id.file_textview_1);
            write = (Button) findViewById(R.id.file_button_input);
            read = (Button) findViewById(R.id.file_button_read);


            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        outputStreams = openFileOutput(FILE_NAME,MODE_APPEND);
                        PrintStream ps = new PrintStream(outputStreams);
                        String stringValue = input.getText().toString();
                        ps.println(stringValue);

                        ps.flush();
                        outputStreams.close();
                        ps.close();
                        input.setText("");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        inputStreams = openFileInput(FILE_NAME);
                        byte[] bytes = new byte[2014];
                        StringBuffer sb = new StringBuffer();
                        int count = 0;
                        while((count = inputStreams.read(bytes)) > 0){
                            sb.append(new String(bytes,0,count));
                        }
                        show.setText(sb);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

**在SD卡中进行File IO**
* 判断是否插入SD卡并且判断是否具有读写权限：`Environment.getExternalStrageState().equals(Environment.MEDIA_MOUNTED)`
* `File Environment.getExternalStrageDirectory()` :获取外部存储器卡的目录 //external 外部的，strage 存储器
* 使用IO进行读写
> 要求在Manifest文件中加上权限
> //在内存卡中创建和删除文件的权限
> `<user-permission android:name="android.permisson.MOUNT_UNMOUNT_FILESYSYTEMS"`
> //向SD卡中写入数据的权限
> `<user-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"`

**如果不想用Environment获取file ,可以直接访问/mnt/sdcarf/ (这个是sd卡的目录)**


