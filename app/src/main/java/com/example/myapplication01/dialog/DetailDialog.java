package com.example.myapplication01.dialog;

import static com.example.myapplication01.util.MapUtil.convertToLatLonPoint;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.services.core.LatLonPoint;
import com.example.myapplication01.R;
import com.example.myapplication01.RouteActivity;
import com.example.myapplication01.data.AttractionInfoDataManager;
import com.example.myapplication01.data.FacilityInfoDataManager;
import com.example.myapplication01.data.FoodInfoDataManager;
import com.example.myapplication01.data.InfoData;

public class DetailDialog extends Dialog {
    private Context context;
    private String title;
    private InfoData infoData;
    private AdapterView.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public DetailDialog(@NonNull Context context, int themeResId,String title,int pageNum) {
        super(context, themeResId);
        this.title = title;
        if(pageNum==0) {
            this.infoData = AttractionInfoDataManager.getInfoData(title);
        }
        else if (pageNum==1) {
            this.infoData = FacilityInfoDataManager.getInfoData(title);
        }
        else if (pageNum==2) {
            this.infoData = FoodInfoDataManager.getInfoData(title);
        }
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);
        TextView titleTextView = findViewById(R.id.dialog_title);
        ImageView imageView = findViewById(R.id.dialog_image);
        TextView descriptionTextView = findViewById(R.id.dialog_text);
        Button button=findViewById(R.id.dialog_button);

        if (infoData != null) {
            titleTextView.setText(infoData.getTitle());
            imageView.setImageResource(infoData.getImageResId());
            descriptionTextView.setText(infoData.getDescription());
        }
        else{
            titleTextView.setText("未检索到信息");
            imageView.setImageResource(R.drawable.radiopage_icon);
            descriptionTextView.setText("缺少必要信息。。。");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RouteActivity.class);
                LatLonPoint latLonPoint = convertToLatLonPoint(infoData.getPosition()); // 示例坐标
                intent.putExtra("end_point", latLonPoint);
                context.startActivity(intent);
            }
        });
    }
}