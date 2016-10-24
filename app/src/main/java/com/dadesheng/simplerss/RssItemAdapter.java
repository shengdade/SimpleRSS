package com.dadesheng.simplerss;

import android.app.ListActivity;
import android.content.Context;
import android.text.Html;
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
            LayoutInflater inflater = ((ListActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ItemHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.labelTitle);
            holder.txtDescription = (TextView) row.findViewById(R.id.labelDescription);
            row.setTag(holder);
        } else {
            holder = (ItemHolder) row.getTag();
        }

        RssItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtDescription.setText(Html.fromHtml(item.getDescription()));

        return (row);
    }

    private static class ItemHolder {
        TextView txtTitle;
        TextView txtDescription;
    }
}
