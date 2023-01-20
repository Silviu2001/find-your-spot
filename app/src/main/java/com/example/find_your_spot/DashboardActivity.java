package com.example.find_your_spot;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    String EmailHolder;
    TextView Email;
    Button LogOUT ;
    ImageButton profile ;
    private SQLiteHelperPhotos SQLiteHelperPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Email = (TextView)findViewById(R.id.textView1);
        LogOUT = (Button)findViewById(R.id.button1);
        profile = (ImageButton)findViewById(R.id.imageView);
//        loadImageFromDB();
        Intent intent = getIntent();

        // Receiving User Email Send By MainActivity.
        EmailHolder = intent.getStringExtra(MainActivity.UserEmail);

        // Setting up received email to TextView.
        Email.setText(Email.getText().toString()+ EmailHolder);

        SQLiteHelperPhotos = new SQLiteHelperPhotos(this);

        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(DashboardActivity.this,"Log Out Successful", Toast.LENGTH_LONG).show();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent1);

            }
        });

    }
    void loadImageFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLiteHelperPhotos.open();
                    final byte[] bytes = SQLiteHelperPhotos.retreiveImageFromDB();
                    SQLiteHelperPhotos.close();
                    // Show Image from DB in ImageView
                    profile.post(new Runnable() {
                        @Override
                        public void run() {
                            profile.setImageBitmap(Utils.getImage(bytes));
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
                    try {
                        SQLiteHelperPhotos.close();
                    }
                    catch (Exception e2) {
                        Log.e(TAG, "<loadImageFromDB> Error : " + e2.getLocalizedMessage());
                    }
                }
            }
        }).start();
    }
}