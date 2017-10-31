package com.re_coded.example.android.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 10/31/2017.
 */

public class NewsUtils {
    public static ArrayList<News> parseNewsJSON(String json) {
        ArrayList<News> news = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String title = "";
                String section = "";
                String date = "";
                String author = "";
                String URL = "";
                if (result.has("webTitle"))
                    title = result.getString("webTitle");

                if (result.has("sectionName"))
                    section = result.getString("sectionName");

                if (result.has("webUrl"))
                    URL = result.getString("webUrl");

                if (result.has("webPublicationDate"))
                    date = result.getString("webPublicationDate");

                if (result.has("tags")) {
                    if (result.getJSONArray("tags").length() > 0) {
                        JSONObject tags = result.getJSONArray("tags").getJSONObject(0);
                        if (tags.has("firstName")) {
                            author = tags.getString("firstName");
                        }

                        if (tags.has("lastName"))
                            author += " " + tags.getString("lastName");

                    }
                }

                news.add(new News(title, section, author, date, URL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
