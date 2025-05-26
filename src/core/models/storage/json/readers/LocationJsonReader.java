/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json.readers;

import core.models.Location;
import core.models.storage.LocationStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Iahhel
 */
public class LocationJsonReader {

    private static final String JSON_FILE_PATH = "src/core/models/storage/json/locations.json";

    public static void loadLocationsFromJson() {
        LocationStorage locationStorage = LocationStorage.getInstance();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(jsonContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject locationJson = jsonArray.getJSONObject(i);

                String airportId = locationJson.getString("airportId");
                String airportName = locationJson.getString("airportName");
                String airportCity = locationJson.getString("airportCity");
                String airportCountry = locationJson.getString("airportCountry");
                double airportLatitude = locationJson.getDouble("airportLatitude");
                double airportLongitude = locationJson.getDouble("airportLongitude");

                Location newLocation = new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude);

                locationStorage.add(newLocation);

            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred during JSON parsing or location creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
