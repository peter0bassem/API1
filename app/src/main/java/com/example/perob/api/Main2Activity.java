package com.example.perob.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TextView toolbartext;
    ProgressDialog progress;
    RecyclerView recyclerView;

    int page = 1;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font.ttf");
        toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setTypeface(custom_font);

        list = new ArrayList();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask {
        //        String url = "http://mazloumtiles.com/wp-json/wp/v2/products?product_group=37&per_page=5";
        String url;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Main2Activity.this);
            progress.setTitle("Loading...");
            progress.setMessage("Loading Data, Please Wait...");
            progress.setIcon(R.drawable.ic_loading);
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            url = "http://mazloumtiles.com/wp-json/wp/v2/products?product_group=37&per_page=5&page=" + page;

            HttpHandler handler = new HttpHandler();
            response = handler.makeServiceCall(url);

            if(response != null){
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");

                        JSONObject titleObject = jsonObject.getJSONObject("title");
                        String title = titleObject.getString("rendered"); // << = title

                        JSONObject imageObject = jsonObject.getJSONObject("better_featured_image");
                        String image = imageObject.getString("source_url");

                        HashMap map = new HashMap();
                        map.put("id", id);
                        map.put("image", image);
                        map.put("title", title);

                        list.add(map);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //
                }
            } else{
                //footer -> text : no data avaailable
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(progress.isShowing())
                progress.dismiss();

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(Main2Activity.this, list);
            recyclerView.setAdapter(adapter);

        }
    }
}
