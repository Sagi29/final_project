package com.example.final_project.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;

import java.util.ArrayList;


public class AdapterFetch extends  RecyclerView.Adapter<AdapterFetch.ViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();



    public void update(String name, String url){
        items.add(name);
        urls.add(url);
        notifyDataSetChanged();
    }
    public AdapterFetch(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }

    @NonNull
    @Override
    public AdapterFetch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_item_files, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFetch.ViewHolder holder, int position) {

        holder.fetchItemFile_LBL_name.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView fetchItemFile_LBL_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fetchItemFile_LBL_name = itemView.findViewById(R.id.fetchItemFile_LBL_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int pos = recyclerView.getChildLayoutPosition(view);
                   // Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urls.get(pos)));
                    //intent.setType(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse(urls.get(pos)));
                    //context.startActivity(intent);
                }
            });
        }
    }
}
