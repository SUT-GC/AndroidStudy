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


