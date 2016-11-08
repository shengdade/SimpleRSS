package com.dadesheng.simplerss;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class HeadlinesActivity extends AppCompatActivity {
    private static final String TAG = "HeadlinesActivity";
    ArrayList<RssItem> rssItems;
    ListView listView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isFirstTime = MyPreferences.isFirst(HeadlinesActivity.this);
        if (isFirstTime) {
            startActivity(new Intent(context, AboutActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines);
        listView = (ListView) findViewById(R.id.headline_list);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        RssItem item = (RssItem) listView.getAdapter().getItem(position);
                        Intent viewItem = new Intent(context, ContentActivity.class);
                        viewItem.putExtra("itemTitle", item.getTitle());
                        viewItem.putExtra("itemDescription", item.getDescription());
                        viewItem.putExtra("itemLink", item.getLink());
                        startActivity(viewItem);
                    }
                }
        );

        String urlString = "http://www-2.rotman.utoronto.ca/rotmanheadlines/rss.aspx";
        FetchRSSTask fetchTask = new FetchRSSTask();
        fetchTask.execute(urlString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(context, AboutActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    class FetchRSSTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                RssFeed feed = RssReader.read(url);
                rssItems = feed.getRssItems();
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
            listView.setAdapter(adapter);
        }
    }
}
