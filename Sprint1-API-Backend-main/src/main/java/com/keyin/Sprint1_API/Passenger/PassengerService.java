package com.keyin.Sprint1_API.Passenger;

import com.keyin.Sprint1_API.Aircraft.Aircraft;
import com.keyin.Sprint1_API.Aircraft.AircraftService;
import com.keyin.Sprint1_API.Airport.Airport;
import com.keyin.Sprint1_API.Airport.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PassengerService {

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private AirportService airportService;

    private Long nextId = 1L;
    private final List<Passenger> passengerList = new ArrayList<>();

    public Passenger createPassenger(Passenger newPassenger) {
        newPassenger.setPassenger_id(nextId++);
        passengerList.add(newPassenger);
        return newPassenger;
    }

    public List<Passenger> getAllPassengers() {
        return passengerList;
    }

    public Passenger getPassengerByIndex(Integer index) {
        if (index >= 0 && index < passengerList.size()) {
            return passengerList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Passenger getPassengerByPassengerId(Long passengerId) {
        return passengerList.stream()
                .filter(passenger -> passenger.getPassenger_id().equals(passengerId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Passenger ID " + passengerId + " was not found."));
    }

    public Passenger updatePassengerByPassengerId(Long passengerId, Passenger updatedPassenger) {
        Passenger passengerToUpdate = getPassengerByPassengerId(passengerId);
        passengerToUpdate.setFirstName(updatedPassenger.getFirstName());
        passengerToUpdate.setLastName(updatedPassenger.getLastName());
        passengerToUpdate.setPhoneNumber(updatedPassenger.getPhoneNumber());
        passengerToUpdate.setCity(updatedPassenger.getCity());
        return passengerToUpdate;
    }

    public void deletePassengerByPassengerId(Long passengerId) {
        Passenger passengerToDelete = getPassengerByPassengerId(passengerId);
        passengerList.remove(passengerToDelete);
    }

    public List<Aircraft> getAllAircraftAPassengerHasTravelledOn(Long passengerId) {
        Passenger passenger = getPassengerByPassengerId(passengerId);
        List<Aircraft> aircraftList = new ArrayList<>();
        for (Aircraft aircraft : aircraftService.getAllAircraft()) {
            if (aircraft.getPassengers().contains(passenger)) {
                aircraftList.add(aircraft);
            }
        }
        return aircraftList;
    }

    public List<Airport> getAllAirportsAPassengerHasUsed(Long passengerId) {
        Passenger passenger = getPassengerByPassengerId(passengerId);
        List<Airport> airportList = new ArrayList<>();
        for (Aircraft aircraft : aircraftService.getAllAircraft()) {
            if (aircraft.getPassengers().contains(passenger)) {
                airportList.addAll(aircraft.getAirports());
            }
        }
        return airportList;
    }
}
