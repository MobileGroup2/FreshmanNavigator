package francefrancerevolution.gonolesdatabase;

/**
 * Created by Alucard5 on 4/4/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import francefrancerevolution.gonolesdatabase.database.DatabaseHelper;
import francefrancerevolution.gonolesdatabase.model.AddClass;
import francefrancerevolution.gonolesdatabase.model.Building;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.R.attr.name;
import static francefrancerevolution.gonolesdatabase.model.AddClass.ID;
import static francefrancerevolution.gonolesdatabase.model.AddClass.bName;


public class MainActivity extends AppCompatActivity {
    private ListView lvBuilding, lvBuildingSaved;
    ArrayAdapter<Building> adapter, adapter2;
    private ArrayList<Building> bBuildingList;
    private ArrayList<Building> savedBuildingList= new ArrayList<>();
    private DatabaseHelper bDBHelper;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lvBuilding = (ListView)findViewById(R.id.listview_building);
        lvBuildingSaved = (ListView)findViewById(R.id.listview_userbuilding);
        sv = (SearchView) findViewById(R.id.searchView);

        bDBHelper = new DatabaseHelper(this);
        final Button btn = (Button) findViewById(R.id.SAVEDLIST);

        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(!database.exists()) {
            bDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                //Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Get Building list in db when db exists
        bBuildingList = bDBHelper.getListBuilding();
        savedBuildingList = bDBHelper.getListBuilding2();
        //Init adapter
        adapter = new ArrayAdapter<Building>(this,android.R.layout.simple_list_item_1, bBuildingList);
        //Set adapter for listview
        lvBuilding.setAdapter(adapter);

        adapter2 = new ArrayAdapter<Building>(this,android.R.layout.simple_list_item_1, savedBuildingList);
        //Set adapter for listview
        lvBuildingSaved.setAdapter(adapter2);


        final Intent intent = getIntent();
        lvBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              if (intent.getExtras().getString(ID).equals("from add class")) {
                  addToClass(((TextView) view).getText().toString());
              }else{
                  passToMap(((TextView) view).getText().toString());
              }
            }
        });

        lvBuildingSaved.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passToMap(((TextView) view).getText().toString());
            }
        });


        lvBuilding.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                saveAddRemove(((TextView) view).getText().toString(),true);
            return true;
            }
        });

        lvBuildingSaved.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                saveAddRemove(((TextView) view).getText().toString(),false);
                updateSavedList();
                return true;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Clear the Query
                sv.setQuery("", false);
                sv.clearFocus();
                updateSavedList();

                if (lvBuildingSaved.getVisibility() == View.VISIBLE)
                {
                    btn.setText("Saved");
                    lvBuildingSaved.setVisibility(View.INVISIBLE);
                    lvBuilding.setVisibility(View.VISIBLE);

                }
                else
                {
                    btn.setText("All");
                    lvBuildingSaved.setVisibility(View.VISIBLE);
                    lvBuilding.setVisibility(View.INVISIBLE);
                }
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter2.getFilter().filter(newText);
                return false;
            }
        });

    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void passToMap(String bldinfo) {
        String bldname = bldinfo.substring(0, bldinfo.indexOf("\n"));
        Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("BldName", bldname);
        mapIntent.putExtras(bundle);
        startActivity(mapIntent);
    }

    private void saveAddRemove(String bldinfo, boolean saveYN) {
        String bldname = bldinfo.substring(0, bldinfo.indexOf("\n"));
        String addremove = null;

        Building Savebuilding = bDBHelper.getBuildingbyName(bldname);
        savedBuildingList.add(Savebuilding);

        SQLiteDatabase db = bDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (saveYN)
        {
            values.put("SAVE","YES");
            addremove = "added to";
        }else {
            values.put("SAVE","NULL");
            addremove = "removed from";
        }

        db.update("Building",values,"NAME= ?",new String[]{Savebuilding.getBuilding_name()});

        Toast.makeText(getBaseContext(), bldname+" has been "+addremove+" saved list",Toast.LENGTH_SHORT).show();
    }

    private void updateSavedList() {
        savedBuildingList = bDBHelper.getListBuilding2();
        adapter2 = new ArrayAdapter<Building>(getBaseContext(),android.R.layout.simple_list_item_1, savedBuildingList);
        lvBuildingSaved.setAdapter(adapter2);
    }

    private void addToClass(String bldinfo)
    {
        String bldname = bldinfo.substring(0, bldinfo.indexOf("\n"));
        Intent intent = new Intent(MainActivity.this, AddClass.class);
        intent.putExtra(bName, bldname);
        startActivity(intent);
    }

}
