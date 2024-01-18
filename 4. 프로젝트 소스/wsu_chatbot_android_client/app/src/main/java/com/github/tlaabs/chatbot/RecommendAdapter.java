package com.github.tlaabs.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter {

    public static RecommendAdapter context_main;
    private static Context context;
    ArrayList<Recommend> recommendArrayList;


    public RecommendAdapter(ArrayList<Recommend> list, Context context) {
        this.recommendArrayList = list;
        this.context = context;
    }

    private static OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend, viewGroup, false);
        return new RecommendViewHolder1(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String message = recommendArrayList.get(position).getMessage();
        ((RecommendViewHolder1) holder).recommendItem.setText(message);
    }

    // 해당하는 위치의 아이템 ID를 반환한다.
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount() {
        return recommendArrayList.size();
    }

    private class RecommendViewHolder1 extends RecyclerView.ViewHolder{
        TextView recommendItem;

        public RecommendViewHolder1(@NonNull View itemView) {
            super(itemView);
            recommendItem = (TextView) itemView.findViewById(R.id.recommendItem);

            recommendItem.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
