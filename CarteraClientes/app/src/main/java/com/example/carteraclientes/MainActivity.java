package com.example.carteraclientes;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void onAddClient(View view){
        Intent intent=new Intent(this,NewClient.class);
        startActivity(intent);

    }

    public  void onSearchClient(View view){
        Intent intent=new Intent(this,SearchClient.class);
        startActivity(intent);
    }
    public  void onViewClients(View view){
        Intent intent=new Intent(this,ViewClients.class);
        startActivity(intent);
    }
}


