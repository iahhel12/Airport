/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json.readers;

import core.models.Plane;
import core.models.storage.PlaneStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Iahhel
 */
public class PlaneJsonReader {
    private static final String JSON_FILE_PATH = "src/core/models/storage/json/planes.json";

    public static void loadPlanesFromJson() {
        PlaneStorage planeStorage = PlaneStorage.getInstance();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(jsonContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject planeJson = jsonArray.getJSONObject(i);

                String id = planeJson.getString("id");
                String brand = planeJson.getString("brand");
                String model = planeJson.getString("model");
                int maxCapacity = planeJson.getInt("maxCapacity");
                String airline = planeJson.getString("airline");

                Plane newPlane = new Plane(id, brand, model, maxCapacity, airline);

                 planeStorage.add(newPlane);
               
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred during JSON parsing or plane creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
