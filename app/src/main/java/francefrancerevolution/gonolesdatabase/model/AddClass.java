package francefrancerevolution.gonolesdatabase.model;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import francefrancerevolution.gonolesdatabase.MainActivity;
import francefrancerevolution.gonolesdatabase.R;
import francefrancerevolution.gonolesdatabase.Schedule;
import francefrancerevolution.gonolesdatabase.UserContentProvider;

//import static francefrancerevolution.gonolesdatabase.MainActivity.bName;


/**
 * Created by sap15e on 7/13/2017.
 */

public class AddClass extends AppCompatActivity
{
    private Button add;
    private EditText editClass, editTime, editBuilding;
    public static String ID = "UniqueID";
    public static String CLASS = "class";
    public static String TIME = "time";
    public static Uri uri;
    public static String bName = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        editBuilding = (EditText) findViewById(R.id.edit_text_building);
        editBuilding.setShowSoftInputOnFocus(false);
        editClass = (EditText)findViewById(R.id.edit_text_class);
        editTime = (EditText) findViewById(R.id.edit_text_time);
        add = (Button) findViewById(R.id.add_button);

        editBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                savePreferences("CLASS",editClass.getText().toString());
//                savePreferences("TIME",editTime.getText().toString());
                Intent intent = new Intent (AddClass.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ID,"from add class");
                bundle.putString(CLASS,editClass.getText().toString());
                bundle.putString(TIME,editTime.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });

        //THIS WILL GET THE NAME FROM THE BUILDING LIST AND CHANGE THE EDIT TEXT WITH THE BUILDING NAME.
        Intent intent = this.getIntent();
        if(intent!=null)
        {
            String name = intent.getStringExtra(bName);
            String className= intent.getStringExtra(CLASS);
            String time = intent.getStringExtra(TIME);
            editBuilding.setText(name);
            editClass.setText(className);
            editTime.setText(time);
        }

        add.setOnClickListener(mAddListner);
        //loadSavedPreferences();
    }
    private void savePreferences(String key, String value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    private void loadSavedPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String className = sharedPreferences.getString("CLASS",null);
        String time= sharedPreferences.getString("TIME",null);
       // editTime.setText(time);
       // editClass.setText(className);

    }





    private View.OnClickListener mAddListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String building, time, _class;
            building = editBuilding.getText().toString();
            time = editTime.getText().toString();
            _class = editClass.getText().toString();
            //info entered is gotten properly

            ContentValues values = new ContentValues();
            values.put(UserContentProvider.NAME, _class);
            values.put(UserContentProvider.BUILDING, building);
            values.put(UserContentProvider.TIME, time);
            uri = getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
            Log.i(uri.toString(), values.toString());
            Intent intent = new Intent(AddClass.this, Schedule.class);
            intent.putExtra("URI", uri.toString());
            startActivityForResult(intent,0);
        }
    };


}


