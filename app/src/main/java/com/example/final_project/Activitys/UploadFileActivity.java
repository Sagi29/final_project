package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadFileActivity extends AppCompatActivity {



    private Button uploadFile_BTN_upload, uploadFile_BTN_fetch;
    private TextView uploadFile_TXT_file;
    private Button uploadFile_BTN_select;

    private ProgressBar upload_PRB_progressBar;
    private Button uploadFile_BTN_back;
    private Uri fileUri;

    //private ProgressBar progressBar;

    private FirebaseStorage storage;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        uploadFile_BTN_upload = findViewById(R.id.uploadFile_BTN_upload);
        uploadFile_TXT_file= findViewById(R.id.uploadFile_TXT_file);
        uploadFile_BTN_select= findViewById(R.id.uploadFile_BTN_select);
        upload_PRB_progressBar = findViewById(R.id.upload_PRB_progressBar);
        uploadFile_BTN_back = findViewById(R.id.uploadFile_BTN_back);
        uploadFile_BTN_fetch = findViewById(R.id.uploadFile_BTN_fetch);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        uploadFile_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadFile_BTN_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        uploadFile_BTN_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fileUri != null)
                    uploadFile(fileUri);
                else
                    Toast.makeText(UploadFileActivity.this, "select a file", Toast.LENGTH_LONG).show();
            }
        });

        uploadFile_BTN_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(ContextCompat.checkSelfPermission(UploadFileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectFile();
               }
               else
                   ActivityCompat.requestPermissions(UploadFileActivity.this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }
        });

    }

    private void uploadFile(Uri fileUri) {
        //upload_PRB_progressBar = new ProgressBar(UploadFileActivity.this,null,android.R.attr.progressBarStyleHorizontal);
        //upload_PRB_progressBar.setTransitionName("Uploading file...");

        //upload_PRB_progressBar.setProgress(0);
        //RelativeLayout layout = new RelativeLayout(UploadFileActivity.this);
        //upload_PRB_progressBar.setVisibility(View.VISIBLE);
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);
        //layout.addView(upload_PRB_progressBar,params);
        //setContentView(layout);

        final String fileName = System.currentTimeMillis()+ "";
        StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                DatabaseReference databaseReference = database.getReference();
                databaseReference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(UploadFileActivity.this, "file successfully uploaded", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(UploadFileActivity.this, "file NOT successfully uploaded", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadFileActivity.this, "file NOT successfully uploaded", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress = (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                Log.d("ptt",""+currentProgress);
                upload_PRB_progressBar.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectFile();
        }
        else
            Toast.makeText(this, "please provide permission", Toast.LENGTH_LONG).show();
    }

    private void selectFile() {

        Intent intent = new Intent();
        intent.setType("image/jpeg");
        //intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            uploadFile_TXT_file.setText("A file is selected : " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(this, "please select a file", Toast.LENGTH_LONG).show();
        }
    }
}