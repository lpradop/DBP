package com.example.carteraclientes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.example.carteraclientes.database.DatabaseHelper;
import com.example.carteraclientes.database.DatabaseManager;

public class MainActivity extends AppCompatActivity {
    private ListView list_view;
    private DatabaseManager database_manager;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[] { DatabaseHelper.NAME, DatabaseHelper.PHONE};
//            DatabaseHelper.ADDRESS, DatabaseHelper.EMAIL,DatabaseHelper.PHONE };

    final int[] to = new int[] { android.R.id.text1,android.R.id.text2 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database_manager=new DatabaseManager(this);
        database_manager.open();
        Cursor cursor=database_manager.fetch();
        list_view=(ListView)findViewById(R.id.clientList);
        list_view.setEmptyView(findViewById(R.id.emptyList));
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        list_view.setAdapter(adapter);

    }
    public void onAddNewClient(View view){
        Intent intent=new Intent(this,NewClient.class);
        startActivity(intent);

    }
}


