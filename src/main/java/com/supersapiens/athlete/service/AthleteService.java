package com.supersapiens.athlete.service;

import com.supersapiens.athlete.exception.AthleteNotFoundException;
import com.supersapiens.athlete.model.Athlete;
import com.supersapiens.athlete.repository.AthleteInMemoryRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AthleteService {
    @Autowired
    private AthleteInMemoryRepository repository;

	public Athlete saveOrUpdateAthlete(Athlete athlete) {
		// TODO Auto-generated method stub
		if(athlete==null) {
			throw new IllegalArgumentException("athlete can not be null");
		}
		return repository.save(athlete);
	}


	public void deleteAthlete(int id) {
		if(id<=0) {
			throw new IllegalArgumentException("Id must be greater than 1");
		}
		// TODO Auto-generated method stub
		repository.deleteById(Long.valueOf(id));
	}

	public Athlete getAthlete(int id) {
		if(id<=0) {
			throw new IllegalArgumentException("Id must be greater than 1");
		}
		// TODO Auto-generated method stub
		return repository.findById(Long.valueOf(id)).orElseThrow(() -> new AthleteNotFoundException("Athlete Not Found"));
	}

    // TODO: Use AthleteInMemoryRepository
}
