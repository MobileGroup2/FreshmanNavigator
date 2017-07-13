package francefrancerevolution.gonolesdatabase.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import francefrancerevolution.gonolesdatabase.MainActivity;
import francefrancerevolution.gonolesdatabase.R;

/**
 * Created by sap15e on 7/13/2017.
 */

public class AddClass extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        EditText building = (EditText) findViewById(R.id.editText2);
        building.setShowSoftInputOnFocus(false);


        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddClass.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}


