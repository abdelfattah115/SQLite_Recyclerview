package com.example.sqlite_recyclerview.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite_recyclerview.ui.main.EditData;
import com.example.sqlite_recyclerview.ui.main.MainActivity;
import com.example.sqlite_recyclerview.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.sqlite_recyclerview.Model.Data;

public class Adapter extends RecyclerView.Adapter<Adapter.Myviewholder> {

    private Context context ;
    private List<Data> list ;
    private DataBase_Helper dataBaseHelper ;

    public Adapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    public Adapter(Context context, List<Data> list, DataBase_Helper dataBaseHelper) {
        this.context = context;
        this.list = list;
        this.dataBaseHelper = dataBaseHelper;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcycler_list,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position) {
        final Data data = list.get(position);
        holder.title.setText(data.getName());
        holder.time.setText(formatDate(data.getTimeStamp()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(data.getId()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditData.class);
                intent.putExtra("position",String.valueOf(data.getId()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        public TextView title , time ;
        public ImageView edit , delete ;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    private String formatDate(String dateStr){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {
            Date date  = fmt.parse(dateStr);
            SimpleDateFormat fmtout = new SimpleDateFormat("MMM-d");
            return fmtout.format(date);
        } catch (ParseException e) {
            Log.e("error",e.getMessage());
        }
         return "";
    }

    private void deleteData(int postion){
        dataBaseHelper.deleteData(list.get(postion));
        list.remove(postion);
        MainActivity.notifyAdapter();
    }
}
