package com.dadesheng.simplerss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String urlString = "http://www-2.rotman.utoronto.ca/rotmanheadlines/rss.aspx";
        FetchRSSTask fetchTask = new FetchRSSTask();
        fetchTask.execute(urlString);

    }

    class FetchRSSTask extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                RssFeed feed = RssReader.read(url);
                ArrayList<RssItem> rssItems = feed.getRssItems();
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
    }
}
