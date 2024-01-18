package com.github.tlaabs.chatbot;


import android.content.Context;
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

public class SignupActivity extends AppCompatActivity {

    public static Context context_main;

    TextView back;
    EditText id;
    EditText pw;
    EditText pw2;
    Button sign, pwcheck, idcheck;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        //기입 항목
        id = findViewById(R.id.signID);
        pw = findViewById(R.id.signPW);
        pw2 = findViewById(R.id.signPW2);

        sign = findViewById(R.id.signupbutton);

        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                if (pw.getText().toString().equals(pw2.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                    sign.setEnabled(true);
                } else {
                    Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //id 중복체크
        idcheck = findViewById(R.id.idcheck);
        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                if (id.getText().toString().equals("")
                        || id.getText().toString() == null) {
                    Toast.makeText(SignupActivity.this, "아이디 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String message1 = id.getText().toString();
                ServerRequest.postRequest_idcheck("CHECKID", message1, new Callback() {
                    // 만약 에라가 일어난 경우
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                        // 현재 스레드가 UI 스레드가 아닐 경우,
                        // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                call.cancel();
                                try {
                                    ParsingAnswer_idcheck(null);
                                } catch (IOException | JSONException | NetworkOnMainThreadException
                                        | NullPointerException e) {
                                    e.getMessage();
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                    ParsingAnswer_idcheck(response);
                                } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                    if (PreferenceManager.getBoolean(SignupActivity.this, "IsTTS")) {
                                        e.getMessage();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });

        //회원가입완료 버튼 눌렀을때
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                if (id.getText().toString().equals("")
                        || id.getText().toString() == null) {
                    Toast.makeText(SignupActivity.this, "아이디 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw.getText().toString().equals("")
                        || pw.getText().toString() == null) {
                    Toast.makeText(SignupActivity.this, "비밀번호 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String msg1 = id.getText().toString();
                String msg2 = pw.getText().toString();

                ServerRequest.postRequest_signup("MAKE_JOIN_MEMBERSHIP", msg1, msg2, new Callback() {
                    // 만약 에라가 일어난 경우
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                        // 현재 스레드가 UI 스레드가 아닐 경우,
                        // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                call.cancel();
                                try {
                                    ParsingAnswer_signup(null);
                                } catch (IOException | JSONException | NetworkOnMainThreadException
                                        | NullPointerException e) {
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                    ParsingAnswer_signup(response);
                                } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                    e.getMessage();
                                }
                            }
                        });
                    }
                });

                // 입력창 초기화
                id.setText("");
                pw.setText("");

                SignupActivity.this.finish();
            }
        });
    }

    private void ParsingAnswer_signup(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
            // 만약 사용자가 설정창에서 TTS기능을 켜놓았을 경우에만 text를 말하게 한다.

            return;
        }
        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String answer = null;

        // JSON 데이터 파싱
        if (!jObject.isNull("Answer")) {
            // Answer 부분의 String 데이터를 가져온다.
            answer = jObject.getString("ANSWER");
        }
        if (!jObject.isNull("Answer")) {
            //회원가입 실패시
            if (answer.contains("존재하는 회원입니다.")) {
                Toast.makeText(SignupActivity.this, "이미 존재하는 회원입니다.", Toast.LENGTH_SHORT).show();
                SignupActivity.this.finish();
            }
            else if (answer.contains("회원가입 성공")) {
                Toast.makeText(SignupActivity.this, "이미 존재하는 회원입니다.", Toast.LENGTH_SHORT).show();
                SignupActivity.this.finish();
            }
        }
    }

    private void ParsingAnswer_idcheck(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            return;
        }

        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String Message = null;

        // JSON 데이터 파싱
        if (!jObject.isNull("answer")) {
            // Answer 부분의 String 데이터를 가져온다.
            Message = jObject.getString("answer");
        }
        if (!jObject.isNull("answer")) {
            //id 중복됐을 때
            if (Message.contains("존재하는 아이디입니다.")) {
                Toast.makeText(SignupActivity.this, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (Message.contains("회원가입 가능한 아이디입니다.")) {
                Toast.makeText(SignupActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                pwcheck.setEnabled(true);
                return;
            }
        }
    }
}

