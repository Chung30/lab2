package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {
    private EditText edtId, edtName, edtPhone;
    private CheckBox cbAdd;
    private Button btnAdd, btnCancel;
    private RecyclerView rcvAvatar;
    private ImgAdapter imgAdapter;
    private ArrayList<Avatar> listImg = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Init();
        UpdateData();

        Intent i = getIntent();
        ArrayList<Integer> listId = i.getIntegerArrayListExtra("listId");
//        System.out.println(listId.size());
        btnAdd.setOnClickListener(v->{
            int id = 0;
            try {
                id = Integer.parseInt(edtId.getText().toString());
                
                } catch (Exception e){
                    Toast.makeText(this, "Cần điền id", Toast.LENGTH_SHORT).show();
                }
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            boolean status = cbAdd.isChecked();
            int img = 0;
            for (Avatar a: listImg) {
                if(a.isCheck()) {
                    img = a.getImg();
                    break;
                }
            }
            if(Validate(id, name, phone, listId, img)) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putInt("id", id);
                b.putString("name", name);
                b.putString("phone", phone);
                b.putInt("img", img);
                b.putBoolean("status", status);

                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(v->{
            finish();
        });
    }

    private void UpdateData() {
        listImg.add(new Avatar(R.drawable.img1, false));
        listImg.add(new Avatar(R.drawable.img2, false));
        listImg.add(new Avatar(R.drawable.img3, false));
        listImg.add(new Avatar(R.drawable.img4, false));

        imgAdapter.notifyDataSetChanged();
    }

    private boolean Validate(int id, String name, String phone, ArrayList<Integer> listId, int img) {
        if(name.equals("") || phone.equals("") || img == 0){
            Toast.makeText(this, "Cần điền đầy đủ", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i=0;i<listId.size();i++){
            if(listId.get(i) == id) {
                Toast.makeText(this, "id đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        
        return true;
    }

    private void Init() {
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        cbAdd = findViewById(R.id.cbAdd);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        rcvAvatar = findViewById(R.id.rcvAvatar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rcvAvatar.setLayoutManager(linearLayoutManager);
        imgAdapter = new ImgAdapter(this, listImg);
        rcvAvatar.setAdapter(imgAdapter);
    }
}