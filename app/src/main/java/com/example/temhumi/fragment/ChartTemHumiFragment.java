package com.example.temhumi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.temhumi.MainActivity;
import com.example.temhumi.R;
import com.example.temhumi.adapter.TemHumiHisAdapter;
import com.example.temhumi.databinding.FragmentChartTemHumiBinding;
import com.example.temhumi.model.TemHumiObject;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartTemHumiFragment extends Fragment implements View.OnClickListener {
    private FragmentChartTemHumiBinding binding;

    private ArrayList<TemHumiObject> temHumiObjects;
    private TemHumiHisAdapter temHumiHisAdapter;

    public ChartTemHumiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChartTemHumiBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getData();
        temHumiObjects = new ArrayList<>();
        initPage();
        initChart();
//        if (temHumiObjects != null) {
//            binding.chartTem.setVisibility(View.VISIBLE);
//            initChart();
//        } else
//            binding.chartTem.setVisibility(View.GONE);
        initRecyclerView();
    }

    private void getData() {
        String data = "";
        if (getActivity() != null) {
            data = MainActivity.readFromFile(getActivity());
        }
        if (!data.isEmpty()) {
            temHumiObjects = new Gson().fromJson(data, new TypeToken<ArrayList<TemHumiObject>>() {
            }.getType());
        } else {
            temHumiObjects = null;
        }
    }

    public void getNewData(TemHumiObject temHumiObject) {
        temHumiObjects.add(0, temHumiObject);
        temHumiHisAdapter.notifyDataSetChanged();
//        binding.chartTem.invalidate();
        initChart();
    }

    private void initRecyclerView() {
        temHumiHisAdapter = new TemHumiHisAdapter(temHumiObjects, getContext());
        binding.rvAll.setAdapter(temHumiHisAdapter);
    }

    private void initChart() {
        binding.chartTem.getDescription().setEnabled(false);

        YAxis right = binding.chartTem.getAxisRight();
        right.setAxisMinimum(0f);
        right.setAxisMaximum(100f);

        YAxis left = binding.chartTem.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setAxisMaximum(100f);

        XAxis xAxis = binding.chartTem.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(80f);
//        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(value);
            }
        });


        LineDataSet lineDataSetTem = new LineDataSet(dataSetTem(), "Nhiệt độ - " + "\u2103");
        lineDataSetTem.setColor(Color.RED);
        lineDataSetTem.setLineWidth(2.5f);
        lineDataSetTem.setCircleColor(Color.RED);
        lineDataSetTem.setCircleRadius(5f);
        lineDataSetTem.setFillColor(Color.RED);
        lineDataSetTem.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetTem.setDrawValues(true);
        lineDataSetTem.setValueTextSize(10f);
        lineDataSetTem.setValueTextColor(Color.RED);

        LineDataSet lineDataSetHumi = new LineDataSet(dataSetHumi(), "Độ ẩm - %");
        lineDataSetHumi.setColor(Color.BLUE);
        lineDataSetHumi.setLineWidth(2.5f);
        lineDataSetHumi.setCircleColor(Color.BLUE);
        lineDataSetHumi.setCircleRadius(5f);
        lineDataSetHumi.setFillColor(Color.BLUE);
        lineDataSetHumi.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetHumi.setDrawValues(true);
        lineDataSetHumi.setValueTextSize(10f);
        lineDataSetHumi.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSetTem);
        iLineDataSets.add(lineDataSetHumi);

        LineData lineData = new LineData(iLineDataSets);
        binding.chartTem.setData(lineData);
        binding.chartTem.invalidate();
    }

    private ArrayList<Entry> dataSetTem() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < temHumiObjects.size(); i++) {
            String[] timeH = temHumiObjects.get(i).getTime().split("/");
            String[] timeM = timeH[1].split(":");
            String min =  timeM[1];
            entries.add(new Entry(Float.parseFloat(min), temHumiObjects.get(i).getTemperature()));
        }
        return entries;
    }

    private ArrayList<Entry> dataSetHumi() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < temHumiObjects.size(); i++) {
            String[] timeH = temHumiObjects.get(i).getTime().split("/");
            String[] timeM = timeH[1].split(":");
            String min =  timeM[1];
            entries.add(new Entry(Float.parseFloat(min), temHumiObjects.get(i).getHumidity()));
        }
        return entries;
    }

    private void initPage() {
        binding.tvChart.setOnClickListener(this);
        binding.tvAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chart: {
                binding.tvChart.setBackgroundColor(getResources().getColor(R.color.trans));
                binding.tvAll.setBackgroundColor(getResources().getColor(R.color.Blue500));
                binding.layoutChart.setVisibility(View.VISIBLE);
                binding.rvAll.setVisibility(View.GONE);
                break;
            }
            case R.id.tv_all: {
                binding.tvAll.setBackgroundColor(getResources().getColor(R.color.trans));
                binding.tvChart.setBackgroundColor(getResources().getColor(R.color.Blue500));
                binding.layoutChart.setVisibility(View.GONE);
                binding.rvAll.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
}
