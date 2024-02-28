package com.example.lab2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {
    private EditText edtId, edtName, edtPhone;
    private CheckBox cbAdd;
    private Button btnAdd, btnCancel;
    ImageView imageView;
    String img;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Init();
        UpdateData();

        Intent i = getIntent();
        ArrayList<Integer> listId = i.getIntegerArrayListExtra("listId");

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

            if(Validate(id, name, phone, listId)) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putInt("id", id);
                b.putString("name", name);
                b.putString("phone", phone);
                b.putString("img", img);
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

        registerResult();

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            Uri uri = o.getData().getData();
                            img = uri.toString();
                            imageView.setImageURI(uri);
                        } catch (Exception e){
                            Toast.makeText(SubActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
//            // Nhận URI của hình ảnh được chọn
//            Uri selectedImageUri = data.getData();
////            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
//            img = selectedImageUri.toString();
//            imageView.setImageURI(selectedImageUri);
//        }
//    }

    private void UpdateData() {

    }

    private boolean Validate(int id, String name, String phone, ArrayList<Integer> listId) {
        if(name.equals("") || phone.equals("")){
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
        edtName = findViewById(R.id.edtFindName);
        edtPhone = findViewById(R.id.edtPhone);
        cbAdd = findViewById(R.id.cbAdd);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        imageView = findViewById(R.id.imgSubView);
    }
}