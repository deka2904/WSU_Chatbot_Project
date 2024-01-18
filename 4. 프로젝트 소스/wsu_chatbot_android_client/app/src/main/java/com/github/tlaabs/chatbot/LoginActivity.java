package com.github.tlaabs.chatbot;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static Context context_main;

    TextView back;
    Button loginbutton, signupbutton;
    EditText editID, editPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        context_main = this;

        //회원가입,로그인 버튼
        signupbutton = findViewById(R.id.signupbutton);
        loginbutton = findViewById(R.id.loginbutton);
        editID = findViewById(R.id.editID);
        editPW = findViewById(R.id.editPassword);

        //회원가입 버튼 클릭시, 회원가입 페이지로 이동
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSignup();
            }
        });

        //로그인 버튼 클릭시
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                if (((LoginActivity)LoginActivity.context_main).editID.getText().toString().equals("")
                        || ((LoginActivity)LoginActivity.context_main).editID.getText().toString() == null) {
                    Toast.makeText(LoginActivity.this, "아이디 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (((LoginActivity)LoginActivity.context_main).editPW.getText().toString().equals("")
                        || ((LoginActivity)LoginActivity.context_main).editPW.getText().toString() == null) {
                    Toast.makeText(LoginActivity.this, "비밀번호 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg2 = editID.getText().toString();
                String msg3 = editPW.getText().toString();
                String msg4 = "1";
                ServerRequest.postRequest_login("LOGINOUT", msg2, msg3, msg4, new Callback() {

                    // 만약 에라가 일어난 경우
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                        // 현재 스레드가 UI 스레드가 아닐 경우,
                        // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                call.cancel();
                                try {
                                    ParsingAnswer_login(null);
                                } catch (IOException | JSONException | NetworkOnMainThreadException
                                        | NullPointerException e) {
                                    e.getMessage();
                                    // 만약
                                    if (PreferenceManager.getBoolean(LoginActivity.this, "IsTTS")) {
                                        //                                                mTTS.speak("뭔가 이상한데", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                    ParsingAnswer_login(response);
                                } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                    e.getMessage();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    public void ParsingAnswer_login(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {
        // 만약 서버로부터 온 대답이 null일 경우
        if(response == null) {
            if(PreferenceManager.getBoolean(LoginActivity.this, "IsTTS")){
            }
            return;
        }



        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String answer = null;

        // JSON 데이터 파싱
        if(!jObject.isNull("answer")) {
            // Answer 부분의 String 데이터를 가져온다.
            answer = jObject.getString("answer");
        }
        if (!jObject.isNull("answer")) {
            //회원가입 실패시
            if (answer.contains("로그인 성공")) {
                Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                ((ChattingActivity)ChattingActivity.context_main).BookmarkOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LoginOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LogoutOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).RecommendOn(answer);
                LoginActivity.this.finish();
            }
            else if (answer.contains("로그인 실패")) {
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                ((ChattingActivity)ChattingActivity.context_main).BookmarkOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LoginOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LogoutOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).RecommendOn(answer);

            }
            else if (answer.contains("로그인 상태입니다.")) {
                Toast.makeText(LoginActivity.this, "로그인 상태입니다.", Toast.LENGTH_SHORT).show();
                ((ChattingActivity)ChattingActivity.context_main).BookmarkOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LoginOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).LogoutOn(answer);
                ((ChattingActivity)ChattingActivity.context_main).RecommendOn(answer);
                LoginActivity.this.finish();

            }
        }

    }

    private void playSignup(){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}