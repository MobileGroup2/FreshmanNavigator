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


package francefrancerevolution.gonolesdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import francefrancerevolution.gonolesdatabase.model.AddClass;
import francefrancerevolution.gonolesdatabase.model.HomeScreen;



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
        addClasses = (Button) findViewById(R.id.button_add_classes);
        schedule = (ListView) findViewById(R.id.class_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classList);
        schedule = (ListView)findViewById(R.id.class_view);
        classList = new ArrayList<String>();

        Cursor cursor = getContentResolver().query(UserContentProvider.CONTENT_URI, null, null, null, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                classList.add("Class: " + cursor.getString(1) + "\n" +"Building: " + cursor.getString(2) + "\n" + "Time: " + cursor.getString(3));
            }
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classList);
        schedule.setAdapter(adapter);

        schedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view).getText().toString();
                name = name.substring(0, name.indexOf("\n"));
                name = name.replace("Class: ", "");
                getContentResolver().delete(UserContentProvider.CONTENT_URI, "Name = ?", new String[]{name});
                Toast.makeText(getBaseContext(),name + " removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(Schedule.this, HomeScreen.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setIntent);
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

