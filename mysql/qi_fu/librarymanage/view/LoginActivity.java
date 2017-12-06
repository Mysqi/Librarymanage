package com.mysql.qi_fu.librarymanage.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.base.BaseActivity;
import com.mysql.qi_fu.librarymanage.base.MyApplication;
import com.mysql.qi_fu.librarymanage.sql.BookEntity;
import com.mysql.qi_fu.librarymanage.sql.greendao.BookEntityDao;
import com.mysql.qi_fu.librarymanage.sql.greendao.CordDao;
import com.mysql.qi_fu.librarymanage.sql.greendao.MessageDao;
import com.mysql.qi_fu.librarymanage.sql.greendao.UserDao;
import com.mysql.qi_fu.librarymanage.util.AlertUtil;
import com.mysql.qi_fu.librarymanage.view.activity.FragManageActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LoginActivity extends BaseActivity {
    private SharedPreferences sp;
    private EditText editCount;
    private EditText editPass;
    private Button btLogin;
    private SharedPreferences.Editor editor;
    private BookEntityDao bookEntityDao;
    private CordDao cordDao;
    private MessageDao messageDao;
    private UserDao userDao;
    private IWXAPI mIwapi;
    private static final String WX_APPID="11112222";//appId
    @Override
    protected void handler(Message msg) {}

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        mIwapi = WXAPIFactory.createWXAPI( LoginActivity. this, WX_APPID, true );
        mIwapi.registerApp(WX_APPID);
        SendAuth.Req req = new SendAuth.Req();
        req. scope = "snsapi_userinfo";
        req. state = "wechat_sdk_demo_test";
        mIwapi.sendReq(req);//执行完毕这句话之后，会在WXEntryActivity回调


        editCount = (EditText) findViewById(R.id.edit_account);
        editPass = (EditText) findViewById(R.id.edit_password);
        editCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        editPass.setInputType(InputType.TYPE_CLASS_NUMBER);
        btLogin = (Button) findViewById(R.id.btn_login);
        sp = getSharedPreferences("LibraryManager", MODE_PRIVATE);
         editor= sp.edit();

    }

    @Override
    protected void initData() {
        addTextData();
        boolean isLogin = sp.getBoolean("isLogin", false);
        if (isLogin) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, FragManageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initOther() {

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(logined()){
                    editor.putBoolean("islogin", true);
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, FragManageActivity.class);
                    startActivity(intent);
                    finish();
//                }
            }
        });
    }

    private boolean logined() {
        String Count = editCount.getText().toString();
        String password = editPass.getText().toString();
        if(Count.equals("")||password.equals("")){
            AlertUtil.showToastLong(this,"学号或密码不能为空。");
            return false;
        }else {
            editor.putString("Count",Count);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        editor.commit();
        super.onDestroy();
    }

    /**
     * 给本应用的数据库添加临时数据
     */
//    public BookEntity(String bookName, String url, String bookNo, String writer,
//                         String time, int type, String content, int number, int sendernumber) {
//        this.bookName = bookName;
//        this.url = url;
//        this.bookNo = bookNo;
//        this.writer = writer;
//        this.time = time;
//        this.type = type;
//        this.content = content;
//        this.number = number;
//        this.sendernumber = sendernumber;
//    }

    private void addTextData(){
        bookEntityDao = MyApplication.getInstances().getDaoSession().getBookEntityDao();
        cordDao = MyApplication.getInstances().getDaoSession().getCordDao();
        messageDao = MyApplication.getInstances().getDaoSession().getMessageDao();
        userDao = MyApplication.getInstances().getDaoSession().getUserDao();


//        BookEntity entity=new BookEntity("完美世界","http://gank.io/api/data/福利/10/1","a0001","辰东","1492153713",0,"",20,15);
        BookEntity entity=new BookEntity();
        entity.setBookName("完美世界");
        entity.setContent(getResources().getString(R.string.book1));
        entity.setBookNo("a0001");
        entity.setBookUrl("http://gank.io/api/data/福利/10/1");
        entity.setNumber(20);
        entity.setSendernumber(15);
        entity.setWriter("辰东");
        entity.setType(0);
        entity.setTime("1492153713");
        bookEntityDao.insert(entity);
        BookEntity entity2=new BookEntity();
        entity2.setBookName("大主宰");
        entity2.setContent(getResources().getString(R.string.book2));
        entity2.setBookNo("b0002");
        entity2.setBookUrl("http://gank.io/api/data/福利/10/1");
        entity2.setNumber(34);
        entity2.setSendernumber(15);
        entity2.setWriter("西红柿");
        entity2.setType(1);
        entity2.setTime("1492154716");
        bookEntityDao.insert(entity2);
        BookEntity entity3=new BookEntity();
        entity3.setBookName("斗破苍穹");
        entity3.setContent(getResources().getString(R.string.book3));
        entity3.setBookNo("c0003");
        entity3.setBookUrl("www.");
        entity3.setNumber(34);
        entity3.setSendernumber(15);
        entity3.setWriter("西红柿");
        entity3.setType(0);
        entity3.setTime("1492155543");
        bookEntityDao.insert(entity3);
        BookEntity entity4=new BookEntity();
        entity4.setBookName("一念永恒");
        entity4.setContent(getResources().getString(R.string.book4));
        entity4.setBookNo("d0004");
        entity4.setBookUrl("www.");
        entity4.setNumber(36);
        entity4.setSendernumber(15);
        entity4.setWriter("天蚕土豆");
        entity4.setType(1);
        entity4.setTime("1492154716");
        bookEntityDao.insert(entity4);

    }
}
