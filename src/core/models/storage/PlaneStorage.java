/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
import java.util.ArrayList;

public class PlaneStorage implements Storage<Plane> {

    private static PlaneStorage instance;
    private ArrayList<Plane> planes;

    private PlaneStorage() {
        this.planes = new ArrayList<>();
    }

    public static PlaneStorage getInstance() {
        if (instance == null) {
            instance = new PlaneStorage();
        }
        return instance;
    }

    @Override
    public boolean add(Plane item) {
        try {
            if (get(item.getId()) != null) {
                return false;
            }
            this.planes.add(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Plane get(String id) {
        for (Plane plane : this.planes) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return null;
    }

    @Override
    public boolean update(Plane item) {
        for (int i = 0; i < this.planes.size(); i++) {
            if (this.planes.get(i).getId().equals(item.getId())) {
                this.planes.set(i, item);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Plane> getArray() {
              return planes;

    }


}
