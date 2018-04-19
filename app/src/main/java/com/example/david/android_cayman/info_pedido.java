package com.example.david.android_cayman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.david.android_cayman.MainActivity.MyPREFERENCES;
import static com.example.david.android_cayman.MainActivity.Value3;

public class info_pedido extends AppCompatActivity {

    String sacabado;//getIntent().getStringExtra("acabado");
    String sclavijero;//getIntent().getStringExtra("clavijero");
    String sdireccion;//getIntent().getStringExtra("direccion");
    String selectronica;//getIntent().getStringExtra("electronica");
    String semail;//getIntent().getStringExtra("email");
    String sfactura;//getIntent().getStringExtra("factura");
    String sfecha;//getIntent().getStringExtra("fecha");
    String smodelo;//getIntent().getStringExtra("modelo");
    String snombre;//getIntent().getStringExtra("nombre");
    String snumero_serie;//getIntent().getStringExtra("numero_serie");
    String sobservaciones;//getIntent().getStringExtra("observaciones");
    String spago_id;//getIntent().getStringExtra("pago_id");
    String spastillas;//getIntent().getStringExtra("pastillas");
    String sprecio;//getIntent().getStringExtra("precio");
    String stelefono;//getIntent().getStringExtra("telefono");
    String spuente;//getIntent().getStringExtra("puente");

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String miToken;
    SharedPreferences sharedPreferences;
    String num_serie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.miToken = sharedPreferences.getString(Value3, "");

        this.sacabado = getIntent().getStringExtra("acabado");
        this.sclavijero = getIntent().getStringExtra("clavijero");
        this.sdireccion = getIntent().getStringExtra("direccion");
        this.selectronica = getIntent().getStringExtra("electronica");
        this.semail = getIntent().getStringExtra("email");
        this.sfactura = getIntent().getStringExtra("factura");
        this.sfecha = getIntent().getStringExtra("fecha");
        this.smodelo = getIntent().getStringExtra("modelo");
        this.snombre = getIntent().getStringExtra("nombre");
        this.snumero_serie = getIntent().getStringExtra("numero_serie");
        num_serie=snumero_serie;
        this.sobservaciones = getIntent().getStringExtra("observaciones");
        this.spago_id = getIntent().getStringExtra("pago_id");
        this.spastillas = getIntent().getStringExtra("pastillas");
        this.sprecio = getIntent().getStringExtra("precio");
        this.stelefono = getIntent().getStringExtra("telefono");
        this.spuente = getIntent().getStringExtra("puente");

        TextView acabado = (TextView)findViewById(R.id.acabado);
        acabado.setText(sacabado);
        TextView clavijero = (TextView)findViewById(R.id.clavijero);
        clavijero.setText(sclavijero);
        TextView direccion = (TextView)findViewById(R.id.direccion);
        direccion.setText(sdireccion);
        TextView electronica = (TextView)findViewById(R.id.electronica);
        electronica.setText(selectronica);
        TextView email = (TextView)findViewById(R.id.email);
        email.setText(semail);
        TextView factura = (TextView)findViewById(R.id.factura);
        factura.setText(sfactura);
        TextView fecha = (TextView)findViewById(R.id.fecha);
        fecha.setText(sfecha);
        TextView modelo = (TextView)findViewById(R.id.modelo);
        modelo.setText(smodelo);
        TextView nombre = (TextView)findViewById(R.id.nombre);
        nombre.setText(snombre);
        TextView numero_serie = (TextView)findViewById(R.id.numero_serie);
        numero_serie.setText(snumero_serie);
        //TextView observaciones = (TextView)findViewById(R.id.observaciones);
        //observaciones.setText(sobservaciones);
        TextView pago_id = (TextView)findViewById(R.id.pago_id);
        pago_id.setText(spago_id);
        TextView pastillas = (TextView)findViewById(R.id.pastillas);
        pastillas.setText(spastillas);
        TextView precio = (TextView)findViewById(R.id.precio);
        precio.setText(sprecio);
        TextView telefono = (TextView)findViewById(R.id.telefono);
        telefono.setText(stelefono);
        TextView puente = (TextView)findViewById(R.id.puente);
        puente.setText(spuente);

    }

    public void volverPedidos(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void borrarPedido(View v){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                "https://leza1313.hopto.org/api/pedido/"+num_serie, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Se ha añadido el objeto
                        Toast.makeText(info_pedido.this,
                                "Pedido eliminado correctamente",
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(info_pedido.this,
                            "No hay conexion con el servidor",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 401) {
                    Toast.makeText(info_pedido.this,
                            "Necesita autorización para esta petición",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 403) {
                    Toast.makeText(info_pedido.this,
                            "Accesso al recurso prohibido",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(info_pedido.this,
                            "El pedido de la guitarra solicitada no existe",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode == 500) {
                    Toast.makeText(info_pedido.this,
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
    public void actualizar(View v){
        Intent intent = new Intent(getBaseContext(), actualizarPedido.class);
        System.out.println("ACABADOO: "+sacabado);
        intent.putExtra("acabado", sacabado);
        intent.putExtra("clavijero", sclavijero);
        intent.putExtra("direccion", sdireccion);
        intent.putExtra("electronica", selectronica);
        intent.putExtra("email", semail);
        intent.putExtra("factura", sfactura);
        intent.putExtra("fecha", sfecha);
        intent.putExtra("modelo", smodelo);
        intent.putExtra("nombre", snombre);
        intent.putExtra("numero_serie", snumero_serie);
        intent.putExtra("observaciones", sobservaciones);
        intent.putExtra("pago_id", spago_id);
        intent.putExtra("pastillas", spastillas);
        intent.putExtra("precio", sprecio);
        intent.putExtra("telefono", stelefono);
        intent.putExtra("puente", spuente);
        startActivity(intent);
    }
}
