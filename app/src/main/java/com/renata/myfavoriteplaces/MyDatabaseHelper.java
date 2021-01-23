package com.renata.myfavoriteplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "myplaces.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "places";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PLACE_NAME = "place_name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_ZOOM = "zoom";

    public final static String SQLITE_CREATE_TABLE_PLACES =
            "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PLACE_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL," +
                    COLUMN_ZOOM + " TEXT)";

    public final static String SQLITE_DROP_TABLE_PLACES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public final static String SQLITE_SELECT_ALL_TABLE = "SELECT * FROM " + TABLE_NAME;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        install(db);
    }

    private void install(SQLiteDatabase db) {
        if (db != null) {
            try {
                db.execSQL(SQLITE_CREATE_TABLE_PLACES);
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getMessage().toString());
            }
        }
    }
    //é chamado quando for necessário atualizar a base de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            destroyAndReinstall(db);
        }
    }

    public void destroyAndReinstall(SQLiteDatabase db) {
        if (db != null) {
            try {
                db.execSQL(SQLITE_DROP_TABLE_PLACES);
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getMessage().toString());
            }
        }
    }
    //adiciona à base de dados as informações do lugar, trazidos pela instanciação do objeto da classe Place.
    void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PLACE_NAME, place.getmPlaceName());
            cv.put(COLUMN_DESCRIPTION, place.getmDescription());
            cv.put(COLUMN_LATITUDE, place.getLatitude());
            cv.put(COLUMN_LONGITUDE, place.getLongitude());
            cv.put(COLUMN_ZOOM, place.getmZoom());
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //ler toda a base de dados
    Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(SQLITE_SELECT_ALL_TABLE, null);
        }

        return cursor;
    }

    //deleta uma linha da base de dados
    void deleteOneRow(String row_id) {
        //db recebe a abertura de escrita da data base
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
