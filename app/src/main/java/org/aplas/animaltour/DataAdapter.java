package org.aplas.animaltour;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>implements ItemMoveCallback.ItemTouchHelperContract {
    private ArrayList<DataItem> itemList;
    private Context mContext;
    private OnItemClickListener mListener;
    // Constructor of the class
    public DataAdapter(Context context, ArrayList<DataItem> itemList) {
        mContext = context;
        this.itemList = itemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).
                inflate(R.layout.layout_data, parent, false);
        return new ViewHolder(v,mListener);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DataItem currData = itemList.get(position);
        holder.bindTo(currData);
    }
    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
    }
    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView info;
        public ImageView icon;
        public View rowView;
        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.animalTitle);
            info = (TextView) itemView.findViewById(R.id.animalInfo);
            icon = (ImageView) itemView.findViewById(R.id.animalIcon);
            rowView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v,position);
                        }
                    }
                }
            });
        }
        public void bindTo(DataItem currData){
            title.setText(currData.getTitle());
            title.setBackgroundColor(currData.getColor());
            info.setText(currData.getInfo());
            icon.setBackgroundResource(currData.getIcon());
            AnimationDrawable anim = (AnimationDrawable) icon.getBackground();
            anim.start();
        }
    }
}