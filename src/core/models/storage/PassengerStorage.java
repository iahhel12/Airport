/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Passenger;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class PassengerStorage implements Storage<Passenger> {

    private static PassengerStorage instance;
    private ArrayList<Passenger> passengers;

    private PassengerStorage() {
        this.passengers = new ArrayList<>();
    }

    public static PassengerStorage getInstance() {
        if (instance == null) {
            instance = new PassengerStorage();
        }
        return instance;
    }



    @Override
    public boolean add(Passenger item) {
        try {
              if(get((String.valueOf(item.getId())))!= null){
                return false;
            }
            this.passengers.add(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Passenger get(String id) {
        try {
            long passengerId = Long.parseLong(id);
            for (Passenger passenger : this.passengers) {
                if (passenger.getId() == passengerId) {
                    return passenger;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid ID format for Passenger. Expected a long, got: " + id);
        }
        return null;
    }
    
    @Override
    public ArrayList<Passenger> getArray() {
        return passengers;
    }
    @Override
    public boolean update(Passenger item) {
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).getId() == item.getId()) {
                this.passengers.set(i, item);
                return true;
            }
        }
        return false;
    }


}
