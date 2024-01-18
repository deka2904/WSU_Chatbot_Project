package com.github.tlaabs.chatbot;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter {

    public static BookmarkAdapter context_main;
    private static Context context;
    ArrayList<Book> bookArrayList;

    View view;

    public BookmarkAdapter(ArrayList<Book> list, Context context) {
        this.bookArrayList = list;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private static OnItemClickListener mListener = null;
    private static OnItemClickListener mListener1 = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    public void setOnItemClickListener1(OnItemClickListener listener1) {
        this.mListener1 = listener1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context_main = this;
        switch(i) {
            case 0:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_S_SUSIViewHolder(view);
            case 1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_S_SCHEDULESANNViewHolder(view);
            case 2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_S_SUBANNViewHolder(view);
            case 3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_S_SYNANNViewHolder(view);
            case 4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_H_CURRICULUMViewHolder(view);
            case 5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_S_ACCEPTANCEANNViewHolder(view);
            case 6:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_H_SCHOOLLIFEViewHolder(view);
            case 7:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_J_JUNGSIViewHolder(view);
            case 8:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_J_SCHEDULESANNViewHolder(view);
            case 9:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_J_MODELANNViewHolder(view);
            case 10:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookmark, viewGroup, false);
                return new B_J_ACCEPTANCEViewHolder(view);
        }
        return null;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시한다.
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        Book book = bookArrayList.get(i);
        final String message = bookArrayList.get(i).getMessage();
        // ArrayList 데이터의 i 위치에 있는 것들 중, 무슨 뷰홀더로 UI를 업데이트를 할지 switch 문을 이용하여 정한다.
        switch(bookArrayList.get(i).getNer()) {
            case "B_S_SUSI": {
                ((B_S_SUSIViewHolder) holder).bookmarkItem.setText("수시전형 안내하기");
                break;
            }
            case "B_S_SCHEDULESANN": {
                ((B_S_SCHEDULESANNViewHolder) holder).bookmarkItem.setText("수시 전형일정 및 모집 인원 안내");
                break;
            }
            case "B_S_SUBANN": {
                ((B_S_SUBANNViewHolder) holder).bookmarkItem.setText("학생부 교과전형 안내");
                break;
            }
            case "B_S_SYNANN": {
                ((B_S_SYNANNViewHolder) holder).bookmarkItem.setText("학생부 종합전형 안내");
                break;
            }
            case "B_H_CURRICULUM": {
                ((B_H_CURRICULUMViewHolder) holder).bookmarkItem.setText("전공별 교육 과정");
                break;
            }
            case "B_S_ACCEPTANCEANN": {
                ((B_S_ACCEPTANCEANNViewHolder) holder).bookmarkItem.setText("수시 합격자 안내");
                break;
            }
            case "B_H_SCHOOLLIFE": {
                ((B_H_SCHOOLLIFEViewHolder) holder).bookmarkItem.setText("학교생활 안내");
                break;
            }
            case "B_J_JUNGSI": {
                ((B_J_JUNGSIViewHolder) holder).bookmarkItem.setText("정시전형 안내하기");
                break;
            }
            case "B_J_SCHEDULESANN": {
                ((B_J_SCHEDULESANNViewHolder) holder).bookmarkItem.setText("정시 전형일정 및 모집인원 안내");
                break;
            }
            case "B_J_MODELANN": {
                ((B_J_MODELANNViewHolder) holder).bookmarkItem.setText("정시 전형유형별 지원안내");
                break;
            }
            case "B_J_ACCEPTANCE": {
                ((B_J_ACCEPTANCEViewHolder) holder).bookmarkItem.setText("정시 합격자 안내");
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
        switch(bookArrayList.get(position).getNer()) {
            case "B_S_SUSI":
                return 0;
            case "B_S_SCHEDULESANN":
                return 1;
            case "B_S_SUBANN":
                return 2;
            case "B_S_SYNANN":
                return 3;
            case "B_H_CURRICULUM":
                return 4;
            case "B_S_ACCEPTANCEANN":
                return 5;
            case "B_H_SCHOOLLIFE":
                return 6;
            case "B_J_JUNGSI":
                return 7;
            case "B_J_SCHEDULESANN":
                return 8;
            case "B_J_MODELANN":
                return 9;
            case "B_J_ACCEPTANCE":
                return 10;
            default:
                return -1;
        }
    }
    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    private class B_S_SUSIViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_S_SUSIViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });

        }
    }
    private static class B_S_SCHEDULESANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_S_SCHEDULESANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_S_SUBANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_S_SUBANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_S_SYNANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_S_SYNANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_H_CURRICULUMViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_H_CURRICULUMViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_S_ACCEPTANCEANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_S_ACCEPTANCEANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);
            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_H_SCHOOLLIFEViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_H_SCHOOLLIFEViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_J_JUNGSIViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_J_JUNGSIViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);
            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_J_SCHEDULESANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_J_SCHEDULESANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);
            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_J_MODELANNViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_J_MODELANNViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);
            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    private static class B_J_ACCEPTANCEViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkItem;
        ImageButton deletebtn;

        public B_J_ACCEPTANCEViewHolder(View itemView) {
            super(itemView);
            bookmarkItem = (TextView) itemView.findViewById(R.id.bookmarkItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);

            bookmarkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
}
