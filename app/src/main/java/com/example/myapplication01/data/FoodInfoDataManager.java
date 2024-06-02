package com.example.myapplication01.data;

import com.amap.api.maps2d.model.LatLng;
import com.example.myapplication01.R;
import com.example.myapplication01.data.InfoData;

import java.util.HashMap;

public class FoodInfoDataManager {
    private static HashMap<String, InfoData> infoDataMap_F2 = new HashMap<>();

    static {
        // 添加数据
        infoDataMap_F2.put("麻辣香锅", new InfoData("麻辣香锅", R.drawable.malaxiangguo,
                "麻辣香锅是一道川菜，以麻辣味为主，将各种食材如蔬菜、肉类、海鲜等与香辣调料一起炒制。口感丰富，辣而不燥，是现代年轻人喜爱的美食之一。",
                new LatLng(30.527378198363813, 114.35838337070848)
        ));
        infoDataMap_F2.put("小龙虾", new InfoData("小龙虾", R.drawable.xiaolongxia,
                "小龙虾是夏季夜宵的热门选择，通常以麻辣、蒜香、十三香等多种口味烹制。小龙虾肉质鲜嫩，味道浓郁，常见于中国南方夜市和餐馆。",
                new LatLng(30.528306982259533, 114.35935433037187)
        ));
        infoDataMap_F2.put("肉夹馍", new InfoData("肉夹馍", R.drawable.roujiamo,
                "肉夹馍是陕西省传统小吃，被誉为“中国汉堡”。将腊汁肉夹在白吉馍中，外脆内嫩，肉香四溢，吃起来非常美味，是古城西安的代表性美食。",
                new LatLng(30.540010746674113, 114.35735340244676)
        ));
        infoDataMap_F2.put("冰镇西瓜", new InfoData("冰镇西瓜", R.drawable.bingzhenxigua,
                "冰镇西瓜是夏季消暑的最佳选择，将新鲜西瓜切块后冰镇，口感清凉爽口，甜而不腻，不仅能解渴，还能帮助身体降温，是夏日家庭和聚会的常见水果。",
                new LatLng(30.530871488721736, 114.37145645744707)
        ));
        infoDataMap_F2.put("香茵波克汉堡", new InfoData("香茵波克汉堡", R.drawable.xiangyinboke,
                "香茵波克汉堡是一款美味的汉堡，通常夹有鲜嫩多汁的牛肉饼、香浓的芝士、爽脆的生菜和丰富的酱料。口感层次丰富，是快餐店和家庭餐桌上的人气食品。",
                new LatLng(30.532539536755518, 114.36493332512285)
        ));
    }

    public static InfoData getInfoData(String title) {
        return infoDataMap_F2.get(title);
    }
}