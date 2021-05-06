package com.supersapiens.athlete.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supersapiens.athlete.exception.AthleteNotFoundException;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.service.AthleteService;


@RestController
public class AthleteController {

    @Autowired
    private AthleteService service;


    @RequestMapping(value= "/athlete/add", method= RequestMethod.POST)
    public ResponseEntity<Object> createAthlete(@RequestBody Athlete athlete) {
     	athlete = service.saveOrUpdateAthlete(athlete);
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	responseMap.put("message", "Athlete created successfully");
    	responseMap.put("athlete",athlete );
    	
    	return ResponseEntity.ok(responseMap);
    	
    }

    @RequestMapping(value= "/athlete/update/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Object> updateAthlete(@RequestBody Athlete athlete, @PathVariable int id) throws Exception {
     	Athlete existingAthlete = service.getAthlete(id);
    	
    	Athlete athleteToUpdate = existingAthlete.toBuilder()
    	.id(Long.valueOf(id))
    	.firstName(athlete.getFirstName())
    	.lastName(athlete.getLastName())
    	.primarySport(athlete.getPrimarySport())
    	.secondarySport(athlete.getSecondarySport()).build();
    	
    	
    	athlete = service.saveOrUpdateAthlete(athleteToUpdate);
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	responseMap.put("message", "Athlete updated successfully");
    	responseMap.put("athlete",athlete );
    	
    	return ResponseEntity.ok(responseMap);
    }

    @RequestMapping(value= "/athlete/{id}", method= RequestMethod.GET)
    public ResponseEntity<Athlete>  getAthleteById(@PathVariable int id) throws Exception {
     	return ResponseEntity.ok(service.getAthlete(id));
    }
    
    @RequestMapping(value= "/athlete/delete/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAthleteById(@PathVariable int id) throws Exception {
    	if(service.getAthlete(id) ==null) {
    		throw new AthleteNotFoundException(String.format("Athlete with id %d does not exist", id));
    	}
        service.deleteAthlete(id);
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	responseMap.put("message", String.format("Athlete with id %d deleted successfully", id));
    	return ResponseEntity.ok(responseMap);
    }

}
