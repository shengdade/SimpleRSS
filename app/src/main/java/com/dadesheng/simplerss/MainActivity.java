package com.dadesheng.simplerss;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class MainActivity extends ListActivity {
    private static final String TAG = "MainActivity";
    ArrayList<RssItem> rssItems;
    ListView listView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String urlString = "http://www-2.rotman.utoronto.ca/rotmanheadlines/rss.aspx";
        FetchRSSTask fetchTask = new FetchRSSTask();
        fetchTask.execute(urlString);
        listView = getListView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RssItem item = (RssItem) listView.getAdapter().getItem(position);
        String link = item.getLink();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    class FetchRSSTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                RssFeed feed = RssReader.read(url);
                rssItems = feed.getRssItems();
                for (RssItem rssItem : rssItems) {
                    Log.i("RSS Reader", rssItem.getTitle());
                }

            } catch (MalformedURLException e) {
                Log.e(TAG, "Malformed URL. " + e.getMessage());
                e.printStackTrace();
            } catch (SAXException e) {
                Log.e(TAG, "SAXException. " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException. " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            RssItemAdapter adapter = new RssItemAdapter(context, R.layout.item_row, rssItems);
            setListAdapter(adapter);
        }
    }
}
