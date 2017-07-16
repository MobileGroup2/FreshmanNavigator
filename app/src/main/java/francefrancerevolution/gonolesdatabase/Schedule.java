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

