/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.controllers;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.models.Plane;
import core.models.storage.PlaneStorage;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class PlaneController {

    public static Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            id = id.trim();
            brand = brand.trim();
            model = model.trim();
            maxCapacity = maxCapacity.trim();
            airline = airline.trim();
            PlaneStorage planeStorage = PlaneStorage.getInstance();
            int intMaxCapacity;

            if (id.isEmpty() || brand.isEmpty() || model.isEmpty() || maxCapacity.isEmpty() || airline.isEmpty()) {
                return new Response("No field can be empty", Status.BAD_REQUEST);
            }

            if (!id.matches("[A-Z]{2}\\d{5}") || id.length() != 7) {
                return new Response("The length of the Id must be 7 characters in the format XXYYYY where X are capital letters and Y digits", Status.BAD_REQUEST);
            }

            try {
                intMaxCapacity = Integer.parseInt(maxCapacity);
            } catch (NumberFormatException ex) {
                return new Response("The capacity has to be a number", Status.BAD_REQUEST);
            }

            if (intMaxCapacity < 0) {
                return new Response("The max capacity can't be negative", Status.BAD_REQUEST);
            }

            //Add to storage/Check if the if is taken
            if (!planeStorage.add(new Plane(id, brand, model, intMaxCapacity, airline))) {
                return new Response("There's already a plane with that Id", Status.BAD_REQUEST);
            }

            //All good
            return new Response("Succesfully created the plane " + id, Status.CREATED);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllPlanes() {
        PlaneStorage planeStorage = PlaneStorage.getInstance();

        try {
            ArrayList<Plane> allPlanes = planeStorage.getArray();
            ArrayList<ArrayList<String>> planesDataForTable = new ArrayList<>();

            for (Plane plane : allPlanes) {
                ArrayList<String> singlePlaneRow = new ArrayList<>();

                singlePlaneRow.add(plane.getId());                 
                singlePlaneRow.add(plane.getBrand());            
                singlePlaneRow.add(plane.getModel());         
                singlePlaneRow.add(String.valueOf(plane.getMaxCapacity()));
                singlePlaneRow.add(plane.getAirline());       
                singlePlaneRow.add(String.valueOf(plane.getNumFlights())); 

                planesDataForTable.add(singlePlaneRow);
            }

            return new Response("Successfully retrieved all plane data.", Status.OK, planesDataForTable);

        } catch (Exception e) {
            e.printStackTrace();
            return new Response("An internal error has occurred while retrieving plane data: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR, null);
        }
    }

}
