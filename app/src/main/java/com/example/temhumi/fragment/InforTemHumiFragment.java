package com.example.temhumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.temhumi.MainActivity;
import com.example.temhumi.R;
import com.example.temhumi.adapter.TemHumiRecyclerAdapter;
import com.example.temhumi.databinding.FragmentInforTemHumiBinding;
import com.example.temhumi.model.TemHumiObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class InforTemHumiFragment extends Fragment {
    public FragmentInforTemHumiBinding binding;

    private TemHumiRecyclerAdapter temHumiRecyclerAdapter;
    private ArrayList<TemHumiObject> temHumiObjects;

    public InforTemHumiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInforTemHumiBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getData();

        setupRecyclerView();
        initUI();

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refresh.setRefreshing(false);
            }
        });
    }

    private void getData() {
        String data = "";
        if (getActivity() != null)
            data = MainActivity.readFromFile(getActivity());

        if (!data.isEmpty()) {
            temHumiObjects = new Gson().fromJson(data, new TypeToken<ArrayList<TemHumiObject>>() {
            }.getType());
        } else {
            temHumiObjects = null;
        }
    }

    private void initUI() {
        if (temHumiObjects.size() > 0) {
            binding.tvDateToday.setText(String.format(getResources().getString(R.string.text_today), temHumiObjects.get(0).getTime()));
            binding.tvTemValue.setText(String.format(getResources().getString(R.string.text_tem), temHumiObjects.get(0).getTemperature()));
            binding.tvHumiValue.setText(String.format(getResources().getString(R.string.text_humi), temHumiObjects.get(0).getHumidity()) + "%");
            binding.tvTemBest.setText(String.format(getResources().getString(R.string.text_tem), temHumiObjects.get(0).getTemperature()));
            binding.tvHumiBest.setText(String.format(getResources().getString(R.string.text_humi), temHumiObjects.get(0).getHumidity()) + "%");
        } else {
            Date date = Calendar.getInstance().getTime();
            String time = DateFormat.getInstance().format(date);
            binding.tvDateToday.setText(String.format(getResources().getString(R.string.text_today), time));
            binding.tvTemValue.setText(String.format(getResources().getString(R.string.text_tem), 0f));
            binding.tvHumiValue.setText(String.format(getResources().getString(R.string.text_humi), 0f) + "%");
            binding.tvTemBest.setText(String.format(getResources().getString(R.string.text_tem), 0f));
            binding.tvHumiBest.setText(String.format(getResources().getString(R.string.text_humi), 0f) + "%");
        }
    }

    public void getNewData(TemHumiObject temHumiObject) {
        if (temHumiObjects == null)
            temHumiObjects = new ArrayList<>();
        temHumiObjects.add(0, temHumiObject);
        initUI();
        temHumiRecyclerAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        temHumiObjects = new ArrayList<>();
        temHumiRecyclerAdapter = new TemHumiRecyclerAdapter(temHumiObjects);
        binding.rvTemHumi.setAdapter(temHumiRecyclerAdapter);
        binding.rvTemHumi.setHasFixedSize(true);
    }
}
