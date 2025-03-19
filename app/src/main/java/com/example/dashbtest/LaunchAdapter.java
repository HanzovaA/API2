package com.example.dashbtest;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchAdapter.ViewHolder> {
    private List<Launch> launchList;

    public LaunchAdapter(List<Launch> launchList) {
        this.launchList = launchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_launch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Launch launch = launchList.get(position);
        holder.name.setText(launch.getName());
        holder.rocket.setText("Raketa: " + launch.getRocket().getConfiguration().getName());
        holder.pad.setText("Startovací rampa: " + launch.getPad().getName());
        holder.date.setText("Čas startu: " + launch.getWindowStart());

        Picasso.get().load(launch.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rocket, pad, date;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.launch_name);
            rocket = itemView.findViewById(R.id.launch_rocket);
            pad = itemView.findViewById(R.id.launch_pad);
            date = itemView.findViewById(R.id.launch_date);
            image = itemView.findViewById(R.id.launch_image);
        }
    }
}

