/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.controllers;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.models.Flight;
import core.models.Passenger;
import core.models.storage.FlightStorage;
import core.models.storage.PassengerStorage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class PassengerController {

    public static Response createPassanger(String id, String firstname, String lastname, String yearS, String monthS, String dayS, String countryPhoneCode, String phone, String country) {
        try {
            long FormattedId, formattedPhone;
            int year, month, day, phoneCode;
            LocalDate birthDate;
            PassengerStorage storage = PassengerStorage.getInstance();
            if (id.trim().isEmpty() || firstname.trim().equals("") || lastname.trim().equals("") || countryPhoneCode.trim().equals("") || phone.trim().equals("") || country.trim().equals("")) {
                return new Response("No fields can be emtpy", Status.BAD_REQUEST);
            }
            id = id.trim();
            phone = phone.trim();
            countryPhoneCode = countryPhoneCode.trim();
            yearS = yearS.trim();
            try {
                FormattedId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                return new Response("Id hast to be a natural number.", Status.BAD_REQUEST);
            }
            if (id.length() < 1 || id.length() > 15 || !(FormattedId >= 0)) {
                return new Response("Id has to have 1 to 15 digits.", Status.BAD_REQUEST);
            }

            try {
                year = Integer.parseInt(yearS);
                month = Integer.parseInt(monthS);
                day = Integer.parseInt(dayS);

            } catch (NumberFormatException ex) {
                return new Response("Invalid birthday format", Status.BAD_REQUEST);
            }

            if (!(year >= 0)) {
                return new Response("The birth year can't be negative", Status.BAD_REQUEST);
            }

            birthDate = LocalDate.of(year, month, day);
            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birth date can't be in the future.", Status.INTERNAL_SERVER_ERROR);
            }

            try {
                formattedPhone = Long.parseLong(phone);
            } catch (NumberFormatException ex) {
                return new Response("The phone number can only contain numbers", Status.BAD_REQUEST);
            }
            if (phone.length() < 1 || phone.length() > 11) {
                return new Response("Digits of the phone number have to be from 1 to 11.", Status.BAD_REQUEST);
            }
            if (!(formattedPhone >= 0)) {
                return new Response("The phone number can't be negative.", Status.BAD_REQUEST);
            }

            try {
                phoneCode = Integer.parseInt(countryPhoneCode);
            } catch (NumberFormatException ex) {
                return new Response("The country phone code can only contain numbers", Status.BAD_REQUEST);
            }
            if (countryPhoneCode.trim().length() < 1 || countryPhoneCode.trim().length() > 3) {
                return new Response("The length of the country phone code must be between 1 and 3 digits", Status.BAD_REQUEST);
            }

            if (!(phoneCode >= 0)) {
                return new Response("The country phone code can't be negative.", Status.BAD_REQUEST);
            }

            if (!storage.add(new Passenger(FormattedId, firstname, lastname, birthDate, phoneCode, formattedPhone, country))) {
                return new Response("There's already a passenger with that Id", Status.BAD_REQUEST);
            }

            return new Response("Succesfully created the passenger with the id: " + id, Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePassenger(String id, String firstname, String lastname, String yearS, String monthS, String dayS, String countryPhoneCode, String phone, String country) {
        try {
            long FormattedId, formattedPhone;
            int year, month, day, phoneCode;
            LocalDate birthDate;
            PassengerStorage storage = PassengerStorage.getInstance();
            if (id.trim().isEmpty() || firstname.trim().equals("") || lastname.trim().equals("") || countryPhoneCode.trim().equals("") || phone.trim().equals("") || country.trim().equals("")) {
                return new Response("No fields can be emtpy", Status.BAD_REQUEST);
            }
            id = id.trim();
            phone = phone.trim();
            countryPhoneCode = countryPhoneCode.trim();
            yearS = yearS.trim();
            try {
                FormattedId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                return new Response("Id hast to be a natural number.", Status.BAD_REQUEST);
            }
            if (id.length() < 1 || id.length() > 15 || !(FormattedId >= 0)) {
                return new Response("Id has to have 1 to 15 digits.", Status.BAD_REQUEST);
            }

            try {
                year = Integer.parseInt(yearS);
                month = Integer.parseInt(monthS);
                day = Integer.parseInt(dayS);

            } catch (NumberFormatException ex) {
                return new Response("Invalid birthday format", Status.BAD_REQUEST);
            }

            if (!(year >= 0)) {
                return new Response("The birth year can't be negative", Status.BAD_REQUEST);
            }

            birthDate = LocalDate.of(year, month, day);
            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birth date can't be in the future.", Status.INTERNAL_SERVER_ERROR);
            }

            try {
                formattedPhone = Long.parseLong(phone);
            } catch (NumberFormatException ex) {
                return new Response("The phone number can only contain numbers", Status.BAD_REQUEST);
            }
            if (phone.length() < 1 || phone.length() > 11) {
                return new Response("Digits of the phone number have to be from 1 to 11.", Status.BAD_REQUEST);
            }
            if (!(formattedPhone >= 0)) {
                return new Response("The phone number can't be negative.", Status.BAD_REQUEST);
            }

            try {
                phoneCode = Integer.parseInt(countryPhoneCode);
            } catch (NumberFormatException ex) {
                return new Response("The country phone code can only contain numbers", Status.BAD_REQUEST);
            }
            if (countryPhoneCode.trim().length() < 1 || countryPhoneCode.trim().length() > 3) {
                return new Response("The length of the country phone code must be between 1 and 3 digits", Status.BAD_REQUEST);
            }

            if (!(phoneCode >= 0)) {
                return new Response("The country phone code can't be negative.", Status.BAD_REQUEST);
            }

            if (!storage.update(new Passenger(FormattedId, firstname, lastname, birthDate, phoneCode, formattedPhone, country))) {
                return new Response("There's no passenger with that id", Status.BAD_REQUEST);
            }

            return new Response("Succesfully created the passenger with the id: " + id, Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addToFlight(String sPassengerId, String sFlightId) {
        try {
            sPassengerId = sPassengerId.trim();
            sFlightId = sFlightId.trim();
            if (sPassengerId.isEmpty() || sPassengerId.equals("")) {
                return new Response("The id can't be empty", Status.BAD_REQUEST);
            }
            if (sFlightId.isEmpty() || sFlightId.equals("Flight")) {
                return new Response("No flight has been selected", Status.BAD_REQUEST);
            }

            Passenger passenger = PassengerStorage.getInstance().get(sPassengerId);
            Flight flight = FlightStorage.getInstance().get(sFlightId);

            if (passenger == null) {
                return new Response("Passenger doesn't exist", Status.BAD_REQUEST);
            }
            if (flight == null) {
                return new Response("Flight doesn't exist", Status.BAD_REQUEST);
            }

            if(passenger.getFlights().contains(flight)){
                 return new Response("The passenger is already in the flight.", Status.BAD_REQUEST);
            }
            passenger.addFlight(flight);

            if (flight.getPlane().getMaxCapacity() <= flight.getNumPassengers()) {
                return new Response("The plane can't hold more passengers.", Status.BAD_REQUEST);
            }

            flight.addPassenger(passenger);
            return new Response("The passenger " + passenger.getFullname() + " has been added to the flight " + flight.getId() + ".", Status.OK);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPassengerFlights(String passengerId) {
        PassengerStorage passengerStorage = PassengerStorage.getInstance();
        FlightStorage flightStorage = FlightStorage.getInstance();
        try {
            Passenger passenger = passengerStorage.get(passengerId);
            if (passenger == null) {
                return new Response("Passenger not found with ID: " + passengerId, Status.BAD_REQUEST);
            }
            ArrayList<ArrayList<String>> flightsDataForTable = new ArrayList<>();
            ArrayList<Flight> passengerFlights = passenger.getFlights();
            if (passengerFlights != null) {
                for (Flight flight : passengerFlights) {
                    ArrayList<String> singleFlightRow = new ArrayList<>();
                    singleFlightRow.add(flight.getId());
                    singleFlightRow.add(flight.getDepartureDate().format(DateTimeFormatter.ISO_DATE));
                    LocalDateTime arrivalTime;
                    singleFlightRow.add(flight.calculateArrivalDate().format(DateTimeFormatter.ISO_DATE));
                    flightsDataForTable.add(singleFlightRow);
                }
            }

            return new Response("Successfully retrieved flights for passenger " + passengerId, Status.OK, flightsDataForTable);

        } catch (Exception e) {
            return new Response("An internal Error has occured. ", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response loadUsers() {
        ArrayList<String> ids = new ArrayList<>();
        for (Passenger ps : PassengerStorage.getInstance().getArray()) {
            ids.add(String.valueOf(ps.getId()));
        }
        return new Response("", Status.OK, ids);
    }

    public static Response getPassengerInfo() {
        PassengerStorage passengerStorage = PassengerStorage.getInstance();

        try {
            ArrayList<Passenger> allPassengers = passengerStorage.getArray();
            ArrayList<ArrayList<String>> passengersDataForTable = new ArrayList<>();

            for (Passenger passenger : allPassengers) {
                ArrayList<String> singlePassengerRow = new ArrayList<>();

                singlePassengerRow.add(String.valueOf(passenger.getId()));
                singlePassengerRow.add(passenger.getFullname());
                singlePassengerRow.add(passenger.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE));
                singlePassengerRow.add(String.valueOf(passenger.calculateAge()));
                singlePassengerRow.add(passenger.generateFullPhone());
                singlePassengerRow.add(passenger.getCountry());
                singlePassengerRow.add(String.valueOf(passenger.getNumFlights()));

                passengersDataForTable.add(singlePassengerRow);
            }

            return new Response("Successfully retrieved all passenger data.", Status.OK, passengersDataForTable);

        } catch (Exception e) {
            e.printStackTrace();
            return new Response("An error occurred while retrieving passenger data: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
