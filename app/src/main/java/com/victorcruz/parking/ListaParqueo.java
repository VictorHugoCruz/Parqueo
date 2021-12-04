package com.victorcruz.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaParqueo extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<ParqueosPojo> lista;
    ArrayList<ParqueosPojo> listAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_parqueo);

        recyclerView = findViewById(R.id.rv_parqueos);
        databaseReference = FirebaseDatabase.getInstance().getReference("Parqueo");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista = new ArrayList<>();
        listAux = new ArrayList<>();
        myAdapter = new MyAdapter(this, lista);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*for(ParqueosPojo parqueosPojo: lista){
                   parqueosPojo = null;
                }*/

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ParqueosPojo parqueosPojo = dataSnapshot.getValue(ParqueosPojo.class);
                    listAux.add(parqueosPojo);
                }
                lista.clear();
                lista.addAll(listAux);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    Toast.makeText(ListaParqueo.this,"la sesion a finalizado", Toast.LENGTH_SHORT).show();
                    irMainActivity();
                    finish();
                }
            });
        }else if(id == R.id.item_mapa){
                Intent intent = new Intent(ListaParqueo.this, MapsActivity.class);
                startActivity(intent);

        }else if(id == R.id.item_reservas){
            Intent intent = new Intent(ListaParqueo.this, Reservas.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}