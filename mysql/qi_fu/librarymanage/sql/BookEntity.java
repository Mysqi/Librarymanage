package com.mysql.qi_fu.librarymanage.sql;

import android.graphics.drawable.Drawable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by qi_fu on 2017/4/13.
 *
 *
 */
@Entity
public class BookEntity {
    @Generated
    String bookName;//书名
    String bookUrl;//书的图片
    String bookNo;   //图书编号
    String writer;//作者
    String time;//时间
    int type;//预约状态 1：已经借阅 0：未借阅
    String content;//内容介绍
    int number;//书的数量
    int sendernumber;//借阅量

    @Transient
    Drawable drawable;

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }



    @Generated(hash = 1373651409)
    public BookEntity() {
    }

    @Generated(hash = 1364757617)
    public BookEntity(String bookName, String bookUrl, String bookNo, String writer,
            String time, int type, String content, int number, int sendernumber) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.bookNo = bookNo;
        this.writer = writer;
        this.time = time;
        this.type = type;
        this.content = content;
        this.number = number;
        this.sendernumber = sendernumber;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSendernumber() {
        return this.sendernumber;
    }

    public void setSendernumber(int sendernumber) {
        this.sendernumber = sendernumber;
    }

    public String getBookUrl() {
        return this.bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
