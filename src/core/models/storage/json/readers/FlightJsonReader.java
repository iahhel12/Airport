/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json.readers;

import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.FlightStorage;
import core.models.storage.LocationStorage;
import core.models.storage.PlaneStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Iahhel
 */
public class FlightJsonReader {

    private static final String JSON_FILE_PATH = "src/core/models/storage/json/flights.json";

    public static void loadFlightsFromJson() {
        FlightStorage flightStorage = FlightStorage.getInstance();
        PlaneStorage planeStorage = PlaneStorage.getInstance();
        LocationStorage locationStorage = LocationStorage.getInstance();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            JSONArray jsonArray = new JSONArray(jsonContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flightJson = jsonArray.getJSONObject(i);

                String id = flightJson.getString("id");
                String planeId = flightJson.getString("plane");
                String departureLocationId = flightJson.getString("departureLocation");
                String arrivalLocationId = flightJson.getString("arrivalLocation");

                String scaleLocationId = null;
                if (!flightJson.isNull("scaleLocation")) {
                    scaleLocationId = flightJson.getString("scaleLocation");
                }

                LocalDateTime departureDate = LocalDateTime.parse(flightJson.getString("departureDate"));
                int hoursDurationArrival = flightJson.getInt("hoursDurationArrival");
                int minutesDurationArrival = flightJson.getInt("minutesDurationArrival");
                int hoursDurationScale = flightJson.getInt("hoursDurationScale");
                int minutesDurationScale = flightJson.getInt("minutesDurationScale");

                Plane plane = planeStorage.get(planeId);

                Location departureLocation = locationStorage.get(departureLocationId);
                Location arrivalLocation = locationStorage.get(arrivalLocationId);
                Location scaleLocation = null;
                if (scaleLocationId != null) {
                    scaleLocation = locationStorage.get(scaleLocationId);
                }

                if (plane != null && departureLocation != null && arrivalLocation != null && (scaleLocationId == null || scaleLocation != null)) {

                    Flight newFlight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation,
                            departureDate, hoursDurationArrival, minutesDurationArrival,
                            hoursDurationScale, minutesDurationScale);

                    flightStorage.add(newFlight);

                } else {
                    System.err.println("Missing required data for flight " + id + ". Flight not created.");
                    if (plane == null) {
                        System.err.println("  Plane with ID '" + planeId + "' not found in PlaneStorage.");
                    }
                    if (departureLocation == null) {
                        System.err.println("  Departure Location with ID '" + departureLocationId + "' not found in LocationStorage.");
                    }
                    if (arrivalLocation == null) {
                        System.err.println("  Arrival Location with ID '" + arrivalLocationId + "' not found in LocationStorage.");
                    }
                    if (scaleLocationId != null && scaleLocation == null) {
                        System.err.println("  Scale Location with ID '" + scaleLocationId + "' not found in LocationStorage.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date/time: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during JSON parsing or flight creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
