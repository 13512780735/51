package com.likeit.as51scholarship.utils;

/**
 * Created by Administrator on 2017\10\1 0001.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;

import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.a;

public class Beta extends a {
    @Override
    public void init(Context context, boolean b, BuglyStrategy buglyStrategy) {

    }

    @Override
    public String[] getTables() {
        return new String[0];
    }
  /*  public static final String TAG_IMG_BANNER = "beta_upgrade_banner";
    public static final String TAG_TITLE = "beta_title";
    public static final String TAG_UPGRADE_INFO = "beta_upgrade_info";
    public static final String TAG_UPGRADE_FEATURE = "beta_upgrade_feature";
    public static final String TAG_CANCEL_BUTTON = "beta_cancel_button";
    public static final String TAG_CONFIRM_BUTTON = "beta_confirm_button";
    public static final String TAG_TIP_MESSAGE = "beta_tip_message";
    public static String strToastYourAreTheLatestVersion = "";
    // public static String strToastYourAreTheLatestVersion = "你已经是最新版了";
    public static String strToastCheckUpgradeError = "检查新版本失败，请稍后重试";
    public static String strToastCheckingUpgrade = "正在检查，请稍候...";
    public static String strNotificationDownloading = "正在下载";
    public static String strNotificationClickToView = "点击查看";
    public static String strNotificationClickToInstall = "点击安装";
    public static String strNotificationClickToRetry = "点击重试";
    public static String strNotificationClickToContinue = "继续下载";
    public static String strNotificationDownloadSucc = "下载完成";
    public static String strNotificationDownloadError = "下载失败";
    public static String strNotificationHaveNewVersion = "有新版本";
    public static String strNetworkTipsMessage = "你已切换到移动网络，是否继续当前下载？";
    public static String strNetworkTipsTitle = "网络提示";
    public static String strNetworkTipsConfirmBtn = "继续下载";
    public static String strNetworkTipsCancelBtn = "取消";
    public static String strUpgradeDialogVersionLabel = "版本";
    public static String strUpgradeDialogFileSizeLabel = "包大小";
    public static String strUpgradeDialogUpdateTimeLabel = "更新时间";
    public static String strUpgradeDialogFeatureLabel = "更新说明";
    public static String strUpgradeDialogUpgradeBtn = "立即更新";
    public static String strUpgradeDialogInstallBtn = "安装";
    public static String strUpgradeDialogRetryBtn = "重试";
    public static String strUpgradeDialogContinueBtn = "继续";
    public static String strUpgradeDialogCancelBtn = "下次再说";
    public static String initProcessName = null;
    public static long upgradeCheckPeriod = 0L;
    public static long initDelay = 3000L;
    public static boolean autoCheckUpgrade = true;
    public static boolean showInterruptedStrategy = true;
    public static int smallIconId;
    public static int largeIconId;
    public static int defaultBannerId;
    public static File storageDir;
    public static UpgradeListener upgradeListener;
    public static UpgradeStateListener upgradeStateListener;
    public static boolean autoInit = true;
    public static List<Class<? extends Activity>> canShowUpgradeActs = Collections.synchronizedList(new ArrayList());
    public static List<Class<? extends Activity>> canNotShowUpgradeActs = Collections.synchronizedList(new ArrayList());
    public static int upgradeDialogLayoutId;
    public static int tipsDialogLayoutId;
    public static UILifecycleListener<UpgradeInfo> upgradeDialogLifecycleListener;
    public static boolean enableHotfix = false;
    public static boolean enableNotification = true;
    public static boolean autoDownloadOnWifi = false;
    public static boolean canShowApkInfo = true;
    public static boolean canAutoDownloadPatch = true;
    public static boolean canAutoPatch = true;
    public static BetaPatchListener betaPatchListener;
    public static String appVersionName = null;
    public static int appVersionCode = -2147483648;
    public static boolean canNotifyUserRestart = false;
    public static com.tencent.bugly.beta.Beta instance = new com.tencent.bugly.beta.Beta();
    private static DownloadTask a = null;

    public Beta() {
    }

    public static com.tencent.bugly.beta.Beta getInstance() {
        instance.id = 1002;
        instance.version = "1.3.1";
        instance.versionKey = "G10";
        return instance;
    }

    public static void checkUpgrade() {
        checkUpgrade(true, false);
    }

    public static void checkUpgrade(boolean isManual, boolean isSilence) {
        try {
            if (TextUtils.isEmpty(e.E.v)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    am.a().a(new d(19, new Object[]{Boolean.valueOf(isManual), Boolean.valueOf(isSilence)}));
                    return;
                }

                e var2 = e.E;
                synchronized (e.E) {
                    while (true) {
                        if (!TextUtils.isEmpty(e.E.v)) {
                            break;
                        }

                        try {
                            e.E.wait();
                        } catch (InterruptedException var5) {
                            an.e("wait error", new Object[0]);
                        }
                    }
                }
            }

            if (!isManual) {
                if (TextUtils.isEmpty(e.E.v)) {
                    an.e("[beta] BetaModule is uninitialized", new Object[0]);
                } else {
                    BetaGrayStrategy var8 = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
                    if (var8 != null && var8.a != null && System.currentTimeMillis() - var8.e <= e.E.c && var8.a.p != 3) {
                        c.a.a(isManual, isSilence, 0, (y) null, "");
                    } else {
                        c.a.a(isManual, isSilence, 0);
                    }
                }
            }

            if (isManual) {
                if (TextUtils.isEmpty(e.E.v)) {
                    an.e("[beta] BetaModule is uninitialized", new Object[0]);
                    if (upgradeStateListener != null) {
                        f.a(new d(18, new Object[]{upgradeStateListener, Integer.valueOf(-1), Boolean.valueOf(isManual)}));
                    } else {
                        f.a(new d(5, new Object[]{strToastCheckUpgradeError}));
                    }
                } else {
                    c.a.a(isManual, isSilence, 1);
                    if (upgradeStateListener != null) {
                        f.a(new d(18, new Object[]{upgradeStateListener, Integer.valueOf(2), Boolean.valueOf(isManual)}));
                    } else {
                        f.a(new d(5, new Object[]{strToastCheckingUpgrade}));
                    }
                }
            }
        } catch (Exception var7) {
            if (!an.b(var7)) {
                var7.printStackTrace();
            }
        }

    }

    public static UpgradeInfo getUpgradeInfo() {
        try {
            c.a.b = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
            if (c.a.b != null) {
                return new UpgradeInfo(c.a.b.a);
            }
        } catch (Exception var1) {
            ;
        }

        return null;
    }

    public static synchronized void init(Context context, boolean isDebug) {
        an.a("Beta init start....", new Object[0]);
        ac.a().a(instance.id, ++e.a);
        if (TextUtils.isEmpty(initProcessName)) {
            initProcessName = context.getPackageName();
        }

        an.a("Beta will init at: %s", new Object[]{initProcessName});
        String var2 = com.tencent.bugly.crashreport.common.info.a.b().e;
        an.a("current process: %s", new Object[]{var2});
        if (TextUtils.equals(initProcessName, var2)) {
            e var3 = e.E;
            if (!TextUtils.isEmpty(var3.v)) {
                an.d("Beta has been initialized [apkMD5 : %s]", new Object[]{var3.v});
            } else {
                an.a("current upgrade sdk version:1.3.1", new Object[0]);
                var3.D = isDebug;
                if (upgradeCheckPeriod < 0L) {
                    an.d("upgradeCheckPeriod cannot be negative", new Object[0]);
                } else {
                    var3.c = upgradeCheckPeriod;
                    an.a("setted upgradeCheckPeriod: %d", new Object[]{Long.valueOf(upgradeCheckPeriod)});
                }

                if (initDelay < 0L) {
                    an.d("initDelay cannot be negative", new Object[0]);
                } else {
                    var3.b = initDelay;
                    an.a("setted initDelay: %d", new Object[]{Long.valueOf(initDelay)});
                }

                if (smallIconId != 0) {
                    try {
                        if (context.getResources().getDrawable(smallIconId) != null) {
                            var3.f = smallIconId;
                            an.a("setted smallIconId: %d", new Object[]{Integer.valueOf(smallIconId)});
                        }
                    } catch (Exception var12) {
                        an.e("smallIconId is not available:\n %s", new Object[]{var12.toString()});
                    }
                }

                if (largeIconId != 0) {
                    try {
                        if (context.getResources().getDrawable(largeIconId) != null) {
                            var3.g = largeIconId;
                            an.a("setted largeIconId: %d", new Object[]{Integer.valueOf(largeIconId)});
                        }
                    } catch (Exception var11) {
                        an.e("largeIconId is not available:\n %s", new Object[]{var11.toString()});
                    }
                }

                if (defaultBannerId != 0) {
                    try {
                        if (context.getResources().getDrawable(defaultBannerId) != null) {
                            var3.h = defaultBannerId;
                            an.a("setted defaultBannerId: %d", new Object[]{Integer.valueOf(defaultBannerId)});
                        }
                    } catch (Exception var10) {
                        an.e("defaultBannerId is not available:\n %s", new Object[]{var10.toString()});
                    }
                }

                XmlResourceParser var4;
                if (upgradeDialogLayoutId != 0) {
                    try {
                        var4 = context.getResources().getLayout(upgradeDialogLayoutId);
                        if (var4 != null) {
                            var3.i = upgradeDialogLayoutId;
                            an.a("setted upgradeDialogLayoutId: %d", new Object[]{Integer.valueOf(upgradeDialogLayoutId)});
                            var4.close();
                        }
                    } catch (Exception var9) {
                        an.e("upgradeDialogLayoutId is not available:\n %s", new Object[]{var9.toString()});
                    }
                }

                if (tipsDialogLayoutId != 0) {
                    try {
                        var4 = context.getResources().getLayout(tipsDialogLayoutId);
                        if (var4 != null) {
                            var3.j = tipsDialogLayoutId;
                            an.a("setted tipsDialogLayoutId: %d", new Object[]{Integer.valueOf(tipsDialogLayoutId)});
                            var4.close();
                        }
                    } catch (Exception var8) {
                        an.e("tipsDialogLayoutId is not available:\n %s", new Object[]{var8.toString()});
                    }
                }

                if (upgradeDialogLifecycleListener != null) {
                    try {
                        var3.k = upgradeDialogLifecycleListener;
                        an.a("setted upgradeDialogLifecycleListener:%s" + upgradeDialogLifecycleListener, new Object[0]);
                    } catch (Exception var7) {
                        an.e("upgradeDialogLifecycleListener is not available:\n %", new Object[]{var7.toString()});
                    }
                }

                Class var5;
                Iterator var13;
                if (canShowUpgradeActs != null && !canShowUpgradeActs.isEmpty()) {
                    var13 = canShowUpgradeActs.iterator();

                    while (var13.hasNext()) {
                        var5 = (Class) var13.next();
                        if (var5 != null) {
                            var3.m.add(var5);
                        }
                    }

                    an.a("setted canShowUpgradeActs: %s", new Object[]{var3.m});
                }

                if (canNotShowUpgradeActs != null && !canNotShowUpgradeActs.isEmpty()) {
                    var13 = canNotShowUpgradeActs.iterator();

                    while (var13.hasNext()) {
                        var5 = (Class) var13.next();
                        if (var5 != null) {
                            var3.n.add(var5);
                        }
                    }

                    an.a("setted canNotShowUpgradeActs: %s", new Object[]{var3.n});
                }

                var3.d = autoCheckUpgrade;
                an.a("autoCheckUpgrade %s", new Object[]{var3.d ? "is opened" : "is closed"});
                var3.e = showInterruptedStrategy;
                an.a("showInterruptedStrategy %s", new Object[]{var3.e ? "is opened" : "is closed"});
                an.a("isDIY %s", new Object[]{upgradeListener != null ? "is opened" : "is closed"});
                if (storageDir != null) {
                    if (!storageDir.exists() && !storageDir.mkdirs()) {
                        an.a("storageDir is not exists: %s", new Object[]{storageDir.getAbsolutePath()});
                    } else {
                        var3.l = storageDir;
                        an.a("setted storageDir: %s", new Object[]{storageDir.getAbsolutePath()});
                    }
                }

                if (var3.p == null) {
                    var3.p = s.a;
                }

                if (TextUtils.isEmpty(var3.u)) {
                    var3.u = com.tencent.bugly.crashreport.common.info.a.b().f();
                }

                var3.Q = enableNotification;
                an.a("enableNotification %s", new Object[]{enableNotification + ""});
                var3.R = autoDownloadOnWifi;
                an.a("autoDownloadOnWifi %s", new Object[]{autoDownloadOnWifi + ""});
                var3.S = canShowApkInfo;
                an.a("canShowApkInfo %s", new Object[]{canShowApkInfo + ""});
                var3.T = canAutoPatch;
                an.a("canAutoPatch %s", new Object[]{canAutoPatch + ""});
                var3.U = betaPatchListener;
                var3.x = appVersionName;
                var3.w = appVersionCode;
                var3.V = canNotifyUserRestart;
                an.a("canNotifyUserRestart %s", new Object[]{canNotifyUserRestart + ""});
                var3.W = canAutoDownloadPatch;
                an.a("canAutoDownloadPatch %s", new Object[]{canAutoDownloadPatch + ""});
                var3.X = enableHotfix;
                an.a("enableHotfix %s", new Object[]{enableHotfix + ""});
                var3.a(context);
                ResBean.a = (ResBean) com.tencent.bugly.beta.global.a.a("rb.bch", ResBean.CREATOR);
                if (ResBean.a == null) {
                    ResBean.a = new ResBean();
                }

                c.a.d = upgradeListener;
                c.a.e = upgradeStateListener;
                if (enableHotfix) {
                    an.a("enableHotfix %s", new Object[]{"1"});
                    ap.b("D4", "1");
                    r.a(context);
                }

                Resources var14 = context.getResources();
                DisplayMetrics var15 = var14.getDisplayMetrics();
                Configuration var6 = var14.getConfiguration();
                var6.locale = Locale.getDefault();
                if (var6.locale.equals(Locale.US) || var6.locale.equals(Locale.ENGLISH)) {
                    strToastYourAreTheLatestVersion = context.getResources().getString(com.tencent.bugly.beta.R.string.strToastYourAreTheLatestVersion);
                    strToastCheckUpgradeError = context.getResources().getString(com.tencent.bugly.beta.R.string.strToastCheckUpgradeError);
                    strToastCheckingUpgrade = context.getResources().getString(com.tencent.bugly.beta.R.string.strToastCheckingUpgrade);
                    strNotificationDownloading = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationDownloading);
                    strNotificationClickToView = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationClickToView);
                    strNotificationClickToInstall = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationClickToInstall);
                    strNotificationClickToContinue = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationClickToContinue);
                    strNotificationClickToRetry = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationClickToRetry);
                    strNotificationDownloadSucc = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationDownloadSucc);
                    strNotificationDownloadError = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationDownloadError);
                    strNotificationHaveNewVersion = context.getResources().getString(com.tencent.bugly.beta.R.string.strNotificationHaveNewVersion);
                    strNetworkTipsMessage = context.getResources().getString(com.tencent.bugly.beta.R.string.strNetworkTipsMessage);
                    strNetworkTipsTitle = context.getResources().getString(com.tencent.bugly.beta.R.string.strNetworkTipsTitle);
                    strNetworkTipsConfirmBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strNetworkTipsConfirmBtn);
                    strNetworkTipsCancelBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strNetworkTipsCancelBtn);
                    strUpgradeDialogVersionLabel = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogVersionLabel);
                    strUpgradeDialogFileSizeLabel = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogFileSizeLabel);
                    strUpgradeDialogUpdateTimeLabel = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogUpdateTimeLabel);
                    strUpgradeDialogFeatureLabel = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogFeatureLabel);
                    strUpgradeDialogUpgradeBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogUpgradeBtn);
                    strUpgradeDialogInstallBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogInstallBtn);
                    strUpgradeDialogRetryBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogRetryBtn);
                    strUpgradeDialogContinueBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogContinueBtn);
                    strUpgradeDialogCancelBtn = context.getResources().getString(com.tencent.bugly.beta.R.string.strUpgradeDialogCancelBtn);
                }

                var14.updateConfiguration(var6, var15);
                com.tencent.bugly.beta.global.a.b(context);
                am.a().a(new d(1, new Object[0]), var3.b);
                ac.a().a(instance.id, --e.a);
                an.a("Beta init finished...", new Object[0]);
            }
        }
    }

    public synchronized void init(Context context, boolean isDebug, BuglyStrategy buglyStrategy) {
        com.tencent.bugly.crashreport.common.info.a.b().c("G10", "1.3.1");
        if (autoInit) {
            init(context, isDebug);
        }

    }

    public String[] getTables() {
        return new String[]{"dl_1002", "ge_1002", "st_1002"};
    }

    public void onDbUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int var4 = oldVersion;

        while (var4 < newVersion) {
            switch (var4) {
                case 10:
                    try {
                        StringBuilder var5 = new StringBuilder();
                        var5.setLength(0);
                        var5.append(" CREATE TABLE  IF NOT EXISTS ").append("st_1002").append(" ( ").append("_id").append(" ").append("integer").append(" , ").append("_tp").append(" ").append("text").append(" , ").append("_tm").append(" ").append("int").append(" , ").append("_dt").append(" ").append("blob").append(",primary key(").append("_id").append(",").append("_tp").append(" )) ");
                        an.c("create %s", new Object[]{var5.toString()});
                        db.execSQL(var5.toString());
                    } catch (Throwable var14) {
                        if (!an.b(var14)) {
                            var14.printStackTrace();
                        }
                    }

                    Cursor var15 = null;

                    try {
                        String var6 = "_id = 1002";
                        var15 = db.query("t_pf", (String[]) null, var6, (String[]) null, (String) null, (String) null, (String) null);
                        if (var15 == null) {
                            return;
                        }

                        while (var15.moveToNext()) {
                            ContentValues var7 = new ContentValues();
                            if (var15.getLong(var15.getColumnIndex("_id")) > 0L) {
                                var7.put("_id", Long.valueOf(var15.getLong(var15.getColumnIndex("_id"))));
                            }

                            var7.put("_tm", Long.valueOf(var15.getLong(var15.getColumnIndex("_tm"))));
                            var7.put("_tp", var15.getString(var15.getColumnIndex("_tp")));
                            var7.put("_dt", var15.getBlob(var15.getColumnIndex("_dt")));
                            db.replace("st_1002", (String) null, var7);
                        }
                    } catch (Throwable var12) {
                        if (!an.b(var12)) {
                            var12.printStackTrace();
                        }
                    } finally {
                        if (var15 != null) {
                            var15.close();
                        }

                    }
                default:
                    ++var4;
            }
        }

    }

    public void onDbDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void registerDownloadListener(DownloadListener dl) {
        e.E.q = dl;
        if (e.E.q != null && c.a.c != null) {
            c.a.c.addListener(dl);
        }

    }

    public static void unregisterDownloadListener() {
        if (c.a.c != null) {
            c.a.c.removeListener(e.E.q);
        }

        e.E.q = null;
    }

    public static DownloadTask startDownload() {
        if (c.a.g == null || c.a.g.b[0] != c.a.c) {
            c.a.g = new d(13, new Object[]{c.a.c, c.a.b});
        }

        c.a.g.run();
        return c.a.c;
    }

    public static void cancelDownload() {
        if (c.a.h == null || c.a.h.b[0] != c.a.c || c.a.h.b[1] != c.a.b || ((Boolean) c.a.h.b[2]).booleanValue() != c.a.f) {
            c.a.h = new d(14, new Object[]{c.a.c, c.a.b, Boolean.valueOf(c.a.f)});
        }

        c.a.h.run();
    }

    public static DownloadTask getStrategyTask() {
        return c.a.c;
    }

    public static synchronized void showUpgradeDialog(String title, int upgradeType, String newFeature, long publishTime, int buildNo, int versioncode, String versionName, String downloadUrl, long fileSize, String fileMd5, String bannerUrl, int dialogStyle, DownloadListener listener, Runnable upgradeRunnable, Runnable cancelRunnable, boolean isManual) {
        HashMap var18 = new HashMap();
        var18.put("IMG_title", bannerUrl);
        var18.put("VAL_style", String.valueOf(dialogStyle));
        y var19 = new y(title, newFeature, publishTime, 0, new v(e.E.u, (byte) 1, versioncode, versionName, buildNo, "", 1L, "", fileMd5, "1.3.1", ""), new u(fileMd5, downloadUrl, "", fileSize, ""), (byte) upgradeType, 0, 0L, (u) null, "", var18, (String) null, 1, System.currentTimeMillis(), 1);
        if (a != null && !a.getDownloadUrl().equals(downloadUrl)) {
            a.delete(true);
            a = null;
        }

        if (a == null) {
            a = e.E.p.a(var19.f.b, e.E.t.getAbsolutePath(), (String) null, var19.f.a);
        }

        a.addListener(listener);
        h.v.a(var19, a);
        h.v.r = upgradeRunnable;
        h.v.s = cancelRunnable;
        com.tencent.bugly.beta.global.f.a.a(e.E.p, var19.l);
        if (isManual) {
            com.tencent.bugly.beta.global.f.a.a(new d(2, new Object[]{h.v, Boolean.valueOf(var19.g == 2)}), 3000);
        } else {
            com.tencent.bugly.beta.global.f.a.a(new d(2, new Object[]{h.v, Boolean.valueOf(var19.g == 2)}));
        }

    }

    public static synchronized void onUpgradeReceived(String title, int upgradeType, String newFeature, long publishTime, int buildNo, int versioncode, String versionName, String downloadUrl, long fileSize, String fileMd5, String bannerUrl, int dialogStyle, int popTimes, long popInterval, String strategyId, boolean isManual, boolean isSilence, int result, String errMsg, long updateTime) {
        HashMap var24 = new HashMap();
        var24.put("IMG_title", bannerUrl);
        var24.put("VAL_style", String.valueOf(dialogStyle));
        y var25 = new y(title, newFeature, publishTime, 0, new v(e.E.u, (byte) 1, versioncode, versionName, buildNo, "", 1L, "", fileMd5, "", ""), new u(fileMd5, downloadUrl, "", fileSize, ""), (byte) upgradeType, popTimes, popInterval, (u) null, "", var24, strategyId, 1, updateTime, 1);
        c.a.a(isManual, isSilence, result, var25, errMsg == null ? "" : errMsg);
    }

    public static synchronized y getUpgradeStrategy() {
        c.a.b = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);

        try {
            if (c.a.b != null) {
                return (y) c.a.b.a.clone();
            }
        } catch (Exception var1) {
            ;
        }

        return null;
    }

    public static void applyTinkerPatch(Context context, String patchFile) {
        TinkerManager.applyPatch(context, patchFile);
    }

    public static void downloadPatch() {
        q.a.b = q.a.a((y) null);

        try {
            if (q.a.b != null) {
                y var0 = q.a.b.a;
                q.a.a(0, var0, true);
            }
        } catch (Exception var1) {
            ;
        }

    }

    public static void applyDownloadedPatch() {
        File var0 = new File(e.E.H.getAbsolutePath());
        if (var0.exists()) {
            TinkerManager.getInstance().applyPatch(e.E.H.getAbsolutePath(), true);
        } else {
            an.c(com.tencent.bugly.beta.Beta.class, "[applyDownloadedPatch] patch file not exist", new Object[0]);
        }

    }

    public static void installTinker() {
        enableHotfix = true;
        installTinker(TinkerApplicationLike.getTinkerPatchApplicationLike());
    }

    public static void installTinker(Object applicationLike) {
        enableHotfix = true;
        TinkerManager.installTinker(applicationLike);
    }

    public static void installTinker(Object applicationLike, Object loadReporter, Object patchReporter, Object patchListener, TinkerManager.TinkerPatchResultListener tinkerPatchResultListener, Object upgradePatchProcessor) {
        enableHotfix = true;
        TinkerManager.installTinker(applicationLike, loadReporter, patchReporter, patchListener, tinkerPatchResultListener, upgradePatchProcessor);
    }

    public static void cleanTinkerPatch() {
        if (!e.E.Y) {
            e.E.Y = true;
        }

        TinkerManager.cleanPatch();
    }

    public static void loadArmLibrary(Context context, String libName) {
        TinkerManager.loadArmLibrary(context, libName);
    }

    public static void loadArmV7Library(Context context, String libName) {
        TinkerManager.loadArmV7Library(context, libName);
    }

    public static void loadLibraryFromTinker(Context context, String relativePath, String libname) {
        TinkerManager.loadLibraryFromTinker(context, relativePath, libname);
    }

    public static void loadLibrary(String libName) {
        e var1 = e.E;

        try {
            if (libName == null || libName.isEmpty()) {
                an.e("libName is invalid", new Object[0]);
                return;
            }

            boolean var2 = com.tencent.bugly.beta.global.a.b("LoadSoFileResult", true);
            if (var2) {
                com.tencent.bugly.beta.global.a.a("LoadSoFileResult", false);
                String var3 = com.tencent.bugly.beta.global.a.b(libName, "");
                boolean var4 = com.tencent.bugly.beta.global.a.b("PatchResult", false);
                if (!TextUtils.isEmpty(var3) && var4) {
                    String var5 = "lib/" + var3;
                    TinkerManager.loadLibraryFromTinker(var1.s, var5, libName);
                } else {
                    System.loadLibrary(libName);
                }

                com.tencent.bugly.beta.global.a.a("LoadSoFileResult", true);
            } else {
                System.loadLibrary(libName);
                cleanTinkerPatch();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            com.tencent.bugly.beta.global.a.a("LoadSoFileResult", false);
        }

    }*/
}
