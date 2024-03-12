package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SubActivity extends AppCompatActivity {
    private EditText edtId, edtName, edtPhone;
    private CheckBox cbAdd;
    private Button btnAdd, btnCancel;
    private ImageView imageView;
    private byte[] img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        init();
        ActionButton();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            int id=bundle.getInt("id");
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            img = bundle.getByteArray("img");

            if(id != 0) edtId.setText(Integer.toString(id));
            edtName.setText(name);
            edtPhone.setText(phone);
            if(img != null)
                imageView.setImageBitmap(convertImgToBitmap(img));
            else
                imageView.setImageResource(R.drawable.img1);
        }
    }

    private void ActionButton() {
        imageView.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(intent,101);
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            int id;
            String name, phone;
            id=Integer.parseInt(edtId.getText().toString());
            name=edtName.getText().toString();
            phone=edtPhone.getText().toString();
            boolean status = cbAdd.isChecked();
            bundle.putInt("id",id);
            bundle.putString("name",name);
            bundle.putString("phone",phone);
            bundle.putByteArray("img",img);
            bundle.putBoolean("status",status);

            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().
                        openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                img = convertImgToByte(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertImgToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private Bitmap convertImgToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    protected void init(){
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        cbAdd = findViewById(R.id.cbAdd);
        btnAdd = findViewById(R.id.btnSubAdd);
        btnCancel = findViewById(R.id.btnCancel);
        imageView = findViewById(R.id.imgSubView);
    }
}