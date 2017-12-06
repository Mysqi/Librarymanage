package com.mysql.qi_fu.librarymanage.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qi_fu on 2017/4/13.
 *
 * 借书表格
 */
@Entity
public class Cord {
    @Generated
    String userNo;
    String bookNo;
    String bookName;
    Long lendTime;//借书日期
    Long returnTime;//还书日期

    @Generated(hash = 1933337446)
    public Cord(String userNo, String bookNo, String bookName, Long lendTime,
            Long returnTime) {
        this.userNo = userNo;
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.lendTime = lendTime;
        this.returnTime = returnTime;
    }

    @Generated(hash = 498506781)
    public Cord() {
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }
}
