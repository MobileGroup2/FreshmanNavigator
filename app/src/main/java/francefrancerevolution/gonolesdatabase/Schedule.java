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

import org.w3c.dom.Text;

import francefrancerevolution.gonolesdatabase.model.AddClass;


public class Schedule extends FragmentActivity {

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
//        Intent intent = getIntent();
//        if(intent != null) {
//            String str = intent.getStringExtra("URI");
//            Log.i("  HERE   ", str);
//            Uri uri = Uri.parse(str);
//            row = ContentUris.parseId(uri);
//        }
        Toast.makeText(this, "IM HERE", Toast.LENGTH_LONG).show();
        String selectionClause = "_ID = ?";
        //String selectionArgs[] = {Long.toString(row)};
        Cursor cursor = getContentResolver().query(UserContentProvider.CONTENT_URI, null, selectionClause, null, null, null);

        if(cursor != null){
            Log.i("C IS! EQUAL TO NULL", "");
            while(cursor.moveToNext()){
                //Log.i("\n\n", "\n");
                //Log.i(cursor.getString(0), cursor.getString(1));
                //Log.i(cursor.getString(2), "");
                schedule.setText(cursor.getString(0));
                schedule.setText("Anjali");
                Log.i("BLABIDDY BLAH", "BLAH BLAH");
            }
        }
        else
            Log.i("CURSOR IS EQUAL TO NULL", "");

    }






    public void classClick(View v)
    {
        Intent intent = new Intent(Schedule.this, AddClass.class);

        startActivity(intent);

        //finish();
    }


}

