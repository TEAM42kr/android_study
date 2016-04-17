package com.example.sungsoo.retrofittest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sungsoo.retrofittest.mafia42.Channel;
import com.example.sungsoo.retrofittest.stackoverflow.Question;
import com.example.sungsoo.retrofittest.stackoverflow.StackOverflowQuestions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.beanutils.BeanUtils;

public class MainActivity extends ActionBarActivity implements Callback, View.OnClickListener{


    public static final String TARGET_API_URL = "https://api.stackexchange.com";
    public static final String TARGET_API_URL2 = "http://mafia42.co.kr";

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    Handler mainHandler;
    @Bind(R.id.mafia42ListView)
    ListView mafiaListView;

    @Bind(R.id.stackoverflowListView)
    ListView stackoverflowListView;

    @Bind(R.id.mafia42SearchButton)
    Button Mmafia42SearchButton;

    @Bind(R.id.stackoverflowSearchButton)
    Button stackoverflowSearchButton;
    @Bind(R.id.editText)
    EditText stackoverflowTagEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainHandler = new Handler();
        ButterKnife.bind(this);
        Mmafia42SearchButton.setOnClickListener(this);
        stackoverflowSearchButton.setOnClickListener(this);
        setProgressBarIndeterminateVisibility(true);
        requestStackoverflowQuestionList(stackoverflowTagEditText.getText().toString());
        requestMafia42ChannelList();
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(final Response response) {
                try {
                    Object responseVo;
                    if (response.request().url().getHost().contains("stack")) {
                        final List<Question> questionList = ((StackOverflowQuestions) gson.fromJson(response.body().string(), StackOverflowQuestions.class)).getItems();
                        if (questionList.size() == 0) {
                            Toast.makeText(MainActivity.this, "no search results", Toast.LENGTH_SHORT).show();
                        }
                        final ArrayAdapter adapter = new ArrayAdapter<Question>(MainActivity.this, android.R.layout.simple_list_item_1, questionList) {
                            @Override
                            public int getCount() {
                                return Math.min(3, questionList.size());
                            }
                        };
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                stackoverflowListView.setAdapter(adapter);
                            }
                        });
                    } else {
                        List<Map<String,String>> rawList = gson.fromJson(response.body().string(), List.class);
                        List<Channel> channelList = new ArrayList<>();
                        for(Map<String,String> rawMap : rawList){
                            Channel channelData = new Channel();
                            channelData.setChannel_id(rawMap.get("channel_id"));
                            channelData.setUser_count(rawMap.get("user_count"));
                            channelList.add(channelData);
                        }
                       final ArrayAdapter adapter = new ArrayAdapter<Channel>(MainActivity.this, android.R.layout.simple_list_item_1,channelList  );

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                            mafiaListView.setAdapter(adapter);
                            }
                        });
                    }
                }catch(Exception e){
                    Log.e("test", "test", e);
            }


    }


    private void requestStackoverflowQuestionList(String tag){
        Request request = new Request.Builder()
                .url(TARGET_API_URL + "/2.2/questions?order=desc&sort=creation&site=stackoverflow&tagged=" + tag)
                .build();
        client.newCall(request).enqueue(this);

    }


    private void requestMafia42ChannelList(){
        Request request = new Request.Builder()
                .url(TARGET_API_URL2 + "/channel.php")
                .build();
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mafia42SearchButton:
                requestMafia42ChannelList();
                break;
            case R.id.stackoverflowSearchButton:
                requestStackoverflowQuestionList(stackoverflowTagEditText.getText().toString());
                break;
        }

    }
}
