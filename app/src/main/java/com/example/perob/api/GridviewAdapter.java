package com.example.perob.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by perob on 9/26/2017.
 */

public class GridviewAdapter extends BaseAdapter {

    Context context;
    List list = new ArrayList();
    String id, title, image;

    public GridviewAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            LayoutInflater inflator = LayoutInflater.from(context);
            view = inflator.inflate(R.layout.item_list, viewGroup, false);
        } else
            viewHolder = (ViewHolder) view.getTag();


        viewHolder = new ViewHolder();
        viewHolder.rowItemImageView = (ImageView) view.findViewById(R.id.image);
        viewHolder.rowItemTitleTextView = (TextView) view.findViewById(R.id.title);
        view.setTag(viewHolder);

        HashMap map = (HashMap) list.get(i);
        id = (String) map.get("id");
        image = (String) map.get("image");
        title = (String) map.get("title");

        Picasso.with(context).load(image).into(viewHolder.rowItemImageView);
        viewHolder.rowItemTitleTextView.setText(title);


        return view;
    }

    class ViewHolder {
        ImageView rowItemImageView;
        TextView rowItemTitleTextView;
    }
}
