package com.example.simplenote;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import com.example.simplenote.database.DatabaseManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class NoteEditor extends AppCompatActivity {
    private String file_name;
    private DatabaseManager database_manager;
    private File file;
    private EditText edit_text_note;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edit_text_note=findViewById(R.id.editTextNote);
        file_name=getIntent().getStringExtra("file_name");

        database_manager=new DatabaseManager(this);
        database_manager.open();
        file = new File(getExternalFilesDir(null), file_name);
        if(file.exists()){
            edit_text_note.setText(readFile(file));
        }
        else{
            writeFile("",file);
            database_manager.insert(file_name);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            writeFile(edit_text_note.getText().toString().trim(),file);
            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void writeFile(String text, File file) {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        FileOutputStream outputStream;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String readFile(File file){
        String data="";
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data += reader.nextLine()+"\n";
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database_manager.close();
    }
}
