/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json.readers;

import core.models.Passenger;
import core.models.storage.PassengerStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Iahhel
 */
public class PassengerJsonReader {
        private static final String JSON_FILE_PATH = "src/core/models/storage/json/passengers.json"; 

    public static void loadPassengersFromJson() {
        PassengerStorage passengerStorage = PassengerStorage.getInstance();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            JSONArray jsonArray = new JSONArray(jsonContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject passengerJson = jsonArray.getJSONObject(i);
                long id = passengerJson.getLong("id");
                String firstname = passengerJson.getString("firstname");
                String lastname = passengerJson.getString("lastname");
                LocalDate birthDate = LocalDate.parse(passengerJson.getString("birthDate")); 
                int countryPhoneCode = passengerJson.getInt("countryPhoneCode");
                long phone = passengerJson.getLong("phone");
                String country = passengerJson.getString("country");
                Passenger newPassenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
                passengerStorage.add(newPassenger);
               
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred during JSON parsing or passenger creation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
