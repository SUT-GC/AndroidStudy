##Fragment
###生命周期
* onAttach : 当Fragment添加到Activity中回调，且回调一次
* onCreate：当创建Fragment会回调，且一次
* onCreateView：每次创建都会绘制View组件
* onActivityCreated：当所在Activity启动完成会回调
* onStart：启动Fragment
* onResune：回复Fragment会被回调，如果调用onstart，则之后一定会被调用
* onPause：暂停Fragment会被回调
* onStop：停止Fragment
* inDestroyView：销毁Fragment所包含的界面时回调
* onDestroy：销毁Fragment会被回调
* onDetach：Fragment从Activity中删除时回调，并且调用一次     

        //创建Fragment管理文件
        FragmentManager fragmentManager = getFragmentManager();
        //开启事物，事物中有add，replace，remove：fragment
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        //给fragment加载布局文件
        beginTransaction.add(R.id.layout ,fragment);
        //事物提交
        beginTransaction.commit()

> 当onCreateView重写的时候，必须返回个View    
> 
>       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
>           View view = inflater.inflate(R.layout.xxxx,container,false);
>           //对View进行操作
>           return view;    
>       }
       

###Fragment 与 Activity 进行通信
* 1 相互获取
    * Fragement中找到Activity：getActivity()方法获取宿主Activity
    * Activity中找到Fragment：FragmentManager.findFragementById()或者findFragmentByTag()获取Fragment，Fragment调用getArguments方法进行数据读取    
* 2 通信数据
    - Activity ----> Fragment : 创建Bundle数据包，找到Fragment并且调用setArguments(Bundle bundle)进行通信    
    - Fragment ----> Activity：在Fragment中定义内部回调接口，让包含该Fragment的Activity实现该回调接口，在Activity中重写接口的方法。Fragment创建接口对象，用Activity给其赋值，之后调用接口函数。    



