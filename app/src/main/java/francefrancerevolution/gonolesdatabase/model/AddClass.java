package francefrancerevolution.gonolesdatabase.model;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import francefrancerevolution.gonolesdatabase.MainActivity;
import francefrancerevolution.gonolesdatabase.R;
import francefrancerevolution.gonolesdatabase.Schedule;
import francefrancerevolution.gonolesdatabase.UserContentProvider;

/**
 * Created by sap15e on 7/13/2017.
 */

public class AddClass extends AppCompatActivity
{
    private Button add;
    private EditText editClass, editTime, editBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        editBuilding = (EditText) findViewById(R.id.edit_text_building);
        editBuilding.setShowSoftInputOnFocus(false);

        //we need to get the building name somehow, not just go to the page
        editBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddClass.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editClass = (EditText)findViewById(R.id.edit_text_class);
        editTime = (EditText) findViewById(R.id.edit_text_time);
        add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(mAddListner);







    }



    private View.OnClickListener mAddListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String building, time, _class;
            building = editBuilding.getText().toString();
            time = editTime.getText().toString();
            _class = editClass.getText().toString();
            Log.i(building, time);
            Log.i("   HERE   ", _class);
            ContentValues values = new ContentValues();
            values.put(UserContentProvider.NAME, _class);
            values.put(UserContentProvider.BUILDING, building);
            values.put(UserContentProvider.TIME, time);
            Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
            Log.i(uri.toString(), values.toString());
            Intent intent = new Intent(AddClass.this, Schedule.class);
            startActivity(intent);
        }
    };


}


