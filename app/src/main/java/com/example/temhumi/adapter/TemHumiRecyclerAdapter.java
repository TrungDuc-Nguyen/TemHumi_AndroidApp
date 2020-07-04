package com.example.temhumi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temhumi.R;
import com.example.temhumi.model.TemHumiObject;

import java.util.ArrayList;

public class TemHumiRecyclerAdapter extends RecyclerView.Adapter<TemHumiRecyclerAdapter.MyViewHolder> {
    private ArrayList<TemHumiObject> temHumiObjects = new ArrayList<>();

    public TemHumiRecyclerAdapter(ArrayList<TemHumiObject> temHumiObjects) {
        this.temHumiObjects = temHumiObjects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tem_humi, parent, false);
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
            tv_time.setText(temHumiObject.getTime());
            tv_tem.setText(String.valueOf(temHumiObject.getTemperature() + " \u2103"));
            tv_humi.setText(String.valueOf(temHumiObject.getHumidity() + " %"));
        }
    }
}
