##ViewPager
###ViewPager加载页卡

* 讲Layout布局转换为View对象

        //第一种方法
        LayoutInflater inflater = getLayoutInflater().from(this);
        View inflater.inflate(resource,root);
        //第二种方法
        View View.inflate(context,resource,root);

* 配置Adapter
    - PagerAdapter 数据源List<View>
    - FragmentPagerAdapter 数据源List<Fragment>
    - FragmentStatePagerAdapter 数据源List<Fragment>

> **注意，ViewPager必须用android.support.v4.view.ViewPager中的ViewPager**     

`MyPagerAdaptor适配器的代码`    

     package android.com.myapplication;

    import android.support.v4.view.PagerAdapter;
    import android.view.View;
    import android.view.ViewGroup;

    import java.util.List;

    /**
     - Created by gouchao on 16-4-19.
     */
    public class MyPagerAdaptor extends PagerAdapter {
        private List<String> titleList;
        private List<View> viewList;
        public MyPagerAdaptor(List<View> viewList, List<String> titleList){
            this.viewList = viewList;
            this.titleList = titleList;
        }

        //返回页卡的数量
        @Override
        public int getCount() {
            return viewList.size();
        }

        //判断当前View是否来自于对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方的写法
            return view==object;
        }

        /**
         - 为了ViewPager自动管理View页卡
         - ViewPager是默认三个三个的加载
         - 显示效果
         - 1,2,3 ==> 2,3,4
         */
        //实例化一个页卡
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加当前View view
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        //销毁一个页卡
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }


        //设置ViewPager的标题
        //如果要复写这个方法，必须要在布局文件中加入PagerTabStrip
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }


`ViewPagerMainActivity代码`    

     package android.com.myapplication;

    import android.app.Activity;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v4.view.PagerTabStrip;
    import android.support.v4.view.ViewPager;
    import android.view.View;

    import java.util.ArrayList;
    import java.util.List;

    /**
     - Created by gouchao on 16-4-19.
     */
    public class ViewPagerMainActivity extends Activity {
        private List<View> viewList;
        private List<String> titleList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.viewpager_main_activity);
            viewList = new ArrayList<>();
            View view1 = View.inflate(this,R.layout.viewpagerview1,null);
            View view2 = View.inflate(this,R.layout.viewpagerview2,null);
            View view3 = View.inflate(this,R.layout.viewpagerview3,null);
            viewList.add(view1);
            viewList.add(view2);
            viewList.add(view3);

            titleList = new ArrayList<>();
            titleList.add("第一页");
            titleList.add("第二页");
            titleList.add("第三页");


            //获取pagerTabStrip
            PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.tab);

            //或PagerTabStrip设置属性
            pagerTabStrip.setTextColor(Color.RED);
            pagerTabStrip.setDrawFullUnderline(false);
            pagerTabStrip.setTabIndicatorColor(Color.GREEN);
            pagerTabStrip.setBackgroundColor(Color.YELLOW);
    //        pagerTabStrip.set

            //创建适配器
            MyPagerAdaptor adaptor = new MyPagerAdaptor(viewList,titleList);

            //创建ViewPager
            ViewPager viewPager = (ViewPager) findViewById(R.id.mainpager);

            //加载适配器
            viewPager.setAdapter(adaptor);
        }
    }


`main_XML配置`      

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:orientation="vertical" android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v4.view.ViewPager
            android:id="@+id/mainpager"
            android:layout_gravity="center"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content">

            <android.support.v4.view.PagerTabStrip
                android:id="@+id/tab"
                android:layout_gravity="top"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content">

            </android.support.v4.view.PagerTabStrip>
            <!--<android.support.v4.view.PagerTitleStrip-->
                <!--android:id="@+id/title"-->
                <!--android:layout_gravity="bottom"-->
                <!--android:layout_width="wrap_content"-->
                <!--android:layout_height="wrap_content">-->

            <!--</android.support.v4.view.PagerTitleStrip>-->
        </android.support.v4.view.ViewPager>
    </LinearLayout>    

`图片效果如下`
![效果](http://7xp423.com1.z0.glb.clouddn.com/ViewPager.png)