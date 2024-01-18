package com.github.tlaabs.chatbot;

import android.content.Context;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Recommendation extends AppCompatActivity {

    public static Context context_main;

    private RecyclerView recommendlistview;
    private RecommendAdapter recommendadapter;
    private ArrayList<Recommend> recommendArrayList;
    private LinearLayoutManager manager;

    private final String RECOMMENDVIEWHOLDER1 = "RecommendVIewHolder1";

    String test2 = "";
    String test4;
    String[] ja;
    String jam;
    String arraytest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        context_main = this;

        recommendlistview = (RecyclerView) findViewById(R.id.recommendview);

        CreateRecommendRecyclerview();

        recommendlistview.setAdapter(recommendadapter);

        recommendadapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                test2 = arraytest;
                jam = ja[position+1].replaceAll("\"", "");
                String name = jam;
                test2=name;
                Toast.makeText(Recommendation.this, test2, Toast.LENGTH_SHORT).show();
                RecommendServerSelect();
                finish();

            }
        });
    }

    // 리사이클러뷰 초기화하는 함수. 처음 액티비티 실행때만 실행되는 것이다.
    private void CreateRecommendRecyclerview() {
        //북마크 리스트를 생성한다.
        recommendArrayList = new ArrayList<>();

        //북마크 리스트에 연동될 아답터를 생성한다.
        recommendadapter = new RecommendAdapter(recommendArrayList, Recommendation.this);

        manager = new LinearLayoutManager(Recommendation.this);

        recommendlistview.setLayoutManager(manager);

        recommendlistview.setAdapter(recommendadapter);

        recommendlistview.setItemViewCacheSize(100);
    }


    public void RecommendServerSelect() {
        ServerRequest.postRequest_RecommendSelect("TEST", test2, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                Recommendation.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        try {
                            ((ChattingActivity) ChattingActivity.context_main).ParsingAnswer(null);
                        } catch (IOException | JSONException | NetworkOnMainThreadException
                                | NullPointerException e) {
                           e.getMessage();
                        }
                    }
                });
            }

            // 만약 서버로부터 정상적으로 대답을 받은 경우
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Recommendation.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                            ((ChattingActivity) ChattingActivity.context_main).ParsingAnswer(response);
                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                           e.getMessage();
                        }
                    }
                });
            }
        });
    }


    public void ParsingAnswer_recommendsearch(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            if (PreferenceManager.getBoolean(Recommendation.this, "IsTTS")) {
                Toast.makeText(Recommendation.this, "다시 한번 눌러주세요.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String query = null;
        String answer = null;

        // JSON 데이터 파싱
        if (!jObject.isNull("query")) {
            // Answer 부분의 query 데이터를 가져온다.
            query = jObject.getString("query");
        }
        if (!jObject.isNull("answer")) {
            // Answer 부분의 answer 데이터를 가져온다.
            answer = jObject.getString("answer");
        }

        if (!jObject.isNull("answer")) {
            if (answer.contains("추천질문을 불러옵니다")) {

                ja = query.split("\\[|,|\\]");

                for(int i = 0; i<ja.length; i++)
                {
                    test4 = ja[i].replaceAll("\"", "");
                    recommendArrayList.add(new Recommend(RECOMMENDVIEWHOLDER1, test4));
                }
                recommendArrayList.remove(0);
                recommendadapter.notifyItemInserted(recommendArrayList.size() -1);
            }
            if(answer.contains("북마크 먼저 등록해 주세요.")){
                Toast.makeText(Recommendation.this, "북마크 먼저 등록해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
