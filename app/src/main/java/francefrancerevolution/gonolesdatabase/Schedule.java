package francefrancerevolution.gonolesdatabase;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        Intent intent = getIntent();
        String str = intent.getStringExtra("URI");
        //Uri uri = Uri.parse(str);
        //Long row = ContentUris.parseId(uri);

        String selectionClause = "_ID = ?";
        String selectionArgs[] = {Long.toString(5)};
        Cursor cursor = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, selectionArgs, null, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                Log.i("\n\n", "\n");
                Log.i(cursor.getString(0), cursor.getString(1));
                Log.i(cursor.getString(2), "");
                schedule.setText(cursor.getString(0));
            }
        }

    }






    public void classClick(View v)
    {
        Intent intent = new Intent(Schedule.this, AddClass.class);
        startActivity(intent);
    }


}

