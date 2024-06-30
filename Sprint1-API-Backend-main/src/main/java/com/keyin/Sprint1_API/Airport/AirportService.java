package com.keyin.Sprint1_API.Airport;

import com.keyin.Sprint1_API.Aircraft.AircraftService;
import com.keyin.Sprint1_API.City.City;
import com.keyin.Sprint1_API.City.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AirportService {

    private int nextId = 1;

    @Autowired
    private CityService cityService;

    @Autowired
    @Lazy
    private AircraftService aircraftService;

    private final List<Airport> airportList = new ArrayList<>();

    public Airport getAirport(int index) {
        if (index >= 0 && index < airportList.size()) {
            return airportList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Airport getAirportByAirportId(int airportId) {
        if (airportId >= 1 && airportId <= airportList.size()) {
            return airportList.get(airportId - 1);
        } else {
            throw new NoSuchElementException("Airport ID " + airportId + " was not found.");
        }
    }

    public Airport createAirport(Airport newAirport) {
        for (Airport airport : airportList) {
            if (airport.getCode().equalsIgnoreCase(newAirport.getCode())) {
                return airport;
            }
        }
        City city = cityService.createCity(newAirport.getCity());
        Airport airportToCreate = new Airport(nextId++, newAirport.getName(), newAirport.getCode(), city);
        airportList.add(airportToCreate);
        return airportToCreate;
    }

    public List<Airport> getAirportsByCityName(String cityName) {
        List<Airport> airportsInCity = new ArrayList<>();
        for (Airport airport : airportList) {
            if (airport.getCity().getName().equalsIgnoreCase(cityName)) {
                airportsInCity.add(airport);
            }
        }
        return airportsInCity;
    }

    public List<Airport> getAllAirports() {
        return airportList;
    }

    public Airport updateAirportByIndex(int index, Airport updatedAirport) {
        if (index >= 0 && index < airportList.size()) {
            Airport airportToUpdate = airportList.get(index);
            airportToUpdate.setName(updatedAirport.getName());
            airportToUpdate.setCode(updatedAirport.getCode());
            City city = cityService.createCity(updatedAirport.getCity());
            airportToUpdate.setCity(city);
            return airportToUpdate;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Airport updateAirportByAirportId(int airportId, Airport updatedAirport) {
        if (airportId >= 1 && airportId <= airportList.size()) {
            Airport airportToUpdate = airportList.get(airportId - 1);
            airportToUpdate.setName(updatedAirport.getName());
            airportToUpdate.setCode(updatedAirport.getCode());
            City city = cityService.createCity(updatedAirport.getCity());
            airportToUpdate.setCity(city);
            return airportToUpdate;
        } else {
            throw new NoSuchElementException("Airport ID " + airportId + " was not found.");
        }
    }

    public Airport deleteAirportByIndex(int index) {
        if (index >= 0 && index < airportList.size()) {
            Airport airportToRemove = airportList.remove(index);
            aircraftService.removeAirportFromAllAircraftAirportList(airportToRemove);
            return airportToRemove;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public Airport deleteAirportByAirportId(int airportId) {
        if (airportId >= 1 && airportId <= airportList.size()) {
            Airport airportToRemove = airportList.remove(airportId - 1);
            aircraftService.removeAirportFromAllAircraftAirportList(airportToRemove);
            return airportToRemove;
        } else {
            throw new NoSuchElementException("Airport ID " + airportId + " was not found.");
        }
    }

    public Airport getAirportByCode(String code) {
        for (Airport airport : airportList) {
            if (airport.getCode().equalsIgnoreCase(code)) {
                return airport;
            }
        }
        return null;
    }
}
