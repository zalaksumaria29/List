package com.example.anew.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anew.Adapter.UserRVAdapter;
import com.example.anew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int page = 1;
    // creating a variable for our array list, adapter class,
    // recycler view, progressbar, nested scroll view
    private ArrayList<UserModal> userModalArrayList;
    private UserRVAdapter userRVAdapter;
    private RecyclerView userRV;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating a new array list.
        userModalArrayList = new ArrayList<>();
        // initializing our views.
        userRV = findViewById(R.id.idRVUsers);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);
        imageView = findViewById(R.id.idIVUser);

        /*findViewById(R.id.cardList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData();
            }
        });*/

        getDataFromAPI();
    }

    /*private void userData() {
    }*/

    private void getDataFromAPI() {
        // creating a string variable for url .
        String url = "https://reqres.in/api/users?page=" + page;

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // creating a variable for our json object request and passing our url to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // on below line we are extracting data from our json array.
                    JSONArray dataArray = response.getJSONArray("data");

                    // passing data from our json array in our array list.
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        // on below line we are extracting data from our json object.
                        userModalArrayList.add(new UserModal(jsonObject.getString("id"),
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                jsonObject.getString("email"),
                                jsonObject.getString("avatar")));

                        // passing array list to our adapter class.
                        userRVAdapter = new UserRVAdapter(userModalArrayList, MainActivity.this);

                       // setting layout manager to our recycler view.
                        userRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                         // setting adapter to our recycler view.
                        userRV.setAdapter(userRVAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "That's all the data..", Toast.LENGTH_SHORT).show();

                // hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // calling a request queue method
        // and passing our json object
        queue.add(jsonObjectRequest);
    }
}