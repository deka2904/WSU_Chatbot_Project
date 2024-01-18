package com.github.tlaabs.chatbot;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Videomain extends AppCompatActivity {


    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private VideoSearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new VideoSearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // input 창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        list.add("솔브릿지학과");
        list.add("AI빅데이터학과");
        list.add("호텔매니지먼트학과");
        list.add("글로벌융합비즈니스학과");
        list.add("융합 경영학과");
        list.add("융합 세무 경영학과");
        list.add("자율융합학과");
        list.add("글로벌 철도학과");
        list.add("철도 건설학과");
        list.add("철도 경영학과");
        list.add("물류시스템학과");
        list.add("철도 전기시스템학과");
        list.add("철도 소프트웨어학과");
        list.add("철도 차량 시스템 학과");
        list.add("건축공학과");
        list.add("글로벌 미디어영상학과");
        list.add("게임멀티미디어학과");
        list.add("미디어디자인 영상학과");
        list.add("컴퓨터 정보보안 학과");
        list.add("IT보안 학과");
        list.add("글로벌조리학과");
        list.add("폴보퀴즈조리학과");
        list.add("글로벌외식창업학과");
        list.add("외식조리학과");
        list.add("한식조리과학학과");
        list.add("외식조리경영학과");
        list.add("외식조리영양학과");
        list.add("호텔관광경영학과");
        list.add("사회복지학과");
        list.add("작업치료학과");
        list.add("언어치료청각재활학과");
        list.add("보건의료경영학과");
        list.add("유아교육학과");
        list.add("뷰티디자인학과");
        list.add("응급구조학과");
        list.add("소방안전학과");
        list.add("간호학과");
        list.add("물리치료학과");
        list.add("스포츠건강재활학과");
        list.add("JCFS 인공지능학과");
        list.add("JCFS 데이터과학학과");
        list.add("JCFS 인지과학 학과");


        /* 아이템 클릭시 작동 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                switch (list.get(position)) {
                    case "솔브릿지학과": // user 뷰홀더
                        Intent urlintent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=DwVGyoX1IBQ"));
                        startActivity(urlintent1);
                        break;

                    case "AI빅데이터학과": // user 뷰홀더
                        Intent urlintent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=XE9TzwBwKyE"));
                        startActivity(urlintent2);
                        break;

                    case "호텔매니지먼트학과": // user 뷰홀더
                        Intent urlintent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FXiVenuIjBw"));
                        startActivity(urlintent3);
                        break;

                    case "글로벌융합비즈니스학과": // user 뷰홀더
                        Intent urlintent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=F_YGbhl_InY"));
                        startActivity(urlintent4);
                        break;

                    case "융합 경영학과": // user 뷰홀더
                        Intent urlintent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=IsHWjFePkW4"));
                        startActivity(urlintent5);
                        break;

                    case "융합 세무 경영학과": // user 뷰홀더
                        Intent urlintent6 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=IsHWjFePkW4"));
                        startActivity(urlintent6);
                        break;

                    case "자율융합학과": // user 뷰홀더
                        Intent urlintent7 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=MjdamJ6Cdbg"));
                        startActivity(urlintent7);
                        break;

                    case "글로벌 철도학과": // user 뷰홀더
                        Intent urlintent8 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6z-iWik_mYc"));
                        startActivity(urlintent8);
                        break;

                    case "철도 건설학과": // user 뷰홀더
                        Intent urlintent9 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=9_BXJi5q9pc"));
                        startActivity(urlintent9);
                        break;

                    case "철도 경영학과": // user 뷰홀더
                        Intent urlintent10 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1aVv2t1tNvk"));
                        startActivity(urlintent10);
                        break;

                    case "물류시스템학과": // user 뷰홀더
                        Intent urlintent11 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=I9Kgd35-L4Y"));
                        startActivity(urlintent11);
                        break;

                    case "철도 전기시스템학과": // user 뷰홀더
                        Intent urlintent12 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=U7B5L72ZWTA"));
                        startActivity(urlintent12);
                        break;

                    case "철도 소프트웨어학과": // user 뷰홀더
                        Intent urlintent13 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=aI0Cc2H4rqg"));
                        startActivity(urlintent13);
                        break;

                    case "철도 차량 시스템 학과": // user 뷰홀더
                        Intent urlintent14 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mJC_OZgP9HI"));
                        startActivity(urlintent14);
                        break;

                    case "건축공학과": // user 뷰홀더
                        Intent urlintent15 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1zoAk83d4Nk"));
                        startActivity(urlintent15);
                        break;

                    case "글로벌 미디어영상학과": // user 뷰홀더
                        Intent urlintent16 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PjCYDswX0kw"));
                        startActivity(urlintent16);
                        break;

                    case "게임멀티미디어학과": // user 뷰홀더
                        Intent urlintent17 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FGAlR-7uI14"));
                        startActivity(urlintent17);
                        break;

                    case "미디어디자인 영상학과": // user 뷰홀더
                        Intent urlintent18 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FKmuDm-6Fsw"));
                        startActivity(urlintent18);
                        break;

                    case "컴퓨터 정보보안 학과": // user 뷰홀더
                        Intent urlintent19 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=I6AkZRPaz14"));
                        startActivity(urlintent19);
                        break;

                    case "IT보안 학과": // user 뷰홀더
                        Intent urlintent20 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=I6AkZRPaz14"));
                        startActivity(urlintent20);
                        break;

                    case "글로벌조리학과": // user 뷰홀더
                        Intent urlintent21 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=p6rALLme_EU"));
                        startActivity(urlintent21);
                        break;

                    case "폴보퀴즈조리학과": // user 뷰홀더
                        Intent urlintent22 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=RZ_4y23t0ZQ"));
                        startActivity(urlintent22);
                        break;

                    case "글로벌외식창업학과": // user 뷰홀더
                        Intent urlintent23 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=MBMWKtBJFRo"));
                        startActivity(urlintent23);
                        break;

                    case "외식조리학과": // user 뷰홀더
                        Intent urlintent24 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=lX7oRvEWzb8&t=2s"));
                        startActivity(urlintent24);
                        break;

                    case "한식조리과학학과": // user 뷰홀더
                        Intent urlintent25 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=jbVsCCO-9NY"));
                        startActivity(urlintent25);
                        break;

                    case "외식조리경영학과": // user 뷰홀더
                        Intent urlintent26 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=tzXXKYI4IG4"));
                        startActivity(urlintent26);
                        break;

                    case "외식조리영양학과": // user 뷰홀더
                        Intent urlintent27 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ojVD1qF-LNo"));
                        startActivity(urlintent27);
                        break;

                    case "호텔관광경영학과": // user 뷰홀더
                        Intent urlintent28 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=C4ayThCr7S8"));
                        startActivity(urlintent28);
                        break;

                    case "사회복지학과": // user 뷰홀더
                        Intent urlintent29 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=qDtf1ZBZ5m0"));
                        startActivity(urlintent29);
                        break;

                    case "작업치료학과": // user 뷰홀더
                        Intent urlintent30 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=HY_SlSCaCl4"));
                        startActivity(urlintent30);
                        break;

                    case "언어치료청각재활학과": // user 뷰홀더
                        Intent urlintent31 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mDZezwuxdxU"));
                        startActivity(urlintent31);
                        break;

                    case "보건의료경영학과": // user 뷰홀더
                        Intent urlintent32 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=hrXwgBUDpEw"));
                        startActivity(urlintent32);
                        break;

                    case "유아교육학과": // user 뷰홀더
                        Intent urlintent33 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=W3joS4Ak3iY"));
                        startActivity(urlintent33);
                        break;

                    case "뷰티디자인학과": // user 뷰홀더
                        Intent urlintent34 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1R8ypf7Jws4"));
                        startActivity(urlintent34);
                        break;

                    case "응급구조학과": // user 뷰홀더
                        Intent urlintent35 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xaxgz--O0bc"));
                        startActivity(urlintent35);
                        break;

                    case "소방안전학과": // user 뷰홀더
                        Intent urlintent36 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=L2aRXLmuxsk"));
                        startActivity(urlintent36);
                        break;

                    case "간호학과": // user 뷰홀더
                        Intent urlintent37 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=pdh7CJhK-EM"));
                        startActivity(urlintent37);
                        break;

                    case "물리치료학과": // user 뷰홀더
                        Intent urlintent38 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=nmH1T186LbA"));
                        startActivity(urlintent38);
                        break;

                    case "스포츠건강재활학과": // user 뷰홀더
                        Intent urlintent39 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=_sbQJHcHGRY"));
                        startActivity(urlintent39);
                        break;

                    case "JCFS 인공지능학과": // user 뷰홀더
                        Intent urlintent40 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=n6uFfvH5-SM"));
                        startActivity(urlintent40);
                        break;

                    case "JCFS 데이터과학학과": // user 뷰홀더
                        Intent urlintent41 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=n6uFfvH5-SM"));
                        startActivity(urlintent41);
                        break;

                    case "JCFS 인지과학 학과": // user 뷰홀더
                        Intent urlintent42 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=n6uFfvH5-SM"));
                        startActivity(urlintent42);
                        break;
                }
            }
        });
    }
}