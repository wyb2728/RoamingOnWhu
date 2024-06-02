package com.example.myapplication01.data;

import com.amap.api.maps2d.model.LatLng;
import com.example.myapplication01.R;
import com.example.myapplication01.data.InfoData;

import java.util.HashMap;

public class FacilityInfoDataManager {
    private static HashMap<String, InfoData> infoDataMap_F1 = new HashMap<>();

    static {
        // 添加数据
        infoDataMap_F1.put("信息学部2食堂", new InfoData("信息学部2食堂", R.drawable.shitang2,
                "信息学部2食堂位于武汉大学信息学部内，提供多样化的餐饮选择，包括中餐、西餐和小吃，满足学生和教职工的不同口味需求。",
                new LatLng(30.52738281920004, 114.35838337070848)
        ));
        infoDataMap_F1.put("信息学部4食堂", new InfoData("信息学部4食堂", R.drawable.shitang4,
                "信息学部4食堂以其丰富的菜品和舒适的用餐环境而闻名，拥有多个档口，供应各类中式和地方特色菜肴，深受师生喜爱。",
                new LatLng(30.528297740671487, 114.35935433037187)
        ));
        infoDataMap_F1.put("912操场", new InfoData("912操场", R.drawable.caochang912,
                "912操场是武汉大学的主要体育设施之一，配备有标准的跑道和足球场地，常用于体育课、课外活动和校内外的体育赛事。",
                new LatLng(30.538857990794174, 114.36617787010576)
        ));
        infoDataMap_F1.put("工学部二食堂", new InfoData("工学部二食堂", R.drawable.gongxuebushitang,
                "工学部二食堂位于工学部区域，提供品种繁多的餐饮服务，包括学生喜爱的家常菜、特色小吃和饮品，价格实惠，广受欢迎。",
                new LatLng(30.543127046251378, 114.36584527618791)
        ));
        infoDataMap_F1.put("枫园食堂", new InfoData("枫园食堂", R.drawable.fengyuanshitang,
                "枫园食堂坐落在枫园宿舍区附近，以其菜品多样、服务周到而闻名，设有多个窗口和座位区，方便学生就餐和交流。",
                new LatLng(30.537702911066408, 114.37204654343034)
        ));
        infoDataMap_F1.put("星湖园食堂", new InfoData("星湖园食堂", R.drawable.xinghuyuanshitang,
                "星湖园食堂位于星湖园景区旁，环境优美，提供丰富的餐饮选择，菜品新鲜可口，深受武汉大学师生的好评。",
                new LatLng(30.530044385009532, 114.36063106186296)
        ));
    }

    public static InfoData getInfoData(String title) {
        return infoDataMap_F1.get(title);
    }
}