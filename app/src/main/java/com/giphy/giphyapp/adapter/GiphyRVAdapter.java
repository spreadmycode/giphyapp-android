package com.giphy.giphyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giphy.giphyapp.R;
import com.giphy.giphyapp.model.GiphyItemModel;
import java.util.ArrayList;

public class GiphyRVAdapter extends RecyclerView.Adapter<GiphyRVAdapter.ViewHolder> {

    private final ArrayList<GiphyItemModel> giphyModelArrayList;
    private final Context context;

    public GiphyRVAdapter(ArrayList<GiphyItemModel> giphyModalArrayList, Context context) {
        this.giphyModelArrayList = giphyModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.giphy_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GiphyItemModel itemModel = giphyModelArrayList.get(position);

        holder.titleTV.setText(itemModel.getTitle());
        Glide.with(context).load(itemModel.getGifUrl()).into(holder.giphyIV);
    }

    @Override
    public int getItemCount() {
        return giphyModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final ImageView giphyIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.idTVTitle);
            giphyIV = itemView.findViewById(R.id.idIVGiphy);
        }
    }
}
