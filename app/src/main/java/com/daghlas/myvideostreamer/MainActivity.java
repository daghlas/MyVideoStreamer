package com.daghlas.myvideostreamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = null;
    RecyclerView videoList;
    VideoAdapter videoAdapter;
    List<VideoModel> all_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all_videos = new ArrayList<>();

        videoList = findViewById(R.id.videoListRecyclerView);
        videoList.setLayoutManager(new LinearLayoutManager(this));

        videoAdapter = new VideoAdapter(this, all_videos);
        videoList.setAdapter(videoAdapter);

        getJsonData();
    }

    private void getJsonData() {
        String url = "https://raw.githubusercontent.com/daghlas/MyStreamer/main/data.json";
        //String url = "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";
        //requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged")
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
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

                    //add the fetched video list to the arrayList
                    all_videos.add(videoModel);
                    videoAdapter.notifyDataSetChanged();

                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Log.d(TAG,"onErrorResponse: " + error.getMessage()));
        //insert request to queue to execute requests
        requestQueue.add(jsonObjectRequest);
    }
}