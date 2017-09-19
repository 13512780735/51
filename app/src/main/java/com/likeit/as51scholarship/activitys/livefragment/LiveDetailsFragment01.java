package com.likeit.as51scholarship.activitys.livefragment;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveDetailsFragment01  extends BaseFragment {


    @Override
    protected int setContentView() {
        return R.layout.fragment_live_details_fragment01;
    }

    @Override
    protected void lazyLoad() {
        TextView tvDetails = findViewById(R.id.tv);
        tvDetails.setText("\t迈阿密大学（University of Miami），简称UM，位于美国佛罗里达州珊瑚阁，是一所始建于1925年的美国顶尖私立综合大学，美国明星级大学，被公认为佛罗里达州最负盛名的大学，尤以商学院、法学院知名，其培养出许多商政首脑，如伯利兹总理、秘鲁商务部部长，参议院成员、众议院成员、多位市长、多位大法官。2016年美国总统候选人Marco Rubio也为迈阿密大学法学院的毕业生之一。\n" +
                "\t2017年，迈阿密大学在U.S.News美国大学综合排名中位列全美第44位，佛州第一。泰晤士报2016-2017世界大学排名中，迈阿密大学位列第182名。迈阿密大学医药学院为研究生设立的物理治疗课程被《美国新闻与世界报道》列为全美第3位，税法专业排名全美第5，法学院于2015年被《国家法律期刊》评为全美第15位。\n" +
                "\t迈阿密大学共拥有本科及研究生15,000多人，包括近1600名来自世界110多个国家的国际学生。该校拥有约2,000名全职教学人员，学生与教员的比例约为11:1，采取小班教学，在");
    }

}
