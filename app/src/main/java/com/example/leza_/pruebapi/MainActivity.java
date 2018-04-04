package com.example.leza_.pruebapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONStringer;

import java.lang.reflect.Array;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etTipo; // This will be a reference to our GitHub username input.
    EditText etNombre;
    String miTipo;
    Button btnBuscar;  // This is a reference to the "Get Repos" button.
    TextView tvResult;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String baseUrl = "https://leza1313.hopto.org/api/";  // This is the API base URL (GitHub API)
    //String baseUrl = "http://192.168.1.200/api/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // This is some magic for Android to load a previously saved state for when you are switching between actvities.
        setContentView(R.layout.activity_main);  // This links our code to our layout which we defined earlier.

        Spinner spinner = (Spinner) findViewById(R.id.Tipo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        this.miTipo = spinner.getSelectedItem().toString();
        //setRepoListText(miTipo);
        this.etNombre = (EditText) findViewById(R.id.etNombre);  // Link our github user text box.
        this.btnBuscar = (Button) findViewById(R.id.buscar);  // Link our clicky button.
        this.tvResult = (TextView) findViewById(R.id.result);  // Link our repository list text output box.
        this.tvResult.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
    }

    private void clearRepoList() {
        this.tvResult.setText("");
    }

    private void addToRepoList(String id, String nombre) {
        String strRow = id + ": " + nombre;
        String currentText = tvResult.getText().toString();
        this.tvResult.setText(currentText + "\n\n" + strRow);
    }

    private void setRepoListText(String str) {
        this.tvResult.setText(str);
    }

    private void getRepoList(String tipo, String nombre) {
        this.url = this.baseUrl + tipo +"/"+nombre;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO Parse JSON, response
                        try {
                            JSONObject obj = new JSONObject(response);
                            String descripcion = obj.getString("descripcion");
                            String nombre = obj.getString("nombre");
                            String fotopal = obj.getString("fotopal");
                            setRepoListText(nombre+": \n"+descripcion+"\n"+fotopal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRepoListText("Error while calling REST API");
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    public void getReposClicked(View v) {
        clearRepoList();
        getRepoList(miTipo, etNombre.getText().toString());
    }
    public void getListTipo(View v) {
        clearRepoList();
        this.url = this.baseUrl + miTipo +"s";
        String busqueda = miTipo.substring(0, 1).toUpperCase() + miTipo.substring(1);
        busqueda= busqueda+"s";
        final String finalBusqueda = busqueda;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO Parse JSON, response
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray arry = obj.getJSONArray(finalBusqueda);
                            for (int i=0;i<arry.length();i++){
                                JSONObject miobj = arry.getJSONObject(i);
                                String nombre = miobj.getString("nombre");
                                String id = String.valueOf(miobj.getInt("id"));
                                addToRepoList(id,nombre);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If there a HTTP error then add a note to our repo list.
                setRepoListText("Error while calling REST API");
            }
        }
        );
        requestQueue.add(stringRequest);
    }

    public void stop(View v){
        clearRepoList();
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
                setRepoListText("Error while calling Repetier DEMO");
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    public void copy(View v){
        clearRepoList();
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
                setRepoListText("Error while calling Repetier DEMO");
            }
        }
        );
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        miTipo = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
