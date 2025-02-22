package com.keyin.Sprint1_API.Aircraft;

import com.keyin.Sprint1_API.Airport.Airport;
import com.keyin.Sprint1_API.Airport.AirportService;
import com.keyin.Sprint1_API.Passenger.Passenger;
import com.keyin.Sprint1_API.Passenger.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AircraftService {

    @Autowired
    private AirportService airportService;

    private int nextId = 1;
    private final List<Aircraft> aircraftList = new ArrayList<>();

    public List<Aircraft> getAllAircraft() {
        return aircraftList;
    }

    public Aircraft getAircraft(Integer index) {
        if (index >= 0 && index < aircraftList.size()) {
            return aircraftList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Aircraft getAircraftByAircraftId(int aircraftId) {
        if (aircraftId >= 1 && aircraftId <= aircraftList.size()) {
            return aircraftList.get(aircraftId - 1);
        } else {
            throw new NoSuchElementException("Aircraft ID " + aircraftId + " was not found.");
        }
    }

    public Aircraft createAircraft(Aircraft newAircraft, PassengerService passengerService) {
        for (Aircraft aircraft : aircraftList) {
            if (aircraft.getAircraftType().equalsIgnoreCase(newAircraft.getAircraftType()) &&
                    aircraft.getAirlineName().equals(newAircraft.getAirlineName())) {
                return aircraft;
            }
        }
        List<Airport> airportList = new ArrayList<>();
        for (Airport airport : newAircraft.getAirports()) {
            Airport newAirport = checkAirport(airport);
            airportList.add(newAirport);
        }
        newAircraft.setAirports(airportList);

        List<Passenger> passengerList = new ArrayList<>();
        for (Passenger passenger : newAircraft.getPassengers()) {
            Passenger newPassenger = checkPassenger(passenger, passengerService);
            passengerList.add(newPassenger);
        }
        newAircraft.setPassengers(passengerList);

        Aircraft aircraftToCreate = new Aircraft(nextId++, newAircraft.getAircraftType(), newAircraft.getAirlineName(), newAircraft.getNumPassengers(), newAircraft.getAirports(), newAircraft.getPassengers());
        aircraftList.add(aircraftToCreate);
        return aircraftToCreate;
    }

    private Passenger checkPassenger(Passenger passenger, PassengerService passengerService) {
        if (passenger.getPassenger_id() != null && passenger.getPassenger_id() > 0) {
            Passenger existingPassenger = passengerService.getPassengerByPassengerId(passenger.getPassenger_id());
            if (existingPassenger != null) {
                return existingPassenger;
            }
        }
        return passengerService.createPassenger(passenger);
    }

    public Aircraft addAirportToAircraft(int aircraftId, Airport airport) {
        if (aircraftId >= 1 && aircraftId <= aircraftList.size()) {
            for (Aircraft aircraft : aircraftList) {
                if (aircraft.getAircraft_id() == aircraftId) {
                    Airport airportToAdd = checkAirport(airport);
                    if (!inAircraftAirportList(aircraft.getAirports(), airportToAdd)) {
                        aircraft.getAirports().add(airportToAdd);
                        return aircraft;
                    }
                }
            }
        }
        throw new NoSuchElementException("An aircraft with that ID was not found.");
    }

    private boolean inAircraftAirportList(List<Airport> aircraftAirportList, Airport airport) {
        for (Airport aircraftAirport : aircraftAirportList) {
            if (airport.getCode().equalsIgnoreCase(aircraftAirport.getCode())) {
                return true;
            }
        }
        return false;
    }

    private Airport checkAirport(Airport airport) {
        Airport existingAirport = null;
        if (airport.getAirport_id() > 0) {
            existingAirport = airportService.getAirportByAirportId(airport.getAirport_id());
        } else {
            existingAirport = airportService.getAirportByCode(airport.getCode());
        }
        if (existingAirport != null) {
            return existingAirport;
        } else {
            return airportService.createAirport(airport);
        }
    }

    public List<Airport> getAllAirportsUsedBySpecificAircraft(Integer index) {
        if (index >= 0 && index < aircraftList.size()) {
            return aircraftList.get(index).getAirports();
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public List<Airport> getAllAirportsUsedBySpecificAircraftId(Integer aircraftId) {
        if (aircraftId >= 1 && aircraftId <= aircraftList.size()) {
            return aircraftList.get(aircraftId - 1).getAirports();
        } else {
            throw new NoSuchElementException("Aircraft ID " + aircraftId + " was not found.");
        }
    }

    public Aircraft updateAircraft(Integer index, Aircraft updatedAircraft, PassengerService passengerService) {
        Aircraft aircraftToUpdate = aircraftList.get(index);
        aircraftToUpdate.setAircraftType(updatedAircraft.getAircraftType());
        aircraftToUpdate.setAirlineName(updatedAircraft.getAirlineName());
        aircraftToUpdate.setNumPassengers(updatedAircraft.getNumPassengers());
        if (updatedAircraft.getAirports() != null) {
            List<Airport> updatedAirports = new ArrayList<>();
            for (Airport airport : updatedAircraft.getAirports()) {
                updatedAirports.add(checkAirport(airport));
            }
            aircraftToUpdate.setAirports(updatedAirports);
        }
        return aircraftToUpdate;
    }

    public Aircraft updateAircraftByAircraftId(Integer aircraftId, Aircraft updatedAircraft, PassengerService passengerService) {
        Aircraft aircraftToUpdate = aircraftList.get(aircraftId - 1);
        aircraftToUpdate.setAircraftType(updatedAircraft.getAircraftType());
        aircraftToUpdate.setAirlineName(updatedAircraft.getAirlineName());
        aircraftToUpdate.setNumPassengers(updatedAircraft.getNumPassengers());
        if (updatedAircraft.getAirports() != null) {
            List<Airport> updatedAirports = new ArrayList<>();
            for (Airport airport : updatedAircraft.getAirports()) {
                updatedAirports.add(checkAirport(airport));
            }
            aircraftToUpdate.setAirports(updatedAirports);
        }
        return aircraftToUpdate;
    }

    public Aircraft deleteAircraft(Integer index) {
        if (index >= 0 && index < aircraftList.size()) {
            Aircraft aircraftToRemove = aircraftList.get(index);
            aircraftList.remove(aircraftToRemove);
            return aircraftToRemove;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Aircraft deleteAircraftByAircraftId(Integer aircraftId) {
        if (aircraftId >= 1 && aircraftId <= aircraftList.size()) {
            Aircraft aircraftToRemove = aircraftList.get(aircraftId - 1);
            aircraftList.remove(aircraftToRemove);
            return aircraftToRemove;
        } else {
            throw new NoSuchElementException("Aircraft ID " + aircraftId + " was not found.");
        }
    }

    public void removeAirportFromAllAircraftAirportList(Airport airportToRemove) {
        for (Aircraft aircraft : aircraftList) {
            List<Airport> airports = aircraft.getAirports();
            List<Airport> updatedAirports = new ArrayList<>();
            for (Airport airport : airports) {
                if (airport.getAirport_id() != airportToRemove.getAirport_id()) {
                    updatedAirports.add(airport);
                }
            }
            aircraft.setAirports(updatedAirports);
        }
    }

    public Aircraft addPassengerToAircraft(Integer aircraftId, Passenger passenger, PassengerService passengerService) {
        if (aircraftId >= 1 && aircraftId <= aircraftList.size()) {
            for (Aircraft aircraft : aircraftList) {
                if (aircraft.getAircraft_id() == aircraftId) {
                    Passenger passengerToAdd = checkPassenger(passenger, passengerService);
                    if (!inAircraftPassengerList(aircraft.getPassengers(), passengerToAdd)) {
                        aircraft.getPassengers().add(passengerToAdd);
                        return aircraft;
                    } else {
                        throw new RuntimeException("Passenger already in list of passengers on aircraft.");
                    }
                }
            }
        }
        throw new NoSuchElementException(" An aircraft with that ID was not found.");
    }

    private boolean inAircraftPassengerList(List<Passenger> aircraftPassengerList, Passenger passenger) {
        for (Passenger aircraftPassenger : aircraftPassengerList) {
            if (passenger.getPassenger_id().equals(aircraftPassenger.getPassenger_id())) {
                return true;
            }
        }
        return false;
    }

    public Aircraft deletePassengerFromAircraft(Integer passengerId, Integer aircraftId) {
        for (Aircraft aircraft : aircraftList) {
            if (aircraft.getAircraft_id() == aircraftId) {
                for (Passenger passenger : aircraft.getPassengers()) {
                    if (passenger.getPassenger_id().equals(passengerId)) {
                        aircraft.getPassengers().remove(passenger);
                        return aircraft;
                    }
                }
            }
        }
        throw new NoSuchElementException("Either no such aircraft ID or No such Passenger ID in aircraft.");
    }
}
