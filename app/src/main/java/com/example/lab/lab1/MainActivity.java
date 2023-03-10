package com.example.lab.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.R;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imgAndroid;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgAndroid = findViewById(R.id.imgLoad);
        message = findViewById(R.id.tvMessage);
    }

    public void loadImage(View view){
        final Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = loadImageFromNetwork("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/9aae003d-27b7-466e-a606-d622de846cc1/d48pnqi-495a31f1-df08-4d29-bcd8-808ba120590b.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzlhYWUwMDNkLTI3YjctNDY2ZS1hNjA2LWQ2MjJkZTg0NmNjMVwvZDQ4cG5xaS00OTVhMzFmMS1kZjA4LTRkMjktYmNkOC04MDhiYTEyMDU5MGIuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.CbS8Yxq7MAzHw8bHHVYcJ0r_2VFM94yAUSIDaYxb-WU");
                imgAndroid.post(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("Image Dowloaded");
                        imgAndroid.setImageBitmap(bitmap);
                    }
                });
            }
        });
        myThread.start();
    }

    private Bitmap loadImageFromNetwork(String link){
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        return bmp;
    }
}