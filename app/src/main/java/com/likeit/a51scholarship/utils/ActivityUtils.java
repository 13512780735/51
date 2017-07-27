package com.likeit.a51scholarship.utils;

import android.content.Context;
import android.content.Intent;

import com.likeit.a51scholarship.activitys.WebActivity;
import com.likeit.a51scholarship.activitys.WebForNewsActivity;
import com.likeit.a51scholarship.activitys.WebHtmlActivity;


public class ActivityUtils {

    public static void toWebActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
//        intent.putExtra(WebActivity.WEB_URL, url);
//        intent.putExtra(WebActivity.WEB_TITLE, title);
        context.startActivity(intent);
    }

    public static void toWebNewsActivity(Context context, String url, String title,String id) {
        Intent intent = new Intent(context, WebForNewsActivity.class);
 //       intent.putExtra(WebForNewsActivity.WEB_URL, url);
//        intent.putExtra(WebForNewsActivity.ID, id);
//        intent.putExtra(WebForNewsActivity.WEB_TITLE, title);
        context.startActivity(intent);
    }

    public static void toWebHtmlActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
//        intent.putExtra(WebHtmlActivity.WEB_HTML, url);
//        intent.putExtra(WebHtmlActivity.WEB_TITLE, title);
        context.startActivity(intent);
    }
}
