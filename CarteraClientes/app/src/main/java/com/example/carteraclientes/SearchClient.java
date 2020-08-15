package com.example.carteraclientes;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.carteraclientes.database.DatabaseManager;

public class SearchClient extends AppCompatActivity {
    TextView[] fields;
    EditText edit_text_id;
    DatabaseManager database_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fields = new TextView[4];
        fields[0] = findViewById(R.id.textViewQueryName);
        fields[1] = findViewById(R.id.textViewQueryAddress);
        fields[2] = findViewById(R.id.textViewQueryEmail);
        fields[3] = findViewById(R.id.textViewQueryPhone);
        edit_text_id = findViewById(R.id.editTextQueryName);
        database_manager = new DatabaseManager(this);


    }

    public void onSearch(View view) {
        database_manager.open();
        String input = edit_text_id.getText().toString().trim();
        if (input.isEmpty()) {
            edit_text_id.requestFocus();
        } else {
            Cursor cursor = database_manager.getByName(input);
            if(cursor.getCount()>0){
                for (int i=0; i<cursor.getColumnCount();++i) {
                    fields[i].setText(cursor.getString(i));
                }
            }else{
                Toast.makeText(this, "no se encontro cliente", Toast.LENGTH_SHORT).show();
            }


            cursor.close();
        }

        database_manager.close();
    }

    public void onDelete(View view) {
        database_manager.open();
        String input = edit_text_id.getText().toString().trim();
        if (input.isEmpty()) {
            edit_text_id.requestFocus();
        } else {
            database_manager.delete(input);
            Toast.makeText(this, "cliente eliminado", Toast.LENGTH_SHORT).show();

            for (TextView field:fields) {
                field.setText("");
            }

        }
        database_manager.close();
    }

}
