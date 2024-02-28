package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnAdd, btnDel;
    private EditText edtFindName;
    private ArrayList<Contact> listContact = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private ArrayList<Integer> listImg = new ArrayList<>();
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
            ArrayList<Integer> listId = new ArrayList<>();
            for(Contact c : listContact)
                listId.add(c.getId());
            intent.putIntegerArrayListExtra("listId", listId);
            startActivityForResult(intent, 100);
        });

        btnDel.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn chắc chắn muốn xoá?");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            for(int i=0;i<listContact.size();){
                                if(listContact.get(i).isStatus()==true){
                                    listContact.remove(i);
                                }
                                else i++;
                            }
                            contactAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            edtFindName.setText(listContact.get(position).getName());
            Toast.makeText(MainActivity.this, "" + listContact.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();

            int id = b.getInt("id");
            String name = b.getString("name");
            String phone = b.getString("phone");
            String img = b.getString("img");
            Boolean status = b.getBoolean("status");

            Contact contact = new Contact(id, name, phone, img, status);
            listContact.add(contact);
            contactAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateData() {

        listContact.add(new Contact(1, "Chung", "011", "", true));
        listContact.add(new Contact(1, "Chung", "011", "", true));
        listContact.add(new Contact(1, "Chung", "011", "", false));
        listContact.add(new Contact(1, "Chung", "011", "", false));
    }

    private void Init() {
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        edtFindName = findViewById(R.id.edtFindName);

        contactAdapter = new ContactAdapter(this, listContact, edtFindName);
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }
}