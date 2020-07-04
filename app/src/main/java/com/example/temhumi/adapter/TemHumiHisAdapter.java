package com.example.temhumi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temhumi.R;
import com.example.temhumi.model.TemHumiObject;

import java.util.ArrayList;

public class TemHumiHisAdapter extends RecyclerView.Adapter<TemHumiHisAdapter.MyViewHolder> {
    private ArrayList<TemHumiObject> temHumiObjects = new ArrayList<>();
    private Context context;

    public TemHumiHisAdapter(ArrayList<TemHumiObject> temHumiObjects, Context context) {
        this.temHumiObjects = temHumiObjects;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tem_humi_his, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(temHumiObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return temHumiObjects == null ? 0 : temHumiObjects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TemHumiObject temHumiObject;
        private TextView tv_time;
        private TextView tv_tem;
        private TextView tv_humi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_tem = itemView.findViewById(R.id.tv_tem);
            tv_humi = itemView.findViewById(R.id.tv_humi);
        }

        public void onBind(TemHumiObject temHumiObject) {
            this.temHumiObject = temHumiObject;
            String[] timeH = temHumiObject.getTime().split("/");
            String[] timeM = timeH[1].split(":");
            String min =  timeM[1];
            tv_time.setText(String.format(context.getResources().getString(R.string.text_time), Float.parseFloat(min)));
            tv_tem.setText(String.format(context.getResources().getString(R.string.text_tem), temHumiObject.getTemperature()));
            tv_humi.setText(String.format(context.getResources().getString(R.string.text_humi), temHumiObject.getHumidity()) + "%");
        }
    }
}
