package com.example.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Contact> data;
    LayoutInflater inflater;
    EditText edtFineName;
    public ContactAdapter(Context mContext, ArrayList<Contact> data, EditText edtFineName) {
        this.mContext = mContext;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.edtFineName = edtFineName;
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
        CheckBox cb = v.findViewById(R.id.checkBox);

        tvName.setText(data.get(position).getName());
        tvPhone.setText(data.get(position).getPhone());
        avatar.setImageResource(data.get(position).getImg());
        cb.setChecked(data.get(position).isStatus());

        cb.setOnClickListener(v1 -> {
            data.get(position).setStatus(cb.isChecked());
        });

        v.setOnClickListener(v2 -> {
            edtFineName.setText(data.get(position).getName());
            Toast.makeText(v2.getContext(), "" + data.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
        return v;
    }
}
