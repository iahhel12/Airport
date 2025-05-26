/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Flight;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class FlightStorage implements Storage<Flight> {

    private static FlightStorage instance;


    private ArrayList< Flight> flights;

    private FlightStorage() {
        this.flights = new ArrayList<>();
    }

    public static FlightStorage getInstance() {
        if (instance == null) {
            instance = new FlightStorage();
        }
        return instance;
    }

    @Override
    public boolean add(Flight item) {
        try {
              if(get(item.getId())!= null){
                return false;
            }
            this.flights.add(item);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Flight get(String id) {
        for (Flight fl : this.flights) {
            if (fl.getId().equals(id)) {
                return fl;
            }
        }
        return null;
    }

    @Override
    public boolean update(Flight item) {
        for (int i = 0; i < this.flights.size(); i++) {
            if (this.flights.get(i).getId().equals(item.getId())) {
                this.flights.set(i, item); 
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Flight> getArray() {
      return flights;
    }





}
