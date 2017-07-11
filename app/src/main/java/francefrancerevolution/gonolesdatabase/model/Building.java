package francefrancerevolution.gonolesdatabase.model;

import java.security.PrivateKey;

/**
 * Created by Alucard5 on 4/4/2017.
 */
public class Building {
    private int id;
    private String Building_name;
    private String Building_abbv;
    private String Building_address;
    private String Building_Lat;
    private String Building_Lng;
    private String Saved;

    public Building(int id, String Building_name, String Building_abbv, String Building_address, String Building_lat, String Building_lng, String saved) {
        this.id = id;
        this.Building_name = Building_name;
        this.Building_abbv = Building_abbv;
        this.Building_address = Building_address;
        this.Building_Lat = Building_lat;
        this.Building_Lng = Building_lng;
        this.Saved = saved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuilding_name() {return Building_name;   }

    public void setBuilding_name(String Building_name) {
        this.Building_name = Building_name;
    }

    public String getBuilding_abbv() {
        return Building_abbv;
    }

    public void setBuilding_abbv(String Building_abbv) {
        this.Building_abbv = Building_abbv;
    }

    public String getBuilding_address() {
        return Building_address;
    }

    public void setBuilding_address(String Building_address) {
        this.Building_address = Building_address;
    }

    public String getBuilding_Lat() {return Building_Lat;  }

    public void setBuilding_Lat(String Building_lat) {
        this.Building_Lat = Building_lat;
    }

    public String getBuilding_Lng() {return Building_Lng;   }

    public void setBuilding_Lng(String Building_lng) {
        this.Building_Lng = Building_lng;
    }



    public String getSaved() {return Saved;   }

    public void setSaved(String saved) {
        this.Saved = saved;
    }


    public String toString(){

        return this.Building_name + "\n "+this.Building_abbv +"\n "+this.Building_address+" Tallahassee, FL 32304";
    }
}
