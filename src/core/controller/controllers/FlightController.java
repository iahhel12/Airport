/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller.controllers;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.FlightStorage;
import core.models.storage.LocationStorage;
import core.models.storage.PlaneStorage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 *
 * @author Iahhel
 */
public class FlightController {

    public static Response createfligth(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String yearStr, String monthStr, String dayStr, String hourStr, String minutesStr, String hoursDurationsArrivalStr, String minutesDurationsArrivalStr, String hoursDurationsScaleStr, String minutesDurationsScaleStr) {
        int year, month, day, hour, minutes;

        if (id == null || id.trim().isEmpty()) {
            return new Response("Flight ID can't be empty", Status.BAD_REQUEST);
        }
        id = id.trim();
        if (!id.matches("[A-Z]{3}\\d{3}")) {
            return new Response(" An ID must consist of three uppercase letters followed by three digits, such as \"ABC123.\"", Status.BAD_REQUEST);
        }

        try {
            year = Integer.parseInt(yearStr.trim());
            month = Integer.parseInt(monthStr.trim());
            day = Integer.parseInt(dayStr.trim());
            hour = Integer.parseInt(hourStr.trim());
            minutes = Integer.parseInt(minutesStr.trim());
        } catch (NumberFormatException e) {
            return new Response("Invalid date received.", Status.BAD_REQUEST);
        }

        int hoursDurationsArrival, minutesDurationsArrival;
        int hoursDurationsScale = 0;
        int minutesDurationsScale = 0;
        Plane plane = null;

        try {
            hoursDurationsArrival = Integer.parseInt(hoursDurationsArrivalStr.trim());
            minutesDurationsArrival = Integer.parseInt(minutesDurationsArrivalStr.trim());
        } catch (NumberFormatException e) {
            return new Response("Duration is invalid.", Status.BAD_REQUEST);
        }

        String scaleLocation = scaleLocationId.trim();

        if (scaleLocationId.trim().isEmpty() || !scaleLocation.equals("Location")) {
            try {
                hoursDurationsScale = Integer.parseInt(hoursDurationsScaleStr.trim());
                minutesDurationsScale = Integer.parseInt(minutesDurationsScaleStr.trim());
            } catch (NumberFormatException e) {
                return new Response("Invalid number recieved.", Status.BAD_REQUEST);
            }
        }

        FlightStorage flightStorage = FlightStorage.getInstance();
        plane = PlaneStorage.getInstance().get(planeId);

        Location departure = null;
        Location arrival = null;
        Location scale = null;

        departure = LocationStorage.getInstance().get(departureLocationId);
        arrival = LocationStorage.getInstance().get(arrivalLocationId);;
        if (!scaleLocation.equals("Location")) {
            scale = LocationStorage.getInstance().get(scaleLocation);
        }

        if (plane == null) {
            return new Response("Plane  " + planeId + " not found.", Status.BAD_REQUEST);
        }
        if (departure == null) {
            return new Response("Departure location " + departureLocationId + " not found.", Status.BAD_REQUEST);
        }
        if (arrival == null) {
            return new Response("Arrival location  " + arrivalLocationId + " not found.", Status.BAD_REQUEST);
        }
        if (!scaleLocation.equals("Location") && scale == null) {
            return new Response("Scale location  " + scaleLocationId + " not found.", Status.BAD_REQUEST);
        }

        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.of(year, month, day, hour, minutes);
        } catch (DateTimeParseException e) {
            return new Response("The departure date isn't valid.", Status.BAD_REQUEST);
        } catch (Exception e) {
            return new Response("Internal Error", Status.INTERNAL_SERVER_ERROR);
        }

        if (departureDate.isBefore(LocalDateTime.now())) {
            return new Response("Departure date must be in the future", Status.BAD_REQUEST);
        }

        if (hoursDurationsArrival <= 0 && minutesDurationsArrival <= 0) {
            return new Response("Arrival duration must be greater than 00:00", Status.BAD_REQUEST);
        }
        if (!scaleLocation.equals("Location") && (hoursDurationsScale <= 0 && minutesDurationsScale <= 0)) {
            return new Response("The scale duration must be greater than 00:00", Status.BAD_REQUEST);
        }

        if (FlightStorage.getInstance().get(id) != null) {
            return new Response("Flight ID already exists", Status.BAD_REQUEST);
        }

        LocalDateTime scheduleConflict = departureDate;
        long totalHours = hoursDurationsArrival + (!scaleLocation.equals("Location") ? hoursDurationsScale : 0);
        long totalMinutes = minutesDurationsArrival + (!scaleLocation.equals("Location") ? minutesDurationsScale : 0);
        LocalDateTime newEnd = departureDate.plusHours(totalHours).plusMinutes(totalMinutes);

        for (Flight existingFlight : plane.getFlights()) {
            LocalDateTime existingStart = existingFlight.getDepartureDate();
            LocalDateTime existingEnd = existingFlight.calculateArrivalDate();

            boolean overlap = scheduleConflict.isBefore(existingEnd) && existingStart.isBefore(newEnd);
            if (overlap) {
                return new Response("This plane has a schedule overlap. Flight not added.", Status.BAD_REQUEST);
            }
        }

        Flight newFlight;
        if (scale == null) {
            newFlight = new Flight(id, plane, departure, arrival, departureDate, hoursDurationsArrival, minutesDurationsArrival);
        } else {
            newFlight = new Flight(id, plane, departure, scale, arrival, departureDate, hoursDurationsArrival, minutesDurationsArrival, hoursDurationsScale, minutesDurationsScale);
        }
        newFlight.getPlane().addFlight(newFlight);
        flightStorage.add(newFlight);

        return new Response("Success: Flight with ID '" + id + "' has been created successfully.", Status.OK);
    }

    public static Response loadFlights() {
        ArrayList<String> ids = new ArrayList<>();
        for (Flight ps : FlightStorage.getInstance().getArray()) {
            ids.add(String.valueOf(ps.getId()));
        }
        return new Response("", Status.OK, ids);
    }

    public static Response getAllFlights() {
        FlightStorage flightStorage = FlightStorage.getInstance();

        try {
            ArrayList<Flight> allFlights = flightStorage.getArray();
            ArrayList<ArrayList<String>> flightsDataForTable = new ArrayList<>();

            for (Flight flight : allFlights) {
                ArrayList<String> singleFlightRow = new ArrayList<>();

                singleFlightRow.add(flight.getId());
                singleFlightRow.add(flight.getDepartureLocation().getAirportId());

                // FIX: Check for null arrivalLocation
                if (flight.getArrivalLocation() != null) {
                    singleFlightRow.add(flight.getArrivalLocation().getAirportId());
                } else {
                    singleFlightRow.add("UNKNOWN_ARRIVAL"); // Placeholder if location is null
                }
                if (flight.getScaleLocation() != null) {
                    singleFlightRow.add(flight.getScaleLocation().getAirportId());
                } else {
                    singleFlightRow.add("N/A");
                }
                singleFlightRow.add(flight.getDepartureDate().format(DateTimeFormatter.ISO_DATE));
                singleFlightRow.add(flight.calculateArrivalDate().format(DateTimeFormatter.ISO_DATE));
                singleFlightRow.add(flight.getPlane().getId());
                singleFlightRow.add(String.valueOf(flight.getNumPassengers()));

                flightsDataForTable.add(singleFlightRow);
            }

            return new Response("Successfully retrieved all flight data.", Status.OK, flightsDataForTable);

        } catch (Exception e) {
            return new Response("An internal error has occured", Status.INTERNAL_SERVER_ERROR, null);
        }
    }

    public static Response DelayFlight(String flightId, String hours, String minutes) {
        try {
            flightId = flightId.trim();
            hours = hours.trim();
            minutes = minutes.trim();
            if (flightId.isEmpty() || flightId.equals("Flight")) {
                return new Response("No flight has been selected", Status.BAD_REQUEST);
            }

            Flight flight = FlightStorage.getInstance().get(flightId);

            if (flight == null) {
                return new Response("Flight doesn't exist", Status.BAD_REQUEST);
            }

            if ((hours.isEmpty() || hours.equals("Hour")) && (minutes.isEmpty() || minutes.equals("Minute"))) {
                return new Response("Invalid time", Status.BAD_REQUEST);
            }
            
            int hour, minute;
            try {
                hour = Integer.parseInt(hours);
                minute = Integer.parseInt(minutes);
            } catch (NumberFormatException e) {
                 return new Response("Invalid time", Status.BAD_REQUEST);
            }

            flight.delay(hour, minute);
            return new Response("Flight delayed", Status.OK);
        } catch (Exception e) {
            return new Response("An internal error has occured", Status.INTERNAL_SERVER_ERROR);
        }

    }
}
