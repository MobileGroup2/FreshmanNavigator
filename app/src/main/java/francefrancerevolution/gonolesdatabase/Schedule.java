package francefrancerevolution.gonolesdatabase;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import francefrancerevolution.gonolesdatabase.model.AddClass;


public class Schedule extends AppCompatActivity {

    private Button addClasses;
    private ListView schedule;
    ArrayAdapter<String> adapter;
    private ArrayList<String> classList;
    public Schedule(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        addClasses = (Button)findViewById(R.id.button_add_classes);
        schedule = (ListView)findViewById(R.id.class_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classList);

        Intent intent = this.getIntent();
        if(intent != null){
            String str = intent.getStringExtra("URI");
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        }
        // adapter.add("THIS IS ADDED");
//        schedule.setAdapter(adapter);
//        classList.add("New Item");
//        adapter.notifyDataSetChanged();




        String selectionClause = "_ID = ?";
        String selectionArgs[] = {"1"};
        String selectionArgs2[] = {"2"};
        String selectionArgs3[] = {"3"};
        String selectionArgs4[] = {"4"};
        String selectionArgs5[] = {"5"};
        Cursor cursor = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs, null);
        Cursor cursor2 = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs2, null);
        Cursor cursor3 = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs3, null);
        Cursor cursor4 = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs4, null);
        Cursor cursor5 = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs5, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                Toast.makeText(this, cursor.getString(1) + " " + cursor.getString(2), Toast.LENGTH_LONG).show();

            }
        }


    }


    public ArrayList<String> getClassList(){
        String str = null;
        ArrayList<String> classList = new ArrayList<>();




        return classList;
    }




    public void classClick(View v)
    {
        Intent intent = new Intent(Schedule.this, AddClass.class);

        startActivity(intent);

        //finish();
    }


}

