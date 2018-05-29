package com.example.david.android_cayman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.david.android_cayman.Credenciales.Value1;
import static com.example.david.android_cayman.Credenciales.Value2;

public class MainActivity extends AppCompatActivity {

    String User; // This will be a reference to our GitHub username input.
    String Password; // This will be a reference to our GitHub username input.
    EditText etNombre;
    String miToken;
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Value3 = "JWToken";

    String baseUrl = "https://leza1313.hopto.org/api/";  // This is the API base URL (GitHub API)
    //String baseUrl = "http://192.168.1.200/api/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    public void credenciales(MenuItem item) {
        Intent intent = new Intent(this, Credenciales.class);
        startActivity(intent);
    }

    public void repetier(MenuItem item) {
        Intent intent = new Intent(this, Repetier.class);
        startActivity(intent);
    }
    public void anadirPedido(View v) {
        Intent intent = new Intent(this, nuevoPedido.class);
        startActivity(intent);
    }

    public ListView lista;
    EditText etnum_serie;

    public ArrayList<Pedido> myPedidos = new ArrayList<Pedido>();

    class Pedido {
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
        public String fecha_salida;
    }

    mAdapter adaptador = null;

    class mAdapter extends ArrayAdapter<Pedido> {
        mAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, myPedidos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            row = inflater.inflate(R.layout.row, parent, false);

            TextView name = (TextView) row.findViewById(R.id.name);
            TextView name2 = (TextView) row.findViewById(R.id.name2);
            TextView name3 = (TextView) row.findViewById(R.id.name3);
            ImageView img = row.findViewById(R.id.img);

            Pedido mpedido = myPedidos.get(position);
            if (mpedido.fecha_salida.equals("None")==false) {
                row.setBackgroundResource(R.color.colorSuccess);
            }

            name.setText(mpedido.nombre);
            name2.setText(mpedido.numero_serie);
            name3.setText(mpedido.fecha);
            if (mpedido.modelo.equals("Mississipi")){
                img.setImageResource(R.drawable.strato);
            }


            return row;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        this.etnum_serie = (EditText) findViewById(R.id.num_serie);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading);
        this.miToken = sharedPreferences.getString(Value3, "");
        this.User = sharedPreferences.getString(Value1, "");
        this.Password = sharedPreferences.getString(Value2, "");

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

        try {
            getToken();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*String recargar = getIntent().getStringExtra("recargar");
        if(recargar!=null) {
            if (recargar.equals("si")) {
                again(progressBar);
            }
        }*/


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://leza1313.hopto.org/api/pedidos", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            JSONArray jarray = response.getJSONArray("Pedidos");
                            for (int i=0;i<jarray.length();i++){
                                JSONObject obj = jarray.getJSONObject(i);
                                Pedido auxrow = new Pedido();
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
                                if (obj.has("fecha_salida")){
                                    auxrow.fecha_salida = obj.getString("fecha_salida");
                                }
                                myPedidos.add(auxrow);
                            }
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
                handleVolleyError(error);
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

        lista = (ListView) findViewById(R.id.listView);


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
        String jwt = miToken.substring(4);
        jwt = jwt.split("\\.")[1];

        byte[] result = Base64.decode(jwt, Base64.DEFAULT);
        String result2 = new String(result);
        JSONObject obj = new JSONObject(result2);
        Long exp2 = obj.getLong("exp");
        java.util.Date time=new java.util.Date(exp2*1000);
        java.util.Date ahora =new java.util.Date();

        if (ahora.compareTo(time)>0){
        //if (1>0){
            //The JWT token expired, we generate a new one
            JSONObject postparams = new JSONObject();
            postparams.put("username", User);
            postparams.put("password", Password);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://leza1313.hopto.org/auth", postparams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //Success Callback
                            //System.out.println("Exito!!");
                            //System.out.println(response);

                            try {
                                miToken = "JWT " + response.getString("access_token");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Value3, miToken);
                                editor.commit();
                                Toast.makeText(MainActivity.this, "Token generado", Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            handleVolleyError(error);

                        }
                    });
            requestQueue.add(jsonObjReq);
        }


    }

    public void buscarpedido(View v) {
        busqueda(etnum_serie.getText().toString());
    }

    private void busqueda(final String num_serie) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://leza1313.hopto.org/api/pedido/" + num_serie, null,
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
                handleVolleyError(error);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", miToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void handleVolleyError(VolleyError error){
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(MainActivity.this,
                    "No hay conexion con el servidor",
                    Toast.LENGTH_LONG).show();
        } else if (error.networkResponse.statusCode == 401) {
            Toast.makeText(MainActivity.this,
                    "Necesita autorización para esta petición",
                    Toast.LENGTH_LONG).show();
            try {
                getToken();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            again((ProgressBar) findViewById(R.id.loading));
        } else if (error.networkResponse.statusCode == 403) {
            Toast.makeText(MainActivity.this,
                    "Accesso al recurso prohibido",
                    Toast.LENGTH_LONG).show();
            try {
                getToken();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (error.networkResponse.statusCode == 404) {
            Toast.makeText(MainActivity.this,
                    "El pedido de la guitarra solicitada no existe",
                    Toast.LENGTH_LONG).show();
        } else if (error.networkResponse.statusCode == 500) {
            Toast.makeText(MainActivity.this,
                    "Error en el servidor, contacte con el administrador",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void again(final ProgressBar progressBar){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://leza1313.hopto.org/api/pedidos", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            JSONArray jarray = response.getJSONArray("Pedidos");
                            for (int i=0;i<jarray.length();i++){
                                JSONObject obj = jarray.getJSONObject(i);
                                Pedido auxrow = new Pedido();
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
                            }
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
                handleVolleyError(error);
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
    }
}
