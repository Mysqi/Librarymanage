package com.mysql.qi_fu.librarymanage.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.adapter.MyViewPagerAdapter;
import com.mysql.qi_fu.librarymanage.base.BaseActivity;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<View > viewList;
    private List<Drawable>drawableList;
    private ViewPager viewPager;

    @Override
    protected void handler(Message msg) {
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.id_viewpager);
    }

    @Override
    protected void initData() {
//        LayoutInflater lf = getLayoutInflater().from(this);
//        View view1 = lf.inflate(R.layout.layout1, null);
//        View view2 = lf.inflate(R.layout.layout2, null);
//        View view3 = lf.inflate(R.layout.layout3, null);
//        viewList= new ArrayList<View>();// 将要分页显示的View装入数组中
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);

        drawableList=new ArrayList<>();
        drawableList.add(getResources().getDrawable(R.mipmap.background));
        drawableList.add(getResources().getDrawable(R.mipmap.bground));
        drawableList.add(getResources().getDrawable(R.mipmap.school));
//        drawableList.add(getResources().getDrawable(R.drawable.school2));
//        drawableList.add(getResources().getDrawable(R.drawable.school3));
//        drawableList.add(getResources().getDrawable(R.drawable.school2));
//        drawableList.add(getResources().getDrawable(R.drawable.school3));
        drawableList.add(getResources().getDrawable(R.mipmap.bground));

//        myViewPagerAdapter=new MyViewPagerAdapter(drawableList);
//        viewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    protected void initOther() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BookEntityDao bookEntityDao = MyApplication.getInstances().getDaoSession().getBookEntityDao();//关闭数据库，清楚数据
        bookEntityDao.deleteAll();
    }
    private long firstTime=0;


    /**
     * 双击退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
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
