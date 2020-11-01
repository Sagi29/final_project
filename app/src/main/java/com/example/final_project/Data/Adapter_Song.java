package com.example.final_project.Data;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.final_project.R;
import java.util.ArrayList;


public class Adapter_Song extends RecyclerView.Adapter<Adapter_Song.ViewHolder> {

    private ArrayList<Song> myData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public Adapter_Song(Context context, ArrayList<Song> data) {
        this.mInflater = LayoutInflater.from(context);
        this.myData = data;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = myData.get(position);

        holder.song_LBL_name.setText(song.getName());
        holder.song_LBL_length.setText("" + song.getLength());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return myData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView song_LBL_name;
        private TextView song_LBL_length;

        ViewHolder(View itemView) {
            super(itemView);
            song_LBL_name = itemView.findViewById(R.id.song_LBL_name);
            song_LBL_length = itemView.findViewById(R.id.song_LBL_length);

        }

    }

}