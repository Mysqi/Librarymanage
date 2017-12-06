package com.mysql.qi_fu.librarymanage.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.BookEntity;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookActivity extends AppCompatActivity {
    @BindView(R.id.tv_book_content)
    public TextView tvContent;
    @BindView(R.id.tv_book_writer)
    public TextView tvWriter;
    @BindView(R.id.tv_book_name)
    public TextView tvName;
    @BindView(R.id.tv_book_userable)
    public TextView tvUserable;
    @BindView(R.id.tv_book_usered)
    public TextView tvUsered;
    @BindView(R.id.tv_book_all)
    public TextView tvAll;
    @BindView(R.id.button)
    public Button button;
    @BindView(R.id.button2)
    public Button button2;
    private BookEntityDao dao;
    private BookEntity entity;
    private String bookNo="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        bookNo = intent.getStringExtra("bookNo");
        if(bookNo.equals(null)){
            bookNo="a0001";
        }
        dao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
        QueryBuilder<BookEntity> builder = dao.queryBuilder().where(BookEntityDao.Properties.BookNo.eq(bookNo));
        List<BookEntity> bookEntities = builder.list();
        this.entity = bookEntities.get(0);
        tvContent.setText(this.entity.getContent());
        tvAll.setText(this.entity.getNumber() + "");
        tvUsered.setText(this.entity.getSendernumber() + "");
        tvUserable.setText(this.entity.getNumber() - this.entity.getSendernumber() + "");
        tvName.setText(this.entity.getBookName());
        tvWriter.setText(this.entity.getWriter());
    }
    private int sendernumber;
    private int type;

    @OnClick(R.id.button)
    public void initLister() {
        if(entity.getSendernumber() ==entity.getNumber()){
            Toast.makeText(BookActivity.this, "该书已经全部借阅，请稍后重试！", Toast.LENGTH_LONG).show();
            return;
        }
        button.setVisibility(View.GONE);
        button2.setVisibility(View.VISIBLE);
        Toast.makeText(BookActivity.this, "预约成功，请尽快去图书管取书！", Toast.LENGTH_LONG).show();
        sendernumber=entity.getSendernumber() + 1;
        tvUsered.setText(sendernumber+ "");
        tvUserable.setText(entity.getNumber() - entity.getSendernumber()-1 + "");
        type=0;
    }
    @OnClick(R.id.button2)
    public void initLister2() {
        Toast.makeText(BookActivity.this, "取消成功！", Toast.LENGTH_LONG).show();
        sendernumber=entity.getSendernumber() -1;
        tvUsered.setText(sendernumber+ "");
        tvUserable.setText(entity.getNumber() - entity.getSendernumber()+1 + "");
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.GONE);
        type=1;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        BookEntity entity2 = dao.queryBuilder().where(BookEntityDao.Properties.BookNo.eq(bookNo)).build().unique();
//        entity2.setSendernumber(sendernumber);
//        entity2.setType(type);
//        dao.update(entity2);
    }
}
