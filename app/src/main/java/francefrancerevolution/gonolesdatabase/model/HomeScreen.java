
package francefrancerevolution.gonolesdatabase.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import francefrancerevolution.gonolesdatabase.MainActivity;
import francefrancerevolution.gonolesdatabase.MapsActivity;
import francefrancerevolution.gonolesdatabase.R;
import francefrancerevolution.gonolesdatabase.Schedule;

import static francefrancerevolution.gonolesdatabase.model.AddClass.ID;


public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //View v = getWindow().getDecorView().getRootView();
        //v.getBackground().setAlpha(10);


        //These buttons not needed because of xml onClick - Alex C ()?

      //  Button maps = (Button) findViewById(R.id.map);
        //Button add_class = (Button) findViewById(R.id.add_class);
        //Button buildings = (Button) findViewById(R.id.buildings);
//        Button schedule = (Button)findViewById(R.id.schedule);
//        schedule.setOnClickListener(mScheduleListener);
    }


//    private View.OnClickListener mScheduleListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(HomeScreen.this, Schedule.class);
//            startActivity(intent);
//
//        }
//    };


    public void mapsClick(View v)
    {
        Intent intent = new Intent(HomeScreen.this, MapsActivity.class);
        startActivity(intent);
    }


    public void scheduleClick(View v)
    {
        //SCHEDULE.JAVA HAS ERRORS OR NEEDS DIFF PACKAGES INCLUDE
        //OR MAYBE
        Intent intent = new Intent(HomeScreen.this, Schedule.class);
        startActivity(intent);
    }

    public void buildingClick(View v)
    {
        Intent intent = new Intent(HomeScreen.this, MainActivity.class);
        intent.putExtra(ID,"from building class");
        startActivity(intent);
    }

}
