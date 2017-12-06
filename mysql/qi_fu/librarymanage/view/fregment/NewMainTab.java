package com.mysql.qi_fu.librarymanage.view.fregment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.adapter.BookRecyAdapter;
import com.mysql.qi_fu.librarymanage.adapter.MyViewPagerAdapter;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.BookEntity;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;
import com.mysql.qi_fu.librarymanage.util.AlertUtil;
import com.mysql.qi_fu.librarymanage.view.activity.BookActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi_fu on 2017/2/10.
 */
public class NewMainTab extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout llXiaoshuo;
    private LinearLayout llXs;
    private LinearLayout llkj;

    private static RecyclerView recyclerview;
    private BookRecyAdapter mAdapter;
    private List<BookEntity> books;
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem ;
    private int page=1;
    private ItemTouchHelper itemTouchHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ViewPager mapPager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.new_tab, container, false);
        initView();//初始化布局
        initData();
        initData2();
        initEvent();
        setListener();//设置监听事件

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();//初始化布局
        initData();
        setListener();//设置监听事件

        //执行加载数据
//        new GetData().execute("http://gank.io/api/data/福利/10/1");

    }
    private boolean isShow;

    private void initView(){

        mapPager= (ViewPager)view.findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_points);
        mView = view.findViewById(R.id.v_redpoint);

        llXiaoshuo=(LinearLayout) view.findViewById(R.id.ll_xiaoshuo);
        llXs=(LinearLayout)view.findViewById(R.id.ll_xs);
        llkj=(LinearLayout)view.findViewById(R.id.ll_keji);



//        books=new ArrayList<>();
        recyclerview=(RecyclerView)view.findViewById(R.id.grid_recycler);
        mLayoutManager=new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
//        mLayoutManager=new GridLayoutManager(getActivity(),4);//设置为一个heng向网格布局
//        mLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.grid_swipe_refresh) ;
        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        //设置swipeRefreshLayout为刷新状态
//            swipeRefreshLayout.setRefreshing(true);
    }

    private void initData() {
//            BookEntity bookEntity=new BookEntity();
//            bookEntity.setContent(getResources().getString(R.string.book1));
//            bookEntity.setBookName(getResources().getString(R.string.bookname1));
//            bookEntity.setDrawable(getResources().getDrawable(R.mipmap.book1));
//            books.add(bookEntity);
//            BookEntity bookEntity2=new BookEntity();
//            bookEntity2.setContent(getResources().getString(R.string.book2));
//            bookEntity2.setBookName(getResources().getString(R.string.bookname2));
//            bookEntity2.setDrawable(getResources().getDrawable(R.mipmap.book2));
//            books.add(bookEntity2);
//            BookEntity bookEntity3=new BookEntity();
//            bookEntity3.setContent(getResources().getString(R.string.book3));
//            bookEntity3.setBookName(getResources().getString(R.string.bookname3));
//            bookEntity3.setDrawable(getResources().getDrawable(R.mipmap.book3));
//            books.add(bookEntity3);
//            BookEntity bookEntity4=new BookEntity();
//            bookEntity4.setContent(getResources().getString(R.string.book4));
//            bookEntity4.setBookName(getResources().getString(R.string.bookname4));
//            bookEntity4.setDrawable(getResources().getDrawable(R.mipmap.book4));
//            books.add(bookEntity4);

        BookEntityDao dao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
        books= dao.queryBuilder().list();
//        books= dao.loadAll();

        if(mAdapter==null){
            recyclerview.setAdapter(mAdapter = new BookRecyAdapter(getActivity(),books));//recyclerview设置适配器

        }else{
            //让适配器刷新数据
            mAdapter.notifyDataSetChanged();
        }
        //实现适配器自定义的点击监听
        mAdapter.setOnItemClickListener(new BookRecyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position=recyclerview.getChildAdapterPosition(view);
                Intent intent=new Intent(getActivity(), BookActivity.class);
                intent.putExtra("bookNo",books.get(position).getBookNo());
                startActivity(intent);

                //彩色Snackbar工具类，请看我之前的文章《没时间解释了，快使用Snackbar!——Android Snackbar花式使用指南》http://www.jianshu.com/p/cd1e80e64311
            }
            @Override
            public void onItemLongClick(View view) {
            }
        });

    }
    private void setListener(){
        final int[] n = {1};
        //swipeRefreshLayout刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                books.clear();
//                page=1;
//                new GetData().execute("http://gank.io/api/data/福利/10/1");
                if(n[0] %2==1) {
                    for (int i = 0; i < 4; i++) {
                        BookEntity bookEntity = new BookEntity();
                        bookEntity.setContent(getResources().getString(R.string.book2));
                        bookEntity.setBookName(getResources().getString(R.string.bookname2));
                        bookEntity.setDrawable(getResources().getDrawable(R.mipmap.book2));
                        books.add(bookEntity);
                    }
                    n[0] = n[0] +1;
                }
                else {
                    for (int i = 0; i < 4; i++) {
                        BookEntity bookEntity = new BookEntity();
                        bookEntity.setContent(getResources().getString(R.string.book3));
                        bookEntity.setBookName(getResources().getString(R.string.bookname3));
                        bookEntity.setDrawable(getResources().getDrawable(R.mipmap.book3));
                        books.add(bookEntity);
                    }
                    n[0] = n[0] +1;
                }
                books=AlertUtil.lisTosit(books);
                //设置swipeRefreshLayout为刷新状态
            swipeRefreshLayout.setRefreshing(false);
                //让适配器刷新数据
                mAdapter.notifyDataSetChanged();
            }
        });
        llXiaoshuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow){
                    llXs.setVisibility(View.GONE);
                    isShow=false;
                }else {
                    llXs.setVisibility(View.VISIBLE);
                    isShow=true;
                }
            }
        });
        llkj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookEntityDao bookEntityDao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
                AlertUtil.showToastLong(getActivity(),"have deleted!");
                bookEntityDao.deleteAll();
                List<BookEntity> list = bookEntityDao.queryBuilder().list();
                Log.e("<><><><<>",list.size()+"");
            }
        });
    }
    private List<ImageView> mDataList;
    private LinearLayout mLinearLayout;
    private View mView;
    private int diatance;


    private void initData2() {
        int[] sorce = new int[]{R.mipmap.background,R.mipmap.bground,R.mipmap.ic_launcher,R.mipmap.school,R.mipmap.court_logo};

        mDataList = new ArrayList<ImageView>();

        for (int i = 0;i < sorce.length;i ++){
            ImageView img = new ImageView(getActivity());
            img.setImageResource(sorce[i]);
            mDataList.add(img);

            //添加底部灰点
            View v = new View(getActivity());
            v.setBackgroundResource(R.drawable.mycycle2);
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if (i != 0)
                params.leftMargin = 20;
            v.setLayoutParams(params);
            mLinearLayout.addView(v);
        }

        mapPager.setAdapter(new MyViewPagerAdapter(mDataList));

        //设置每次加载时第一页在MAX_VALUE / 2 - Extra 页，造成用户无限轮播的错觉
        int startPage = Integer.MAX_VALUE / 2;
        int extra = startPage % mDataList.size();
        startPage = startPage - extra;
        mapPager.setCurrentItem(startPage);
    }

    private void initEvent() {


        /**
         * 当底部红色小圆点加载完成时测出两个小灰点的距离，便于计算后面小红点动态移动的距离
         */
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                diatance = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                Log.d("两点间距",diatance + "测出来了");
            }
        });

        mapPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //测出页面滚动时小红点移动的距离，并通过setLayoutParams(params)不断更新其位置
                position = position % mDataList.size();
                float leftMargin = diatance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                params.leftMargin = Math.round(leftMargin);
                mView.setLayoutParams(params);
                Log.d("红点在这",leftMargin + "");
            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_keji:
                BookEntityDao bookEntityDao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
                AlertUtil.showToastLong(getActivity(),"have deleted!");
                bookEntityDao.deleteAll();
                List<BookEntity> list = bookEntityDao.queryBuilder().list();
                Log.e("<><><><<>",list.size()+"");
                break;
        }
    }

//    private class GetData extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //设置swipeRefreshLayout为刷新状态
//            swipeRefreshLayout.setRefreshing(true);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
////            return MyOkhttp.get(params[0]);
//            return "";
//        }
//
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if(!TextUtils.isEmpty(result)){
//
//                JSONObject jsonObject;
//                Gson gson=new Gson();
//                String jsonData=null;
//
//                    try {
//                        jsonObject = new JSONObject(result);
//                        jsonData = jsonObject.getString("results");
//                    } catch (org.json.JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                if(books==null||books.size()==0){
//                    books= gson.fromJson(jsonData, new TypeToken<List<BookEntity>>() {}.getType());
//
//                    BookEntity pages=new BookEntity();
////                    pages.setPage(page);
//                    books.add(pages);//在数据链表中加入一个用于显示页数的item
//                }else{
//                    List<BookEntity>  more= gson.fromJson(jsonData, new TypeToken<List<BookEntity>>() {}.getType());
//                    books.addAll(more);
//
//                    BookEntity pages=new BookEntity();
////                    pages.setPage(page);
//                    books.add(pages);//在数据链表中加入一个用于显示页数的item
//                }
//
//                if(mAdapter==null){
//                    recyclerview.setAdapter(mAdapter = new BookRecyAdapter(getActivity(),books));//recyclerview设置适配器
//
//                    //实现适配器自定义的点击监听
//                    mAdapter.setOnItemClickListener(new BookRecyAdapter.OnRecyclerViewItemClickListener() {
//                        @Override
//                        public void onItemClick(View view) {
//                            int position=recyclerview.getChildAdapterPosition(view);
//                            //彩色Snackbar工具类，请看我之前的文章《没时间解释了，快使用Snackbar!——Android Snackbar花式使用指南》http://www.jianshu.com/p/cd1e80e64311
//                        }
//                        @Override
//                        public void onItemLongClick(View view) {
//
//                        }
//                    });
//                }else{
//                    //让适配器刷新数据
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//            //停止swipeRefreshLayout加载动画
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    }
}