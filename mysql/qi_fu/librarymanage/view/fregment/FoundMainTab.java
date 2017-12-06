package com.mysql.qi_fu.librarymanage.view.fregment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.adapter.BookRecyAdapter;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.BookEntity;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;
import com.mysql.qi_fu.librarymanage.view.activity.BookActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by qi_fu on 2017/2/10.
 */
public class FoundMainTab extends Fragment{
    private LinearLayout ll_history;
    private RelativeLayout ll_found;
    private RelativeLayout ll_found2;
    private RelativeLayout ll_found3;
    private RelativeLayout ll_found4;
    private SweetAlertDialog pDialog;
    private EditText editText;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private BookRecyAdapter mAdapter;
    private Handler handler = new Handler(){
               @Override
               public void handleMessage(Message msg) {
                   Toast.makeText(getActivity(),"加载成功！",Toast.LENGTH_SHORT).show();
                   pDialog.dismiss();
                   Intent intent=new Intent(getActivity(), BookActivity.class);
                   startActivity(intent);
               }
           };


    private List<BookEntity>books=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.found_tab, container, false);
        ll_history= (LinearLayout) view.findViewById(R.id.ll_history);
        ll_found= (RelativeLayout) view.findViewById(R.id.ll_found1);
        ll_found2= (RelativeLayout) view.findViewById(R.id.ll_found2);
        ll_found3= (RelativeLayout) view.findViewById(R.id.ll_found2);
        ll_found4= (RelativeLayout) view.findViewById(R.id.ll_found2);
        editText= (EditText) view.findViewById(R.id.edt_search_box);
        recyclerView=(RecyclerView)view.findViewById(R.id.book_recycler);
        mLayoutManager=new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ll_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                handler.sendEmptyMessageDelayed(0,1000);
                pDialog.show();
            }
        });
        ll_found2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ll_found2.setVisibility(View.GONE);
                            }
                        })
                        .show();
            }
        });
        ll_found3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ll_found3.setVisibility(View.GONE);
                            }
                        }).show();
            }
        });
        ll_found4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ll_found4.setVisibility(View.GONE);
                            }
                        });
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ll_history.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                BookEntityDao dao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
                books = dao.queryBuilder().whereOr(BookEntityDao.Properties.BookName.like(s.toString()), BookEntityDao.Properties.BookNo.like(s.toString())).build().list();

                if(mAdapter==null){
                    recyclerView.setAdapter(mAdapter = new BookRecyAdapter(getActivity(),books));//recyclerview设置适配器
                }else{
                    //让适配器刷新数据
                    mAdapter.notifyDataSetChanged();
                }
                //实现适配器自定义的点击监听
                mAdapter.setOnItemClickListener(new BookRecyAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position=recyclerView.getChildAdapterPosition(view);
                        Intent intent=new Intent(getActivity(), BookActivity.class);
                        intent.putExtra("bookNo",books.get(position).getBookNo());
                        startActivity(intent);

                    }
                    @Override
                    public void onItemLongClick(View view) {
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().equals("")){
                    ll_history.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });


    }
}
