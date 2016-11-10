package com.dadesheng.simplerss;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class ContentActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private String title;
    private String content;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent i = getIntent();
        title = i.getStringExtra("itemTitle");
        content = i.getStringExtra("itemDescription");
        link = i.getStringExtra("itemLink");

        TextView txtTitle = (TextView) findViewById(R.id.labelContentTitle);
        TextView txtContent = (TextView) findViewById(R.id.labelContent);

        txtTitle.setText(title);
        txtContent.setText(Html.fromHtml(content));
        txtContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_link:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
                break;
            case R.id.menu_share:
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
                bundle.putString(FirebaseAnalytics.Param.VALUE, link);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(link));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_link_share, menu);
        return true;
    }

}