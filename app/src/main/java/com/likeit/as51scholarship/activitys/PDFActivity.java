package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.likeit.as51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PDFActivity extends Container implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener {
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private String file_url;
    private String file_name;
    private int myPage;
    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        file_url = intent.getStringExtra("file_url");
        file_name = intent.getStringExtra("file_name");
        initView();
    }

    private void initView() {
        tvHeader.setText(file_name);
        displayFromFile1(file_url, file_name);

    }

    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1(String fileUrl, String fileName) {
        showProgress();
        pdfView.fileFromLocalStorage(this, this, this, fileUrl, fileName);   //设置pdf文件地址

    }

    /**
     * 翻页回调
     *
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
//        Toast.makeText(mContext, "page= " + page +
//                " pageCount= " + pageCount, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载完成回调
     *
     * @param nbPages 总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
        // Toast.makeText(mContext, "加载完成" + nbPages, Toast.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
        // Toast.makeText( MainActivity.this ,  "pageWidth= " + pageWidth + "
        // pageHeight= " + pageHeight + " displayedPage="  + displayedPage , Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示对话框
     */
    private void showProgress() {
        //LoadingUIHelper.showDialogForLoading(this, "报告加载中,请等待。。。", false);
    }

    /**
     * 关闭等待框
     */
    private void hideProgress() {
        //LoadingUIHelper.hideDialogForLoading();
    }

    @OnClick(R.id.backBtn)
    public void onClick(View v) {
        onBackPressed();
    }
}
