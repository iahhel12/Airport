/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class LocationStorage implements Storage<Location> {

    private static LocationStorage instance;
    private ArrayList<Location> locations;

    private LocationStorage() {
        this.locations = new ArrayList<>();
    }

    public static LocationStorage getInstance() {
        if (instance == null) {
            instance = new LocationStorage();
        }
        return instance;
    }

    @Override
    public boolean add(Location item) {
        try {
            if(get(item.getAirportId())!= null){
                return false;
            }
            this.locations.add(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Location get(String id) {
        for (Location loc : this.locations) {
            if (loc.getAirportId().equals(id)) {
                return loc;
            }
        }
        return null;
    }

    @Override
    public boolean update(Location item) {
        for (int i = 0; i < this.locations.size(); i++) {
            if (this.locations.get(i).getAirportId().equals(item.getAirportId())) {
                this.locations.set(i, item);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Location> getArray() {
       return locations;
    }
    
    

    
}
