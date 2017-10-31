package com.re_coded.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    ListView newsList;
    NewsArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = (ListView) findViewById(R.id.news_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.reload_news);

        swipeRefreshLayout.setRefreshing(true);
        excuteLoaderTask();

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News item = (News) adapterView.getItemAtPosition(i);
                Uri uri = Uri.parse(item.getURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                excuteLoaderTask();
            }
        });
    }

    public void excuteLoaderTask() {
        if (NewsUtils.isNetworkAvailable(this)) {

            final String API_URL = "http://content.guardianapis.com/search";
            final String API_KEY = "test";
            final String REQUIRED_TAG = "contributor";
            final String SEARCH_QUERY = "world";
            final String ORDER_BY = "newest";

            Uri uri = Uri.parse(API_URL);
            Uri.Builder uriBuilder = uri.buildUpon();
            uriBuilder.appendQueryParameter("q", SEARCH_QUERY);
            uriBuilder.appendQueryParameter("api-key", API_KEY);
            uriBuilder.appendQueryParameter("show-tags", REQUIRED_TAG);
            uriBuilder.appendQueryParameter("order-by", ORDER_BY);

            Bundle bundle = new Bundle();
            bundle.putString("url", uriBuilder.toString());

            if (getLoaderManager().getLoader(1) != null) {
                getLoaderManager().restartLoader(1, bundle, this).forceLoad();
            } else {
                getLoaderManager().initLoader(1, bundle, this).forceLoad();
            }

        } else {
            Toast.makeText(this, R.string.no_internet_connection_message, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void updateUI(ArrayList<News> news) {
        adapter = new NewsArrayAdapter(this, R.layout.news_list_item, news);
        newsList.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, bundle.getString("url"));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> newses) {
        updateUI(newses);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        adapter.clear();
    }
}
