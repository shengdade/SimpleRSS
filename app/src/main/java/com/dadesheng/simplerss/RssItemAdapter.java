package com.dadesheng.simplerss;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.matshofman.saxrssreader.RssItem;

public class RssItemAdapter extends ArrayAdapter<RssItem> {

    private Context context;
    private int layoutResourceId;
    private List<RssItem> data = null;

    public RssItemAdapter(Context context, int layoutResourceId, List<RssItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ItemHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.labelTitle);
            row.setTag(holder);
        } else {
            holder = (ItemHolder) row.getTag();
        }

        RssItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());

        return (row);
    }

    private static class ItemHolder {
        TextView txtTitle;
    }
}
