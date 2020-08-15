package com.example.carteraclientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.carteraclientes.database.DatabaseManager;

public class NewClient extends AppCompatActivity {
    DatabaseManager database_manager;
    EditText name;
    EditText address;
    EditText email;
    EditText phone;
    EditText[] fields;
    String[] fields_values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.editTextName);
        address = findViewById(R.id.editTextAddress);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhone);
        fields_values = new String[4];
        fields = new EditText[4];
        fields[0] = name;
        fields[1] = address;
        fields[2] = email;
        fields[3] = phone;

        database_manager = new DatabaseManager(this);
        database_manager.open();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_new_client, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.actionOk:
                if (validateFields()) {
                    getFieldsValues();
                    database_manager.insert(fields_values[0], fields_values[1], fields_values[2], fields_values[3]);
                    Intent intent = new Intent(this, MainActivity.class);

                    Toast.makeText(this, "cliente agregado", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
                break;

            case R.id.actionCancel:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateFields() {
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void getFieldsValues() {
        for (int i = 0; i < fields.length; ++i) {
            fields_values[i] = fields[i].getText().toString().trim();
        }
    }
}
