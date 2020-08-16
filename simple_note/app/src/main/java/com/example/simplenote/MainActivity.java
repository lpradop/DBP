package com.example.simplenote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import com.example.simplenote.database.DatabaseHelper;
import com.example.simplenote.database.DatabaseManager;


public class MainActivity extends AppCompatActivity {
    private DatabaseManager database_manager;
    private ListView list_view;
    final String[] from = new String[]{DatabaseHelper.FILE_NAME};
    final int[] to = new int[]{android.R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list_view = findViewById(R.id.listNotes);
        list_view.setEmptyView(findViewById(R.id.emptyList));
        database_manager = new DatabaseManager(this);
        database_manager.open();
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Cursor selected_item_cursor = (Cursor) list_view.getItemAtPosition(position);
                String file_name = selected_item_cursor.getString(1).trim();

                Toast.makeText(MainActivity.this, file_name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NoteEditor.class);
                intent.putExtra("file_name", file_name);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new) {
            final View view = getLayoutInflater().inflate(R.layout.dialog_new, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Nueva nota");
            alertDialog.setCancelable(true);

            final EditText note_name = view.findViewById(R.id.editTextNewNoteName);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String input = note_name.getText().toString().trim();
                    if (input.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();

                    } else if (database_manager.noteExists(input)) {
                        Toast.makeText(MainActivity.this, "Esa nota ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, NoteEditor.class);
                        intent.putExtra("file_name", input);
                        startActivity(intent);
                    }
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialog.setView(view);
            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList() {
        Cursor cursor = database_manager.fetch();
        if (cursor.getCount() > 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to, 0);
            adapter.notifyDataSetChanged();
            list_view.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database_manager.close();
    }
}