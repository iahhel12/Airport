/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.controllers;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.models.Location;
import core.models.storage.LocationStorage;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class LocationController {

    public static Response newLocation(String id, String name, String city, String country, String latitude, String longitude) {
        try {
            double lat;
            double lon;
            LocationStorage storage = LocationStorage.getInstance();

            if (id.trim().isEmpty() || name.trim().isEmpty() || city.trim().isEmpty() || country.trim().isEmpty() || country.trim().isEmpty() || latitude.trim().isEmpty() || longitude.trim().isEmpty()) {
                return new Response("There can't be emtpy fields", Status.BAD_REQUEST);
            }

            if (id.length() != 3) {
                return new Response("Id has to be 3 letters", Status.BAD_REQUEST);
            }
            if (!(Character.isUpperCase(id.trim().charAt(0)) && Character.isUpperCase(id.trim().charAt(1)) && Character.isUpperCase(id.trim().charAt(2)))) {
                return new Response("Id has to be uppercase.", Status.BAD_REQUEST);
            }

            try {
                lat = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);
            } catch (NumberFormatException ex) {
                return new Response("Latitude and Longitude have to be numbers.", Status.BAD_REQUEST);
            }

            if (lat > 90 | lat < -90) {
                return new Response("Latitude has to be in the interval between -90 and 90", Status.BAD_REQUEST);
            }
            if (lon > 180 | lon < -180) {
                return new Response("Longitude has to be in the interval between -180 and 180", Status.BAD_REQUEST);
            }

            if ((lat * 10000) % 1 != 0) {
                return new Response("Latitude can't have 5 decimals or more", Status.BAD_REQUEST);
            }
            if ((lon * 10000) % 1 != 0) {
                return new Response("Longitude can't have 5 decimals or more", Status.BAD_REQUEST);
            }
            if (!storage.add(new Location(id, name, city, country, lat, lon))) {
                return new Response("That ID is taken", Status.BAD_REQUEST);
            }
            return new Response("The location has been properly added", Status.CREATED);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static Response getAllLocations() {
        LocationStorage locationStorage = LocationStorage.getInstance();

        try {
            ArrayList<Location> allLocations = locationStorage.getArray();
            ArrayList<ArrayList<String>> locationsDataForTable = new ArrayList<>();

            for (Location location : allLocations) {
                ArrayList<String> singleLocationRow = new ArrayList<>();

                singleLocationRow.add(location.getAirportId());
                singleLocationRow.add(location.getAirportName());
                singleLocationRow.add(location.getAirportCity());
                singleLocationRow.add(location.getAirportCountry());

                locationsDataForTable.add(singleLocationRow);
            }

            return new Response("Successfully retrieved all location data.", Status.OK, locationsDataForTable);

        } catch (Exception e) {
            e.printStackTrace();
            return new Response("An internal error has occurred while retrieving location data: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR, null);
        }
    }

}
