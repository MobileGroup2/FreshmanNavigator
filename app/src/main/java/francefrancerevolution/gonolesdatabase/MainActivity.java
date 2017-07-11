package francefrancerevolution.gonolesdatabase;

/**
 * Created by Alucard5 on 4/4/2017.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
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
import android.widget.Toast;

import francefrancerevolution.gonolesdatabase.database.DatabaseHelper;
import francefrancerevolution.gonolesdatabase.model.Building;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView lvBuilding;
    private ListView lvBuilding2;
    ArrayAdapter<Building> adapter;
    ArrayAdapter<Building> adapter2;
    private ArrayList<Building> bBuildingList;
    private ArrayList<Building> userBuildingList= new ArrayList<>();
    private DatabaseHelper bDBHelper;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lvBuilding = (ListView)findViewById(R.id.listview_building);
        lvBuilding2 = (ListView)findViewById(R.id.listview_userbuilding);
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
        userBuildingList = bDBHelper.getListBuilding2();
        //Init adapter
        adapter = new ArrayAdapter<Building>(this,android.R.layout.simple_list_item_1, bBuildingList);
        //Set adapter for listview
        lvBuilding.setAdapter(adapter);

        adapter2 = new ArrayAdapter<Building>(this,android.R.layout.simple_list_item_1, userBuildingList);
        //Set adapter for listview
        lvBuilding2.setAdapter(adapter2);

        lvBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+bBuildingList.get(position).getBuilding_Lat()+", "+bBuildingList.get(position).getBuilding_Lng()+"&mode=walking");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);


                //Toast.makeText(getBaseContext(), bBuildingList.get(position).getSaved(),Toast.LENGTH_SHORT).show();
            }
        });

        lvBuilding.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Building building2 = new Building(bBuildingList.get(position).getId(), bBuildingList.get(position).getBuilding_name(), bBuildingList.get(position).getBuilding_abbv(), bBuildingList.get(position).getBuilding_address(), bBuildingList.get(position).getBuilding_Lat(), bBuildingList.get(position).getBuilding_Lng(), bBuildingList.get(position).getSaved());
                userBuildingList.add(building2);

                SQLiteDatabase db = bDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("SAVE","YES");

                db.update("Building",values,"NAME= ?",new String[]{building2.getBuilding_name()});

                 Toast.makeText(getBaseContext(), building2.getBuilding_name()+" has been added to saved list",Toast.LENGTH_SHORT).show();
            return true;
            }
        });



        lvBuilding2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+userBuildingList.get(position).getBuilding_Lat()+", "+userBuildingList.get(position).getBuilding_Lng()+"&mode=walking");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);


                //Toast.makeText(getBaseContext(), bBuildingList.get(position).getSaved(),Toast.LENGTH_SHORT).show();
            }
        });

        lvBuilding2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Building building2 = new Building(userBuildingList.get(position).getId(), userBuildingList.get(position).getBuilding_name(), userBuildingList.get(position).getBuilding_abbv(), userBuildingList.get(position).getBuilding_address(), userBuildingList.get(position).getBuilding_Lat(), userBuildingList.get(position).getBuilding_Lng(), userBuildingList.get(position).getSaved());
                //userBuildingList.remove(position);
                Toast.makeText(getBaseContext(), building2.getBuilding_name()+" has been removed to saved list",Toast.LENGTH_SHORT).show();

                //UPDATE `Building` SET `SAVE`=? WHERE `_rowid_`='1';

                SQLiteDatabase db = bDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("SAVE","NULL");

                db.update("Building",values,"NAME= ?",new String[]{building2.getBuilding_name()});
                //db.update("Building",values,"NAME= ?",new String[]{building2.getBuilding_name()});

                return true;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userBuildingList = bDBHelper.getListBuilding2();
                adapter2 = new ArrayAdapter<Building>(getBaseContext(),android.R.layout.simple_list_item_1, userBuildingList);
                lvBuilding2.setAdapter(adapter2);

                if (lvBuilding2.getVisibility() == View.VISIBLE)
                {
                    btn.setText("Saved");
                    lvBuilding2.setVisibility(View.INVISIBLE);
                    lvBuilding.setVisibility(View.VISIBLE);

                }
                else
                {
                    btn.setText("All");
                    lvBuilding2.setVisibility(View.VISIBLE);
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
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
