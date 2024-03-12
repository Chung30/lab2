package com.example.lab2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ContactAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Contact> data;
    LayoutInflater inflater;
    public ContactAdapter(Context mContext, ArrayList<Contact> data) {
        this.mContext = mContext;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) v = inflater.inflate(R.layout.sub_item, null);

        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvPhone = v.findViewById(R.id.tvPhone);
        ImageView avatar = v.findViewById(R.id.imgAvatar);
        CheckBox cb = v.findViewById(R.id.subCB);

        tvName.setText(data.get(position).getName());
        tvPhone.setText(data.get(position).getPhone());
        byte[] bytes = data.get(position).getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        avatar.setImageBitmap(bitmap);
//        System.out.println(data.get(position).getImg());
        cb.setChecked(data.get(position).isStatus());

        cb.setOnClickListener(v1 -> {
            boolean isChecked = cb.isChecked();
            data.get(position).setStatus(isChecked);
        });

        return v;
    }
}
