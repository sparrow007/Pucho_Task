package com.jackandphantom.stackquestion.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jackandphantom.stackquestion.Interface.StackApi;
import com.jackandphantom.stackquestion.R;
import com.jackandphantom.stackquestion.Utils.SimpleDividerItemDecoration;
import com.jackandphantom.stackquestion.adapter.TagDataAdapter;
import com.jackandphantom.stackquestion.model.TagData;
import com.jackandphantom.stackquestion.model.TagResponse;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This activity calls the stackoverflow api for tag and list them
 * user will select the 4 tags and then move to another tag
 * also have the condition if they selected hwo many tag at a time
 * it must be 4 tag selected by user.
 * */
public class TagSelectionActivity extends AppCompatActivity implements TagDataAdapter.OnClickListener {

   private Retrofit retrofit;
   private RecyclerView recyclerView;
   //These are selected tags from the user on which they get the questions
   private String [] tags;
   private TagContainerLayout mTagContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        * When user don't have the internet connection then it will goes for offline Activity
        * */
        if (!haveNetworkConnection()) {
            Intent intent = new Intent(TagSelectionActivity.this, OfflineActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_tag_selection);
        tags = new String[4];
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        recyclerView = findViewById(R.id.tag_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(TagSelectionActivity.this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tags[3] != null && !tags[3].isEmpty()) {
                    Intent intent = new Intent(TagSelectionActivity.this, QuestionListActivity.class);
                    intent.putExtra("SELECTED_TAG", tags);
                    startActivity(intent);
                }else {
                    Toast.makeText(TagSelectionActivity.this, "Select minimum 4 tags", Toast.LENGTH_SHORT).show();
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        callForTags(1);
    }

    /*
    * Using the network api for checking if user connected to either wifi or internet
    * If it gets both are not working means user is offline
    * */
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    /*
    * This method is calling the stackoverflow api for the getting the tags then
    * it will be listed on the reyclerview for selection the tags
    * */
    private void callForTags(int page) {
        retrofit.create(StackApi.class).stackTagCall(page, "desc", "popular", "stackoverflow")
                .enqueue(new Callback<TagResponse>() {
                    @Override
                    public void onResponse(Call<TagResponse> call, Response<TagResponse> response) {
                        if (response.isSuccessful()) {
                            TagResponse tagResponse = response.body();
                            List<TagData> list = tagResponse.getItems();
                            TagDataAdapter tagDataAdapter = new TagDataAdapter(list, TagSelectionActivity.this);
                            recyclerView.setAdapter(tagDataAdapter);
                            tagDataAdapter.setOnClickListener(TagSelectionActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<TagResponse> call, Throwable t) {
                     t.printStackTrace();
                    }
                });
    }

    //This onclick method has condition on which if user has already selected the tag or not
    @Override
    public void onClick(String tag, int position) {
        //Searching for existing
        for (String a : tags){
            if (a != null)
            if (a.equals(tag)) {
                Toast.makeText(this, "Already selected tag", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        for (int i = 3; i > 0; i--)
            tags[i] = tags[i-1];

        tags[0] = tag;
        mTagContainerLayout.setTags(tags);
    }
}
