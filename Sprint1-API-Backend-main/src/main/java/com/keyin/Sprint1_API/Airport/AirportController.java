package com.keyin.Sprint1_API.Airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("airports")
    public List<Airport> getAllAirports(){
        return airportService.getAllAirports();
    }

    @GetMapping("airport/{index}")
    public Airport getAirport(@PathVariable Integer index){
        return airportService.getAirport(index);
    }

    @GetMapping("airport/id/{airport_id}")
    public Airport getAirportByAirportId(@PathVariable Integer airport_id){
        return airportService.getAirportByAirportId(airport_id);
    }

    @GetMapping("airports/city/{cityName}")
    public List<Airport> getAirportsByCityName(@PathVariable String cityName) {
        return airportService.getAirportsByCityName(cityName);
    }

    @PostMapping("airport")
    public Airport createAirport(@RequestBody Airport newAirport){
        return airportService.createAirport(newAirport);
    }

    @PutMapping("airport/{index}")
    public Airport updateAirportByIndex(@PathVariable Integer index, @RequestBody Airport updatedAirport){
        return airportService.updateAirportByIndex(index, updatedAirport);
    }

    @PutMapping("airport/id/{airport_id}")
    public Airport updateAirportByAirportId(@PathVariable Integer airport_id, @RequestBody Airport updatedAirport){
        return airportService.updateAirportByAirportId(airport_id,updatedAirport);
    }

    @DeleteMapping("airport/{index}")
    public Airport deleteAirportByIndex(@PathVariable Integer index){
        return airportService.deleteAirportByIndex(index);
    }

    @DeleteMapping("airport/id/{airport_id}")
    public Airport deleteAirportByAirportId(@PathVariable Integer airport_id){
        return airportService.deleteAirportByAirportId(airport_id);
    }

}