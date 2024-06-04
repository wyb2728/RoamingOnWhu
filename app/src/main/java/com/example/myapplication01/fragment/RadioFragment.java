package com.example.myapplication01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myapplication01.R;
import com.example.myapplication01.RoomActivity;

public class RadioFragment extends Fragment {

    private EditText editTextId, editTextKey;
    private Button buttonJoin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio, container, false);

        editTextId = view.findViewById(R.id.et_radio1);
        editTextKey = view.findViewById(R.id.et_radio2);
        buttonJoin = view.findViewById(R.id.btn_radio);

        buttonJoin.setOnClickListener(v -> {
            String id = editTextId.getText().toString();
            String key = editTextKey.getText().toString();
            Intent intent = new Intent(getActivity(), RoomActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("key", key);
            startActivity(intent);
        });

        return view;
    }
}
