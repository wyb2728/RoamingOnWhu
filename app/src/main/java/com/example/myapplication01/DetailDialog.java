package com.example.myapplication01;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

public class DetailDialog extends Dialog {
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public DetailDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);
    }
}