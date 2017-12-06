package com.mysql.qi_fu.librarymanage.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mysql.qi_fu.librarymanage.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected UIHandler handler = new UIHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBar();
        setHandler();
        initView(savedInstanceState);
        initData();
        initOther();

    }

    private void setBar() {
    }

    private void setHandler() {
        handler.setHandler(new IHandler() {
            public void handleMessage(Message msg) {
                handler(msg);//有消息就提交给子类实现的方法
            }
        });
    }

    protected abstract void handler(Message msg);

    protected abstract void initView(Bundle savedInstanceState) ;

    protected abstract void initData() ;

    protected abstract  void initOther() ;

    public class UIHandler extends Handler {

        private IHandler handler;//回调接口，消息传递给注册者

        public UIHandler(Looper looper) {
            super(looper);
        }

        public UIHandler(Looper looper,IHandler handler) {
            super(looper);
            this.handler = handler;
        }

        public void setHandler(IHandler handler) {
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (handler != null) {
                handler.handleMessage(msg);//有消息，就传递
            }
        }
    }

    public interface IHandler {
        public void handleMessage(Message msg);
    }


}
