package com.example.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Avatar> data;
    private int mSelectedItem = -1; // Chỉ mục của mục được chọn hiện tại

    public ImgAdapter(Context mContext, ArrayList<Avatar> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public CheckBox cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgView);
            cb = itemView.findViewById(R.id.imgCb);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Avatar a = data.get(position);
        holder.img.setImageResource(a.getImg());
        holder.cb.setChecked(position == mSelectedItem);

        holder.cb.setOnClickListener(v -> {
            mSelectedItem = holder.getAdapterPosition();
            a.setCheck(true);
            for (Avatar avatar: data) {
                if(!avatar.equals(a)) avatar.setCheck(false);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
