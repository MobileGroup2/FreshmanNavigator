package francefrancerevolution.gonolesdatabase;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import francefrancerevolution.gonolesdatabase.model.AddClass;


public class Schedule extends AppCompatActivity {

    private Button addClasses;
    private TextView schedule;

    public Schedule(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        addClasses = (Button)findViewById(R.id.button_add_classes);
        schedule = (TextView)findViewById(R.id.text_schedule);
//        Long row = null;
        long row=0;
        Intent intent = this.getIntent();
        if(intent != null){

            String str = intent.getStringExtra("URI");
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        }



        String selectionClause = "_ID = ?";
       // String selectionArgs[] = {Long.toString(row)};
        String selectionArgs[] = new String[]{"1"};
        Cursor cursor = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs, null);

       if(cursor != null){
            while(cursor.moveToNext()){
                schedule.setText(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
            }
        }


    }






    public void classClick(View v)
    {
        Intent intent = new Intent(Schedule.this, AddClass.class);

        startActivity(intent);

        //finish();
    }


}

