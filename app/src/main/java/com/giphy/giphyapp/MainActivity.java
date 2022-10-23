package com.giphy.giphyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.giphy.giphyapp.adapter.GiphyRVAdapter;
import com.giphy.giphyapp.model.GiphyItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArrayList<GiphyItemModel> giphyItemModelArrayList;
    private GiphyRVAdapter giphyRVAdapter;
    private RecyclerView giphyRV;
    private ProgressBar loadingPB;
    int offset = 0, totalCount = 10, pageItemCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giphyItemModelArrayList = new ArrayList<>();

        giphyRV = findViewById(R.id.idRVGiphy);
        loadingPB = findViewById(R.id.idPBLoading);
        NestedScrollView nestedSV = findViewById(R.id.idNestedSV);

        getDataFromAPI();

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    offset += pageItemCount;
                    loadingPB.setVisibility(View.VISIBLE);
                    getDataFromAPI();
                }
            }
        });
    }

    private void getDataFromAPI() {
        if (offset > totalCount) {
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }

        String API_KEY = "EEjeWKnay8eNwJ091mC2ffGuQe96tdBN";
        String url = "https://api.giphy.com/v1/gifs/trending?api_key=" + API_KEY + "&limit=" + pageItemCount + "&offset=" + offset;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        giphyItemModelArrayList.add(new GiphyItemModel(jsonObject.getString("title"), Objects.requireNonNull(jsonObject.optJSONObject("images")).getJSONObject("original").getString("url")));

                        giphyRVAdapter = new GiphyRVAdapter(giphyItemModelArrayList, MainActivity.this);

                        giphyRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        giphyRV.setAdapter(giphyRVAdapter);
                    }

                    JSONObject paginationInfo = response.getJSONObject("pagination");
                    totalCount = paginationInfo.getInt("total_count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}