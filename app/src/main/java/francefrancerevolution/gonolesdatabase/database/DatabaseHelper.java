/**********************************************
 Group Sensors Project
 FSU - Mobile Programming
 FreshmanNavigator App

 Description:
 Allows students of FSU to keep a schedule of their
 classes, and then view where each class is on
 a google maps api.

 Authors@
 Alex Quesenberry
 Alex Comes
 Sree Paruchuri
 Midlenex Joseph
 Andrew Dang
 ***********************************************/
package francefrancerevolution.gonolesdatabase.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import francefrancerevolution.gonolesdatabase.model.Building;

/**
 * Created by Alucard5 on 4/4/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "sample.sqlite";
    public static final String DBLOCATION = "/data/data/francefrancerevolution.gonolesdatabase/databases/";
    private static final String ID = "ID";
    private static final String SAVE = "SAVE";
    private Context bContext;
    private SQLiteDatabase bDatabase;


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.bContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = bContext.getDatabasePath(DBNAME).getPath();
        if(bDatabase != null && bDatabase.isOpen()) {
            return;
        }
        bDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(bDatabase!=null) {
            bDatabase.close();
        }
    }

    public ArrayList<Building> getListBuilding() {
        Building building = null;
        ArrayList<Building> buildingList = new ArrayList<>();
        openDatabase();
        Cursor cursor = bDatabase.rawQuery("SELECT * FROM BUILDING", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            building = new Building(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6));
            buildingList.add(building);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return buildingList;
    }

    public ArrayList<Building> getListBuilding2() {
        Building building = null;
        ArrayList<Building> buildingList = new ArrayList<>();
        openDatabase();
        String [] paramters = new String [] {"YES"};
        Cursor cursor = bDatabase.rawQuery("SELECT * FROM BUILDING WHERE SAVE =?",paramters );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            building = new Building(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6));
            buildingList.add(building);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return buildingList;
    }


    public Building getBuildingbyName(String name) {
        Building building = null;
        openDatabase();
        String [] paramters = new String [] {name};
        Cursor cursor = bDatabase.rawQuery("SELECT * FROM BUILDING WHERE NAME =?",paramters );
        cursor.moveToFirst();

        building = new Building(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6));

        cursor.close();
        closeDatabase();
        return building;
    }


}
