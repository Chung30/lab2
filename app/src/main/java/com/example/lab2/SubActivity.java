package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {
    private EditText edtId, edtName, edtPhone;
    private CheckBox cbAdd;
    private Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Init();
        btnAdd.setOnClickListener(v->{
            Intent intent = new Intent();
            Bundle b = new Bundle();
            b.putInt("id", Integer.parseInt(edtId.getText().toString()));
            b.putString("name", edtName.getText().toString().trim());
            b.putString("phone", edtPhone.getText().toString().trim());
            b.putBoolean("status", cbAdd.isChecked());

            intent.putExtras(b);
            setResult(150, intent);
            finish();
        });

        btnCancel.setOnClickListener(v->{
            finish();
        });
    }

    private void Init() {
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        cbAdd = findViewById(R.id.cbAdd);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
    }
}