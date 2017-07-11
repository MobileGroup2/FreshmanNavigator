

package francefrancerevolution.gonolesdatabase.model;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import francefrancerevolution.gonolesdatabase.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        View v = getWindow().getDecorView().getRootView();
        v.getBackground().setAlpha(10);

        Button maps = (Button) findViewById(R.id.map);
        Button schedule = (Button) findViewById(R.id.schedule);
        Button buildings = (Button) findViewById(R.id.buildings);





    }


    public void mapsClick(View v)
    {

    }

    public void scheduleClick(View v)
    {

    }

    public void buildingClick(View v)
    {
      ;
    }

}
