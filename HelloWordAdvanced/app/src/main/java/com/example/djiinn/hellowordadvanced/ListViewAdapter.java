package com.example.djiinn.hellowordadvanced;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by djiinn on 01.12.2016.
 */
public class ListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListViewAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.content_main, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        // Change the icon for Windows and iPhone
        String s = values[position];
        if (s.startsWith("Salata") || s.startsWith("Hiyar"))
        {
            imageView.setImageResource(R.drawable.transparent_gurke);
        } else {
            imageView.setImageResource(R.drawable.transparent_tomaten);
        }

        return rowView;
    }

}
