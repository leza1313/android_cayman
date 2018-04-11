package com.example.david.android_cayman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Repetier extends AppCompatActivity {

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetier);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.repetiermenu, menu);
        return true;
    }
    public void credenciales(MenuItem item){
        Intent intent = new Intent(this, Credenciales.class);
        startActivity(intent);
    }
    public void volverMain(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void stop(View v){
        this.url = "http://demo.repetier-server.com:4010/printer/api/Cartesian?a=stopJob&data=%7B%22id%22%3A2%7D&apikey=7075e377-7472-447a-b77e-86d481995e7b";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO Parse JSON, response
                        System.out.println("Stop");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If there a HTTP error then add a note to our repo list.
                System.out.println("Error while calling Repetier DEMO");
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    public void copy(View v){
        //URL for the Repetier-Server
        this.url = "http://demo.repetier-server.com:4010/printer/api/Cartesian?a=copyModel&data=%7B%22id%22%3A2%7D&apikey=7075e377-7472-447a-b77e-86d481995e7b";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO Parse JSON, response
                        System.out.println("Copy - Start");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If there a HTTP error then add a note to our repo list.
                System.out.println("Error while calling Repetier DEMO");
            }
        }
        );
        requestQueue.add(stringRequest);
    }
}
