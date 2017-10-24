package com.example.perob.api;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private GridView gridview;
    private ProgressDialog progress;

    private List list;

    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        list = new ArrayList();
        gridview = (GridView) findViewById(R.id.gridview);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask {
        String url;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Main3Activity.this);
            progress.setTitle("Loading...");
            progress.setMessage("Loading Data, Please Wait...");
            progress.setIcon(R.drawable.ic_loading);
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            url = "http://mazloumtiles.com/wp-json/wp/v2/products?product_group=37&per_page=100&page=" + page;
            HttpHandler handler = new HttpHandler();
            response = handler.makeServiceCall(url);

            if(response != null){
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
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

            GridviewAdapter gridviewAdapter = new GridviewAdapter(Main3Activity.this, list);
            gridview.setAdapter(gridviewAdapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    HashMap map = (HashMap) list.get(i);
                    String title = (String) map.get("title");
                    String id = (String) map.get("id");
                    Toast.makeText(Main3Activity.this, id + " : " + title, Toast.LENGTH_SHORT).show();
                }
            });
            gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    HashMap map = (HashMap) list.get(i);
                    String id = (String) map.get("id");
                    Toast.makeText(Main3Activity.this, "Long click on item with id: " +id +"!" , Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
}
