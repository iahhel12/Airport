/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.models.Passenger;
import core.models.storage.PassengerStorage;
import core.models.storage.json.readers.FlightJsonReader;
import core.models.storage.json.readers.LocationJsonReader;
import core.models.storage.json.readers.PassengerJsonReader;
import core.models.storage.json.readers.PlaneJsonReader;
import core.views.AirportFrame;
import javax.swing.UIManager;

/**
 *
 * @author Iahhel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        PlaneJsonReader.loadPlanesFromJson();
        LocationJsonReader.loadLocationsFromJson();
        PassengerJsonReader.loadPassengersFromJson();
        FlightJsonReader.loadFlightsFromJson();
   

        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AirportFrame().setVisible(true);
            }
        });
    }
}
