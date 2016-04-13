#Activity

Activity 是这样一个程序组件，它为用户提供一个用于任务交互的画面。例如，拨打电话，拍照，发邮件。或者查看地图。每一个activity都被分配一个窗口。在这个窗口里，你可以绘制用户交互的内容。 这个窗口通常占满屏幕，但也有可能比屏幕小，并且浮在其它窗口的上面.

一个应用程序通常由多个activity组成，它们彼此保持弱的绑定状态。典型的，当一个activity在一个应用程序内被指定为主activity, 那么当程序第一次启动时，它将第一个展现在用户面前。为了展现不同的内容，每一个activity可以启动另外一个。 每当一个新的activity被启动，那么之前的将被停止。但系统将会把它压入一个栈（“back stack”即后退栈）,当一个新的activity启动，它将被 放到栈顶并获得用户焦点。后台栈遵循后进先出的栈机制。所以当用户完成当前页面并按下返回按钮时，它将被pop出栈（并销毁），之前的activity将被恢复。 （关于后退栈的更多讨论在任务和后退栈）

当一个activity因为另一个activity的启动而被停止，那么其生命周期中的回调方法，将会以状态改变的形式被调用。 activity通过它自身状态的改变可以收到多个回调方法。当系统创建，停止，恢复，销毁它的时候。并且每个回调方法都给你做相应处理工作的机会。 例如，当停止的时候，你的activity应当释放比较大的对象，例如网络连接，数据连接。当你的activity恢复时，你可以请求必须的资源并恢复一些被打断的动作。 这些状态事务的处理就构成了activity的生命周期。 

###创建一个Acticity

要创建一个activity,你必须创建一个Activity（或者它存在的子类）的子类。 在你的子类里，你需要实现系统调用的回调方法，这些方法用于activity在生命周期中进行事务处理。例如创建，停止，恢复，销毁。其中两个最重要的回调方法分别为：

`onCreate()`:你必须实现这个方法。系统会在创建activity的时候调用这个方法。 在实现这个方法的同时，你需要实现你activity的重要组件。 最重要的是，你必须在这里调用 setContentView() 来定义你activity用于用户交互的布局；super.onCreate();
`onPause()`:系统将会调用这个方法作为用户离开activity的首先提示（虽然这并不意味着activity正在被销毁）。这通常是你应该在用户会话之前提交并保存任何更改的时机。 （因为用户可能不会再回到这个activity）

###管理Activity的生命周期

activity可能处于三种基本的状态：
    
* Resumed :     activity在屏幕的前台并且拥有用户的焦点。（这个状态有时也被叫做“running”。） 
* Paused :     另一个activity在前台并拥有焦点，但是本activity还是可见的。也就是说，另外一个activity覆盖在本activity的上面，并且那个activity是部分透明的或没有覆盖整个屏幕。 一个paused的activity是完全存活的（Activity 对象仍然保留在内存里，它保持着所有的状态和成员信息，并且保持与window  manager的联接），但在系统内存严重不足的情况下它能被杀死。 
* Stopped :     本activity被其它的activity完全遮挡住了（本activity目前在后台）。 一个stopped的activity也仍然是存活的（Activity 对象仍然保留在内存中，它保持着所有的状态和成员信息，但是不再与window manager联接了）。但是，对于用户而言它已经不再可见了，并且当其它地方需要内存时它将会被杀死。

> 如果activity被paused或stopped了，则系统可以从内存中删除它，通过请求finish（调用它的 finish() 方法）或者直接杀死它的进程。 
> 当这个activity被再次启动时（在被finish或者kill后），它必须被完全重建。
> 
> >onCreate  //The activity is being created 创建
> >onStart   //The activity is about to become visible 可见
> >onResume  //The activity has become visible (it is now "resumed") 置顶
> >onPause   //Another activity is taking focus (this activity is about to be "paused") 取消置顶 之后可以被杀死
> >onStop    //The activity is no longer visible (it is now "stopped") 不可见 之后可以被杀死
> >onDestroy     //The activity is about to be destroyed 注销 之后可以被杀死


> 在activity变得很容易被销毁之前，系统会调用 onSaveInstanceState()方法。 
> 调用时系统会传入一个Bundle对象， 你可以利用 putString() 之类的方法，以键值对的方式来把activity状态信息保存到该Bundle对象中。
> 然后，如果系统杀掉了你的application进程并且用户又返回到你的activity，系统就会重建activity并将这个 Bundle 传入onCreate() 和onRestoreInstanceState() 中，你就可以从 Bundle 中解析出已保存信息并恢复activity状态。
> 如果没有储存状态信息，那么传入的 Bundle 将为null（当activity第一次被创建时就是如此）。

**注意：** activity被销毁之前，并不能确保每次都会调用 onSaveInstanceState() ，因为存在那些不必保存状态的情况（比如用户使用BACK键离开了你的activity，因为用户明显是关了这个activity）。 如果系统要调用 onSaveInstanceState() 方法，那么它通常会在 onStop() 方法之前并且可能是在 onPause() 之前调用。

因为 onSaveInstanceState() 并不保证每次都会被调用，所以你应该只用它来记录activity的一些临时状态信息（UI的状态）——千万不要用它来保存那些需要长久保存的数据。 替代方案是，你应该在用户离开activity的时候利用 onPause() 来保存永久性数据（比如那些需要存入数据库里的数据）。

一个检测应用程序状态恢复能力的好方法就是旋转设备，使得屏幕方向发生改变。 当屏幕的方向改变时，因为要换用符合实际屏幕参数的资源，系统会销毁并重建这个activity。 正因如此，你的activity能够在被重建时完整地恢复状态是非常重要的，因为用户会在使用应用程序时会频繁地旋转屏幕。

