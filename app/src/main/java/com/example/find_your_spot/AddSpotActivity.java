package com.example.find_your_spot;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class AddSpotActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";

    private Button btnSaveImage;
    private ImageButton btnOpenGallery;
    private TextView tvStatus;
    private Uri selectedImageUri;
//    private TextMultiLine description;

    private SQLiteHelperPhotos SQLiteHelperPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Find the views...
        btnOpenGallery = findViewById(R.id.btnSelectImage);
        btnSaveImage = findViewById(R.id.btnSaveImage);

//        location = (EditText)findViewById(R.id.location);
//        description = (EditText)findViewById(R.id.description);

//        sqLiteHelperUsers = new SQLiteHelperUsers(this);
        btnOpenGallery.setOnClickListener(this);
        btnSaveImage.setOnClickListener(this);

        // Create the Database helper object
        SQLiteHelperPhotos = new SQLiteHelperPhotos(this);

    }

    void showMessage(final String message) {
        tvStatus.post(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(message);
            }
        });
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    btnOpenGallery.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnOpenGallery)
            openImageChooser();

        if (v == btnSaveImage) {
            if (saveImageInDB()) {
                Log.e(TAG, "Image Saved in Database...");
            }
            finish();
        }
    }

    boolean saveImageInDB() {

        try {
            SQLiteHelperPhotos.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            SQLiteHelperPhotos.insertImage(inputData);
            SQLiteHelperPhotos.close();
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            SQLiteHelperPhotos.close();
            finish();
            return false;
        }

    }
}

