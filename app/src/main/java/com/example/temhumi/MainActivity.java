package com.example.temhumi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.temhumi.adapter.TemHumiAdapter;
import com.example.temhumi.databinding.ActivityMainBinding;
import com.example.temhumi.fragment.ChartTemHumiFragment;
import com.example.temhumi.fragment.InforTemHumiFragment;
import com.example.temhumi.model.TemHumiObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private DatabaseReference mDatabase;

    private InforTemHumiFragment inforTemHumiFragment;
    private ChartTemHumiFragment chartTemHumiFragment;
    private TemHumiAdapter temHumiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        initUI();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TemHumiObject dt = dataSnapshot.child("TemHumiObject").getValue(TemHumiObject.class);
                Log.d("getadd", dt.toString());
                String data = new Gson().toJson(dt);
                Log.d("getadđ", data);
                writeToFile(MainActivity.this, data);
                if (inforTemHumiFragment != null)
                    inforTemHumiFragment.getNewData(dt);
                if (chartTemHumiFragment != null) {
                    chartTemHumiFragment.getNewData(dt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void writeToFile(Context context, String data) {
        try {
            File file = new File(context.getFilesDir(), "TemHumi.txt");

            if (!file.exists()) {
                file.mkdir();
            }

            FileOutputStream fo = new FileOutputStream(file, true);
            fo.write(data.getBytes());
            fo.close();
        } catch (IOException ignored) {
        }
    }

    public static String readFromFile(Context context) {
        try {
            File file = new File(context.getFilesDir(), "TemHumi.txt");
            if (!file.exists()){
                return "";
            }
            FileInputStream fi = new FileInputStream(file);
            DataInputStream da = new DataInputStream(fi);
            BufferedReader bf = new BufferedReader(new InputStreamReader(da));

            StringBuilder builder = new StringBuilder();
            String data;

            while ((data = bf.readLine()) != null) {
                builder.append(data);
                builder.append("\n");
            }

            fi.close();
            return builder.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private void initUI() {
        inforTemHumiFragment = new InforTemHumiFragment();
        chartTemHumiFragment = new ChartTemHumiFragment();

        temHumiAdapter = new TemHumiAdapter(getSupportFragmentManager(), inforTemHumiFragment, chartTemHumiFragment);

        binding.viewPager.setAdapter(temHumiAdapter);
        binding.viewPager.setCurrentItem(0, true);
        binding.viewPager.setOffscreenPageLimit(2);
    }

}
