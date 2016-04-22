##Android 菜单

Menu接口是一个父接口，该接口下有两个子接口 :    

* SubMenu :代表一个子菜单，可以包含1～N个MenuItem
* ContextMenu : 代表一个上下文菜单，可以包含1～N个MenuItem

Android 的不同菜单具有的特征 :    

* 选项菜单：不支持勾选标记，不支持嵌套子菜单
* 子菜单（SubMenu）：不支持菜单项的图标，不支持嵌套子菜单
* 上下文菜单（ContextMenu）：不支持菜单快捷键和图标

Menu接口定义了如下的方法：    

* MenuItem add(int titleRes) :添加一个新的菜单项
*  MenuItem add(CharSequence title) : 添加一个新的菜单项
* MenuItem add(int groupid, int itemid, int order, int titleRes) :添加一个新的处于groupId组的菜单项
* MenuItem add(int groupid, int itemid, int order, CharSequence title) :添加一个新的groupId
* SubMenu addSubMenu(int titleRes) : 添加一个新的子菜单
* SubMenu addSubMenu(CharSequence title) : 添加一个新的子菜单
* SubMenu addSubMenu(int groupid, int itemid, int order, int titleRes) :添加一个新的处于groupId组的子菜单
* SubMenu addSubMenu(int groupid, int itemid, int order, CharSequence title) :添加一个新的groupId

> 上面的方法总结起来就是：add方法添加菜单项，addSubMenu方法添加子菜单。
> 这些重载的方法区别是：是否将子菜单，菜单项添加到指定菜单组中，是否使用资源文件中的字符串资源来设置
> MenuItem 是对Menu进行控制的对象：当PAI>=11的时候，MenuItem图标是不显示的
SubMenu 继承了Menu，额外提供了如下方法:    

* SubMenu setHeaderIcon(Drawable icon) :设置菜单头的图标
* SubMenu setHeaderIcon(int iconRes) :设置菜单头的图标
* SubMenu setHeaderTitle(int titleRes) :设置菜单头标题
* SubMenu setHeaderTitle(CharSequence title) :设置菜单头标题
* SubMenu setHeaderView(View view) :使用View设置菜单头    

重写Activity的onCreateOptionsMenu(Menu menu)方法，在方法里添加菜单项或者子菜单就可以了    
如果希望程序相应单击事件，重写Activity的onOpyionsItemSelected(MenuItem mi)方法即可    


ContextMenu额外提供的方法:    

* ContextMenu setHeaderIcon(Drawable icon) : 为上下文菜单设置图标
* ContextMenu setHeaderIcon(int iconRes) : 为上下文菜单设置图标
* ContextMenu setHeaderTitle(int titleRes) : 为上下文菜单设置标题

开发上下文菜单的步骤:    

* 重写Activity的onCreateContextMenu(ContextMenu menu, View source, ContextMenu Context, MenuInfo menuinfo)
* 调用Activity的registerForContextMenu(View view)
* 响应时间要重写onContextItemSelected(MenuItem mi)方法

创建选项菜单：    

* 设置菜单项
* 通过XML进行配置
* 设置菜单项点击监听: onOptionsItemSelected()

`代码展示`    

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main,menu);
            //动态添加菜单
            //100是添加菜单项的id
            menu.add(2,100,1,"用add方法添加的菜单");
            menu.addSubMenu("用addSubMenu添加的菜单");
            return super.onCreateOptionsMenu(menu);
        }

    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:id="@+id/putong1" android:title="普通菜单1"></item>
        <item android:id="@+id/putong2" android:title="普通菜单2"/>

    </menu>


这样就添加进菜单了 。。。。   

在Activity中添加菜单点击时间:    

* 直接重写boolean onOptionsItemSelected(MenuItem item)方法

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Toast.makeText(this,item.getTitle()+"被点击了",Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }


