package com.likeit.a51scholarship.app;

import android.app.Application;
import android.content.Context;

import com.likeit.a51scholarship.model.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.likeit.a51scholarship.R;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;


public class MyApplication extends Application {

    public static MyApplication context;
    private static MyApplication instance;
    private UserInfo userInfo = null;

    public static MyApplication getInstance() {
        if (context == null) {
            return new MyApplication();
        } else {
            return context;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
        initMob();
        // 图片加载工具初始化
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_public_nophoto)
                .showImageOnFail(R.mipmap.ic_public_nophoto)
                .cacheInMemory(true).cacheOnDisc(true).build();
        // 图片加载工具配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
//        //初始化
//        Beta.autoCheckUpgrade = true;//设置自动检查
       // Bugly.init(context, "f8279afbf2", false);
    }



    private void initMob() {
        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "1b2bd24a56f24", "Secret:c6970e8b5b801e120f01656349a20e08");
    }


    public static MyApplication getInstance(Context appContext) {
        return instance;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
//    public UserInfo getUserInfo() {
//        if (userInfo == null)
//            init();
//        return userInfo;
//    }
}
//    private void init() {
//        userInfo = Storage.getObject(AppConfig.USER_INFO, UserInfo.class);
//
//    }

    /**
     * 登录信息的保存
     *
     * @param user
     */
//    public static void doLogin(UserInfo user) {
//        Storage.saveObject(AppConfig.USER_INFO, user);
//        Storage.saveObject(AppConfig.USER_INFO, user);
//        Preferences
//                .saveString(AppConfig.USER_ID, user.getUkey(), getInstance());
//        MyApplication.getInstance().init();
//    }

//    /**
//     * 清除登录信息(退出账号)
//     */
//    public static void doLogout() {
//        Storage.clearObject(AppConfig.USER_INFO);
//        MyApplication.getInstance().init();
//    }
//}