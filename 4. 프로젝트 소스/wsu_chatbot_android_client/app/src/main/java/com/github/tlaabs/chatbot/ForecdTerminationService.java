package com.github.tlaabs.chatbot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
public class ForecdTerminationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분

        try {
            ((ChattingActivity)ChattingActivity.mContext).Logout();
            Toast.makeText(this, "로그아웃 ", Toast.LENGTH_SHORT).show();
            Thread.sleep(2000);

            stopSelf(); //서비스 종료
        }
        catch (Exception e)
        {
        }
    }
}
