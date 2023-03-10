package com.example.lab.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AppCompatActivity {

    private ImageView imgLoad;
    private ProgressDialog progressDialog;
    private String url = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/9aae003d-27b7-466e-a606-d622de846cc1/d48pnqi-495a31f1-df08-4d29-bcd8-808ba120590b.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzlhYWUwMDNkLTI3YjctNDY2ZS1hNjA2LWQ2MjJkZTg0NmNjMVwvZDQ4cG5xaS00OTVhMzFmMS1kZjA4LTRkMjktYmNkOC04MDhiYTEyMDU5MGIuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.CbS8Yxq7MAzHw8bHHVYcJ0r_2VFM94yAUSIDaYxb-WU";
    private Bitmap bitmap = null;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        imgLoad = findViewById(R.id.imgLoad);
        tvMessage = findViewById(R.id.tvMessage);
    }

    private Handler messageHandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String message = bundle.getString("message");
            tvMessage.setText(message);
            imgLoad.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    };

    public void loadImageButton(View v){
        progressDialog =ProgressDialog.show(LoadImage.this,"","Dowloading...");
        Runnable aRunnable = new Runnable() {
            @Override
            public void run() {
                bitmap = downloadBitmap(url);
                Message msg = messageHandler.obtainMessage();
                Bundle bundle = new Bundle();
                String threadMessage = "Image Downloaded";
                bundle.putString("message",threadMessage);
                msg.setData(bundle);
                messageHandler.sendMessage(msg);
            }
        };
        Thread aThread = new Thread(aRunnable);
        aThread.start();
    }

    private Bitmap downloadBitmap(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}