package com.yunfeng.retrofit2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.yunfeng.demo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit
 * Introduction
 * Retrofit turns your HTTP API into a Java interface.
 * <p>
 * public interface GitHubService {
 *
 * @GET("users/{user}/repos") Call<List<Repo>> listRepos(@Path("user") String user);
 * }
 * The Retrofit class generates an implementation of the GitHubService interface.
 * <p>
 * Retrofit retrofit = new Retrofit.Builder()
 * .baseUrl("https://api.github.com/")
 * .build();
 * <p>
 * GitHubService service = retrofit.create(GitHubService.class);
 * Each Call from the created GitHubService can make a synchronous or asynchronous HTTP request to the remote webserver.
 * <p>
 * Call<List<Repo>> repos = service.listRepos("octocat");
 * Use annotations to describe the HTTP request:
 * <p>
 * URL parameter replacement and query parameter support
 * Object conversion to request body (e.g., JSON, protocol buffers)
 * Multipart request body and file upload
 * <p>
 * Converters can be added to support other types. Six sibling modules adapt popular serialization libraries for your convenience.
 * <p>
 * Gson: com.squareup.retrofit2:converter-gson
 * Jackson: com.squareup.retrofit2:converter-jackson
 * Moshi: com.squareup.retrofit2:converter-moshi
 * Protobuf: com.squareup.retrofit2:converter-protobuf
 * Wire: com.squareup.retrofit2:converter-wire
 * Simple XML: com.squareup.retrofit2:converter-simplexml
 * Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars
 * <p>
 * Created by xll on 2018/9/27.
 */
public class MainActivity extends Activity {
    public static final String API_URL = "https://api.github.com";

    private Retrofit2Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        adapter = new Retrofit2Adapter(this);

        ListView listView = findViewById(R.id.retrofit2_list_view);
        listView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    // Create an instance of our GitHub API interface.
                    GitHubService github = retrofit.create(GitHubService.class);

                    // Create a call instance for looking up Retrofit contributors.
                    Call<List<Contributor>> call = github.contributors("square", "retrofit");

                    // Fetch and print a list of the contributors to the library.
                    List<Contributor> contributors = call.execute().body();
                    for (Contributor contributor : contributors) {
                        System.out.println(contributor.login + " (" + contributor.contributions + ")");
                        add(contributor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void add(final Contributor contributor) {
        new Handler(this.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.addData(contributor);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
