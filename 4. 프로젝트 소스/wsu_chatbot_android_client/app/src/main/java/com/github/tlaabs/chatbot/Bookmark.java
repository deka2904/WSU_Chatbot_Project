package com.github.tlaabs.chatbot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Bookmark extends AppCompatActivity {

    public static Context context_main;

    private RecyclerView bookmarklistview;
    private BookmarkAdapter bookmarkadapter;
    private ArrayList<Book> bookArrayList;
    private LinearLayoutManager manager;

    ArrayList<String> arraylist_bookmark = ((ChatAdapter) ChatAdapter.context_main).arraylist_bookmark;

    private final String B_S_SUSI = "B_S_SUSI";
    private final String B_S_SCHEDULESANN = "B_S_SCHEDULESANN";
    private final String B_S_SUBANN = "B_S_SUBANN";
    private final String B_S_SYNANN = "B_S_SYNANN";
    private final String B_H_CURRICULUM = "B_H_CURRICULUM";
    private final String B_S_ACCEPTANCEANN = "B_S_ACCEPTANCEANN";
    private final String B_H_SCHOOLLIFE = "B_H_SCHOOLLIFE";
    private final String B_J_JUNGSI = "B_J_JUNGSI";
    private final String B_J_SCHEDULESANN = "B_J_SCHEDULESANN";
    private final String B_J_MODELANN = "B_J_MODELANN";
    private final String B_J_ACCEPTANCE = "B_J_ACCEPTANCE";

    String test1 = ((ChattingActivity) ChattingActivity.context_main).ID;
    String test2 = "";
    String test4 = "select";
    String test5 = "delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        context_main = this;

        bookmarklistview = (RecyclerView) findViewById(R.id.bookmarkview);

        CreateBookRecyclerview();

        //북마크 리스트뷰에 아답터를 연결한다.
        bookmarklistview.setAdapter(bookmarkadapter);

        //북마크 deletebtn 클릭시
        bookmarkadapter.setOnItemClickListener1(new BookmarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (bookArrayList.get(position).getNer()) {

                    case B_S_SUSI:
                        test2 = "수시전형 안내하기";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_S_SCHEDULESANN:
                        test2 = "수시 전형일정 및 모집 인원 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_S_SUBANN:
                        test2 = "학생부 교과전형 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_S_SYNANN:
                        test2 = "학생부 종합전형 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_H_CURRICULUM:
                        test2 = "전공별 교육 과정";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_S_ACCEPTANCEANN:
                        test2 = "수시 합격자 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_H_SCHOOLLIFE:
                        test2 = "학교생활 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_J_JUNGSI:
                        test2 = "정시전형 안내하기";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_J_SCHEDULESANN:
                        test2 = "정시 전형일정 및 모집인원 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_J_MODELANN:
                        test2 = "정시 전형유형별 지원안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;

                    case B_J_ACCEPTANCE:
                        test2 = "정시 합격자 안내";
                        BookmarkServerDelete();
                        finish();
                        try{
                            Thread.sleep(100);
                            intenthome();
                        }
                        catch (Exception e){}
                        break;
                }
            }
        });

        bookmarkadapter.setOnItemClickListener(new BookmarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (bookArrayList.get(position).getNer()) {
                    case B_S_SUSI:
                        finish();
                        test2 = "수시전형 안내하기";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "수시전형 안내하기", Toast.LENGTH_SHORT);
                        break;
                    case B_S_SCHEDULESANN:
                        finish();
                        test2 = "수시 전형일정 및 모집 인원 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "수시 전형일정 및 모집 인원 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_S_SUBANN:
                        finish();
                        test2 = "학생부 교과전형 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "학생부 교과전형 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_S_SYNANN:
                        finish();
                        test2 = "학생부 종합전형 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "학생부 종합전형 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_H_CURRICULUM:
                        finish();
                        test2 = "전공별 교육 과정";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "전공별 교육 과정", Toast.LENGTH_SHORT);
                        break;
                    case B_S_ACCEPTANCEANN:
                        finish();
                        test2 = "수시 합격자 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "수시 합격자 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_H_SCHOOLLIFE:
                        finish();
                        test2 = "학교생활 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "학교생활 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_J_JUNGSI:
                        finish();
                        test2 = "정시전형 안내하기";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "정시전형 안내하기", Toast.LENGTH_SHORT);
                        break;
                    case B_J_SCHEDULESANN:
                        finish();
                        test2 = "정시 전형일정 및 모집인원 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "정시 전형일정 및 모집인원 안내", Toast.LENGTH_SHORT);
                        break;
                    case B_J_MODELANN:
                        finish();
                        test2 = "정시 전형유형별 지원안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "정시 전형유형별 지원안내", Toast.LENGTH_SHORT);
                        break;
                    case B_J_ACCEPTANCE:
                        finish();
                        test2 = "정시 합격자 안내";
                        BookmarkServerSelect();
                        Toast.makeText(Bookmark.this, "정시 합격자 안내", Toast.LENGTH_SHORT);
                        break;
                }
            }
        });
    }


    // 리사이클러뷰 초기화하는 함수. 처음 액티비티 실행때만 실행되는 것이다.
    private void CreateBookRecyclerview() {
        //북마크 리스트를 생성한다.
        bookArrayList = new ArrayList<>();

        //북마크 리스트에 연동될 아답터를 생성한다.
        bookmarkadapter = new BookmarkAdapter(bookArrayList, Bookmark.this);

        manager = new LinearLayoutManager(Bookmark.this);

        bookmarklistview.setLayoutManager(manager);

        bookmarklistview.setAdapter(bookmarkadapter);

        bookmarklistview.setItemViewCacheSize(100);
    }

    public void BookmarkServerSelect() {
        ServerRequest.postRequest_BookmarkSelect("BOOKMARK", test1, test2, test4, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                Bookmark.this.runOnUiThread(new Runnable() {
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
                Bookmark.this.runOnUiThread(new Runnable() {
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

    public void BookmarkServerDelete() {
        ServerRequest.postRequest_BookmarkDelete("BOOKMARK", test1, test2, test5, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                Bookmark.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        try {
                            ParsingAnswer_bookmarkdelete(null);
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
                Bookmark.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                            ParsingAnswer_bookmarkdelete(response);
                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }
        });
    }

    public void ParsingAnswer_bookmarksearch(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            if (PreferenceManager.getBoolean(Bookmark.this, "IsTTS")) {
                Toast.makeText(Bookmark.this, "다시 한번 눌러주세요.", Toast.LENGTH_SHORT).show();
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
            if (answer.contains("북마크 불러오기 성공") && query.contains("수시전형 안내하기")) {
                Book book = new Book(B_S_SUSI, "수시전형 안내하기");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);

            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("수시 전형일정 및 모집 인원 안내")) {
                Book book = new Book(B_S_SCHEDULESANN, "수시 전형일정 및 모집 인원 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("학생부 교과전형 안내")) {
                Book book = new Book(B_S_SUBANN, "학생부 교과전형 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("학생부 종합전형 안내")) {
                Book book = new Book(B_S_SYNANN, "학생부 종합전형 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("전공별 교육 과정")) {
                Book book = new Book(B_H_CURRICULUM, "전공별 교육 과정");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("수시 합격자 안내")) {
                Book book = new Book(B_S_ACCEPTANCEANN, "수시 합격자 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("학교생활 안내")) {
                Book book = new Book(B_H_SCHOOLLIFE, "학교생활 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("정시전형 안내하기")) {
                Book book = new Book(B_J_JUNGSI, "정시전형 안내하기");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("정시 전형일정 및 모집인원 안내")) {
                Book book = new Book(B_J_SCHEDULESANN, "정시 전형일정 및 모집인원 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("정시 전형유형별 지원안내")) {
                Book book = new Book(B_J_MODELANN, "정시 전형유형별 지원안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }

            if (answer.contains("북마크 불러오기 성공") && query.contains("정시 합격자 안내")) {
                Book book = new Book(B_J_ACCEPTANCE, "정시 합격자 안내");
                bookArrayList.add(book);
                bookmarkadapter.notifyItemInserted(bookArrayList.size() - 1);
            }
            else if (answer.contains("북마크 불러오기 실패")) {
                Toast.makeText(Bookmark.this, "북마크 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ParsingAnswer_bookmarkdelete(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            if (PreferenceManager.getBoolean(Bookmark.this, "IsTTS")) {
                Toast.makeText(Bookmark.this, "다시 한번 눌러주세요.", Toast.LENGTH_SHORT).show();
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
            if (answer.contains("북마크 삭제 성공") && query.contains("수시전형 안내하기")) {
                Book book = new Book(B_S_SUSI, "수시전형 안내하기");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("수시 전형일정 및 모집 인원 안내")) {
                Book book = new Book(B_S_SCHEDULESANN, "수시 전형일정 및 모집 인원 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("학생부 교과전형 안내")) {
                Book book = new Book(B_S_SUBANN, "학생부 교과전형 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("학생부 종합전형 안내")) {
                Book book = new Book(B_S_SYNANN, "학생부 종합전형 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("전공별 교육 과정")) {
                Book book = new Book(B_H_CURRICULUM, "전공별 교육 과정");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("수시 합격자 안내")) {
                Book book = new Book(B_S_ACCEPTANCEANN, "수시 합격자 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("학교생활 안내")) {
                Book book = new Book(B_H_SCHOOLLIFE, "학교생활 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("정시전형 안내하기")) {
                Book book = new Book(B_J_JUNGSI, "정시전형 안내하기");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("정시 전형일정 및 모집인원 안내")) {
                Book book = new Book(B_J_SCHEDULESANN, "정시 전형일정 및 모집인원 안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("정시 전형유형별 지원안내")) {
                Book book = new Book(B_J_MODELANN, "정시 전형유형별 지원안내");
                bookArrayList.remove(book);
            }

            if (answer.contains("북마크 삭제 성공") && query.contains("정시 합격자 안내")) {
                Book book = new Book(B_J_ACCEPTANCE, "정시 합격자 안내");
                bookArrayList.remove(book);
            }

            else if (answer.contains("북마크 삭제 실패")) {
                Toast.makeText(Bookmark.this, "북마크 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void intenthome() {
        setContentView(R.layout.activity_bookmark);
        Intent intent5 = new Intent(Bookmark.this, Bookmark.class);
        startActivity(intent5);
        String msg6 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();
        String ID = msg6;

        String msg5 = ID;
        ServerRequest.postRequest_BookmarkSearch("BOOKMARK_SEARCH", msg5, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                Bookmark.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        try {
                            ((Bookmark)Bookmark.context_main).ParsingAnswer_bookmarksearch(null);
                        } catch (IOException | JSONException | NetworkOnMainThreadException
                                | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Bookmark.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                            ((Bookmark)Bookmark.context_main).ParsingAnswer_bookmarksearch(response);
                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }
        });
    }
}
