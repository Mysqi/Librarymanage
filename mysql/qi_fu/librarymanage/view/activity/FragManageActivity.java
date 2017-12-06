package com.mysql.qi_fu.librarymanage.view.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.adapter.FragmentAdapter;
import com.mysql.qi_fu.librarymanage.adapter.MyViewPagerAdapter;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;
import com.mysql.qi_fu.librarymanage.view.MainActivity;
import com.mysql.qi_fu.librarymanage.view.fregment.NewMainTab;
import com.mysql.qi_fu.librarymanage.view.fregment.ContactMainTab;
import com.mysql.qi_fu.librarymanage.view.fregment.FoundMainTab;
import com.mysql.qi_fu.librarymanage.view.fregment.MyMainTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi_fu on 2017/2/10.
 */
public class FragManageActivity extends FragmentActivity {
    /**
     * 顶部三个LinearLayout
     */
    private LinearLayout mTabChat;
    private LinearLayout mTabFound;
    private LinearLayout mTabContact;
    private LinearLayout mTabMy;
//    private LinearLayout mLinearLayout;

    /**
     * 顶部的三个TextView
     */
    private TextView chat;
    private TextView found;
    private TextView contact;
    private TextView me;

    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    private ViewPager mViewPager;
//    private ViewPager mapPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private List<ImageView> mDataList;

    private Resources res;
//    private View mView;
//    private int diatance;
    private FragmentTransaction transaction;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        res=getResources();

        initView();
//        initData();
//        initEvent();

        mViewPager=(ViewPager) findViewById(R.id.id_viewpager);
//        mapPager= (ViewPager) findViewById(R.id.viewpager);

        /**
         * 初始化Adapter
         */
        mAdapter=new FragmentAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());

        initTabLine();
    }

    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine=(ImageView) findViewById(R.id.id_tab_line);

        //获取屏幕的宽度
        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width=screenWidth/3;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams
    }

    /**
     * 初始化控件，初始化Fragment
     */
    private void initView() {
        me= (TextView) findViewById(R.id.id_me);
        chat=(TextView) findViewById(R.id.id_chat);
        found=(TextView) findViewById(R.id.id_found);
        contact=(TextView) findViewById(R.id.id_contact);

        me.setOnClickListener(new TabOnClickListener(3));
        chat.setOnClickListener(new TabOnClickListener(0));
        found.setOnClickListener(new TabOnClickListener(1));
        contact.setOnClickListener(new TabOnClickListener(2));

        fragments.add(new NewMainTab());
        fragments.add(new FoundMainTab());
        fragments.add(new ContactMainTab());
        fragments.add(new MyMainTab());



        mTabChat=(LinearLayout) findViewById(R.id.id_tab1_chat);
        mTabFound=(LinearLayout) findViewById(R.id.id_tab2_found);
        mTabContact=(LinearLayout) findViewById(R.id.id_tab3_contact);
        mTabMy= (LinearLayout) findViewById(R.id.id_tab4_me);


//        mapPager= (ViewPager) findViewById(R.id.viewpager);
//        mLinearLayout = (LinearLayout) findViewById(R.id.ll_points);
//        mView = findViewById(R.id.v_redpoint);


        FragmentManager manager = getFragmentManager();
         transaction= manager.beginTransaction();
    }

    /**
     * 功能：点击主页TAB事件
     */
    public class TabOnClickListener implements View.OnClickListener{
        private int index=0;

        public TabOnClickListener(int i){
            index=i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//选择某一页
        }

    }

    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin= (int) ((positionOffset+position)*screenWidth/3);
            mTabLine.setLayoutParams(lp);
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            //重置所有TextView的字体颜色
            resetTextView();
            switch (position) {
                case 0:
                    findViewById(R.id.layout_tob1).setVisibility(View.VISIBLE);
                    mTabChat.setBackgroundColor(getResources().getColor(R.color.gray));
                    chat.setTextColor(res.getColor(R.color.green));
                    break;
                case 1:
//                    findViewById(R.id.layout_tob1).setVisibility(View.GONE);
                    mTabFound.setBackgroundColor(getResources().getColor(R.color.gray));
                    found.setTextColor(res.getColor(R.color.green));
                    break;
                case 2:
//                    findViewById(R.id.layout_tob1).setVisibility(View.GONE);
                    mTabContact.setBackgroundColor(getResources().getColor(R.color.gray));
                    contact.setTextColor(res.getColor(R.color.green));
                    break;
                case 3:
//                    findViewById(R.id.layout_tob1).setVisibility(View.GONE);
                    mTabMy.setBackgroundColor(getResources().getColor(R.color.gray));
                    me.setTextColor(res.getColor(R.color.green));
                    break;
            }
        }
        /**
         * 重置颜色
         */
        private void resetTextView() {
            chat.setTextColor(res.getColor(R.color.black));
            found.setTextColor(res.getColor(R.color.black));
            contact.setTextColor(res.getColor(R.color.black));
            me.setTextColor(res.getColor(R.color.black));
            mTabChat.setBackgroundColor(res.getColor(R.color.white));
            mTabFound.setBackgroundColor(res.getColor(R.color.white));
            mTabContact.setBackgroundColor(res.getColor(R.color.white));
            mTabMy.setBackgroundColor(res.getColor(R.color.white));

        }
    }
//    private void initData() {
//        int[] sorce = new int[]{R.mipmap.background,R.mipmap.bground,R.mipmap.ic_launcher,R.mipmap.school,R.mipmap.court_logo};
//
//        mDataList = new ArrayList<ImageView>();
//
//        for (int i = 0;i < sorce.length;i ++){
//            ImageView img = new ImageView(getApplicationContext());
//            img.setImageResource(sorce[i]);
//            mDataList.add(img);
//
//            //添加底部灰点
//            View v = new View(getApplicationContext());
//            v.setBackgroundResource(R.drawable.mycycle2);
//            //指定其大小
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
//            if (i != 0)
//                params.leftMargin = 20;
//            v.setLayoutParams(params);
//            mLinearLayout.addView(v);
//        }
//
//        mapPager.setAdapter(new MyViewPagerAdapter(mDataList));
//
//        //设置每次加载时第一页在MAX_VALUE / 2 - Extra 页，造成用户无限轮播的错觉
//        int startPage = Integer.MAX_VALUE / 2;
//        int extra = startPage % mDataList.size();
//        startPage = startPage - extra;
//        mapPager.setCurrentItem(startPage);
//    }
//
//    private void initEvent() {
//
//
//        /**
//         * 当底部红色小圆点加载完成时测出两个小灰点的距离，便于计算后面小红点动态移动的距离
//         */
//        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                diatance = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
//                Log.d("两点间距",diatance + "测出来了");
//            }
//        });
//
//        mapPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                //测出页面滚动时小红点移动的距离，并通过setLayoutParams(params)不断更新其位置
//                position = position % mDataList.size();
//                float leftMargin = diatance * (position + positionOffset);
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
//                params.leftMargin = Math.round(leftMargin);
//                mView.setLayoutParams(params);
//                Log.d("红点在这",leftMargin + "");
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BookEntityDao bookEntityDao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
        bookEntityDao.deleteAll();
    }
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}