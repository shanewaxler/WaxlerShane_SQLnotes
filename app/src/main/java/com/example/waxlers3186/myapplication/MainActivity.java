package com.example.waxlers3186.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_number);
        editAddress = findViewById(R.id.editText_address);


        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }
    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(),editPhone.getText().toString(),editAddress.getText().toString());
        if (isInserted == true){
            Toast.makeText(MainActivity.this,"Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"Failed - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }
    public void viewData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor");

        if (res.getCount()==0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Name: " + res.getString(1));
            buffer.append(" /// Address: " + res.getString(2));
            buffer.append(" /// Phone: " + res.getString(3));
            buffer.append("\n\n");
        }
        showMessage("Data", buffer.toString());

    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.ortizj4473.mycontactapp.MESSAGE";
    public void searchRecord(View view){
        Log.d("MyContactApp", "MainActivity: Launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        StringBuffer buffer = new StringBuffer();
        Cursor res = myDb.getAllData();

        if (res.getCount()==0){
            showMessage("Error", "No data found in database");
            return;
        }


        while (res.moveToNext()){
            Log.d("MyContactApp", "searchRecord: appending data");
            if(res.getString(1).equals(editName.getText().toString())){
                Log.d("MyContactApp", "searchRecord: searchData is True");
                buffer.append("Name: " + res.getString(1));
                buffer.append(" /// Phone: " + res.getString(2));
                buffer.append(" /// Address: " + res.getString(3));
                buffer.append("\n\n");
            }

        }
        showMessage("Data", buffer.toString());

        intent.putExtra(EXTRA_MESSAGE, buffer.toString());
        startActivity(intent);
    }
}