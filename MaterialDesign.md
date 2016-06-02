# Material Design 设计规范

## Material Design 支持库

### FloatingActionBar

* 引入xmlns:app="xxxxx / res-auto"
* app:backgroundTint 设置背景色
* app:fabSize 设置大小
* app:elevation 设置阴影大小
* app:rippleColor 设置点击之后的颜色

### TextInputLayout

* 优化输入的布局效果和用户体验
* 在TextInputLayout必须放置一个EditText
* EditText et = textInputLayout.getEditText()
* et.setTextChangedListener(xxxxx) 设置输入内容改变监听事件
    - textInputLayout.setError("string") 提示错误的string

### Snackbar

```
Snackbar sb = Snackbar.make(View,StringInfor,ShowTime);
sb.show();
sb.setAction("yes",new OnclickListener());
```
### TabLayout
### Navigation View
### CoordinatorLayout
### CollapsingToolbarLayout