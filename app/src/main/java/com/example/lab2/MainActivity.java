package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private int selectedItemId;
    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
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
                    (dialog, id) -> {
                        for(int i=0;i<listContact.size();){
                            if(listContact.get(i).isStatus()==true){
                                listContact.remove(i);
                            }
                            else i++;
                        }
                        contactAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    });

            builder.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert = builder.create();
            alert.show();

        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            edtFindName.setText(listContact.get(position).getName());
            Toast.makeText(MainActivity.this, "" + listContact.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedItemId = position;
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            Bundle b = data.getExtras();
            int id = b.getInt("id");
            String name = b.getString("name");
            String phone = b.getString("phone");
            byte[] img = b.getByteArray("img");
            Boolean status = b.getBoolean("status");

            Contact contact = new Contact(id, name, phone, img, status);

            if (requestCode == 100 && resultCode == RESULT_OK) {
                db.addContact(contact);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == 110 && resultCode == RESULT_OK) {
                listContact.set(selectedItemId, contact);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }
            UpdateData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_menu_sort_name){
            Toast.makeText(this, "Sort by name", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.action_menu_sort_phone){
            Toast.makeText(this, "Sort by phone", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.action_menu_broadcast){
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.context_menu_edit){
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();

            Contact contact=listContact.get(selectedItemId);
            Intent intent = new Intent(this, SubActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", contact.getId());
            bundle.putString("name", contact.getName());
            bundle.putString("phone", contact.getPhone());
            bundle.putByteArray("img", contact.getImg());
            bundle.putBoolean("status", contact.isStatus());
            intent.putExtras(bundle);
            startActivityForResult(intent, 110);

        }
        else if(item.getItemId()==R.id.context_menu_delete){
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            listContact.remove(selectedItemId);
            contactAdapter.notifyDataSetChanged();
        }
        else if(item.getItemId()==R.id.context_menu_call){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                    listContact.get(selectedItemId).getPhone()));
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.context_menu_sms){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" +
                    listContact.get(selectedItemId).getPhone()));

//            send email
//            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("email:" +
//                    listContact.get(selectedItemId).getName()));
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//            intent.putExtra(Intent.EXTRA_TEXT, "Body");
//            intent.setData(Uri.parse("mailto:"));
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void UpdateData() {
        listContact.clear();
        listContact.addAll(db.getAllContact());
        contactAdapter.notifyDataSetChanged();
    }

    private void Init() {
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        edtFindName = findViewById(R.id.edtFindName);
        db = new MyDB(this, "ContactDB", null, 1);
        contactAdapter = new ContactAdapter(this, listContact);
        listView.setAdapter(contactAdapter);
        UpdateData();
        registerForContextMenu(listView);
    }
}