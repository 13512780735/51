package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.utils.UtilPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends Container {
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
        myPage = (int) UtilPreference.getIntValue(mContext, "page", 0);
        initView();
    }

    private void initView() {
        tvHeader.setText(file_name);
        pdfView.fromAsset(file_url)
//                .pages(0, 2, 3, 4, 5); // 把0 , 2 , 3 , 4 , 5 过滤掉
                //是否允许翻页，默认是允许翻页
                .enableSwipe(true)
                //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                //设置默认显示第0页
                .defaultPage(myPage)
                //允许在当前页面上绘制一些内容，通常在屏幕中间可见。
//                .onDraw(onDrawListener)
//                // 允许在每一页上单独绘制一个页面。只调用可见页面
//                .onDrawAll(onDrawListener)
                //设置加载监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                    }
                })
                //设置翻页监听
                .onPageChange(new OnPageChangeListener() {

                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        p = page;
                    }
                })
                .load();
    }
}
