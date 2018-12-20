package org.elis.jp4application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, FoodListAdapter.OnQuantityChange {

TextView welcomeTW, emailTV, totalTextView;
RecyclerView recyclerView;
LinearLayoutManager layoutManager;
FoodListAdapter adapter;
int total=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_welcome);

       welcomeTW= findViewById(R.id.welcome_tv);
       emailTV = findViewById(R.id.email_et);
       totalTextView= findViewById(R.id.total);
       recyclerView = findViewById(R.id.food_row);
       layoutManager = new LinearLayoutManager(this);



       adapter = new FoodListAdapter(this);
       adapter.setOnQuantityChange(this);
       getProducts();


       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);

    }


public void scegliMail(View view){
    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", getIntent().getStringExtra(MainActivity.WELCOME), null));
    startActivity(Intent.createChooser(i, "Choose an Email client : "));

}
    public void onClick(View view){
        if (view.getId() == R.id.welcome_tv) {
            scegliMail(this.welcomeTW);
        }

    }
    private  void getProducts(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://5ba19290ee710f0014dd764c.mockapi.io/Food";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("success", response);
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray jsonArray = responseJSON.getJSONArray("foods");
                            ArrayList<Food> foodArrayList = new ArrayList<>();

                            for(int i=0; i<jsonArray.length();i++){
                                Food food = new Food(jsonArray.getJSONObject(i));
                                foodArrayList.add(food);
                            }
                            adapter.setData(foodArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
    public void onItemAdded(double price) {
        total += price;
        totalTextView.setText("Total:" + total);
    }

    public void onItemRemoved(double price) {
        if(total==0) return;
        total -= price;
       totalTextView.setText("Total :" +total);

    }

}

