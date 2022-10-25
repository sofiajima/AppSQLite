package com.example.actividad11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_code, et_description, et_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_code = (EditText) findViewById(R.id.txt_code);
        et_description = (EditText) findViewById(R.id.txt_description);
        et_price = (EditText) findViewById(R.id.txt_price);


    }

    public void Register(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();
        String description = et_description.getText().toString();
        String price = et_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues registerRow = new ContentValues();

            registerRow.put("code", code);
            registerRow.put("description", description);
            registerRow.put("price", price);

            dataBase.insert("products", null, registerRow);

            dataBase.close();

            et_code.setText("");
            et_description.setText("");
            et_price.setText("");

            Toast.makeText(this, "Registro existoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Search(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();

        if(!code.isEmpty()){
            Cursor row = dataBase.rawQuery("SELECT description, price FROM products WHERE code =" + code, null);

            if(row.moveToFirst()){
                et_description.setText(row.getString(0));
                et_price.setText(row.getString(1));
                dataBase.close();
            } else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes introducit el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminate(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();

        if (!code.isEmpty()){
            int quantity = dataBase.delete("products", "code=" + code, null);
            dataBase.close();

            et_code.setText("");
            et_description.setText("");
            et_price.setText("");

            if (quantity == 1) {
                Toast.makeText(this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes introducit el codigo del producto", Toast.LENGTH_SHORT).show();
        }
    }

    public void Update(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();
        String description = et_description.getText().toString();
        String price = et_price.getText().toString();

        if (!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues registerRow = new ContentValues();

            registerRow.put("code", code);
            registerRow.put("description", description);
            registerRow.put("price", price);

            int quantity = dataBase.update("products", registerRow, "code=" + code, null);

            dataBase.close();

            if (quantity == 1) {
                Toast.makeText(this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}