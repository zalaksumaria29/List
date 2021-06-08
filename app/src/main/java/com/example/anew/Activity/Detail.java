package com.example.anew.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.anew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {

    String sessionId;
    TextView id, firstname, lastname, email;
    private ImageView imageView;
    private ArrayList<UserModal> detailModalArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        sessionId = getIntent().getStringExtra("id");
        //String BPI_ENDPOINT = "https://reqres.in/api/users/" + sessionId;

        id = findViewById(R.id.textViewId);
        firstname = findViewById(R.id.textViewfirstname);
        lastname = findViewById(R.id.textViewlastname);
        email = findViewById(R.id.textViewEmail);
        imageView = findViewById(R.id.detailIVUser);
        /*userRV = findViewById(R.id.linear);

        Intent intent = getIntent();
        String u_id = intent.getStringExtra("id");
        String u_firstname = intent.getStringExtra("firstname");
        String u_lastname = intent.getStringExtra("lastname");
        String u_email = intent.getStringExtra("email");
        String u_image = intent.getStringExtra("image");

        id.setText(u_id);
        firstname.setText(u_firstname);
        lastname.setText(u_lastname);
        email.setText(u_email);

        Glide.with(this)
                .load(u_image)
                .into(imageView);*/

        //Toast.makeText(this, "id = " + BPI_ENDPOINT , Toast.LENGTH_SHORT).show();
        detailData();

    }

    private void detailData() {
        String API = "https://reqres.in/api/users/" + sessionId;
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Detail.this);

        // creating a variable for our json object request and passing our url to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    detailModalArrayList = new ArrayList<>();

                    detailModalArrayList.add(new UserModal(jsonObject.getString("id"),
                            jsonObject.getString("first_name"),
                            jsonObject.getString("last_name"),
                            jsonObject.getString("email"),
                            jsonObject.getString("avatar")));

                    id.setText(detailModalArrayList.get(0).getId());
                    firstname.setText(detailModalArrayList.get(0).getFirst_name());
                    lastname.setText(detailModalArrayList.get(0).getLast_name());
                    email.setText(detailModalArrayList.get(0).getEmail());
                    Glide.with(Detail.this)
                            .load(detailModalArrayList.get(0).getAvatar())
                            .into(imageView);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Detail.this, "DATA Fetch...", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(Detail.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
