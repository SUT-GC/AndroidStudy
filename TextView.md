##TextView Study
###TextView 的属性
* autoLink (all/email/phone/none/web/map) 当文本为以上内容时，显示为可以点击的链接
* autoText (true/false) 自动执行输入值的拼写纠正
* bufferType (edittable/spannable) editable->可以调用append方法增加textview的内容
* drawableLeft/Right/Top/Bottom 在textView的left..方向上输出一个drawable
* ellipsize (start/end/middle/marquee) 当文字超出范围时候，显示('...'开头/结尾/中间/跑马灯)

    >如果不加singleLine， 则ellipsize效果是不会出来的。而且加上singleLine，跑马灯效果也不会出来(待解决)

* gravity (center) 文字显示中间
* lineSpacingMultiplier 设置行间距倍数
* password 以'.'显示文本
* shadowColor 指定文本阴影颜色
* shadowDx 设置阴影横向的开始位置
* shadowDy 纵向
* shadowRadius 设置阴影半径
* :singleLine设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示。
* text设置显示文本.
* textColor设置文本颜色
* textColorHightlight 被选中的文字底色（默认是蓝色）
* textColorLink 文字链接的颜色
* textScaleX 设置文字的间距
* textSize 设置文字的大小 （单位sp）
* width / height / maxWidth / minWidth / maxHeight / minHeight
* 