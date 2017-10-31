package com.re_coded.example.android.newsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 10/31/2017.
 */

public class NewsArrayAdapter extends ArrayAdapter<News> {
    private int layoutResource;

    public NewsArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            holder = new NewsHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title_text);
            holder.section = (TextView) convertView.findViewById(R.id.section_text);
            holder.date = (TextView) convertView.findViewById(R.id.date_text);
            holder.author = (TextView) convertView.findViewById(R.id.author_text);
            convertView.setTag(holder);
        } else {
            holder = (NewsHolder) convertView.getTag();
        }

        News item = getItem(position);
        holder.title.setText(item.getTitle());
        holder.section.setText(item.getSection());
        holder.date.setText(item.getDate());
        holder.author.setText(item.getAuthor());

        return convertView;
    }

    static class NewsHolder {
        TextView title;
        TextView section;
        TextView date;
        TextView author;
    }
}
