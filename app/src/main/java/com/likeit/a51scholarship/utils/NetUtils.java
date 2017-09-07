package com.likeit.a51scholarship.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.likeit.a51scholarship.app.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

@SuppressLint("NewApi")
public class NetUtils {
	/**
	 * 检查是否连接了Wifi
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isWifiConnected() {
		boolean isWifiConnect = true;
		ConnectivityManager cm = (ConnectivityManager) MyApplication.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
					isWifiConnect = false;
				}
				if (networkInfos[i].getType() == cm.TYPE_WIFI) {
					isWifiConnect = true;
				}
			}
		}
		return isWifiConnect;
	}

	/**
	 * 检查是否连接了手机网络
	 * 
	 * @return
	 */
	public static boolean isMobileConnected() {
		ConnectivityManager connManager = (ConnectivityManager) MyApplication.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null) {
			return false;
		}
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return false;
		} else if (!networkInfo.isAvailable()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查是否在线（Wifi或者手机网络）
	 * 
	 * @return
	 */
	public static boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) MyApplication.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * 强制 HttpsURLConnection 信任任何证书，该操作很危险，仅用于测试，建议不要在生产环境使用
	 */
	@SuppressLint("TrulyRandom")
	@Deprecated
	public static SSLSocketFactory getTrustedAnyCertsSocketFactory() {

		try {

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					X509Certificate[] myTrustedAnchors = new X509Certificate[0];
					return myTrustedAnchors;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
					});
			sc.getSocketFactory();
		} catch (Exception e) {
		}

		return null;

	}

	/**
	 * 加载可信的证书文件，并返回SSLSocketFactory
	 * 
	 * @param certificates
	 *            ，证书，可以放在assets下面，getAssets().open("***.cert");
	 * @return 加载了证书的 SSLSocketFactory
	 */
	public static SSLSocketFactory loadTrustedCertificates(
			InputStream... certificates) {
		try {

			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);
			int index = 0;
			for (InputStream certificate : certificates) {

				String certificateAlias = Integer.toString(index++);
				keyStore.setCertificateEntry(certificateAlias,
						certificateFactory.generateCertificate(certificate));

				try {
					if (certificate != null) {
						certificate.close();
					}
				} catch (IOException e) {
					Log.e("SSLSocketFactory",
							"Load & Close Certificates Error!");
				}

			}

			SSLContext sslContext = SSLContext.getInstance("TLS");

			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(),
					new SecureRandom());
			return sslContext.getSocketFactory();

		} catch (Exception e) {

			Log.e("SSLSocketFactory",
					"CertificateException or KeyStoreException");
		}

		return null;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @return true 表示开启
	 */
	public static boolean isOPen() {
		LocationManager locationManager = (LocationManager) MyApplication.mContext
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (gps) {
			return true;
		}

		return true;
	}
}
