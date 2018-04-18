package com.example.david.android_cayman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.david.android_cayman.Credenciales.Value1;
import static com.example.david.android_cayman.Credenciales.Value2;
import static com.example.david.android_cayman.MainActivity.MyPREFERENCES;
import static com.example.david.android_cayman.MainActivity.Value3;

public class listviewPedidos extends AppCompatActivity {

    public ListView lista;
    SharedPreferences sharedPreferences;
    EditText etnum_serie;
    String miToken;
    String User;
    String Password;

    public ArrayList<Pedido> myPedidos = new ArrayList<Pedido>();
    class Pedido{
        public String precio;
        public String acabado;
        public String pastillas;
        public String puente;
        public String electronica;
        public String clavijero;
        public String factura;
        public String direccion;
        public String nombre;
        public String email;
        public String observaciones;
        public String fecha;
        public String pago_id;
        public String telefono;
        public String numero_serie;
        public String modelo;
    }

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    mAdapter adaptador = null;
    class mAdapter extends ArrayAdapter<Pedido>{
        mAdapter(){
            super(listviewPedidos.this, android.R.layout.simple_list_item_1, myPedidos);
        }
        public View getView(int position,View convertView, ViewGroup parent) {
            View row = convertView;

            LayoutInflater inflater = LayoutInflater.from(listviewPedidos.this);
            row = inflater.inflate(R.layout.row,parent,false);

            TextView name = (TextView) row.findViewById(R.id.name);
            TextView name2 = (TextView) row.findViewById(R.id.name2);

            Pedido mpedido = myPedidos.get(position);

            name.setText(mpedido.nombre);
            name2.setText(mpedido.numero_serie);


            return row;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_pedidos);

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        this.etnum_serie = (EditText) findViewById(R.id.num_serie);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading);
        this.miToken = sharedPreferences.getString(Value3,"");
        this.User = sharedPreferences.getString(Value2,"");
        this.Password = sharedPreferences.getString(Value1,"");

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://leza1313.hopto.org/api/pedidos",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            Pedido auxrow = new Pedido();
                            JSONArray jarray = response.getJSONArray("Pedidos");
                            JSONObject obj = jarray.getJSONObject(0);

                            auxrow.precio = obj.getString("precio");
                            auxrow.acabado = obj.getString("acabado");
                            auxrow.pastillas = obj.getString("pastillas");
                            auxrow.puente = obj.getString("puente");
                            auxrow.electronica = obj.getString("electronica");
                            auxrow.clavijero = obj.getString("clavijero");
                            auxrow.factura = obj.getString("factura");
                            auxrow.direccion = obj.getString("direccion");
                            auxrow.nombre = obj.getString("nombre");
                            auxrow.email = obj.getString("email");
                            auxrow.observaciones = obj.getString("observaciones");
                            auxrow.fecha = obj.getString("fecha");
                            auxrow.pago_id = obj.getString("pago_id");
                            auxrow.numero_serie = obj.getString("numero_serie");
                            auxrow.modelo = obj.getString("modelo");
                            auxrow.telefono = obj.getString("telefono");

                            myPedidos.add(auxrow);

                            adaptador = new mAdapter();

                            lista.setAdapter(adaptador);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        // If there a HTTP error then add a note to our repo list.
                        Toast.makeText(listviewPedidos.this,"Error en http request, generando token...",Toast.LENGTH_LONG).show();
                        try {
                            getToken();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization",miToken);
                        return headers;
                    }
        };
        requestQueue.add(jsonObjectRequest);

        lista = (ListView)findViewById(R.id.listView);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //System.out.println("Clicado item: "+mypedidos[i]);
                Intent intent = new Intent(getBaseContext(), info_pedido.class);
                intent.putExtra("acabado", myPedidos.get(i).acabado);
                intent.putExtra("clavijero", myPedidos.get(i).clavijero);
                intent.putExtra("direccion", myPedidos.get(i).direccion);
                intent.putExtra("electronica", myPedidos.get(i).electronica);
                intent.putExtra("email", myPedidos.get(i).email);
                intent.putExtra("factura", myPedidos.get(i).factura);
                intent.putExtra("fecha", myPedidos.get(i).fecha);
                intent.putExtra("modelo", myPedidos.get(i).modelo);
                intent.putExtra("nombre", myPedidos.get(i).nombre);
                intent.putExtra("numero_serie", myPedidos.get(i).numero_serie);
                intent.putExtra("observaciones", myPedidos.get(i).observaciones);
                intent.putExtra("pago_id", myPedidos.get(i).pago_id);
                intent.putExtra("pastillas", myPedidos.get(i).pastillas);
                intent.putExtra("precio", myPedidos.get(i).precio);
                intent.putExtra("telefono", myPedidos.get(i).telefono);
                intent.putExtra("puente", myPedidos.get(i).puente);
                startActivity(intent);
            }
        });
    }
    public void getToken() throws JSONException {

        JSONObject postparams=new JSONObject();
        postparams.put("username",User);
        postparams.put("password",Password);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "https://leza1313.hopto.org/auth", postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Success Callback
                        System.out.println("Exito!!");
                        System.out.println(response);

                        try {
                            miToken = "JWT "+response.getString("access_token");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Value3, miToken);
                            editor.commit();
                            Toast.makeText(listviewPedidos.this,"Token generado",Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.out.println("ERROR!!");
                        System.out.println(error);

                    }
                });
        requestQueue.add(jsonObjReq);

    }
    public void buscarpedido(View v) {
        busqueda(etnum_serie.getText().toString());
    }
    private void busqueda(final String num_serie) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://leza1313.hopto.org/api/pedido/"+num_serie,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Parse JSON, response
                        try {
                            Intent intent = new Intent(getBaseContext(), info_pedido.class);
                            JSONObject obj = response;
                            intent.putExtra("precio", obj.getString("precio"));
                            intent.putExtra("acabado", obj.getString("acabado"));
                            intent.putExtra("pastillas", obj.getString("pastillas"));
                            intent.putExtra("puente", obj.getString("puente"));
                            intent.putExtra("electronica", obj.getString("electronica"));
                            intent.putExtra("clavijero", obj.getString("clavijero"));
                            intent.putExtra("factura", obj.getString("factura"));
                            intent.putExtra("direccion", obj.getString("direccion"));
                            intent.putExtra("nombre", obj.getString("nombre"));
                            intent.putExtra("email", obj.getString("email"));
                            intent.putExtra("observaciones", obj.getString("observaciones"));
                            intent.putExtra("fecha", obj.getString("fecha"));
                            intent.putExtra("pago_id", obj.getString("pago_id"));
                            intent.putExtra("numero_serie", obj.getString("numero_serie"));
                            intent.putExtra("modelo", obj.getString("modelo"));
                            intent.putExtra("telefono", obj.getString("telefono"));
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.networkResponse.statusCode);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(listviewPedidos.this,
                            "No hay conexion con el servidor",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode==401) {
                    Toast.makeText(listviewPedidos.this,
                            "Necesita autorización para esta petición",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode==403) {
                    Toast.makeText(listviewPedidos.this,
                            "Token de autorización caducado",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode==404) {
                    Toast.makeText(listviewPedidos.this,
                            "El pedido de la guitarra "+num_serie+" no existe",
                            Toast.LENGTH_LONG).show();
                } else if (error.networkResponse.statusCode==500) {
                    Toast.makeText(listviewPedidos.this,
                            "Error en el servidor, contacte con el administrador",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",miToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
