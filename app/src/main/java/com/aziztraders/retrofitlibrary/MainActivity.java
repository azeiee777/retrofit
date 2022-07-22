package com.aziztraders.retrofitlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ProgressBar progressBar;
    ApiCalls apiCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        progressBar = findViewById(R.id.progressbar);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                //Acting as preExecute
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        progressBar.setVisibility(View.VISIBLE);
                        tv.setText("Loading....");
                    }
                });

                //Acting as DoInBackground
                //Background work here
                //getting object of API Calls
                apiCalls = NetworkObjects.getApiCalls();
                Call<List<Model>> call = apiCalls.getModels();

                //Acting as postExecute
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        call.enqueue(new Callback<List<Model>>() {
                            @Override
                            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                                tv.setText("");
                                progressBar.setVisibility(View.INVISIBLE);
                                List<Model> data = response.body();
                                for (int i = 0; i < data.size(); i++) {
                                    tv.append("SL No. : " + data.get(i).getId() +
                                            "\nTitle : " + data.get(i).getTitle() + "\n\n\n");
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Model>> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //UI Thread work here
                    }
                });
            }
        });
    }
}