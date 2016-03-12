package com.example.sungsoo.retrofittest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sungsoo.retrofittest.mafia42.Channel;
import com.example.sungsoo.retrofittest.mafia42.Mafia42API;
import com.example.sungsoo.retrofittest.stackoverflow.Question;
import com.example.sungsoo.retrofittest.stackoverflow.StackOverflowAPI;
import com.example.sungsoo.retrofittest.stackoverflow.StackOverflowQuestions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends ActionBarActivity implements Callback  , View.OnClickListener{


    public static final String TARGET_API_URL = "https://api.stackexchange.com";
    public static final String TARGET_API_URL2 = "http://mafia42.co.kr";

    Retrofit stackoverlowRetrofit ;
    Retrofit mafia42Retrofit2;

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
        ButterKnife.bind(this);
        Mmafia42SearchButton.setOnClickListener(this);
        stackoverflowSearchButton.setOnClickListener(this);
        setProgressBarIndeterminateVisibility(true);
        stackoverlowRetrofit = new Retrofit.Builder()
                .baseUrl(TARGET_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mafia42Retrofit2 = new Retrofit.Builder()
                .baseUrl(TARGET_API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestStackoverflowQuestionList(stackoverflowTagEditText.getText().toString());
        requestMafia42ChannelList();
    }
    @Override
    public void onResponse(Response response, Retrofit retrofit) {
        Object responseVo = response.body();
        if(responseVo instanceof StackOverflowQuestions){
            final List<Question> questionList = ( (StackOverflowQuestions)responseVo).getItems();
            if(questionList.size()==0){
                Toast.makeText(MainActivity.this,"no search results", Toast.LENGTH_SHORT).show();
            }
           ArrayAdapter adapter= new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_1,questionList){
               @Override
               public int getCount() {
                   return Math.min(3, questionList.size());
               }
           };
            stackoverflowListView.setAdapter(adapter);
        }else{
            ArrayAdapter adapter= new ArrayAdapter<Channel>(this, android.R.layout.simple_list_item_1,( (List<Channel>)responseVo));
            mafiaListView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void requestStackoverflowQuestionList(String tag){
        StackOverflowAPI stackOverflowAPI = stackoverlowRetrofit.create(StackOverflowAPI.class);

        Call<StackOverflowQuestions> stackoverflowCall = stackOverflowAPI.loadQuestions(tag);
        stackoverflowCall.enqueue(this);
    }

    private void requestMafia42ChannelList(){

        Mafia42API mafia42API = mafia42Retrofit2.create(Mafia42API.class);

        Call<List<Channel>> mafiaCall = mafia42API.loadChannelList();
        mafiaCall.enqueue(this);
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
