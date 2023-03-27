package com.example.root.intouchsmsapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.R;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ExcelActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    String TAG ="main";
    private TextView textView;
    private Button btn_choose;

    public static final int PICKFILE_RESULT_CODE = 1;

    private Uri fileUri;
    private String filePath;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);


        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            //EasyPermissions.requestPermissions(this, getString(R.string.permission_read_external_storage), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        textView = findViewById(R.id.textview);

        btn_choose = findViewById(R.id.btn_choose_file);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermission()){
                    requestPermission();
                } else {
                    openFileManager();
                }

            }
        });


    }

    private void openFileManager(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }
    
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        File file = new File(fileUri.getPath());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    textView.setText(filePath);
                }

                break;
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(ExcelActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

        /*if (ActivityCompat.shouldShowRequestPermissionRationale(ExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(ExcelActivity.this, "Read External Storage permission allows us to do read excel files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ExcelActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileManager();
                } else {
                    Toast.makeText(this, "Permiission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
