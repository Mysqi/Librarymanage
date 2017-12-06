package com.mysql.qi_fu.librarymanage.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.qi_fu.librarymanage.sql.BookEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 16/7/5.
 */
public class AlertUtil {
	public static void showToastLong(Context ctx, String str) {
		Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
	}

	public static void showToastShort(Context ctx, String str) {
		Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
	}

	public static void createAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {
		TextView tvTitle = new TextView(context);
		tvTitle.setText(title);
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setPadding(18, 10, 18, 0);
		tvTitle.setGravity(Gravity.LEFT);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
		}
		tvTitle.setTextSize(20);

		TextView tvMsg = new TextView(context);
		tvMsg.setText(Html.fromHtml(msg));
		tvMsg.setTextColor(Color.WHITE);
		tvMsg.setPadding(24, 8, 24, 0);
		tvMsg.setGravity(Gravity.LEFT);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			tvMsg.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
		}
		tvMsg.setTextSize(16);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCustomTitle(tvTitle);
		builder.setView(tvMsg);
		builder.setCancelable(true);
		builder.setPositiveButton(android.R.string.yes, okListener);
		builder.setNegativeButton(android.R.string.no, noListener);

		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}
	public static List<BookEntity> lisTosit(List<BookEntity> list){
		List<BookEntity> newlis=new ArrayList<>();
		for (int i = list.size()-1; i >0; i--) {
			newlis.add(list.get(i));
		}
		return newlis;
	}
}
