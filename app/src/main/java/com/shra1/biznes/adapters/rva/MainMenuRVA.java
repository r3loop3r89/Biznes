package com.shra1.biznes.adapters.rva;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shra1.biznes.R;
import com.shra1.biznes.dtos.MainMenuItem;

import java.util.List;

public class MainMenuRVA extends RecyclerView.Adapter<MainMenuRVA.MMRVAViewHolder> {
    Context mCtx;
    List<MainMenuItem> mainMenuItems;

    public MainMenuRVA(Context mCtx, List<MainMenuItem> mainMenuItems) {
        this.mCtx = mCtx;
        this.mainMenuItems = mainMenuItems;
    }

    @NonNull
    @Override
    public MMRVAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.main_menu_recyclerview_item_layout, parent, false);
        return new MMRVAViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MMRVAViewHolder holder, int position) {
        MainMenuItem i = mainMenuItems.get(position);
        if (i.getBadgeCount() == 0) {
            holder.tvMMRVILBadgeCount.setVisibility(View.GONE);
        } else {
            holder.tvMMRVILBadgeCount.setVisibility(View.VISIBLE);
        }

        if (i.getIconResource() == 0) {

        } else {
            holder.ivMMRVILIcon.setImageResource(i.getIconResource());
        }

        holder.tvMMRVILBadgeCount.setText("" + i.getBadgeCount());
        holder.tvMMRVILTitle.setText(i.getTitle());
        holder.rlMMRVILView.setOnClickListener(i.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return mainMenuItems.size();
    }

    class MMRVAViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMMRVILIcon;
        private TextView tvMMRVILTitle;
        private TextView tvMMRVILBadgeCount;
        private RelativeLayout rlMMRVILView;

        public MMRVAViewHolder(View v) {
            super(v);

            ivMMRVILIcon = (ImageView) v.findViewById(R.id.ivMMRVILIcon);
            tvMMRVILTitle = (TextView) v.findViewById(R.id.tvMMRVILTitle);
            tvMMRVILBadgeCount = (TextView) v.findViewById(R.id.tvMMRVILBadgeCount);
            rlMMRVILView = v.findViewById(R.id.rlMMRVILView);
        }
    }
}
