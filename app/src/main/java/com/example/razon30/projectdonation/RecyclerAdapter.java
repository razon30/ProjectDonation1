package com.example.razon30.projectdonation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by razon30 on 20-02-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderImages> {

    ArrayList<Integer> imageList = new ArrayList<Integer>();
    Context context;
    private LayoutInflater layoutInflater;

    public RecyclerAdapter(ArrayList<Integer> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerAdapter.ViewHolderImages onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_recycler, parent, false);
        ViewHolderImages  viewHolder = new ViewHolderImages (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolderImages holder, int position) {

        Picasso.with(context).load(imageList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        public ViewHolderImages(View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.image4);
        }
    }
}
