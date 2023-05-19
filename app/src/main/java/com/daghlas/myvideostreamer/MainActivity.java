package com.daghlas.myvideostreamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = null;
    RecyclerView videoList;
    VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoList = findViewById(R.id.videoListRecyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));

        videoAdapter = new VideoAdapter();
        videoList.setAdapter(videoAdapter);

        getJsonData();
    }

    private void getJsonData() {
        String url = "https://github.com/daghlas/MyStreamer/blob/main/data.json";
        //requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                    //extract all video data from list of videos
                    for(int i = 0; i < videos.length(); i++){
                        JSONObject video = videos.getJSONObject(i);

                        //use ModelClass to get and set videos to app
                        VideoModel videoModel = new VideoModel();
                        videoModel.setTitle(video.getString("title"));
                        videoModel.setDescription(video.getString("description"));
                        videoModel.setAuthor(video.getString("subtitle"));
                        videoModel.setImageUri(video.getString("thumb"));
                        //
                        JSONArray videoUrl = video.getJSONArray("sources");
                        videoModel.setVideoUri(videoUrl.getString(0));

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"onErrorResponse: " + error.getMessage());
            }
        });
        //insert request to queue to execute requests
        requestQueue.add(jsonObjectRequest);
    }
}