package com.example.calculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "history";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "calculator";

    private static final String ID_COL = "id";

    private static final String NUMBER1_COL = "number1";

    private static final String NUMBER2_COL = "number2";

    private static final String OPERATOR_COL = "operator";

    private static final String RESULT_COL = "result";

    public DBHandler(@Nullable Context context) {
        super(context,  DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NUMBER1_COL + " TEXT,"
                + NUMBER2_COL + " TEXT,"
                + OPERATOR_COL + " TEXT,"
                + RESULT_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertOperation(@NonNull Operation operation){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("number1", operation.getNumber1());
        values.put("number2", operation.getNumber2());
        values.put("operator", operation.getOperator());
        values.put("result", operation.getResult());

        try{
            long id = db.insert(TABLE_NAME, null, values);

            operation.setId(id);

            return id;
        }catch (Exception exception){
            return -1;
        }finally {
            db.close();
        }
    }

    public ArrayList<Operation> getOperations(){
        ArrayList<Operation> operations = new ArrayList<Operation>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if(cursor.moveToFirst()){
            do{
                try {
                    int id = cursor.getInt(0);
                    String number1 = cursor.getString(1);
                    String number2 = cursor.getString(2);
                    String operator = cursor.getString(3);
                    String result = cursor.getString(4);

                    Operation operation = new Operation(id, number1, number2, operator, result);

                    operations.add(operation);
                }catch (Exception exception){

                }
            }while (cursor.moveToNext());
        }

        db.close();

        return operations;
    }

    public long deleteOperation(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            return db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{""+ id});
        }catch (Exception exception){
            return -1;
        }finally {
            db.close();
        }
    }
}
