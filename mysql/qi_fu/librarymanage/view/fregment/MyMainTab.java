package com.mysql.qi_fu.librarymanage.view.fregment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.view.activity.FkActivity;
import com.mysql.qi_fu.librarymanage.view.activity.JsxqActivity;
import com.mysql.qi_fu.librarymanage.view.activity.LlHistoryActivity;
import com.mysql.qi_fu.librarymanage.view.activity.SettingActivity;
import com.mysql.qi_fu.librarymanage.view.activity.ShareActivity;
import com.mysql.qi_fu.librarymanage.view.activity.ZhMessageActivity;

/**
 * Created by qi_fu on 2017/2/10.
 */
public class MyMainTab extends Fragment implements View.OnClickListener{

    private RelativeLayout rlMessge,rlJsxq,rlHistory,rlFk,rlShare,rlSetting;
    private Intent intent;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_tab, container, false);
        rlMessge= (RelativeLayout) view.findViewById(R.id.rl_massge);
        rlMessge.setOnClickListener(this);
        rlJsxq= (RelativeLayout) view.findViewById(R.id.rl_jsxq);
        rlJsxq.setOnClickListener(this);
        rlHistory= (RelativeLayout) view.findViewById(R.id.rl_history);
        rlHistory.setOnClickListener(this);
        rlFk= (RelativeLayout) view.findViewById(R.id.rl_fk);
        rlFk.setOnClickListener(this);
        rlShare= (RelativeLayout) view.findViewById(R.id.rl_share);
        rlShare.setOnClickListener(this);
        rlSetting= (RelativeLayout) view.findViewById(R.id.rl_setting);
        rlSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_massge:
                intent=new Intent(getActivity(), ZhMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_jsxq:
                intent=new Intent(getActivity(),JsxqActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_history:
                intent=new Intent(getActivity(), LlHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_fk:
                intent=new Intent(getActivity(), FkActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_share:
                intent=new Intent(getActivity(), ShareActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting:
                intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

        }
    }
}
