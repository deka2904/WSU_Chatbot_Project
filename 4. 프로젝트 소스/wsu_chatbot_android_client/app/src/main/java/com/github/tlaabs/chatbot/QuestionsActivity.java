package com.github.tlaabs.chatbot;

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

public class QuestionsActivity extends AppCompatActivity {

    TextView back;
    EditText phone, questions;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        phone = findViewById(R.id.phone);
        questions=findViewById(R.id.questions);

        submit = findViewById(R.id.submitbutton);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                if (phone.getText().toString().equals("")
                        || phone.getText().toString() == null) {
                    Toast.makeText(QuestionsActivity.this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (questions.getText().toString().equals("")
                        || questions.getText().toString() == null) {
                    Toast.makeText(QuestionsActivity.this, "질문을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String msg1 = phone.getText().toString();
                String msg2 = questions.getText().toString();

                ServerRequest.postRequest_question("QUESTIONS", msg1, msg2, new Callback() {
                    // 만약 에라가 일어난 경우
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                        // 현재 스레드가 UI 스레드가 아닐 경우,
                        // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                        QuestionsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                call.cancel();
                                try {
                                    ParsingAnswer_questions(null);
                                } catch (IOException | JSONException | NetworkOnMainThreadException
                                        | NullPointerException e) {
                                    // 만약
                                    if (PreferenceManager.getBoolean(QuestionsActivity.this, "IsTTS")) {
//                                                mTTS.speak("뭔가 이상한데", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        QuestionsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                    ParsingAnswer_questions(response);
                                } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                    e.getMessage();
                                }
                            }
                        });
                    }
                });

                // 입력창 초기화
                questions.setText("");

            }
        });
    }
    private void ParsingAnswer_questions(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {

            return;
        }
        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String answer = null;

        // JSON 데이터 파싱
        if (!jObject.isNull("answer")) {
            // Answer 부분의 String 데이터를 가져온다.
            answer = jObject.getString("answer");
        }
        if (!jObject.isNull("answer")) {
            //회원가입 실패시
            if (answer.contains("문의 실패")) {
                Toast.makeText(QuestionsActivity.this, "연결 실패.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (answer.contains("문의 성공")) {
                Toast.makeText(QuestionsActivity.this, "제출 완료.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}