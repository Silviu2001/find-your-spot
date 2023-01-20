package com.example.find_your_spot;
import static com.example.find_your_spot.SQLiteHelperPhotos.Table_Column_USER_ID;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    Button addSPOT ;
    LinearLayout linearLayout;

    private SQLiteHelperPhotos SQLiteHelperPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        Email = (TextView)findViewById(R.id.textView1);
        addSPOT = (Button)findViewById(R.id.add_spot);
        linearLayout = findViewById(R.id.linear_layout);

        SQLiteHelperPhotos = new SQLiteHelperPhotos(this);
        load_Spots();
        // Adding click listener to Log Out button.
        addSPOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.

                Intent intent = new Intent(ProfileActivity.this, AddSpotActivity.class);
                startActivity(intent);

            }
        });

    }

//    void loadImageFromDB() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SQLiteHelperPhotos.open();
//                    final byte[] bytes = SQLiteHelperPhotos.retreiveImageFromDB();
//                    SQLiteHelperPhotos.close();
//                    // Show Image from DB in ImageView
//                    profile.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            profile.setImageBitmap(Utils.getImage(bytes));
//                        }
//                    });
//                } catch (Exception e) {
//                    Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//                    try {
//                        SQLiteHelperPhotos.close();
//                    }
//                    catch (Exception e2) {
//                        Log.e(TAG, "<loadImageFromDB> Error : " + e2.getLocalizedMessage());
//                    }
//                }
//            }
//        }).start();
//    }

    public void load_Spots()
    {

//        String[] col=new String[]{Table_Column_USER_ID,KEY_NAME,KEY_ADDRESS};

        TextView textView = new TextView(this);

//        TextView textView1 = new TextView(this);
//        textView1.setText("TextView " + String.valueOf(2));
//        linearLayout.addView(textView1);

        try{
            SQLiteHelperPhotos.open();
            Cursor c = SQLiteHelperPhotos.mDb.query(false, SQLiteHelperPhotos.IMAGES_TABLE, new String[]{SQLiteHelperPhotos.Table_Column_IMAGE_ID, Table_Column_USER_ID, SQLiteHelperPhotos.Table_Column_IMAGE},
                null, null, null, null,
                SQLiteHelperPhotos.Table_Column_IMAGE_ID + " DESC", "1");
//            if(c!=null){
//                String array[] = new String[c.getCount()];
                int i = 0;
//
                c.moveToFirst();
//                while (!c.isAfterLast()) {
//                    array[i] = c.getString(0);
//                    byte[] blob = c.getBlob(c.getColumnIndex(SQLiteHelperPhotos.Table_Column_IMAGE));
//                    c.close();
//
//
////                return blob;
//                    i++;
//                    c.moveToNext();
//                }
//            }
//            SQLiteHelperPhotos.close();
        }
        catch (Exception e) {
            Log.e(TAG, "<load_spots> Error : " + e.getLocalizedMessage());
            SQLiteHelperPhotos.close();
        }

    }

}