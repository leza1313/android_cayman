package com.example.david.android_cayman;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class info_pedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);

        String sacabado = getIntent().getStringExtra("acabado");
        String sclavijero = getIntent().getStringExtra("clavijero");
        String sdireccion = getIntent().getStringExtra("direccion");
        String selectronica = getIntent().getStringExtra("electronica");
        String semail = getIntent().getStringExtra("email");
        String sfactura = getIntent().getStringExtra("factura");
        String sfecha = getIntent().getStringExtra("fecha");
        String smodelo = getIntent().getStringExtra("modelo");
        String snombre = getIntent().getStringExtra("nombre");
        String snumero_serie = getIntent().getStringExtra("numero_serie");
        String sobservaciones = getIntent().getStringExtra("observaciones");
        String spago_id = getIntent().getStringExtra("pago_id");
        String spastillas = getIntent().getStringExtra("pastillas");
        String sprecio = getIntent().getStringExtra("precio");
        String stelefono = getIntent().getStringExtra("telefono");
        String spuente = getIntent().getStringExtra("puente");

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
        Intent intent = new Intent(this, listviewPedidos.class);
        startActivity(intent);
    }
}
