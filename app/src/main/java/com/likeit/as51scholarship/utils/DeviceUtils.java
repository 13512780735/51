package com.likeit.as51scholarship.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *  机器设备工具集
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-26  下午2:45:17
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class DeviceUtils {
	
	/**
	 * 获取系统版本Code
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getSystemVersionCode() {
		return android.os.Build.VERSION.SDK + "";
	}

	/**
	 * 获取系统版本name
	 * 
	 * @return
	 */
	public static String getSystemVersionName() {
		return android.os.Build.VERSION.RELEASE + "";
	}
	
	/**
	 * 获取设备名称
	 * 
	 * @return
	 */
	public static String getDeviceName() {
		return android.os.Build.MODEL + "";
	}
	
	/**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp,字体的转换
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取DisplayMetrics，包括屏幕高宽，密度等
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获得屏幕宽度 px
     * @param context
     * @return
     */
    public static int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度 px
     * @param context
     * @return
     */
    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    
	/**
	 * 获取设备密度(0.75/1.0/1.5/2.0)
	 * 
	 * @return
	 */
	public static float getDeviceDensity(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		float density = metric.density;
		return density;
	}

	/**
	 * Get the Device's DensityDpi per inch
	 * 
	 * @return the Device's DensityDpi per inch
	 */

	public static float getDeviceDensityDpi(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		float densityDpi = metric.densityDpi;
		return densityDpi;
	}
	
	/**
	 * Get the Device's Language
	 * 
	 * @return the Device's Language
	 */
	public static String getDeviceLanguage(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		return language;
	}
	
	/**
	 * Get the Device's TotalMemory
	 * 
	 * @return the Device's TotalMemory(M)
	 */
	public String getTotalMemory() {
		long mTotal;
		// /proc/meminfo Kernel messages read out to explain
		String path = "/proc/meminfo";
		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path), 8);
			String line;
			if ((line = br.readLine()) != null) {
				content = line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// beginIndex
		int begin = content.indexOf(':');
		// endIndex
		int end = content.indexOf('k');
		content = content.substring(begin + 1, end).trim();
		mTotal = Integer.parseInt(content);

		return mTotal / 1024 + "";
	}
	
	/**
	 * Get the Device's Available Memory
	 * 
	 * @return the Device's Available Memory
	 */

	public static String getAvailableMemory(Context context) {
		long MEM_UNUSED;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		MEM_UNUSED = (mi.availMem / (1024 * 1024));
		return MEM_UNUSED + "";
	}
    
    /**
     * 获取IMSI 
     * 
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        } catch (Exception exception1) {
        }
        return "";
    }
    
    /**
     * 获取IMEI 唯一号
     * 
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            if (context == null) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei != null && !imei.equals("")) {
                return imei;
            }
        } catch (Exception exception1) {
        }

        return "";
    }
    
    /**
     * 获取设备Id
     * 
     * @param context
     * 
     * @return
     */
    public static String getDeviceId(Context context) {
        try {
            String strIMEI = getIMEI(context);
            if (strIMEI == null || strIMEI.equals("")) {
                strIMEI = getIMSI(context);
                if (strIMEI == null || strIMEI.equals("")) {
                    return "";
                }
            }
            String strTemp = strIMEI + strIMEI + strIMEI;
            String strMd5 = StringUtils.getMD5(strTemp.getBytes());
            return strMd5;
        } catch (Exception exception1) {
        }
        return "";
    }
    
    /**
     * 获取SDcard可用存储大小
     * 
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String getSDCardAvailableStorage() {
    	long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
//			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[1] = bSize * availBlocks;
		}
		return sdCardInfo[1] / (1024 * 1024) + "MB";
    }
    
    /**
	 * 检测照相机
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isHasCamera(Context mContext) {
		PackageManager packageManager = mContext.getPackageManager();

		Intent resolve_intent = new Intent();
		resolve_intent.setAction("android.media.action.IMAGE_CAPTURE");
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(resolve_intent, PackageManager.MATCH_DEFAULT_ONLY);
		if (resolveInfo != null && resolveInfo.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 检查手机上是否安装了指定的软件 
     * @param context 
     * @param packageName：应用包名 
     * @return 
     */  
    public static boolean isAvilible(Context context, String packageName){   
        //获取packagemanager   
        final PackageManager packageManager = context.getPackageManager();  
      //获取所有已安装程序的包信息   
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);  
      //用于存储所有已安装程序的包名   
        List<String> packageNames = new ArrayList<String>();  
        //从pinfo中将包名字逐一取出，压入pName list中   
        if(packageInfos != null){   
            for(int i = 0; i < packageInfos.size(); i++){   
                String packName = packageInfos.get(i).packageName;   
                packageNames.add(packName);   
            }   
        }   
      //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE   
        return packageNames.contains(packageName);  
  }   
}
