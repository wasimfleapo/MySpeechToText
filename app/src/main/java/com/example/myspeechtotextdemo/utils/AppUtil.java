package com.example.myspeechtotextdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myspeechtotextdemo.R;


public class AppUtil {

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            openNoNetworkConnectionDialog(context);
        }
        return isConnected;

    }

    private static void openNoNetworkConnectionDialog(Context context) {

        final Dialog noConnectionDialog = new Dialog(context);
        noConnectionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noConnectionDialog.setContentView(R.layout.simple_dialog_layout);

        noConnectionDialog.getWindow().getAttributes().width = (int) (AppUtil.getDeviceMetrics(context).widthPixels * 0.8);
        noConnectionDialog.getWindow().getAttributes().height = (int) (AppUtil.getDeviceMetrics(context).heightPixels * 0.4);

        noConnectionDialog.setCancelable(false);
        noConnectionDialog.show();
        TextView tvOK = (TextView) noConnectionDialog.findViewById(R.id.tv_ok);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noConnectionDialog.dismiss();
            }
        });
    }





    public static boolean isNetworkConnectionAvailable(Context ctx, boolean showDialog) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected && showDialog) {
            AppUtil.showSimpleDialog(ctx, ctx.getString(R.string.network_not_available),
                    ctx.getString(R.string.internet_not_available));
        }
        return isConnected;
    }

    public static void showSimpleDialog(Context context, String title, String message) {

        final Dialog responseDialog = new Dialog(context);
        responseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        responseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        responseDialog.setContentView(R.layout.response_dialog);

        TextView tvMessage = (TextView) responseDialog.findViewById(R.id.tv_message);
        tvMessage.setText("" + message);
        TextView tvOk = (TextView) responseDialog.findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickOk = true;
                responseDialog.dismiss();
            }
        });

        responseDialog.setCancelable(false);
        responseDialog.show();
    }






    public static SharedPreferences getAppPreferences(Context context) {
        return context.getSharedPreferences("BaseBall", Context.MODE_PRIVATE);
    }






    public static Dialog showProgressDialog(Context context) {
        final Dialog responseDialog = new Dialog(context);
        responseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        responseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        responseDialog.setContentView(R.layout.progress_dialog_layout2);

        responseDialog.setCancelable(false);
        responseDialog.show();
        return responseDialog;
    }



    public static boolean isNetworkAvailableSplash(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }






}
