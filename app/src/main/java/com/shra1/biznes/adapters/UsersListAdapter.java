package com.shra1.biznes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shra1.biznes.R;
import com.shra1.biznes.webservices.ListOfAllUsersTask;

import java.util.List;

public class UsersListAdapter extends BaseAdapter {
    Context mCtx;
    List<ListOfAllUsersTask.ResponseDto.Details> l;
    ULACallbacks c;

    public UsersListAdapter(Context mCtx, List<ListOfAllUsersTask.ResponseDto.Details> l, ULACallbacks c) {
        this.mCtx = mCtx;
        this.l = l;
        this.c = c;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Object getItem(int position) {
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ULAViewHolder h;
        if (v == null) {
            v = LayoutInflater.from(mCtx).inflate(R.layout.users_list_item_layout, parent, false);
            h = new ULAViewHolder(v);
            v.setTag(h);
        } else {
            h = (ULAViewHolder) v.getTag();
        }

        ListOfAllUsersTask.ResponseDto.Details d = (ListOfAllUsersTask.ResponseDto.Details) getItem(position);

        h.tvULILId.setText(d.getUserId());
        h.tvULILUsername.setText(d.getUserName());
        h.tvULILFullname.setText(d.getFirstName() + " " + d.getLastName());

        v.setOnClickListener(view -> c.onClick(d));

        return v;
    }

    public interface ULACallbacks {
        public void onClick(ListOfAllUsersTask.ResponseDto.Details d);
    }

    class ULAViewHolder {
        TextView tvULILId;
        TextView tvULILUsername;
        TextView tvULILFullname;

        public ULAViewHolder(View v) {
            tvULILId = v.findViewById(R.id.tvULILId);
            tvULILUsername = v.findViewById(R.id.tvULILUsername);
            tvULILFullname = v.findViewById(R.id.tvULILFullname);
        }
    }
}
