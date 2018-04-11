package com.example.david.android_cayman;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Credenciales extends AppCompatActivity {

    EditText etUser,etPassword;
    Button btnGuardar;
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Value1 = "username";
    public static final String Value2 = "password";

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.configuracionmenu, menu);
        return true;
    }
    public void volverMain(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void repetier(MenuItem item){
        Intent intent = new Intent(this, Repetier.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenciales);

        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        etUser.setText(sharedPreferences.getString(Value1,""));
        etPassword.setText(sharedPreferences.getString(Value2,""));


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n  = etUser.getText().toString();
                String ph  = etPassword.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Value1, n);
                editor.putString(Value2, ph);
                editor.commit();
                Toast.makeText(Credenciales.this,"Generado Token",Toast.LENGTH_LONG).show();
                volverMain(v);

            }
        });

    }
    public void volverMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
