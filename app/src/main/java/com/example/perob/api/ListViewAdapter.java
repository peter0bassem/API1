package com.example.perob.api;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by perob on 9/3/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    List list = new ArrayList();
    List lstData = new ArrayList();
    Context context;

    String id, title, image;

   /* public ListViewAdapter(Context context, List data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.lstData = data;
    }*/

    public ListViewAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font.ttf");

        //smart initialization
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.item_list, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        HashMap map = (HashMap) list.get(position);
        id = (String) map.get("id");
        image = (String) map.get("image");
        title = (String) map.get("title");

        Picasso.with(context).load(image).into(holder.image);
        holder.title.setTypeface(custom_font);
        holder.title.setText(title);

      /*  holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, id + " : " + title, Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }

    /* @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();

        if(view == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.item_list, viewGroup, false);

            holder.image = (ImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.findViewById(R.id.title);

            HashMap map = (HashMap) list.get(i);
            String title = map.get("title").toString();
            String image = (String) map.get("iamge");

            holder.title.setText(title);
            Picasso.with(context).load(image).resize(500, 500).centerCrop().into(holder.image);
        }else
            holder = (ViewHolder) view.getTag();

        return view;
    }*/

    private class ViewHolder {
        ImageView image;
        TextView title;
    }
}
