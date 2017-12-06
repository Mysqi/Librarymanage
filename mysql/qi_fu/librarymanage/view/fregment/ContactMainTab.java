package com.mysql.qi_fu.librarymanage.view.fregment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.calendar.CompactCalendarView;
import com.mysql.qi_fu.librarymanage.calendar.domain.Event;
import com.mysql.qi_fu.librarymanage.info.ChatInfo;
import com.mysql.qi_fu.librarymanage.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qi_fu on 2017/2/10.
 */
public class ContactMainTab extends Fragment {
    private TextView tvAfter;
    private TextView tvBefor;
    public TextView tvData;
    private ImageView imageBelow;
    private CompactCalendarView calendar;
    private View view;
    private List<ChatInfo>list;
    private ChatInfo chatinfo;
    private String selectTime;
    private List<Event> events;
    private boolean isFrist=true;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.contact_tab, container, false);
        tvAfter = (TextView)view.findViewById(R.id.tv_day_after);
        tvBefor = (TextView) view.findViewById(R.id.tv_day_befor);
        tvData = (TextView) view.findViewById(R.id.tv_data_listact);
        imageBelow= (ImageView) view.findViewById(R.id.image_below);
        calendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        events = new ArrayList<>();
        initView();
        initData();

        return view;
    }

    private void initView() {
        selectTime = String.format(DateUtil.getCurrentDate().toString(), "yyyy-MM-dd").substring(0, 10);
        view.findViewById(R.id.tv_data_listact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.isShown()) {
                    tvAfter.setVisibility(View.VISIBLE);
                    tvBefor.setVisibility(View.VISIBLE);
                    imageBelow.setVisibility(View.VISIBLE);
                    calendar.hideCalendarWithAnimation();
                } else {
                    calendar.setCurrentDate(DateUtil.string2Date(selectTime));
                    tvAfter.setVisibility(View.GONE);
                    tvBefor.setVisibility(View.GONE);
                    imageBelow.setVisibility(View.GONE);
                    calendar.showCalendarWithAnimation();
                }
            }
        });


        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                long time = dateClicked.getTime();
                String timeString = DateUtil.getDateToString(time, 1);
                String nextdata = DateUtil.getNextdata(timeString);
                selectTime = timeString;
                loadData();
                calendar.hideCalendar();
                tvAfter.setVisibility(View.VISIBLE);
                tvBefor.setVisibility(View.VISIBLE);
                imageBelow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendar.removeAllEvents();
                long time = firstDayOfNewMonth.getTime();
                String firstday = DateUtil.getDateToString(time, 1);
                selectTime = firstday;
                String nextdata = DateUtil.getNextdata(firstday);
                loadData();
                String data[] = firstday.substring(0, 7).split("-");
                String datatime = data[0] + "年" + data[1] + "月";
                tvData.setText(datatime);

                if (events.size() != 0) {
                    events.clear();
                }
                //改变日历月中的数字
                changeMonthNumber();
            }
        });

        view.findViewById(R.id.tv_data_listact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.isShown()) {
                    tvAfter.setVisibility(View.VISIBLE);
                    tvBefor.setVisibility(View.VISIBLE);
                    imageBelow.setVisibility(View.VISIBLE);
                    calendar.hideCalendarWithAnimation();
                } else {
                    if (isFrist) {
                        String data[] = DateUtil.getDateToString(DateUtil.getCurrenTimeline()).substring(0, 7).split("-");
                        String datatime = data[0] + "年" + data[1] + "月";
                        tvData.setText(datatime);
                        isFrist = false;
                    }
                    calendar.setCurrentDate(DateUtil.string2Date(selectTime));
                    tvAfter.setVisibility(View.GONE);
                    tvBefor.setVisibility(View.GONE);
                    imageBelow.setVisibility(View.GONE);
                    calendar.showCalendarWithAnimation();
                }
            }
        });

    }

    private void initData() {

    }
    //加载数据
    private void loadData() {
        long timeline = DateUtil.getCurrenTimeline();
        for (int i = 0; i < 10; i++) {
            Event event = new Event(Color.RED, timeline+i*64000, i + "本");
            events.add(event);
        }
        calendar.addEvents(events);
    }
    //切换月份时加载
    private void changeMonthNumber() {
        long timeline = DateUtil.getCurrenTimeline()-1000030;
        for (int i = 0; i < 10; i++) {
            Event event = new Event(Color.RED, timeline+i*100020, i + "本");
            events.add(event);
        }
        calendar.addEvents(events);
    }
}
