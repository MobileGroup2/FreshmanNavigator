package francefrancerevolution.gonolesdatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by alex on 7/13/2017.
 */

public class UserContentProvider extends ContentProvider {

    public final static String DBNAME = "USERDATABASE";
    public static final String TABLE_NAME = "UserDataTable";
    public MainDatabaseHelper mOpenHelper;
    public static final Uri CONTENT_URI = Uri.parse("content://francefrancerevolution.gonolesdatabase.provider");

    public final static String NAME = "Name";
    public final static String BUILDING = "Building";
    public final static String TIME = "Time";
    public final static String PKEY = "_ID";

    //user data to be stored in the class name building and time of class
    private static final String SQL_CREATE_MAIN = "CREATE TABLE UserDataTable ( " +
                                                    "_ID INTEGER PRIMARY KEY, " +
                                                    "Name TEXT, " +
                                                    "Building TEXT, " +
                                                    "Time TEXT )";


    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }


    //from values(key-value pairs), you get the info
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mOpenHelper.getWritableDatabase()
                .insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //need to check for invalid values?
        return mOpenHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }


    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

}
