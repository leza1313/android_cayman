package com.example.david.android_cayman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.david.android_cayman.MainActivity.MyPREFERENCES;
import static com.example.david.android_cayman.MainActivity.Value3;

public class nuevoPedido extends AppCompatActivity {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String miToken;
    SharedPreferences sharedPreferences;

    EditText numero_serie;//findViewById(R.id.numero_serie);
    EditText nombre;//findViewById(R.id.nombre);
    EditText telefono;//findViewById(R.id.telefono);
    EditText direccion;//findViewById(R.id.direccion);
    EditText email;//findViewById(R.id.email);
    EditText modelo;//findViewById(R.id.modelo);
    EditText acabado;//findViewById(R.id.acabado);
    EditText pastillas;//findViewById(R.id.pastillas);
    EditText puente;//findViewById(R.id.puente);
    EditText electronica;//findViewById(R.id.electronica);
    EditText clavijero;//findViewById(R.id.clavijero);
    EditText precio;//findViewById(R.id.precio);
    EditText fecha;//findViewById(R.id.fecha);
    EditText factura;//findViewById(R.id.factura);
    EditText pago_id;//findViewById(R.id.pago_id);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.miToken = sharedPreferences.getString(Value3, "");

        numero_serie = findViewById(R.id.numero_serie);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        direccion = findViewById(R.id.direccion);
        email = findViewById(R.id.email);
        modelo = findViewById(R.id.modelo);
        acabado = findViewById(R.id.acabado);
        pastillas = findViewById(R.id.pastillas);
        puente = findViewById(R.id.puente);
        electronica = findViewById(R.id.electronica);
        clavijero = findViewById(R.id.clavijero);
        precio = findViewById(R.id.precio);
        fecha = findViewById(R.id.fecha);
        factura = findViewById(R.id.factura);
        pago_id = findViewById(R.id.pago_id);
    }

    public void nuevo(View v){

        JSONObject postparams = new JSONObject();
        try {
            postparams.put("precio",precio.getText().toString());
            postparams.put("acabado",acabado.getText().toString());
            postparams.put("pastillas",pastillas.getText().toString());
            postparams.put("puente",puente.getText().toString());
            postparams.put("electronica",electronica.getText().toString());
            postparams.put("clavijero",clavijero.getText().toString());
            postparams.put("factura",factura.getText().toString());
            postparams.put("direccion",direccion.getText().toString());
            postparams.put("nombre",nombre.getText().toString());
            postparams.put("email",email.getText().toString());
            //postparams.put("observaciones",observaciones.getText().toString());
            postparams.put("fecha",fecha.getText().toString());
            postparams.put("pago_id",pago_id.getText().toString());
            postparams.put("modelo",modelo.getText().toString());
            postparams.put("telefono",telefono.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(postparams);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "https://leza1313.hopto.org/api/pedido/"+numero_serie.getText().toString(), postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Se ha añadido el objeto
                        Toast.makeText(nuevoPedido.this,
                                "Pedido añadido correctamente",
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(nuevoPedido.this,
                            "No hay conexion con el servidor",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 401) {
                    Toast.makeText(nuevoPedido.this,
                            "Necesita autorización para esta petición",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 403) {
                    Toast.makeText(nuevoPedido.this,
                            "Accesso al recurso prohibido",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(nuevoPedido.this,
                            "El pedido de la guitarra solicitada no existe",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 500) {
                    Toast.makeText(nuevoPedido.this,
                            "Error en el servidor, contacte con el administrador",
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", miToken);
                return headers;
            }
        };
            requestQueue.add(jsonObjectRequest);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void volverPedidos(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
