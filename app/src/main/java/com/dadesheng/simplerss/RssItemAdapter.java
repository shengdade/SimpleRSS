package com.dadesheng.simplerss;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            holder.txtTime = (TextView) row.findViewById(R.id.labelTime);
            row.setTag(holder);
        } else {
            holder = (ItemHolder) row.getTag();
        }

        RssItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtTime.setText(getDate(item.getPubDate()));

        return (row);
    }

    private String getDate(Date pubDate) {
        Calendar today = Calendar.getInstance();
        Calendar publishDate = Calendar.getInstance();
        publishDate.setTime(pubDate);
        boolean sameDay = today.get(Calendar.YEAR) == publishDate.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == publishDate.get(Calendar.DAY_OF_YEAR);
        publishDate.add(Calendar.DAY_OF_YEAR, 1);
        boolean yesterday = today.get(Calendar.YEAR) == publishDate.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == publishDate.get(Calendar.DAY_OF_YEAR);
        if (sameDay) {
            return "today";
        } else if (yesterday) {
            return "yesterday";
        } else {
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
            return simpleDate.format(pubDate);
        }
    }

    private static class ItemHolder {
        TextView txtTitle;
        TextView txtTime;
    }
}
