package com.supersapiens.athlete.controller;

import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_CREATED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_UPDATED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_WITH_ID_D_DELETED_SUCCESSFULLY;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ApiMessages.ATHLETE_WITH_ID_D_DOES_NOT_EXIST;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supersapiens.athlete.exception.AthleteNotFoundException;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.service.AthleteService;


@RestController
public class AthleteController {

    @Autowired
    private AthleteService service;


    @RequestMapping(value= "/athlete/add", method= RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createAthlete(@Valid @RequestBody Athlete athlete) {
     	athlete = service.saveOrUpdateAthlete(athlete);
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	responseMap.put("message", ATHLETE_CREATED_SUCCESSFULLY);
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
    	responseMap.put("message", ATHLETE_UPDATED_SUCCESSFULLY);
    	responseMap.put("athlete",athlete );
    	
    	return ResponseEntity.ok(responseMap);
    }

    @RequestMapping(value= "/athlete/{id}", method= RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Athlete>  getAthleteById(@PathVariable int id) throws Exception {
     	return ResponseEntity.ok(service.getAthlete(id));
    }
    
    @RequestMapping(value= "/athlete/delete/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAthleteById(@PathVariable int id) throws Exception {
    	if(service.getAthlete(id) ==null) {
    		throw new AthleteNotFoundException(String.format(ATHLETE_WITH_ID_D_DOES_NOT_EXIST, id));
    	}
        service.deleteAthlete(id);
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	responseMap.put("message", String.format(ATHLETE_WITH_ID_D_DELETED_SUCCESSFULLY, id));
    	return ResponseEntity.ok(responseMap);
    }
    
    // for automatic validation using @Valid with @RequestBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
