package com.technoabinash.networkaccess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
   List<NewsHeadlineModel> headlineModels;
    Context context;

    public NewsAdapter(List<NewsHeadlineModel> headlineModels, Context context) {
        this.headlineModels = headlineModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsHeadlineModel model=headlineModels.get(position);
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getContent());
        holder.source.setText(model.getSource());

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_baseline_signal_wifi_off_24).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return headlineModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView title,desc,source;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage=itemView.findViewById(R.id.imageNews);
            title=itemView.findViewById(R.id.textNewsTitle);
            desc=itemView.findViewById(R.id.textNewsContent);
            source=itemView.findViewById(R.id.textNewsSource);
        }
    }
}
