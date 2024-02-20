package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnAdd, btnDel;
    private EditText edtName;
    private ArrayList<Contact> listContact = new ArrayList<>();
    private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        UpdateData();
        ActionButton();
    }

    private void ActionButton() {
        btnAdd.setOnClickListener(v->{
            Intent intent = new Intent(this, SubActivity.class);
            startActivityForResult(intent, 100);
        });

        btnDel.setOnClickListener(v->{
            for(int i=0;i<listContact.size();){
                if(listContact.get(i).isStatus()==true){
                    listContact.remove(i);
                }
                else i++;
            }
            contactAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b = data.getExtras();

        int id = b.getInt("id");
        String name = b.getString("name");
        String phone = b.getString("phone");
        Boolean status = b.getBoolean("status");

        Contact contact = new Contact(id, name, phone, status);
        if(requestCode == 100 && resultCode == 150){
            listContact.add(contact);
            contactAdapter.notifyDataSetChanged();
        }
    }

    private void UpdateData() {
        listContact.add(new Contact(1, "Chung", "011", true));
        listContact.add(new Contact(2, "Chung", "011", true));
        listContact.add(new Contact(3, "Chung", "011", false));
        listContact.add(new Contact(4, "Chung", "011", false));
    }

    private void Init() {
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        edtName = findViewById(R.id.edtName);

        contactAdapter = new ContactAdapter(this, listContact);
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }
}