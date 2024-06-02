package com.example.myapplication01.data;

import com.amap.api.maps2d.model.LatLng;
import com.example.myapplication01.R;
import com.example.myapplication01.data.InfoData;

import java.util.HashMap;

public class AttractionInfoDataManager {
    private static HashMap<String, InfoData> infoDataMap_A = new HashMap<>();

    static {
        // 添加数据
        infoDataMap_A.put("星湖", new InfoData("星湖", R.drawable.xinghu,
                "武汉大学星湖位于校园中心，是一座风景如画的小湖，湖中小岛和周围的绿树交相辉映。湖畔的步道是学生们散步和放松的好去处。",
                new LatLng(30.527055, 114.359472)
        ));
        infoDataMap_A.put("国立武汉大学牌楼", new InfoData("国立武汉大学牌楼", R.drawable.pailou,
                "武汉大学牌楼是校园的标志性建筑，位于校门口。它以其古典的中式建筑风格和雄伟的气势，迎接着每天进出的师生和游客。",
                new LatLng(30.533526, 114.358772)
        ));
        infoDataMap_A.put("墨子雕像", new InfoData("墨子雕像", R.drawable.mozi,
                "武汉大学墨子雕像坐落在校园的广场上，以纪念中国古代伟大的思想家和科学家墨子。雕像栩栩如生，象征着智慧和学术精神。",
                new LatLng(30.536334, 114.364339)
                ));
        infoDataMap_A.put("珞珈广场", new InfoData("珞珈广场", R.drawable.luojiaguangchang,
                "位于武汉大学校园中心，是学校的主要活动场地，经常举办各种大型集会、庆典和文化活动。广场周围绿树环绕，是师生们交流和休闲的重要场所，象征着武汉大学的核心精神。",
                new LatLng(30.534796, 114.360750)
        ));
        infoDataMap_A.put("樱花城堡", new InfoData("樱花城堡", R.drawable.yinghuachengbao,
                "武汉大学的著名景点，每年春季樱花盛开时吸引成千上万的游客前来观赏。樱花城堡是古典建筑与自然美景的完美结合，是学校历史与文化的重要象征之一。",
                new LatLng(30.540194, 114.363673)
        ));
        infoDataMap_A.put("李达雕像", new InfoData("李达雕像", R.drawable.lidadx,
                "为纪念武汉大学著名校长李达而设立，雕像位于校园显著位置，象征着李达对中国教育事业和武汉大学发展的重大贡献，激励着师生传承学术精神。",
                new LatLng(30.536334, 114.364339)
        ));
        infoDataMap_A.put("行政楼", new InfoData("行政楼", R.drawable.xingzhenglou,
                "武汉大学的行政中心，处理学校各类行政事务。建筑风格庄重典雅，既是校内重要的办公地点，也是学校对外形象的代表，体现着武汉大学的文化底蕴与管理水平。",
                new LatLng(30.537588, 114.366552)
        ));
    }

    public static InfoData getInfoData(String title) {
        return infoDataMap_A.get(title);
    }
}