package com.example.final_project.Data;

import android.content.Context;
import android.util.Log;
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
    //private ExerciseItemClickListener exerciseItemClickListener;

    // data is passed into the constructor
    public Adapter_Song(Context context, ArrayList<Song> data) {
        this.mInflater = LayoutInflater.from(context);
        this.myData = data;
        this.context = context;
    }

    /*public void setClickListeners(ExerciseItemClickListener exerciseItemClickListener) {
        this.exerciseItemClickListener = exerciseItemClickListener;
    }*/

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_song, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = myData.get(position);

       /* String link = "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png";


        if (position > 10) {
            Glide
                    .with(context)
                    .load(link)
                    .centerCrop()
                    .into(holder.exercise_IMG_icon);
        }*/


        holder.song_LBL_name.setText(song.getName());
        holder.song_LBL_length.setText("" + song.getLength());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return myData.size();
    }

    // convenience method for getting data at click position
    Song getItem(int position) {
        return myData.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        //private ShapeableImageView exercise_IMG_icon;
        private TextView song_LBL_name;
        private TextView song_LBL_length;

        ViewHolder(View itemView) {
            super(itemView);
            //exercise_IMG_icon = itemView.findViewById(R.id.exercise_IMG_icon);
            song_LBL_name = itemView.findViewById(R.id.song_LBL_name);
            song_LBL_length = itemView.findViewById(R.id.song_LBL_length);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (exerciseItemClickListener != null) {
                        exerciseItemClickListener.itemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            }); */
        }

    }

    /*public interface ExerciseItemClickListener {
        void itemClicked(Exercise exercise, int position);
    }*/
}