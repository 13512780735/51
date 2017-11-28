package com.likeit.as51scholarship.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.likeit.as51scholarship.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 *  @author 码农哥
 *	@date 2016-7-21上午11:43:17
 *	@email 441293364@qq.com
 *	@TODO
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */

@SuppressLint("InflateParams") 
public class DialogUtils {

	private static Dialog dialog = null;
	
	/**
	 * @param context
	 *            必须是 activity的实例
	 */
	public static void showProgressDialogs(Context context) {
		if (!(context instanceof Activity)) {
			return;
		}
		dialog = new Dialog(context, R.style.Theme_Light_FullScreenDialogAct);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View mView = LayoutInflater.from(context).inflate(R.layout.app_loading_message_layout, null);
		TextView textView = (TextView) mView.findViewById(R.id.app_progressbar_hint);
		textView.setText("");
		dialog.setContentView(mView);
		dialog.setCanceledOnTouchOutside(false);
		dialog.dismiss();

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * @param context
	 *            必须是 activity的实例
	 * @param msg
	 *            显示的文字
	 */
	public static void showProgressDialogWithMessage(Context context, String msg) {
		if (!(context instanceof Activity)) {
			return;
		}
		dialog = new Dialog(context, R.style.Theme_Light_FullScreenDialogAct);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View mView = LayoutInflater.from(context).inflate(R.layout.app_loading_message_layout, null);
		TextView textView = (TextView) mView.findViewById(R.id.app_progressbar_hint);
		textView.setText(msg + "...");
		dialog.setContentView(mView);
		dialog.setCanceledOnTouchOutside(false);
		dialog.dismiss();

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}
	

	public static void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	/**
	 * 底部弹出分享选择菜单
	 */
	@SuppressWarnings("deprecation")//final String shareMsg,
	public static void chooseShareDialog(final Activity activity,final String logoUrl,final String shopMsg,final PlatformActionListener paListener) {
		ShareSDK.initSDK(activity);
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share_layout, null);
		dialog = new Dialog(activity, R.style.Theme_Light_FullScreenDialogAct);
		dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围取消对话框
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

		Button button_cancel = (Button) view.findViewById(R.id.btn_cancel);
		TextView tv_wqzone = (TextView) view.findViewById(R.id.tv_share_wqzone);
		TextView tv_weixin = (TextView) view.findViewById(R.id.tv_share_weixin);
		TextView tv_qq = (TextView) view.findViewById(R.id.tv_share_qq);
		TextView tv_qzone = (TextView) view.findViewById(R.id.tv_share_qzone);
		TextView tv_weibo = (TextView) view.findViewById(R.id.tv_share_weibo);
		try {
			OnClickListener listener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.tv_share_wqzone:
						Platform.ShareParams wqzone = new WechatMoments.ShareParams();
						wqzone.setShareType(Platform.SHARE_TEXT);
						wqzone.setTitle(activity.getResources().getString(R.string.app_name));
						wqzone.text = shopMsg;
						wqzone.setShareType(Platform.SHARE_WEBPAGE);
						wqzone.setUrl("http://www.sikmore.com/app/app.php");
//						wqzone.imagePath=logoUrl;
//						sp.imagePath = "/mnt/sdcard/测试分享的图片.jpg";
						Platform weixinqzone = ShareSDK.getPlatform(activity, WechatMoments.NAME);
//						weixinqzone.removeAccount();//清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
						weixinqzone.setPlatformActionListener(paListener);
						weixinqzone.share(wqzone);
						break;

					case R.id.tv_share_weixin:
						Platform.ShareParams wxParams = new Wechat.ShareParams();
						wxParams.setShareType(Platform.SHARE_TEXT);
						wxParams.setTitle(activity.getResources().getString(R.string.app_name));
						wxParams.setText("测试分享");//shopMsg
//						wxParams.setImageUrl(logoUrl);
						wxParams.setShareType(Platform.SHARE_WEBPAGE);
						wxParams.setUrl("http://www.sikmore.com/app/app.php");
//						sp.imagePath = "/mnt/sdcard/测试分享的图片.jpg";
						Platform weixin = ShareSDK.getPlatform(activity, Wechat.NAME);
						weixin.setPlatformActionListener(paListener);
						weixin.share(wxParams);
						break;
						
					case R.id.tv_share_qq:
						Platform.ShareParams qqParams = new QQ.ShareParams();
						qqParams.setTitle(activity.getResources().getString(R.string.app_name));
						qqParams.setText(shopMsg);
						qqParams.setImageUrl(logoUrl);
						qqParams.setSiteUrl("http://www.sikmore.com/app/app.php");
						
//						sp.imagePath = "/mnt/sdcard/测试分享的图片.jpg";
						Platform qq = ShareSDK.getPlatform(activity, QQ.NAME);
						qq.removeAccount();//清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
						qq.setPlatformActionListener(paListener);
						qq.share(qqParams);
						break;
						
					case R.id.tv_share_qzone:
						Platform.ShareParams qzoneParams = new QZone.ShareParams();
						qzoneParams.setTitle(activity.getResources().getString(R.string.app_name));
						qzoneParams.setText(shopMsg);
						qzoneParams.setImageUrl(logoUrl);
						qzoneParams.setSiteUrl("http://www.sikmore.com/app/app.php");
//						sp.imagePath = "/mnt/sdcard/测试分享的图片.jpg";
						Platform qzone = ShareSDK.getPlatform(QZone.NAME);
//						qzone.removeAccount();//清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
						qzone.setPlatformActionListener(paListener);
						qzone.share(qzoneParams);
						break;
						
					case R.id.tv_share_weibo:
						Platform.ShareParams params = new SinaWeibo.ShareParams();
						params.setTitle(activity.getResources().getString(R.string.app_name));
						params.setText(shopMsg);
						params.setImageUrl(logoUrl);
						params.setSiteUrl("http://www.sikmore.com/app/app.php");
//						sp.imagePath = "/mnt/sdcard/测试分享的图片.jpg";
						Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
						weibo.removeAccount();//清除以前缓存在本地的账户信息，如果注释掉此行，将不用授权，直接分享
						weibo.setPlatformActionListener(paListener);
						weibo.share(params);
						break;

					default:
						break;
					}
					
					dismiss();
				}
			};			
			tv_wqzone.setOnClickListener(listener);
			tv_weixin.setOnClickListener(listener);
			tv_qq.setOnClickListener(listener);
			tv_qzone.setOnClickListener(listener);
			tv_weibo.setOnClickListener(listener);
		} catch (Exception e) {
			// TODO: handle exception
		}
		button_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	
	public static void showShare(Activity activity,String imgUrl,String title,String msg,String link) {
		ShareSDK.initSDK(activity);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(link);//("http://www.sikmore.com/app/app.php");
		// text是分享文本，所有平台都需要这个字段
		if(StringUtils.notBlank(msg)){
			oks.setText(msg);
		}else{
			oks.setText("51奖学金 APP");
		}
		// 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
		oks.setImageUrl(imgUrl);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(link);//("http://www.sikmore.com/app/app.php");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		if(StringUtils.notBlank(msg)){
			oks.setComment(msg);
		}else{
			oks.setComment("51奖学金 APP");
		}
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(activity.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(link);//("http://www.sikmore.com/app/app.php");

		// 启动分享GUI
		oks.show(activity);
	}
	
	
	/**
	 * 演示调用ShareSDK执行分享
	 *
	 * @param context
	 * @param platformToShare  指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
	 * @param showContentEdit  是否显示编辑页
	 */

	


	


}
