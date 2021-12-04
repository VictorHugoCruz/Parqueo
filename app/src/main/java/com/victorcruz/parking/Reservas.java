package com.victorcruz.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Reservas extends AppCompatActivity {

    TextInputEditText placa;
    Button generador;
    ImageView salidaQR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        placa= findViewById(R.id.et_placa);
        generador= findViewById(R.id.btn_ticket);
        salidaQR= findViewById(R.id.iv_salidaqr);


        generador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = placa.getText().toString().trim();

                MultiFormatWriter multiFormatWriter= new MultiFormatWriter();

                try {
                    BitMatrix bitMatrix=multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,350,350);

                    BarcodeEncoder encoder = new BarcodeEncoder();

                    Bitmap bitmap = encoder.createBitmap(bitMatrix);

                    salidaQR.setImageBitmap(bitmap);

                    InputMethodManager  manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(placa.getApplicationWindowToken(),0);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void irMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;

    }


    //metodo que devuelde la opcion de item seleccionadp
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id== R.id.item_historial){
            Toast.makeText(this, "Se lo lavo??", Toast.LENGTH_SHORT ).show();
        }else if(id == R.id.item_cerrar_sesion){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Reservas.this,"la sesion a finalizado", Toast.LENGTH_SHORT).show();
                    irMainActivity();
                    finish();
                }
            });
        }else if(id == R.id.item_mapa){
            Intent intent = new Intent(Reservas.this, MapsActivity.class);
            startActivity(intent);

        }else if(id == R.id.item_parqueos) {
            Intent intent = new Intent(Reservas.this, ListaParqueo.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}