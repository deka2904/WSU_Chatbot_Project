package com.github.tlaabs.chatbot;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

// 채팅 화면에 대한 자바소스코드. + 서버 연결 코드.
public class ChattingActivity extends AppCompatActivity {

    public static Context context_main;


    //키 값 구하기
    private void getHashKey(Context context){
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for(int i = 0; i < packageInfo.signatures.length; i++){
                Signature signature = packageInfo.signatures[i];
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("<클래스명>","keyhash="+ Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }

    // 리사이클러뷰에 필요한 변수들
    private ArrayList<Chat> chatArrayList;
    private ArrayList<String> arraylist_bookmark;
    private ChatAdapter adapter;
    private LinearLayoutManager manager;
    private RecyclerView chatView;
    private ArrayList<String> arraylist_query;

    // 뷰홀더 타입별로 생성
    private final String USER_KEY = "user";
    private final String BOTTEXT_KEY = "bottext";
    private final String BOTSTART_KEY = "botstart";
    private final String BOTBUTTON_KEY = "botbutton";
    private final String BOTWEB_KEY = "botweb";
    private final String BTN_MAPKAKAO_KEY = "btn_mapkakao";
    private final String BTN_1_KEY = "btn_1";
    private final String BTN_2_KEY = "btn_2";
    private final String BTN_3_KEY = "btn_3";
    private final String BTN_4_KEY = "btn_4";
    private final String BTN_5_KEY = "btn_5";
    private final String BTN_8_KEY = "btn_8";
    private final String BTN_9_KEY = "btn_9";
    private final String BTN_11_KEY = "btn_11";
    private final String BTN_12_KEY = "btn_12";
    private final String BTN_13_KEY = "btn_13";
    private final String BTN_16_KEY = "btn_16";
    private final String BTN_17_KEY = "btn_17";
    private final String BTN_18_KEY = "btn_18";
    private TextToSpeech mTTS;
    public static Context mContext;

    EditText editMessage;
    ImageButton btnsend, imageButton2;
    FloatingActionButton speak;
    Toolbar toolbar;
    DrawerLayout navigationDrawer, drawer;
    NavigationView navigationView;

    Switch switchTTS;

    String ID = "";
    String test1 = "";
    String test2 = "";
    String test3 = "save";

    String msg1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startService(new Intent(this, ForecdTerminationService.class));
        setContentView(R.layout.activity_chatting);
        getHashKey(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mContext = this;
        context_main = this;

        AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.editMessage);

        edit.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items));

        // 최초 실행 여부를 판단 ->>>
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", false);

        // false일 경우 최초 실행
        if(!checkFirst){
            // 앱 최초 실행시 하고 싶은 작업
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("checkFirst",true);
            editor.apply();

            finish();

            Intent intent99 = new Intent(ChattingActivity.this, ChattingActivity.class);
            startActivity(intent99);
            Intent intent = new Intent(ChattingActivity.this, TutorialActivity.class);
            startActivity(intent);
        }

        // 접근 허용 확인
        if(!TedPermission.isGranted(ChattingActivity.this, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION)) { // 2022-01-19 황우진 --> 위치 액세스 삭제
            //퍼미션 체크 추가
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    // 접근 허용할시 실행 코드
                }

                @Override
                public void onPermissionDenied(List<String> list) {
                    // 접근 거부시 실행할 코드
                }
            };
            TedPermission.with(this)
                    .setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        }

        editMessage =(EditText)findViewById(R.id.editMessage);
        btnsend = (ImageButton) findViewById(R.id.btnSend);
        speak = (FloatingActionButton)findViewById(R.id.speak);
        chatView = (RecyclerView)findViewById(R.id.chatView);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        navigationDrawer = (DrawerLayout)findViewById(R.id.navigationDrawer);
        drawer = (DrawerLayout)findViewById(R.id.drawer);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        switchTTS = (Switch)findViewById(R.id.switchTTS);


        //Toolbar 드로어 이벤트 처리 & 햄버거버튼 클릭 시 서버에 저장요청
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationDrawer.openDrawer(Gravity.LEFT);

                try {
                    //ChatAdapter msg1값 arraylist_bookmark에 담아서 포함된 값대로 서버에 북마크 save 요청
                    if (arraylist_bookmark.contains("starbtn1 클릭됨")) {
                        test2 = "수시전형 안내하기";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);
                    }
                    if (arraylist_bookmark.contains("starbtn2 클릭됨")) {
                        test2 = "수시 전형일정 및 모집 인원 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);
                    }
                    if (arraylist_bookmark.contains("starbtn3 클릭됨")) {
                        test2 = "학생부 교과전형 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn4 클릭됨")) {
                        test2 = "학생부 종합전형 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);
                    }
                    if (arraylist_bookmark.contains("starbtn5 클릭됨")) {
                        test2 = "전공별 교육 과정";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);
                    }
                    if (arraylist_bookmark.contains("starbtn8 클릭됨")) {
                        test2 = "수시 합격자 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn9 클릭됨")) {
                        test2 = "학교생활 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn11 클릭됨")) {
                        test2 = "정시전형 안내하기";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn12 클릭됨")) {
                        test2 = "정시 전형일정 및 모집인원 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn13 클릭됨")) {
                        test2 = "정시 전형유형별 지원안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);

                    }
                    if (arraylist_bookmark.contains("starbtn16 클릭됨")) {
                        test2 = "정시 합격자 안내";
                        arraylist_query.add(test2);
                        BookmarkServerCont();
                        Thread.sleep(100);
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }

                arraylist_query.clear();
                arraylist_bookmark.clear();

            }
        });


//        네비게이션 드로어 아이템 이벤트 처리
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false);
                navigationDrawer.closeDrawers();

                int id = item.getItemId();

                switch(id) {

                    //추천질문
                    case R.id.nav_recommend:
                        Intent intent6 = new Intent(ChattingActivity.this, Recommendation.class);
                        startActivity(intent6);
                        String msg8 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();
                        ID = msg8;

                        String msg7 = ID;
                        ServerRequest.postRequest_RecommendSearch("RECOMMEND_QUESTION", msg7, new Callback() {
                            // 만약 에라가 일어난 경우
                            @Override
                            public void onFailure(Call call, IOException e) {
                                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                                // 현재 스레드가 UI 스레드가 아닐 경우,
                                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                                ChattingActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        call.cancel();
                                        try {
                                            ((Recommendation)Recommendation.context_main).ParsingAnswer_recommendsearch(null);
                                        } catch (IOException | JSONException | NetworkOnMainThreadException
                                                | NullPointerException e) {
                                            e.getMessage();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ChattingActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                            ((Recommendation)Recommendation.context_main).ParsingAnswer_recommendsearch(response);
                                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                            e.getMessage();
                                        }
                                    }
                                });
                            }
                        });
                        break;

                    //북마크
                    case R.id.nav_bookmark:
                        Intent intent5 = new Intent(ChattingActivity.this, Bookmark.class);
                        startActivity(intent5);

                        String msg6 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();
                        ID = msg6;
                        String msg5 = ID;
                        ServerRequest.postRequest_BookmarkSearch("BOOKMARK_SEARCH", msg5, new Callback() {
                            // 만약 에라가 일어난 경우
                            @Override
                            public void onFailure(Call call, IOException e) {
                                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                                // 현재 스레드가 UI 스레드가 아닐 경우,
                                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                                ChattingActivity.this.runOnUiThread(new Runnable() {
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
                                ChattingActivity.this.runOnUiThread(new Runnable() {
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
                        break;

                        //튜토리얼
                    case R.id.nav_tutorial:
                        Intent intent = new Intent(ChattingActivity.this, TutorialActivity.class);
                        startActivity(intent);
                        break;

                        //카카오 맵
                    case R.id.nav_map:
                        Intent intent1 = new Intent(ChattingActivity.this, MapActivity.class);
                        startActivity(intent1);
                        break;

                        //전공별 홍보 영상
                    case R.id.nav_video:
                        Intent intent3 = new Intent(ChattingActivity.this, Videomain.class);
                        startActivity(intent3);
                        break;

                        //설정
                    case R.id.nav_setting:
                        playSetting();
                        break;

                    //문의사항
                    case R.id.nav_questions:
                        Intent intent4 = new Intent(ChattingActivity.this, QuestionsActivity.class);
                        startActivity(intent4);
                        break;

                        //로그인
                    case R.id.nav_login:
                        Intent intent2 = new Intent(ChattingActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_logout:
                        try {
                            String msg1 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();

                            String msg2 = ((LoginActivity) LoginActivity.context_main).editPW.getText().toString();
                            String msg4 = "0";
                            ServerRequest.postRequest_logout("LOGINOUT", msg1, msg2, msg4, new Callback() {
                                // 만약 에라가 일어난 경우
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                                    // 현재 스레드가 UI 스레드가 아닐 경우,
                                    // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                                    ChattingActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            call.cancel();
                                            try {
                                                ParsingAnswer_logout(null);
                                            } catch (IOException | JSONException | NetworkOnMainThreadException
                                                    | NullPointerException e) {
                                                e.getMessage();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    ChattingActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                                ParsingAnswer_logout(response);
                                            } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                                e.getMessage();
                                            }
                                        }
                                    });
                                }
                            });
                            break;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                }

                return false;
            }
        });

        // TTS
        mTTS = new TextToSpeech(ChattingActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TTS 언어 설정
                    int result = mTTS.setLanguage(Locale.KOREAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                }
                else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        // 리사이클러뷰 만드는 함수 (길어서 따로 분리..)
        CreateRecyclerview();

        StartChat();


        // 마이크 버튼 눌렀을때
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // 마이크에 관한 권한 승인 여부를 확인한다.
                if(!TedPermission.isGranted(ChattingActivity.this,
                        Manifest.permission.RECORD_AUDIO)) {
                    // 권한 승인 여부에 관한 리스너 함수이다.
                    PermissionListener permissionlistener = new PermissionListener() {
                        // 권한 승인을 하였을 경우는 정상적으로 음성인식을 한다.
                        @Override
                        public void onPermissionGranted() {
                            Snackbar.make(view, "음성인식", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            VoiceTask voiceTask = new VoiceTask();
                            voiceTask.execute();
                        }

                        // 권한 승인을 하지 않았을 경우에는 아무런 행동을 하지 않는다.
                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {

                        }
                    };

                    TedPermission.with(ChattingActivity.this)
                            .setPermissionListener(permissionlistener) // 리스너 등록
                            // 권한 승인을 하지 않았을 경우의 뜨는 다이얼로그의 메세지를 다음과 같이 설정한다.
                            .setDeniedMessage("접근 거부하셨습니다." +
                                    "\n[설정] - [권한]에서 권한을 허용해주세요.")
                            // 어떤 퍼미션을 확인할 것인지 설정한다.
                            .setPermissions(Manifest.permission.RECORD_AUDIO)
                            .check();
                }
                // 만약 이미 권한 승인을 하였을 경우, 아래와 같은 구문을 실행한다.
                else {
                    // Snackbar 를 일시적으로 만든다.
                    Snackbar.make(view, "음성인식", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    // 음성인식을 하는 객체를 새로 생성한다.
                    VoiceTask voiceTask = new VoiceTask();
                    // 음성인식을 동기로 실행한다.
                    voiceTask.execute();
                }
            }
        });

        adapter.setOnItemClickListener2(new ChatAdapter.OnItemClickListener2() {
            @Override
            public void onItemClick(View v, int position) {
                switch(chatArrayList.get(position).getWho()) {

                    case BTN_1_KEY:
                        Log.d(msg1, "starbtn1클릭");
                        msg1 = "starbtn1 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_2_KEY:
                        Log.d(msg1, "starbtn2클릭");
                        msg1 = "starbtn2 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_3_KEY:
                        Log.d(msg1, "starbtn3클릭");
                        msg1 = "starbtn3 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_4_KEY:
                        Log.d(msg1, "starbtn4클릭");
                        msg1 = "starbtn4 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_5_KEY:
                        Log.d(msg1, "starbtn5클릭");
                        msg1 = "starbtn5 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_8_KEY:
                        Log.d(msg1, "starbtn8클릭");
                        msg1 = "starbtn8 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_9_KEY:
                        Log.d(msg1, "starbtn9클릭");
                        msg1 = "starbtn9 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_11_KEY:
                        Log.d(msg1, "starbtn11클릭");
                        msg1 = "starbtn11 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_12_KEY:
                        Log.d(msg1, "starbtn12클릭");
                        msg1 = "starbtn12 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_13_KEY:
                        Log.d(msg1, "starbtn13클릭");
                        msg1 = "starbtn13 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case BTN_16_KEY:
                        Log.d(msg1, "starbtn16클릭");
                        msg1 = "starbtn16 클릭됨";
                        arraylist_bookmark.add(msg1);
                        Toast.makeText(ChattingActivity.this, "북마크에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        // 보내기 버튼 눌렀을때
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // 만약 editMessage 에 아무런 문자열도 들어가있지 않으면 null값을 리턴한다.
                    if(editMessage.getText().toString().equals("")
                            || editMessage.getText().toString() == null) {
                        return;
                    }

                    // 유저가 보낸 메세지를 ArrayList 에 넣는다.
                    String message = editMessage.getText().toString();
                    Chat chat = new Chat(USER_KEY, message);
                    chatArrayList.add(chat);
                    // 해당하는 위치에 아이템 추가 업데이트 한다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);

                    // 만약 유저가 보낸 메세지에 설정 단어가 포함되어 있으면 안드로이드 내부에서 자체적으로 처리한다.
                    if(message.contains("설정")) {
                        playSetting();
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        chatArrayList.add(new Chat(BOTTEXT_KEY, "자동으로 설정으로 넘어갑니다"));
                        // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        // 만약 사용자가 설정창에서 TTS기능을 켜놓았을 경우에만 text를 말하게 한다.
                        mTTS.speak("자동으로 설정으로 넘어갑니다", TextToSpeech.QUEUE_FLUSH, null);

                        editMessage.setText("");
                        chatView.scrollToPosition(chatArrayList.size()-1);
                        return;
                    }

                    if(message.contains("카카오 캠퍼스맵 바로가기")) {
                        kakaomap();
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        chatArrayList.add(new Chat(BOTTEXT_KEY, "자동으로 카카오 맵으로 넘어갑니다"));
                        // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        // 만약 사용자가 설정창에서 TTS기능을 켜놓았을 경우에만 text를 말하게 한다.
                        mTTS.speak("자동으로 카카오 맵으로 넘어갑니다", TextToSpeech.QUEUE_FLUSH, null);

                        editMessage.setText("");
                        chatView.scrollToPosition(chatArrayList.size()-1);
                        return;
                    }
                    if(message.contains("튜토리얼")) {
                        tutorial();
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        chatArrayList.add(new Chat(BOTTEXT_KEY, "자동으로 카카오 맵으로 넘어갑니다"));
                        // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                        adapter.notifyItemInserted(chatArrayList.size()-1);
                        // 만약 사용자가 설정창에서 TTS기능을 켜놓았을 경우에만 text를 말하게 한다.
                        mTTS.speak("자동으로 카카오 맵으로 넘어갑니다", TextToSpeech.QUEUE_FLUSH, null);

                        editMessage.setText("");
                        chatView.scrollToPosition(chatArrayList.size()-1);
                        return;
                    }

                    else {
                        // 서버에 전송하여 봇의 대답을 받는다.
                        ServerRequest.postRequest("TEST", message, new Callback() {
                            // 만약 에라가 일어난 경우
                            @Override
                            public void onFailure(Call call, IOException e) {
                                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                                // 현재 스레드가 UI 스레드가 아닐 경우,
                                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                                ChattingActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        call.cancel();
                                        try {
                                            ParsingAnswer(null);
                                        }
                                        catch (IOException | JSONException | NetworkOnMainThreadException
                                                | NullPointerException e) {
                                            chatArrayList.add(new Chat(BOTTEXT_KEY, "뭔가 이상한데"));
                                            adapter.notifyItemInserted(chatArrayList.size()-1);
                                            // 만약
                                            if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                                                mTTS.speak("뭔가 이상한데", TextToSpeech.QUEUE_FLUSH, null);
                                            }
                                        }


                                    }
                                });
                            }

                            // 만약 서버로부터 정상적으로 대답을 받은 경우
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ChattingActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                                            ParsingAnswer(response);
                                        }
                                        catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                                            chatArrayList.add(new Chat(BOTTEXT_KEY, "다시한번 입력해주세요"));
                                            adapter.notifyItemInserted(chatArrayList.size()-1);
                                            chatView.scrollToPosition(chatArrayList.size()-1);
                                            if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                                                mTTS.speak("다시한번 입력해주세요", TextToSpeech.QUEUE_FLUSH, null);
                                            }
                                        }
                                    }
                                });

                            }
                        });
                    }

                // 스크롤 위치 업데이트
                chatView.scrollToPosition(chatArrayList.size()-1);
                // 입력창 초기화
                editMessage.setText("");
            }
        });
    }

    private void BookmarkServerCont() {
        test1 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();
        if (test1.equals("") || test1 == null) {
            Toast.makeText(ChattingActivity.this, "아이디 칸이 비었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        //id, query, bookmark
        ServerRequest.postRequest_bookmark("BOOKMARK", test1, test2, test3, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                ChattingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        try {
                            ParsingAnswer_bookmark(null);
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
                ChattingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                            ParsingAnswer_bookmark(response);
                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }
        });
    }
    private void ParsingAnswer_bookmark(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if (response == null) {
            if (PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")) {
                Toast.makeText(ChattingActivity.this, "다시 한번 눌러주세요.", Toast.LENGTH_SHORT).show();
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
            if (answer.contains("북마크 설정 실패")) {
                //Toast.makeText(ChattingActivity.this, "북마크에 이미 저장되어있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Logout(){
        String msg1 = ((LoginActivity) LoginActivity.context_main).editID.getText().toString();
        String msg2 = ((LoginActivity) LoginActivity.context_main).editPW.getText().toString();
        String msg4 = "0";
        ServerRequest.postRequest_logout("LOGINOUT", msg1, msg2, msg4, new Callback() {
            // 만약 에라가 일어난 경우
            @Override
            public void onFailure(Call call, IOException e) {
                // .runOnUiThread 메소드를 이용하여 현재 스레드가 UI 스레드라면 UI 자원을 즉시 실행
                // 현재 스레드가 UI 스레드가 아닐 경우,
                // UI 스레드의 자원 사용 이벤트 큐에 들어가도록 한다.
                ChattingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        try {
                            ParsingAnswer_logout(null);
                        } catch (IOException | JSONException | NetworkOnMainThreadException
                                | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ChattingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 서버로부터 온 대답을 분석하여 Recyclerview 에 아이템을 추가시킨다.
                            ParsingAnswer_logout(response);
                        } catch (IOException | JSONException | NetworkOnMainThreadException | NullPointerException e) {
                            e.getMessage();
                        }
                    }
                });
            }
        });
    }


    private void ParsingAnswer_logout(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

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
            if (Message.contains("로그아웃 성공")) {
                Toast.makeText(ChattingActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();                NavigationView navigationView = ((ChattingActivity)ChattingActivity.context_main).navigationView;
                Menu menu = navigationView.getMenu();
                MenuItem bookmark = menu.findItem(R.id.nav_bookmark);
                bookmark.setEnabled(false);
                MenuItem logout = menu.findItem(R.id.nav_logout);
                logout.setEnabled(false);
                MenuItem login = menu.findItem(R.id.nav_login);
                login.setEnabled(true);
                MenuItem recommend = menu.findItem(R.id.nav_recommend);
                recommend.setEnabled(false);
                return;
            }

            else if (Message.contains("로그아웃 실패")) {
                Toast.makeText(ChattingActivity.this, "로그인이 되어있지 않습니다.", Toast.LENGTH_LONG).show();
                Menu menu = navigationView.getMenu();
                MenuItem bookmark = menu.findItem(R.id.nav_bookmark);
                MenuItem logout = menu.findItem(R.id.nav_logout);
                bookmark.setEnabled(true);
                logout.setEnabled(true);
                MenuItem login = menu.findItem(R.id.nav_login);
                login.setEnabled(false);
                MenuItem recommend = menu.findItem(R.id.nav_recommend);
                recommend.setEnabled(true);
                return;
            }
        }
    }

    public void BookmarkOn(String answer) {
        if (answer.contains("로그인 성공")) {
            //Toast.makeText(ChattingActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_bookmark);
            bookmark.setEnabled(true);
        }

        else if(answer.contains("로그인 실패")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_bookmark);
            bookmark.setEnabled(false);
        }

        else if(answer.contains("로그인 상태입니다")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_bookmark);
            bookmark.setEnabled(true);
        }
    }

    String login_msg;
    public void LoginOn(String answer) {
        if (answer.contains("로그인 성공")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_login);
            bookmark.setEnabled(false);
            login_msg = "로그인버튼 비활성화";

        }

        else if(answer.contains("로그인 실패")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_login);
            bookmark.setEnabled(true);
        }

        else if(answer.contains("로그인 상태입니다")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_login);
            bookmark.setEnabled(false);
        }
    }

    public void LogoutOn(String answer) {
        if (answer.contains("로그인 성공")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_logout);
            bookmark.setEnabled(true);

        }

        else if(answer.contains("로그인 실패")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_logout);
            bookmark.setEnabled(false);
        }

        else if(answer.contains("로그인 상태입니다")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem bookmark = menu.findItem(R.id.nav_logout);
            bookmark.setEnabled(true);
        }
    }

    public void RecommendOn(String answer) {
        if (answer.contains("로그인 성공")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem recommend = menu.findItem(R.id.nav_recommend);
            recommend.setEnabled(true);

        }

        else if(answer.contains("로그인 실패")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem recommend = menu.findItem(R.id.nav_recommend);
            recommend.setEnabled(false);
        }

        else if(answer.contains("로그인 상태입니다")) {
            NavigationView navigationView = ((ChattingActivity) ChattingActivity.context_main).navigationView;
            Menu menu = navigationView.getMenu();
            MenuItem recommend = menu.findItem(R.id.nav_recommend);
            recommend.setEnabled(true);
        }
    }


    //뒤로가기 두번해야 종료됨
    private long backpressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backpressedTime + 2000) {
            Logout();
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    //toolBar설정
    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.btn_Clear:
                playClearBtn();
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    private void StartChat() {
        chatArrayList.add(new Chat(BOTSTART_KEY, null));
        adapter.notifyItemInserted(chatArrayList.size() - 1);
        // 스크롤 위치 업데이트
        chatView.scrollToPosition(chatArrayList.size() - 1);
    }

    //설정
    private void playSetting(){
        Intent intent = new Intent(ChattingActivity.this, SettingActivity.class);
        startActivity(intent);
    }
    //카카오 맵
    private void kakaomap(){
        Intent intent = new Intent(ChattingActivity.this, MapActivity.class);
        startActivity(intent);
    }
    //튜토리얼
    private void tutorial(){
        Intent intent = new Intent(ChattingActivity.this, TutorialActivity.class);
        startActivity(intent);
    }

    // 새로고침
    private void playClearBtn() {
        chatArrayList.add(new Chat(BOTSTART_KEY, null));
        adapter.notifyItemInserted(chatArrayList.size()-1);
        chatView.scrollToPosition(chatArrayList.size()-1);
    }

    @Override
    protected void onDestroy() {
        // 만약 TTS 기능이 작동중 일때 TTS 기능 정지
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    // 음성인식 하는 클래스. AsyncTask로 음성인식을 한 결과를 돌려준다.
    public class VoiceTask extends AsyncTask<String, Integer, String> {
        String str = null;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                getVoice();
            } catch (Exception e) {
                // TODO: handle exception
            }
            return str;
        }
        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL exception");
            }
        }
    }

    // 구글 음성인식 창 보여주는 함수.
    private void getVoice() {
        // 새로운 인텐트 생성
        Intent intent = new Intent();
        // 인텐트에서 할 작업 선택
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 인텐트에 데이터 전달
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        String language = "ko-KR";
        // 인텐트에 셋팅할 언어 데이터 전달
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        startActivityForResult(intent, 2);

    }

    // 음성인식한 것의 결과를 받는 함수.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // 만약 정상적인 종료를 하였을 경우
        if (resultCode == RESULT_OK) {
            // String 결과 데이터 받기
            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String str = results.get(0);

            // UI에 출력(editMessage 에 출력)
            TextView tv = findViewById(R.id.editMessage);
            tv.setText(str);
        }
    }

    // 리사이클러뷰 초기화하는 함수. 처음 액티비티 실행때만 실행되는 것이다.
    private void CreateRecyclerview(){
        chatArrayList = new ArrayList<>();
        arraylist_bookmark = new ArrayList<>();
        arraylist_query = new ArrayList<>();
        // Adapter 생성
        adapter = new ChatAdapter(chatArrayList, ChattingActivity.this, arraylist_bookmark);
        // LayoutManager 생성 --> 아이템 뷰가 나열되는 형태를 관리하기 위한 요소이다. 여러가지가 있지만 그중에서 LinearLayoutManager를 사용함.
        manager = new LinearLayoutManager(ChattingActivity.this);
        // Recycler 에 LayoutManager 등록하기
        chatView.setLayoutManager(manager);
        // Recycler 에 Adapter 등록하기
        chatView.setAdapter(adapter);
        // Recycler 에 함수 인자만큼의 Item 데이터 저장
        chatView.setItemViewCacheSize(100);
    }

    // 정상적으로 서버로부터 받은 대답을 분석하는 함수이다.
    public void ParsingAnswer(Response response)
            throws JSONException, IOException, NetworkOnMainThreadException, NullPointerException {

        // 만약 서버로부터 온 대답이 null일 경우
        if(response == null) {
            // BOTTEXT 형태의 뷰홀더를 출력한다.
           chatArrayList.add(new Chat(BOTTEXT_KEY, "연결안됨"));
           // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
           adapter.notifyItemInserted(chatArrayList.size()-1);
            chatView.scrollToPosition(chatArrayList.size()-1);
           // 만약 사용자가 설정창에서 TTS기능을 켜놓았을 경우에만 text를 말하게 한다.
            if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                mTTS.speak("연결안됨", TextToSpeech.QUEUE_FLUSH, null);
            }
            return;
        }


        // 데이터를 JSON 데이터 형식으로 받는다.
        String message = response.body().string();
        JSONObject jObject = new JSONObject(message);

        String intent = null;
        String Message = null;
        String image = null;
        String Ner = null;


        // JSON 데이터 파싱
        if(!jObject.isNull("NER")) {
            // Answer 부분의 String 데이터를 가져온다.
            Ner = jObject.getString("NER");
        }
        if(!jObject.isNull("Answer")) {
            // Answer 부분의 String 데이터를 가져온다.
            Message = jObject.getString("Answer");
        }
        if(!jObject.isNull("Intent")) {
            // Intent 부분의 String 데이터를 가져온다.
            intent = jObject.getString("Intent");
        }
        if(!jObject.isNull("AnswerImageUrl")) {
            // AnswerImageUrl 부분의 String 데이터를 가져온다.
            image = jObject.getString("AnswerImageUrl");
        }



        if(!jObject.isNull("Answer")) {
            //수시
            if(Ner.contains("B_S_SUSI")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_1_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 수시 전형일정 및 모집인원
            else if(Ner.contains("B_S_SCHEDULESANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_2_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 수시 학생부 교과
            else if(Ner.contains("B_S_SUBANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_3_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            //수시 학생부 종합
            else if(Ner.contains("B_S_SYNANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_4_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 전공별 교육과정
            else if(Ner.contains("B_H_CURRICULUM")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_5_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 수시 합격자 안내
            else if(Ner.contains("B_S_ACCEPTANCEANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_8_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 학교생활
            else if(Ner.contains("B_H_SCHOOLLIFE")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_9_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 정시
            else if(Ner.contains("B_J_JUNGSI")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_11_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 정시 전형일정 및 모집인원
            else if(Ner.contains("B_J_SCHEDULESANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_12_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 정시전형 정보
            else if(Ner.contains("B_J_MODELANN")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_13_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 정시 합격자 안내
            else if(Ner.contains("B_J_ACCEPTANCE")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_16_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            // 캠퍼스 맵
            else if(Ner.contains("B_H_MAP")) {
                if(Message.contains(Message)){
                    // BOTTEXT 형태의 뷰홀더를 출력한다.
                    chatArrayList.add(new Chat(BTN_MAPKAKAO_KEY, Message));
                    // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                    adapter.notifyItemInserted(chatArrayList.size()-1);
                    // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                    if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                        mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
            //예외
            else if(!Message.contains("http:")) {
                // BOTTEXT 형태의 뷰홀더를 출력한다.
                chatArrayList.add(new Chat(BOTTEXT_KEY, Message));
                // ArrayList 형태의 데이터에서 새롭게 데이터가 추가되었다고 알려주는 함수이다. 인자는 새롭게 추가된 데이터 위치이다.
                adapter.notifyItemInserted(chatArrayList.size()-1);
                // 만약 사용자가 설정창에서 TTS 기능을 켜놓았을 경우에만 Message 를 말하게 한다.
                if(PreferenceManager.getBoolean(ChattingActivity.this, "IsTTS")){
                    mTTS.speak(Message, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }

        if(!jObject.isNull("AnswerImageUrl")) {
            // PDF 처리
            if(image != null){
                List<String> urls =  DistinguishAnswer.extractUrls(image);
                // List 형태를 String 형태로 변환한다. 첫번째 인사에 들어가있는 문자를 두번째 인자 에 들어가있는 각각의 데이터들의 구분 문자로 사용한다.
                String url = TextUtils.join("", urls);
                if (image.contains("https://drive.google.com/")){
                    chatArrayList.add(new Chat(BOTWEB_KEY, url));
                }

                else if (!image.contains("https://drive.google.com/")){
                    if(image.contains("$")) {
                        String[] name = url.split("\\$");
                        String butwe1 = name[0];
                        String butwe2 = name[1];
                        chatArrayList.add(new Chat(BTN_17_KEY, butwe1)); // 교육과정
                        chatArrayList.add(new Chat(BTN_18_KEY, butwe2));    // 졸업 후 진로
                    }
                    //홈페이지 연결
                    else{
                        chatArrayList.add(new Chat(BOTBUTTON_KEY, url));
                    }
                }
            }
        }
        chatView.scrollToPosition(chatArrayList.size()-1);
    }
    // 자동완성
    String[] items = {"수시전형 안내하기",
            "수시 전형일정 및 모집 인원 안내",
            "학생부 교과전형 안내",
            "학생부 종합전형 안내",
            "면접평가 안내",
            "수시 장학제도 안내",
            "수시 합격자 안내",
            "성적산출프로그램",
            "수시 전형일정",
            "수시 모집인원",
            "수시 일반1",
            "수시 일반2",
            "수시 지역인재전형",
            "수시 교과 지역인재",
            "수시 종합 지역인재",
            "수시 자기추천",
            "수시 농어촌학생",
            "수시 특성화고교졸업자",
            "수시 기초생활수급자 및 차상위계층",
            "수시 교과지원방법",
            "수시 교과전형방법",
            "수시 종합지원방법",
            "수시 종합전형방법",
            "수시 지원방법",
            "수시 생활기록부 반영비율",
            "수시 학교생활기록부 반영비율",
            "수시 전년도 등급컷",
            "수시 작년도 등급컷",
            "수시 종합전형",
            "수시 종합전형1",
            "수시 종합전형2",
            "수시 소프트웨어인재 전형",
            "수시 면접고사일정 및 평가기준",
            "수시 장학제도 안내",
            "수시 장학금 안내",
            "수시 장학금",
            "수시 합격자발표",
            "수시 합격자조회",
            "수시 1차 합격자",
            "수시 최종 합격자 조회",
            "수시 고지서",
            "수시 성적산출",
            "정시전형 안내하기",
            "정시 전형일정 및 모집인원 안내",
            "정시 전형유형별 지원안내",
            "입학원서 접수 및 전형료 안내",
            "정시 전형일정",
            "정시 모집인원",
            "정시 일반전형",
            "정시 일반 가군",
            "정시 일반 나군",
            "정시 일반 다군",
            "일반 가군",
            "일반 나군",
            "일반 다군",
            "정시 학생부위주",
            "정시 농어촌학생",
            "정시 특성화고교졸업자",
            "정시 기초생활수급자 및 차상위계층",
            "정시 특수교육대상자",
            "정시 수능시험 반영 방법 및 점수",
            "수능시험 반영 방법 및 점수",
            "정시 전년도 등급컷",
            "정시 작년도 등급컷",
            "정시 지원자격",
            "정시 지원방법",
            "정시 전형방법",
            "정시 합격자 안내",
            "정시 합격자 발표",
            "정시 최종 합격자 조회",
            "정시 고지서",
            "정시 장학제도 안내",
            "정시 장학금 안내",
            "전공별 교육 과정",
            "학교생활 안내",
            "출석 성적 반영 방법",
            "출석 성적 반영 비율",
            "솔브릿지학과",
            "AI빅데이터학과",
            "호텔매니지먼트학과",
            "호텔학과",
            "글로벌융합비즈니스학과",
            "융합비즈니스학과",
            "융합 경영학과",
            "융합 세무 경영학과",
            "자율융합학과",
            "글로벌 철도학과",
            "철도 건설학과",
            "철도 경영학과",
            "물류시스템학과",
            "철도 전기시스템학과",
            "철도 소프트웨어학과",
            "철도 차량 시스템 학과",
            "건축공학과",
            "글로벌 미디어영상학과",
            "게임멀티미디어학과",
            "미디어디자인 영상학과",
            "컴퓨터 정보보안 학과",
            "IT 학과",
            "IT보안",
            "IT보안 학과",
            "글로벌조리학과",
            "폴보퀴즈조리학과",
            "글로벌외식학과",
            "글로벌외식창업학과",
            "글로벌외식창업전공",
            "외식조리학과",
            "한식조리학과",
            "한식조리과학",
            "한식조리과학학과",
            "외식조리경영학과",
            "외식조리영양학과",
            "호텔경영학과",
            "호텔관광학과",
            "호텔관광경영학과",
            "사회복지학과",
            "작업치료학과",
            "언어치료학과",
            "언어치료청각재활학과",
            "청각재활학과",
            "보건경영학과",
            "보건의료학과",
            "보건의료경영",
            "보건의료경영학과",
            "유아교육학과",
            "뷰티디자인학과",
            "응급구조학과",
            "소방안전학과",
            "간호학과",
            "물리치료학과",
            "스포츠건강재활학과",
            "JCFS 인공지능학과",
            "JCFS 데이터과학학과",
            "JCFS 인지과학 학과",
            "장학금",
            "장학제도",
            "캠퍼스",
            "학교",
            "지도",
            "캠퍼스 맵",
            "학교맵",
            "기숙사",
            "교무처 연락처",
            "부서별 연락처",
            "입학처",
            "편의시설",
            "카카오 캠퍼스맵 바로가기",
            "튜토리얼"};
}