package com.example.perob.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main1Activity extends AppCompatActivity {

    List list;
    int page = 1;
    ListView listview;
    TextView toolbartext;
    ProgressDialog progress;
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font.ttf");

        listview = (ListView) findViewById(R.id.listview);
        toolbartext = (TextView) findViewById(R.id.toolbartext);

        toolbartext.setTypeface(custom_font);

        list = new ArrayList();

        footerView =  ((LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer_loadmore, null, false);
//        footerView.setVisibility(View.GONE);
        listview.addFooterView(footerView);

        Button loadmore = (Button) footerView.findViewById(R.id.loadmore_btn);
        loadmore.setTypeface(custom_font);

        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
               new LoadData().execute(page);
            }
        });

        new LoadData().execute(page);
    }

    private class LoadData extends AsyncTask{
//        String url = "http://mazloumtiles.com/wp-json/wp/v2/products?product_group=37&per_page=5";
        String url;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Main1Activity.this);
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

           /* ListAdapter adapter = new ListViewAdapter(Main1Activity.this,
                    list,
                    R.layout.item_list,
                    new String[]{"image, title"},
                    new int[] {R.id.image, R.id.title});*/

           ListAdapter adapter = new ListViewAdapter(list, Main1Activity.this);

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {String position = list.get(i).toString() ;
                    HashMap map = (HashMap) list.get(i);
                    String title = (String) map.get("title");
                    String id = (String) map.get("id");
                    Toast.makeText(Main1Activity.this, id + " : " + title, Toast.LENGTH_SHORT).show();
                }
            });
            int count = listview.getCount() - 5;
            listview.setSelection(count);

//            footerView.setVisibility(View.VISIBLE);
        }
    }
}
