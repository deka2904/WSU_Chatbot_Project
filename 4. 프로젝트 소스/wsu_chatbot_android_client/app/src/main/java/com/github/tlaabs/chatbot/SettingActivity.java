package com.github.tlaabs.chatbot;


import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    Switch switchTTS;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );


        switchTTS = (Switch)findViewById(R.id.switchTTS);

        // TTS 기능을 사전에 미리 설정을 하였다면, 그 설정대로 UI를 설정한다.
        Boolean IsTTS = PreferenceManager.getBoolean(SettingActivity.this, "IsTTS");
        if(IsTTS != null){
            switchTTS.setChecked(IsTTS);
        }

        // TTS 스위치 버튼 눌렀을 때, 휴대폰에 해당하는 설정으로 수정하여 저장한다.
        switchTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.setBoolean(SettingActivity.this, "IsTTS", switchTTS.isChecked());
            }
        });
    }

}