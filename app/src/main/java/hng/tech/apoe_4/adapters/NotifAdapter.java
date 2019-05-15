package hng.tech.apoe_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.retrofit.responses.Notif;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {


    //declare array
    private List<Notif> mNotif;
    Context mContext;

    public NotifAdapter(Context mContext, List<Notif> notif) {
        this.mNotif = notif;
        this.mContext =mContext;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class NotifViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nofify;
        public ImageView desc, goBtn;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);

            nofify = itemView.findViewById(R.id.notText);
            desc = itemView.findViewById(R.id.notType);
            goBtn = itemView.findViewById(R.id.notGo);
        }
    }


    public void setBlogList(List<Notif> movieList) {
        this.mNotif = movieList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent. getContext()).inflate(R.layout.notifitem, parent, false);
        return new NotifViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        Notif notif =mNotif.get(position);

        holder.nofify.setText(notif.getNotification());
        if (notif.getType().equals("1")){
            holder.desc.setImageResource(R.drawable.ic_volume_up_black_24dp);


        }else if (notif.getType().equals("2")){
            holder.desc.setImageResource(R.drawable.ic_timer_black_24dp);

        }else if (notif.getType().equals("3")){
            holder.desc.setImageResource(R.drawable.ic_trending_up_black_24dp);

        }else if (notif.getType().equals("4")){
            holder.desc.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        holder.goBtn.setOnClickListener(view -> {
            //this is a placeholder
            Toast.makeText(view.getContext(), "Notify me", Toast.LENGTH_SHORT).show();
        });

    }



    @Override
    public int getItemCount() {

        return mNotif.size();
    }
}
