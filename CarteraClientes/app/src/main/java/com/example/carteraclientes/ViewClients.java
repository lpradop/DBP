package com.example.carteraclientes;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.carteraclientes.database.DatabaseHelper;
import com.example.carteraclientes.database.DatabaseManager;
public class ViewClients extends AppCompatActivity {
    final String[] from = new String[] { DatabaseHelper.NAME, DatabaseHelper.PHONE};

    final int[] to = new int[] { android.R.id.text1,android.R.id.text2 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clients);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DatabaseManager database_manager = new DatabaseManager(this);
        database_manager.open();
        Cursor cursor= database_manager.fetch();
        ListView list_view = (ListView) findViewById(R.id.clientList);
        list_view.setEmptyView(findViewById(R.id.emptyList));
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        list_view.setAdapter(adapter);

    }
}
