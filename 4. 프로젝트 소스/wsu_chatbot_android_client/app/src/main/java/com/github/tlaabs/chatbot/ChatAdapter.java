package com.github.tlaabs.chatbot;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


// 채팅창 구현을 위한 ChatAdapter 제작
// 대답 형식에 따라 각각의 UI 뷰홀더를 갖추고 있음.
public class ChatAdapter extends RecyclerView.Adapter {

    public static ChatAdapter context_main;
    private static Context context;
    ArrayList<Chat> chatArrayList;
    ArrayList<String> arraylist_bookmark;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ChatAdapter(ArrayList<Chat> chatArrayList, Context context, ArrayList<String> arraylist_bookmark) {
        this.chatArrayList = chatArrayList;
        this.context = context;
        this.arraylist_bookmark = arraylist_bookmark;
    }
    public interface OnItemClickListener2 {
        void onItemClick(View v, int position);
    }
    private static OnItemClickListener2 mListener = null;

    public void setOnItemClickListener2(OnItemClickListener2 listener) {
        this.mListener = listener;
    }


    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 해당하는 뷰홀더 객체를 리턴한다.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        context_main = this;
        switch (i) {
            case 0:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
                return new userViewHolder(view);
            case 1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bottext, viewGroup, false);
                return new bottextViewHolder(view);
            case 2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_botstart_1, viewGroup, false);
                return new botStartViewHolder(view);
            case 3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_botbutton, viewGroup, false);
                return new botButtonViewHolder(view);
            case 4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_botweb, viewGroup, false);
                return new botWebViewHolder(view);
            case 5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_mapkakao, viewGroup, false);
                return new btn_mapkakao_ViewHolder(view);
            case 7:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_1, viewGroup, false);
                return new btn_1_ViewHolder(view);
            case 8:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_2, viewGroup, false);
                return new btn_2_ViewHolder(view);
            case 9:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_3, viewGroup, false);
                return new btn_3_ViewHolder(view);
            case 10:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_4, viewGroup, false);
                return new btn_4_ViewHolder(view);
            case 11:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_5, viewGroup, false);
                return new btn_5_ViewHolder(view);
            case 12:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_8, viewGroup, false);
                return new btn_8_ViewHolder(view);
            case 13:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_9, viewGroup, false);
                return new btn_9_ViewHolder(view);
            case 14:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_11, viewGroup, false);
                return new btn_11_ViewHolder(view);
            case 15:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_12, viewGroup, false);
                return new btn_12_ViewHolder(view);
            case 16:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_13, viewGroup, false);
                return new btn_13_ViewHolder(view);
            case 17:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_16, viewGroup, false);
                return new btn_16_ViewHolder(view);
            case 18:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_17, viewGroup, false);
                return new btn_17_ViewHolder(view);
            case 19:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.btn_18, viewGroup, false);
                return new btn_18_ViewHolder(view);

        }
        return null;
    }


    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시한다.
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        Chat chat = chatArrayList.get(i);
        final String message = chatArrayList.get(i).getMessasge();
        // ArrayList 데이터의 i 위치에 있는 것들 중, 무슨 뷰홀더로 UI를 업데이트를 할지 switch 문을 이용하여 정한다.
        switch (chatArrayList.get(i).getWho()) {
            // user 텍스트 일 경우
            case "user": {
                ((userViewHolder) viewHolder).userMsg.setText(message);
                break;
            }
            // bot 텍스트 ui 일경우
            case "bottext": {
                ((bottextViewHolder) viewHolder).bottextMsg.setText(message);

                break;
            }
            // bot 처음 시작할 때의 UI 일경우
            case "botstart": {
                // 첫번째 질문 edittext에..
                ((botStartViewHolder) viewHolder).btnQnA1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((botStartViewHolder) viewHolder).btnQnA1.setText("수시전형 안내하기");
                        et.setText(((botStartViewHolder) viewHolder).btnQnA1.getText());
                        ((botStartViewHolder) viewHolder).btnQnA1.setText("수시");
                    }

                });

                // 두번째 질문 edittext에..
                ((botStartViewHolder) viewHolder).btnQnA2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((botStartViewHolder) viewHolder).btnQnA2.setText("정시전형 안내하기");
                        et.setText(((botStartViewHolder) viewHolder).btnQnA2.getText());
                        ((botStartViewHolder) viewHolder).btnQnA2.setText("정시");
                    }
                });

                // 세번째 질문 edittext에..
                ((botStartViewHolder) viewHolder).btnQnA3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        et.setText(((botStartViewHolder) viewHolder).btnQnA3.getText());
                    }
                });

                // 네번째 질문 edittext에..
                ((botStartViewHolder) viewHolder).btnQnA4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        et.setText(((botStartViewHolder) viewHolder).btnQnA4.getText());
                    }
                });
                break;
            }
            // bot 버튼 형식의 UI일경우
            case "botbutton": {
                ((botButtonViewHolder) viewHolder).buturl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 인터넷으로 이동
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        context.startActivity(intent);
                    }
                });
                break;
            }
            // bot 웹 미리보기 및 바로가기 UI 일경우
            case "botweb": {
                // 웹 버튼을 클릭할 경우
                ((botWebViewHolder) viewHolder).butweb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 인터넷으로 이동
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        context.startActivity(intent);
                    }
                });
                break;
            }
            //kakaomap
            case "btn_mapkakao": {
                ((btn_mapkakao_ViewHolder) viewHolder).botTextMsg_map.setText(message);

                ((btn_mapkakao_ViewHolder) viewHolder).butmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_mapkakao_ViewHolder) viewHolder).butmap.setText("카카오 캠퍼스맵 바로가기");
                        et.setText(((btn_mapkakao_ViewHolder) viewHolder).butmap.getText());
                        ((btn_mapkakao_ViewHolder) viewHolder).butmap.setText("카카오 캠퍼스맵 바로가기");
                    }
                });
                break;
            }
            // 수시
            case "btn_1": {
                ((btn_1_ViewHolder) viewHolder).botStartMsg_1_1.setText(message);

                ((btn_1_ViewHolder) viewHolder).btn_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_1.setText("수시 전형일정 및 모집 인원 안내");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_1.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_1.setText("전형일정 모집인원");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_2.setText("학생부 교과전형 안내");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_2.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_2.setText("학생부 교과");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_3.setText("학생부 종합전형 안내");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_3.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_3.setText("학생부 종합");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_4.setText("전공별 교육 과정");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_4.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_4.setText("과별 교육과정");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_5.setText("수시 면접");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_5.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_5.setText("면접");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);

                        et.setText(((btn_1_ViewHolder) viewHolder).btn_6.getText());
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_7.setText("수시 합격자 안내");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_7.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_7.setText("수시 합격자안내");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_1_ViewHolder) viewHolder).btn_8.setText("학교생활 안내");
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_8.getText());
                        ((btn_1_ViewHolder) viewHolder).btn_8.setText("학교생활");
                    }
                });
                ((btn_1_ViewHolder) viewHolder).btn_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        et.setText(((btn_1_ViewHolder) viewHolder).btn_9.getText());
                    }
                });
                break;
            }
            // 수시 전형일정 모집인원
            case "btn_2": {
                ((btn_2_ViewHolder) viewHolder).botStartMsg_2.setText(message);

                ((btn_2_ViewHolder) viewHolder).btn_2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_2_ViewHolder) viewHolder).btn_2_1.setText("수시 전형일정");
                        et.setText(((btn_2_ViewHolder) viewHolder).btn_2_1.getText());
                        ((btn_2_ViewHolder) viewHolder).btn_2_1.setText("전형일정");
                    }
                });
                ((btn_2_ViewHolder) viewHolder).btn_2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_2_ViewHolder) viewHolder).btn_2_2.setText("수시 모집인원");
                        et.setText(((btn_2_ViewHolder) viewHolder).btn_2_2.getText());
                        ((btn_2_ViewHolder) viewHolder).btn_2_2.setText("모집인원");
                    }
                });
                break;
            }
            // 수시 학생부 교과
            case "btn_3": {
                ((btn_3_ViewHolder) viewHolder).botStartMsg_3.setText(message);

                ((btn_3_ViewHolder) viewHolder).spn_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(l == 0) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 일반1");
                        }
                        if(l == 1) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 일반2");
                        }
                        if(l == 2) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("교과 지역인재");
                        }
                        if(l == 3) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 자기추천");
                        }
                        if(l == 4) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 농어촌학생");
                        }
                        if(l == 5) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 특성화고교졸업자");
                        }
                        if(l == 6) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 기초생활수급자 및 차상위계층");

                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ((btn_3_ViewHolder) viewHolder).btn_3_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_3_ViewHolder) viewHolder).btn_3_7.setText("수시 교과지원방법");
                        et.setText(((btn_3_ViewHolder) viewHolder).btn_3_7.getText());
                        ((btn_3_ViewHolder) viewHolder).btn_3_7.setText("지원방법 전형방법");
                    }
                });
                ((btn_3_ViewHolder) viewHolder).btn_3_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_3_ViewHolder) viewHolder).btn_3_8.setText("수시 생활기록부 반영비율");
                        et.setText(((btn_3_ViewHolder) viewHolder).btn_3_8.getText());
                        ((btn_3_ViewHolder) viewHolder).btn_3_8.setText("생활기록부 반영 방법");
                    }
                });
                ((btn_3_ViewHolder) viewHolder).btn_3_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        et.setText(((btn_3_ViewHolder) viewHolder).btn_3_9.getText());
                    }
                });
                ((btn_3_ViewHolder) viewHolder).btn_3_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_3_ViewHolder) viewHolder).btn_3_10.setText("수시 전년도 등급컷");
                        et.setText(((btn_3_ViewHolder) viewHolder).btn_3_10.getText());
                        ((btn_3_ViewHolder) viewHolder).btn_3_10.setText("전년도 등급컷");
                    }
                });
                break;
            }
            // 수시 학생부 종합
            case "btn_4": {
                ((btn_4_ViewHolder) viewHolder).botStartMsg_4.setText(message);

                ((btn_4_ViewHolder) viewHolder).spn_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(l == 0) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("종합전형1");
                        }
                        if(l == 1) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("종합전형2");
                        }
                        if(l == 2) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("수시 소프트웨어인재");
                        }
                        if(l == 3) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("종합 지역인재");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ((btn_4_ViewHolder) viewHolder).btn_4_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_4_ViewHolder) viewHolder).btn_4_5.setText("종합지원방법");
                        et.setText(((btn_4_ViewHolder) viewHolder).btn_4_5.getText());
                        ((btn_4_ViewHolder) viewHolder).btn_4_5.setText("지원방법 전형방법");
                    }
                });
                ((btn_4_ViewHolder) viewHolder).btn_4_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        et.setText(((btn_4_ViewHolder) viewHolder).btn_4_6.getText());
                    }
                });
                ((btn_4_ViewHolder) viewHolder).btn_4_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_4_ViewHolder) viewHolder).btn_4_7.setText("수시 전년도 등급컷");
                        et.setText(((btn_4_ViewHolder) viewHolder).btn_4_7.getText());
                        ((btn_4_ViewHolder) viewHolder).btn_4_7.setText("전년도 등급컷");
                    }
                });
                break;
            }
            // 전공별 교육과정
            case "btn_5": {
                ((btn_5_ViewHolder) viewHolder).botStartMsg_5.setText(message);

                ((btn_5_ViewHolder) viewHolder).spn_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(l == 0) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("솔브릿지학과");
                        }
                        if(l == 1) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("AI빅데이터학과");
                        }
                        if(l == 2) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("호텔매니지먼트학과");
                        }
                        if(l == 3) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("글로벌융합비즈니스학과");
                        }
                        if(l == 4) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("융합 경영학과");
                        }
                        if(l == 5) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("융합 세무 경영학과");
                        }
                        if(l == 6) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("자율융합학과");
                        }
                        if(l == 7) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("글로벌 철도학과");
                        }
                        if(l == 8) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("철도 건설학과");
                        }
                        if(l == 9) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("철도 경영학과");
                        }
                        if(l == 10) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("물류시스템학과");
                        }
                        if(l == 11) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("철도 전기시스템학과");
                        }
                        if(l == 12) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("철도 소프트웨어학과");
                        }
                        if(l == 13) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("철도 차량 시스템 학과");
                        }
                        if(l == 14) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("건축공학과");
                        }
                        if(l == 15) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("글로벌 미디어영상학과");
                        }
                        if(l == 16) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("게임멀티미디어학과");
                        }
                        if(l == 17) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("미디어디자인 영상학과");
                        }
                        if(l == 18) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("컴퓨터 정보보안 학과");
                        }
                        if(l == 19) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("IT보안 학과");
                        }
                        if(l == 20) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("글로벌조리학과");
                        }
                        if(l == 21) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("폴보퀴즈조리학과");
                        }
                        if(l == 22) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("글로벌외식창업학과");
                        }
                        if(l == 23) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("외식조리학과");
                        }
                        if(l == 24) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("한식조리과학학과");
                        }
                        if(l == 25) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("외식조리경영학과");
                        }
                        if(l == 26) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("외식조리영양학과");
                        }
                        if(l == 27) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("호텔관광경영학과");
                        }
                        if(l == 28) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("사회복지학과");
                        }
                        if(l == 29) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("작업치료학과");
                        }
                        if(l == 30) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("언어치료청각재활학과");
                        }
                        if(l == 31) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("보건의료경영학과");
                        }
                        if(l == 32) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("유아교육학과");
                        }
                        if(l == 33) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("뷰티디자인학과");
                        }
                        if(l == 34) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("응급구조학과");
                        }
                        if(l == 35) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("소방안전학과");
                        }
                        if(l == 36) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("간호학과");
                        }
                        if(l == 37) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("물리치료학과");
                        }
                        if(l == 38) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("스포츠건강재활학과");
                        }
                        if(l == 39) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("JCFS 인공지능학과");
                        }
                        if(l == 40) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("JCFS 데이터과학학과");
                        }
                        if(l == 41) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("JCFS 인지과학 학과");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            }
            // 수시 합격자 안내
            case "btn_8": {
                ((btn_8_ViewHolder) viewHolder).botStartMsg_8.setText(message);

                ((btn_8_ViewHolder) viewHolder).btn_8_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_8_ViewHolder) viewHolder).btn_8_1.setText("수시 합격자 발표");
                        et.setText(((btn_8_ViewHolder) viewHolder).btn_8_1.getText());
                        ((btn_8_ViewHolder) viewHolder).btn_8_1.setText("합격자 발표 및 유의사항");
                    }
                });
                ((btn_8_ViewHolder) viewHolder).btn_8_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_8_ViewHolder)viewHolder).btn_8_2.setText("수시 1차 합격자");
                        et.setText(((btn_8_ViewHolder) viewHolder).btn_8_2.getText());
                        ((btn_8_ViewHolder)viewHolder).btn_8_2.setText("1단계 합격자 조회");
                    }
                });
                ((btn_8_ViewHolder) viewHolder).btn_8_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_8_ViewHolder) viewHolder).btn_8_3.setText("수시 최종 합격자 조회");
                        et.setText(((btn_8_ViewHolder) viewHolder).btn_8_3.getText());
                        ((btn_8_ViewHolder) viewHolder).btn_8_3.setText("최종 합격자 조회 및 고지서");
                    }
                });
                break;
            }
            // 학교생활
            case "btn_9": {
                ((btn_9_ViewHolder) viewHolder).botStartMsg_9.setText(message);

                ((btn_9_ViewHolder) viewHolder).btn_9_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_9_ViewHolder) viewHolder).btn_9_1.setText("캠퍼스 맵");
                        et.setText(((btn_9_ViewHolder) viewHolder).btn_9_1.getText());
                        ((btn_9_ViewHolder) viewHolder).btn_9_1.setText("캠퍼스 맵");
                    }
                });
                ((btn_9_ViewHolder) viewHolder).btn_9_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_9_ViewHolder)viewHolder).btn_9_2.setText("기숙사");
                        et.setText(((btn_9_ViewHolder) viewHolder).btn_9_2.getText());
                        ((btn_9_ViewHolder)viewHolder).btn_9_2.setText("기숙사 ");
                    }
                });
                ((btn_9_ViewHolder) viewHolder).btn_9_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_9_ViewHolder) viewHolder).btn_9_3.setText("부서별 연락처");
                        et.setText(((btn_9_ViewHolder) viewHolder).btn_9_3.getText());
                        ((btn_9_ViewHolder) viewHolder).btn_9_3.setText("부서별 연락처");
                    }
                });
                ((btn_9_ViewHolder) viewHolder).btn_9_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_9_ViewHolder) viewHolder).btn_9_4.setText("입학처");
                        et.setText(((btn_9_ViewHolder) viewHolder).btn_9_4.getText());
                        ((btn_9_ViewHolder) viewHolder).btn_9_4.setText("입학처");
                    }
                });
                ((btn_9_ViewHolder) viewHolder).btn_9_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_9_ViewHolder) viewHolder).btn_9_5.setText("편의시설");
                        et.setText(((btn_9_ViewHolder) viewHolder).btn_9_5.getText());
                        ((btn_9_ViewHolder) viewHolder).btn_9_5.setText("편의시설");
                    }
                });
                break;
            }
            // 정시
            case "btn_11": {
                ((btn_11_ViewHolder) viewHolder).botStartMsg_11.setText(message);

                ((btn_11_ViewHolder) viewHolder).btn_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_12.setText("정시 전형일정 및 모집인원 안내");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_12.getText());
                        ((btn_11_ViewHolder) viewHolder).btn_12.setText("전형일정 모집인원");
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_13.setText("정시 전형유형별 지원안내");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_13.getText());
                        ((btn_11_ViewHolder) viewHolder).btn_13.setText("정시전형 정보안내");
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_14.setText("입학원서 접수 및 전형료 안내");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_14.getText());
                        ((btn_11_ViewHolder) viewHolder).btn_14.setText("입학원서 접수 및 전형료 안내");
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_15.setText("전공별 교육과정");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_15.getText());
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_16.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_16.setText("정시 합격자 안내");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_16.getText());
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_17.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_17.setText("정시 장학금");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_17.getText());
                    }
                });
                ((btn_11_ViewHolder) viewHolder).btn_18.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_11_ViewHolder) viewHolder).btn_18.setText("학교 생활");
                        et.setText(((btn_11_ViewHolder) viewHolder).btn_18.getText());
                    }
                });

                break;
            }
            // 정시 전형일정 및 모집인원
            case "btn_12": {
                ((btn_12_ViewHolder) viewHolder).botStartMsg_12.setText(message);

                ((btn_12_ViewHolder) viewHolder).btn_12_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_12_ViewHolder) viewHolder).btn_12_1.setText("정시 전형일정");
                        et.setText(((btn_12_ViewHolder) viewHolder).btn_12_1.getText());
                        ((btn_12_ViewHolder) viewHolder).btn_12_1.setText("전형일정 ");
                    }
                });
                ((btn_12_ViewHolder) viewHolder).btn_12_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_12_ViewHolder)viewHolder).btn_12_2.setText("정시 모집인원");
                        et.setText(((btn_12_ViewHolder) viewHolder).btn_12_2.getText());
                        ((btn_12_ViewHolder)viewHolder).btn_12_2.setText("모집인원 ");
                    }
                });
                break;
            }
            // 정시 전형정보
            case "btn_13": {
                ((btn_13_ViewHolder) viewHolder).botStartMsg_13.setText(message);

                ((btn_13_ViewHolder) viewHolder).spn_13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(l == 0) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 일반전형");
                        }
                        if(l == 1) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 학생부위주");
                        }
                        if(l == 2) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 농어촌학생");
                        }
                        if(l == 3) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 특성화고교졸업자");
                        }
                        if(l == 4) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 기초생활수급자");
                        }
                        if(l == 5) {
                            EditText et = ((Activity)context).findViewById(R.id.editMessage);
                            et.setText("정시 특수교육 대상자");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                ((btn_13_ViewHolder) viewHolder).btn_13_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_13_ViewHolder) viewHolder).btn_13_7.setText("정시 수능시험 반영 방법 및 점수");
                        et.setText(((btn_13_ViewHolder) viewHolder).btn_13_7.getText());
                        ((btn_13_ViewHolder) viewHolder).btn_13_7.setText("수능시험 반영 방법 및 점수");
                    }
                });
                ((btn_13_ViewHolder) viewHolder).btn_13_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_13_ViewHolder)viewHolder).btn_13_8.setText("정시 지원자격");
                        et.setText(((btn_13_ViewHolder) viewHolder).btn_13_8.getText());
                        ((btn_13_ViewHolder)viewHolder).btn_13_8.setText("지원방법 전형방법");
                    }
                });
                ((btn_13_ViewHolder) viewHolder).btn_13_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_13_ViewHolder) viewHolder).btn_13_9.setText("정시 전년도 등급컷");
                        et.setText(((btn_13_ViewHolder) viewHolder).btn_13_9.getText());
                        ((btn_13_ViewHolder) viewHolder).btn_13_9.setText("전년도등급컷 ");
                    }
                });
                break;
            }
            // 정시 합격자 안내
            case "btn_16": {
                ((btn_16_ViewHolder) viewHolder).botStartMsg_16.setText(message);

                ((btn_16_ViewHolder) viewHolder).btn_16_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = ((Activity) context).findViewById(R.id.editMessage);
                        ((btn_16_ViewHolder)viewHolder).btn_16_1.setText("정시 최종 합격자 조회");
                        et.setText(((btn_16_ViewHolder) viewHolder).btn_16_1.getText());
                        ((btn_16_ViewHolder)viewHolder).btn_16_1.setText("최종 합격자 조회 및 고지서");
                    }
                });
                break;
            }
            // 전공별 교육과정
            case "btn_17": {
                ((btn_17_ViewHolder) viewHolder).butwe1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 인터넷으로 이동
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        context.startActivity(intent);
                    }
                });
                break;
            }
            //전공별 진로
            case "btn_18": {
                ((btn_18_ViewHolder) viewHolder).butwe2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 인터넷으로 이동
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        context.startActivity(intent);
                    }
                });
                break;
            }

        }
    }

    // 해당하는 위치의 아이템 ID를 반환한다.
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    // position에 해당하는 아이템 항목에 따른 뷰타입을 리턴한다. Chat 이라는 데이터 클래스를 따로 정의한다.
    @Override
    public int getItemViewType(int position) {
        switch (chatArrayList.get(position).getWho()) {
            case "user": // user 뷰홀더
                return 0;
            case "bottext": // bot text 뷰홀더
                return 1;
            case "botstart": // bot 시작 뷰홀더
                return 2;
            case "botbutton": // bot Button 대답 뷰홀더
                return 3;
            case "botweb": // bot web 미리보기 대답 뷰홀더
                return 4;
            case "btn_mapkakao": // mapkakao
                return 5;
            case "btn_1":      // 수시
                return 7;
            case "btn_2":       //수시 전형일정 및 모집인원
                return 8;
            case "btn_3":       //수시 학생부 교과
                return 9;
            case "btn_4":       //수시 학생부 종합
                return 10;
            case "btn_5":       //전공별 교육과정
                return 11;
            case "btn_8":       //수시 합격자 안내
                return 12;
            case "btn_9":       //학교생활
                return 13;
            case "btn_11":       //정시
                return 14;
            case "btn_12":       //정시 전형일정 및 모집인원
                return 15;
            case "btn_13":       //정시 전형 정보
                return 16;
            case "btn_16":       //정시 합격자 안내
                return 17;
            case "btn_17":       //교육 과정
                return 18;
            case "btn_18":       //진로
                return 19;
            default:            // 오류
                return -1;
        }
    }


    // 데이터 개수 반환
    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    // 유저 텍스트 뷰홀더 클래스
    public static class userViewHolder extends RecyclerView.ViewHolder {
        TextView userMsg;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            userMsg = (TextView) itemView.findViewById(R.id.userTextMsg);
        }
    }

    // 챗봇 텍스트 뷰홀더 클래스
    public static class bottextViewHolder extends RecyclerView.ViewHolder {
        TextView bottextMsg;

        public bottextViewHolder(@NonNull View itemView) {
            super(itemView);
            bottextMsg = (TextView) itemView.findViewById(R.id.botTextMsg);
        }
    }

    // 챗봇 시작 UI 뷰홀더 클래스
    public static class botStartViewHolder extends RecyclerView.ViewHolder {
        //        TextView botStartMsg;
        Button btnQnA1, btnQnA2, btnQnA3, btnQnA4;

        public botStartViewHolder(@NonNull View itemView) {
            super(itemView);
            btnQnA1 = (Button) itemView.findViewById(R.id.btnQnA1);
            btnQnA2 = (Button) itemView.findViewById(R.id.btnQnA2);
            btnQnA3 = (Button) itemView.findViewById(R.id.btnQnA3);
            btnQnA4 = (Button) itemView.findViewById(R.id.btnQnA4);
        }
    }

    // 챗봇 버튼 UI 뷰홀더 클래스
    public static class botButtonViewHolder extends RecyclerView.ViewHolder {
        Button buturl;

        public botButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            buturl = (Button) itemView.findViewById(R.id.buturl);
        }
    }

    // 챗봇 웹 미리보기 및 바로가기 UI 뷰홀더 클래스
    public static class botWebViewHolder extends RecyclerView.ViewHolder {
        Button butweb;

        public botWebViewHolder(@NonNull View itemView) {
            super(itemView);
            butweb = (Button) itemView.findViewById(R.id.butweb);
        }
    }

    // mapkakao
    public static class btn_mapkakao_ViewHolder extends RecyclerView.ViewHolder {
        TextView botTextMsg_map;
        Button butmap;


        public btn_mapkakao_ViewHolder(@NonNull View itemView) {
            super(itemView);
            botTextMsg_map = (TextView) itemView.findViewById(R.id.botTextMsg_map);
            butmap = (Button) itemView.findViewById(R.id.butmap);

        }
    }

    // 수시 버튼
    public static class btn_1_ViewHolder extends RecyclerView.ViewHolder {
        private ListView bookmarklistview;
        TextView botStartMsg_1_1;
        Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
        ImageButton starbtn1;

        public btn_1_ViewHolder(@NonNull View itemView) {

            super(itemView);

            starbtn1 = (ImageButton) itemView.findViewById(R.id.starbtn1);
            bookmarklistview = (ListView) itemView.findViewById(R.id.bookmarkview);
            botStartMsg_1_1 = (TextView) itemView.findViewById(R.id.botStartMsg_1);
            btn_1 = (Button) itemView.findViewById(R.id.btn_1);
            btn_2 = (Button) itemView.findViewById(R.id.btn_2);
            btn_3 = (Button) itemView.findViewById(R.id.btn_3);
            btn_4 = (Button) itemView.findViewById(R.id.btn_4);
            btn_5 = (Button) itemView.findViewById(R.id.btn_5);
            btn_6 = (Button) itemView.findViewById(R.id.btn_6);
            btn_7 = (Button) itemView.findViewById(R.id.btn_7);
            btn_8 = (Button) itemView.findViewById(R.id.btn_8);
            btn_9 = (Button) itemView.findViewById(R.id.btn_9);

            starbtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 수시 전형일정 및 모집인원
    public static class btn_2_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_2;
        Button btn_2_1, btn_2_2;
        ImageButton starbtn2;


        public btn_2_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn2 = (ImageButton) itemView.findViewById(R.id.starbtn2);
            botStartMsg_2 = (TextView) itemView.findViewById(R.id.botStartMsg_2);
            btn_2_1 = (Button) itemView.findViewById(R.id.btn_2_1);
            btn_2_2 = (Button) itemView.findViewById(R.id.btn_2_2);

            starbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 수시 학생부 교과
    public static class btn_3_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_3;
        Button btn_3_7, btn_3_8, btn_3_9, btn_3_10;
        Spinner spn_3;
        ImageButton starbtn3;

        public btn_3_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn3 = (ImageButton) itemView.findViewById(R.id.starbtn3);
            botStartMsg_3 = (TextView) itemView.findViewById(R.id.botStartMsg_3);
            btn_3_7 = (Button) itemView.findViewById(R.id.btn_3_7);
            btn_3_8 = (Button) itemView.findViewById(R.id.btn_3_8);
            btn_3_9 = (Button) itemView.findViewById(R.id.btn_3_9);
            btn_3_10 = (Button) itemView.findViewById(R.id.btn_3_10);
            spn_3 = (Spinner) itemView.findViewById(R.id.spn_3);

            starbtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 수시 학생부 종합
    public static class btn_4_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_4;
        Button btn_4_5, btn_4_6, btn_4_7;
        Spinner spn_4;
        ImageButton starbtn4;


        public btn_4_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn4 = (ImageButton) itemView.findViewById(R.id.starbtn4);
            botStartMsg_4 = (TextView) itemView.findViewById(R.id.botStartMsg_4);
            btn_4_5 = (Button) itemView.findViewById(R.id.btn_4_5);
            btn_4_6 = (Button) itemView.findViewById(R.id.btn_4_6);
            btn_4_7 = (Button) itemView.findViewById(R.id.btn_4_7);
            spn_4 = (Spinner) itemView.findViewById(R.id.spn_4);

            starbtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 전공별 교육과정
    public static class btn_5_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_5;
        Spinner spn_5;
        ImageButton starbtn5;


        public btn_5_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn5 = (ImageButton) itemView.findViewById(R.id.starbtn5);
            botStartMsg_5 = (TextView) itemView.findViewById(R.id.botStartMsg_5);
            spn_5 = (Spinner) itemView.findViewById(R.id.spn_5);

            starbtn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 수시 합격자 안내
    public static class btn_8_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_8;
        Button btn_8_1, btn_8_2, btn_8_3;
        ImageButton starbtn8;


        public btn_8_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn8 = (ImageButton) itemView.findViewById(R.id.starbtn8);
            botStartMsg_8 = (TextView) itemView.findViewById(R.id.botStartMsg_8);
            btn_8_1 = (Button) itemView.findViewById(R.id.btn_8_1);
            btn_8_2 = (Button) itemView.findViewById(R.id.btn_8_2);
            btn_8_3 = (Button) itemView.findViewById(R.id.btn_8_3);

            starbtn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 학교생괄
    public static class btn_9_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_9;
        Button btn_9_1, btn_9_2, btn_9_3, btn_9_4, btn_9_5;
        ImageButton starbtn9;


        public btn_9_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn9 = (ImageButton) itemView.findViewById(R.id.starbtn9);
            botStartMsg_9 = (TextView) itemView.findViewById(R.id.botStartMsg_9);
            btn_9_1 = (Button) itemView.findViewById(R.id.btn_9_1);
            btn_9_2 = (Button) itemView.findViewById(R.id.btn_9_2);
            btn_9_3 = (Button) itemView.findViewById(R.id.btn_9_3);
            btn_9_4 = (Button) itemView.findViewById(R.id.btn_9_4);
            btn_9_5 = (Button) itemView.findViewById(R.id.btn_9_5);

            starbtn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 정시
    public static class btn_11_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_11;
        Button btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18;
        ImageButton starbtn11;


        public btn_11_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn11 = (ImageButton) itemView.findViewById(R.id.starbtn11);
            botStartMsg_11 = (TextView) itemView.findViewById(R.id.botStartMsg_11);
            btn_12 = (Button) itemView.findViewById(R.id.btn_12);
            btn_13 = (Button) itemView.findViewById(R.id.btn_13);
            btn_14 = (Button) itemView.findViewById(R.id.btn_14);
            btn_15 = (Button) itemView.findViewById(R.id.btn_15);
            btn_16 = (Button) itemView.findViewById(R.id.btn_16);
            btn_17 = (Button) itemView.findViewById(R.id.btn_17);
            btn_18 = (Button) itemView.findViewById(R.id.btn_18);

            starbtn11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 정시 전형일정 및 모집인원
    public static class btn_12_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_12;
        Button btn_12_1, btn_12_2;
        ImageButton starbtn12;


        public btn_12_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn12 = (ImageButton) itemView.findViewById(R.id.starbtn12);
            botStartMsg_12 = (TextView) itemView.findViewById(R.id.botStartMsg_12);
            btn_12_1 = (Button) itemView.findViewById(R.id.btn_12_1);
            btn_12_2 = (Button) itemView.findViewById(R.id.btn_12_2);

            starbtn12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 정시 전형 정보
    public static class btn_13_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_13;
        Button btn_13_7, btn_13_8, btn_13_9;
        Spinner spn_13;
        ImageButton starbtn13;


        public btn_13_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn13 = (ImageButton) itemView.findViewById(R.id.starbtn13);
            botStartMsg_13 = (TextView) itemView.findViewById(R.id.botStartMsg_13);
            btn_13_7 = (Button) itemView.findViewById(R.id.btn_13_7);
            btn_13_8 = (Button) itemView.findViewById(R.id.btn_13_8);
            btn_13_9 = (Button) itemView.findViewById(R.id.btn_13_9);
            spn_13 = (Spinner) itemView.findViewById(R.id.spn_13);

            starbtn13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 정시 합격자 안내
    public static class btn_16_ViewHolder extends RecyclerView.ViewHolder {
        TextView botStartMsg_16;
        Button btn_16_1;
        ImageButton starbtn16;


        public btn_16_ViewHolder(@NonNull View itemView) {
            super(itemView);
            starbtn16 = (ImageButton) itemView.findViewById(R.id.starbtn16);
            botStartMsg_16 = (TextView) itemView.findViewById(R.id.botStartMsg_16);
            btn_16_1 = (Button) itemView.findViewById(R.id.btn_16_1);

            starbtn16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 전공별 교육과정
    public static class btn_17_ViewHolder extends RecyclerView.ViewHolder {
        Button butwe1;

        public btn_17_ViewHolder(@NonNull View itemView) {
            super(itemView);
            butwe1 = (Button) itemView.findViewById(R.id.butwe1);
        }
    }
    //전공별 진로
    public static class btn_18_ViewHolder extends RecyclerView.ViewHolder {
        Button butwe2;

        public btn_18_ViewHolder(@NonNull View itemView) {
            super(itemView);
            butwe2 = (Button) itemView.findViewById(R.id.butwe2);
        }
    }
}