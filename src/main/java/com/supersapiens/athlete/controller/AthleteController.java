package com.supersapiens.athlete.controller;

import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AthleteController {

    @Autowired
    private AthleteService service;



    @RequestMapping(value= "/athlete/add", method= RequestMethod.POST)
    public Athlete createAthlete(@RequestBody Athlete athlete) {
        // TODO
        return null;
    }

    @RequestMapping(value= "/athlete/update/{id}", method= RequestMethod.PUT)
    public Athlete updateAthlete(@RequestBody Athlete athlete, @PathVariable int id) throws Exception {
        // TODO
        return null;
    }

    @RequestMapping(value= "/athlete/{id}", method= RequestMethod.GET)
    public Athlete getAthleteById(@PathVariable int id) throws Exception {
        // TODO
        return null;
    }
    @RequestMapping(value= "/athlete/delete/{id}", method= RequestMethod.DELETE)
    public void deleteAthleteById(@PathVariable int id) throws Exception {
        // TODO
    }

}
